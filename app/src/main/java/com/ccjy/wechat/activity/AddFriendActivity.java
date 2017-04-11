package com.ccjy.wechat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.ccjy.wechat.R;
import com.ccjy.wechat.adpater.AddFriendAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/4/6.
 * 添加好友页面
 */

public class AddFriendActivity extends BaseActivity implements View.OnClickListener {
    private EditText userID;
    private Button add;
    private ListView listView;
    private List<String> list = new ArrayList<>();
    private List<String> userNames;
    private String userName;  //好友昵称
    private AddFriendAdapter addFriendAdapter;
    public static final String ALL_MESSAGE = "allMessage"; //个人聊天记录
    public static final String FRIEND_USERNAME = "friend_userName"; //好友昵称
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            msg.what = 1;

            //listView item点击事件 点击进入聊天详情页面
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    userName = list.get(position);
                    List<EMMessage> messages = null;
                    try {
                        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userName);
                        messages = conversation.getAllMessages();
                    } catch (Exception e) {
                        toastShow(AddFriendActivity.this, "查询不到信息");
                    }
                    Intent intent = new Intent(AddFriendActivity.this, ChatDetailsActivity.class);
                    intent.putParcelableArrayListExtra(ALL_MESSAGE, (ArrayList<? extends Parcelable>) messages);
                    intent.putExtra(FRIEND_USERNAME, userName);
                    startActivity(intent);

                }
            });
            //listView item长按监听事件 长按删除好友
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    userName = list.get(position);
                    try {
                        EMClient.getInstance().contactManager().deleteContact(userName);


                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        getData();
        initView();
    }

    private void initView() {
        userID = (EditText) findViewById(R.id.add_friend_userID);
        add = (Button) findViewById(R.id.add_friend_add);
        add.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.add_friend_listView);
        addFriendAdapter = new AddFriendAdapter(this, list);
        listView.setAdapter(addFriendAdapter);
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    if (userNames != null) {
                        list.addAll(userNames);
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                if (handler != null) {
                    handler.sendEmptyMessage(1);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        try {
            //获取到输入的好友账号
            String add_friend_userName = userID.getText().toString();
            //添加好友
            EMClient.getInstance().contactManager().addContact(add_friend_userName, null);
            EMClient.getInstance().contactManager().acceptInvitation(add_friend_userName);
            //把好友昵称添加到好友列表中
            list.add(add_friend_userName);
            //刷新好友列表
            addFriendAdapter.notify(list);
            toastShow(this, "添加成功");
        } catch (HyphenateException e) {
            e.printStackTrace();
        }

    }
}
