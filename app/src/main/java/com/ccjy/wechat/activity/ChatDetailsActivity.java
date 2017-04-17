package com.ccjy.wechat.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ccjy.wechat.R;
import com.ccjy.wechat.adpater.ChatDetailsAdapter;
import com.ccjy.wechat.fragment.PictureFragment;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by dell on 2017/3/29.
 * 消息详情页面
 */

public class ChatDetailsActivity extends BaseActivity implements EMMessageListener, View.OnClickListener, EMCallBack {
    private List<EMMessage> list = new ArrayList<EMMessage>();
    private RecyclerView recyclerView;
    private ChatDetailsAdapter chatDetailsAdapter;
    private EditText content; //发送消息文本框
    private Button send, picture, video, voice;  //发送按钮
    private String userName, groupId;
    public static final String GROUPID = "groupId";
    public static final String USERNAME = "userName";
    private String text;
    private FragmentManager fragmentManager;
    private PictureFragment pictureFragment;
    private FragmentTransaction ft;
    private static final int OPEN_IMAGE_CAMERA =1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        //注册消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        //获取携带数据
        getExtra();
        initView();
        initFragment();


    }

    private void initView() {
        getData();
        recyclerView = (RecyclerView) findViewById(R.id.chat_details_activity_recyclerView);
        content = (EditText) findViewById(R.id.chat_details_activity_content);
        send = (Button) findViewById(R.id.chat_details_activity_send);
        picture = (Button) findViewById(R.id.chat_details_picture);
        video = (Button) findViewById(R.id.chat_details_video);
        voice = (Button) findViewById(R.id.chat_details_voice);
        send.setOnClickListener(this);
        picture.setOnClickListener(this);
        video.setOnClickListener(this);
        voice.setOnClickListener(this);
        chatDetailsAdapter = new ChatDetailsAdapter(this, list);
        // list.clear();

        //给文本输入框 设置监听
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
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(chatDetailsAdapter);
    }


    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        pictureFragment = new PictureFragment();
        //设置actionBar
        setActionBar();
    }


    private void getExtra() {
        Intent intent = getIntent();
        try {
            userName = intent.getStringExtra(USERNAME);
            groupId = intent.getStringExtra(GROUPID);
            text = intent.getStringExtra("text");
            List<EMMessage> messages = intent.getParcelableArrayListExtra(AddFriendActivity.ALL_MESSAGE);
            String username = intent.getStringExtra(AddFriendActivity.FRIEND_USERNAME);
            list.addAll(messages);
            this.userName = username;
        } catch (Exception e) {
            e.printStackTrace();
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

    //发送监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_details_activity_send:
                send();
                break;
            case R.id.chat_details_picture:
                //判断底部fragment是否添加过  如果有则 删除fragment 反之 添加
                if (pictureFragment.isAdded()) {
                    closeImageFragment();
                } else {
                    openImageFragment();
                }
                break;
            case R.id.chat_details_video://拍照
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用系统相机
                //  MediaStore.ACTION_VIDEO_CAPTURE
                // intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile())
                startActivityForResult(intent,OPEN_IMAGE_CAMERA);

                break;


            case R.id.chat_details_voice:
                break;

        }


    }

    /**
     * 设置ActionBar
     */
    private void setActionBar() {
        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置返回键的图片
            //   actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_chat_details_menu, menu);
//        View view = menu.findItem(R.id.menu_title).getActionView();
//        TextView  tv= (TextView)view.findViewById(R.id.menu_textName);
//        tv.setText(userName);
        if (TextUtils.isEmpty(groupId)) {
            setTitle(userName);
        } else {
            setTitle(groupId);
        }
        return true;
    }


    /**
     * setActionBar所有按钮的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("text", text);
                intent.putExtra("userName", userName);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            case R.id.menu_test1:
                toastShow(ChatDetailsActivity.this, "test1");
                break;
            case R.id.menu_test2:
                toastShow(ChatDetailsActivity.this, "test2");
                break;
            case R.id.menu_test3:
                toastShow(ChatDetailsActivity.this, "test3");
                break;
            case R.id.menu_test4:
                toastShow(ChatDetailsActivity.this, "test4");
                break;
            case R.id.menu_test5:
                toastShow(ChatDetailsActivity.this, "test5");
                break;
        }
        return super.onOptionsItemSelected(item);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    //打开图片fragment页面
    private void openImageFragment() {
        ft = fragmentManager.beginTransaction();
        ft.replace(R.id.chat_details_activity_fragment, pictureFragment);
        ft.addToBackStack("message_btn_fragment");
        ft.commit();
    }

    //关闭图片fragment页面
    private void closeImageFragment() {
        ft = fragmentManager.beginTransaction();
        ft.remove(pictureFragment);
        ft.commit();
        //从fragment的返回栈中移除fragment
        fragmentManager.popBackStackImmediate("message_btn_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

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
        //发送消息
        sendMessage(message);

    }

    private void sendMessage(EMMessage message) {
        //消息回调监听
        message.setMessageStatusCallback(this);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        //把消息添加到集合中
        addMsgToList(message);
    }

    /**
     * 发送图片消息
     *
     * @param imagePath   图片路径
     * @param isThumbnail 是否发送原图
     *                    userName 用户名
     */
    public void sendImageMessage(String imagePath, boolean isThumbnail) {

        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, userName);
        if (message == null) {
            return;
        }
        //发送消息
        sendMessage(message);

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
