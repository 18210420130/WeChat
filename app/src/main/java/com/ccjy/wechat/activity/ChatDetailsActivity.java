package com.ccjy.wechat.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ccjy.wechat.R;
import com.ccjy.wechat.adpater.ChatDetailsAdapter;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/29.
 * 消息详情页面
 */

public class ChatDetailsActivity extends BaseActivity implements EMMessageListener, View.OnClickListener, EMCallBack {
    private List<EMMessage> list = new ArrayList<EMMessage>();
    private RecyclerView recyclerView;
    private ChatDetailsAdapter chatDetailsAdapter;
    private EditText content; //发送消息文本框
    private TextView send,picture,video, voice;  //发送按钮
    private TextView title_name;  //显示昵称
    private String userName, groupId;
    private static final String GROUPID = "groupId";
    public static final String USERNAME = "userName";
    private String text;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置actionBar
        setActionBar();
        setContentView(R.layout.activity_chat_details);
        //注册消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        initView();
        //设置title_name
        setTitleName();
    }

    private void setTitleName() {
        //判断是群组会话 还是 个人会话
        if (TextUtils.isEmpty(userName)) {
            title_name.setText(groupId);
        } else {
            title_name.setText(userName);
        }
    }

    private void initView() {
        //获取携带数据
        getExtra();
        //给输入框设置文本监听
        setContentListener();
        recyclerView = (RecyclerView) findViewById(R.id.chat_details_activity_recyclerView);
        content = (EditText) findViewById(R.id.chat_details_activity_content);
        send = (TextView) findViewById(R.id.chat_details_activity_send);
        picture= (TextView) findViewById(R.id.chat_details_picture);
        video= (TextView) findViewById(R.id.chat_details_video);
        voice= (TextView) findViewById(R.id.chat_details_voice);

        send.setOnClickListener(this);
        picture.setOnClickListener(this);
        video.setOnClickListener(this);
        voice.setOnClickListener(this);

        list.clear();
        getData();
        chatDetailsAdapter = new ChatDetailsAdapter(this, list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(chatDetailsAdapter);
    }

    private void setContentListener() {
        if (content!=null) {
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    text = s.toString();
                }
            });
        }
    }

    private void getExtra() {
        Intent intent = getIntent();
        try {
            userName = intent.getStringExtra(USERNAME);
            groupId = intent.getStringExtra(GROUPID);
            text= intent.getStringExtra("text");
            List<EMMessage> messages = intent.getParcelableArrayListExtra(AddFriendActivity.ALL_MESSAGE);
            String username = intent.getStringExtra(AddFriendActivity.FRIEND_USERNAME);
            list.addAll(messages);
            this.userName = username;
        } catch (Exception e) {
            toastShow(ChatDetailsActivity.this, "暂时没有新的聊天记录");
        }
        if (!TextUtils.isEmpty(text)) {
            content.setText(text);
            content.setSelection(content.getText().length());
        }
    }


    //获取会话数据
    private void getData() {
        if (TextUtils.isEmpty(groupId)) {
            EMConversation conversation = EMClient
                    .getInstance()
                    .chatManager()
                    .getConversation(userName);
            if (conversation != null) {
                list = conversation.getAllMessages();
            }
        } else {
            EMConversation conversation = EMClient
                    .getInstance()
                    .chatManager()
                    .getConversation(groupId);
            if (conversation != null) {
                list = conversation.getAllMessages();
            } else {
                list = new ArrayList<EMMessage>();
            }
        }
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //显示自定义的actionBar
        actionBar.setDisplayShowCustomEnabled(true);
        View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.layout_actionbar, null);
        actionBar.setCustomView(actionbarLayout);
        title_name = (TextView) actionbarLayout.findViewById(R.id.message_list_layout_title);
        TextView setting = (TextView) actionbarLayout.findViewById(R.id.message_list_layout_setting);
        title_name.setTextColor(Color.WHITE);
        setting.setTextColor(Color.WHITE);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onCmdMessageReceived(final List<EMMessage> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (EMMessage message : list) {
                    addMsgToList(message);
                }
            }
        });
    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //消除消息监听
        EMClient.getInstance()
                .chatManager()
                .removeMessageListener(this);
    }

    //发送监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
                send();
                break;
            case R.id.chat_details_picture:
                break;
            case R.id.chat_details_video:
                break;
            case R.id.chat_details_voice:
                break;

        }


    }
   //发送方法
    private void send() {
        String str = content.getText().toString();
        try {
            sendTxt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        content.setText("");
        text = "";
        chatDetailsAdapter.notifyDataSetChanged();
        recyclerView.setBottom(recyclerView.getBottom());
    }

    //发送文本消息
    private void sendTxt(String str) {
        EMMessage message;
        //获取到输入框中的内容

        if (TextUtils.isEmpty(userName)) {
            message = EMMessage.createTxtSendMessage(str, groupId);
            message.setChatType(EMMessage.ChatType.GroupChat);
        } else {
            message = EMMessage.createTxtSendMessage(str, userName);
        }
        //消息回调监听
        message.setMessageStatusCallback(this);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        //把消息添加到集合中
        addMsgToList(message);
    }

    private void addMsgToList(EMMessage message) {
        list.add(message);
        chatDetailsAdapter.notifyDataSetChanged();
        //设置recyclerView滑动到最底部
        recyclerView.setBottom(recyclerView.getBottom());
    }

    @Override
    public void onSuccess() {
        toastShow(this, "发送成功");
    }

    @Override
    public void onError(int i, String s) {
        toastShow(this, "发送失败");
    }

    @Override
    public void onProgress(int i, String s) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("text", text);
        intent.putExtra(USERNAME, userName);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
