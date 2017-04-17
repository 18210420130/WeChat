package com.ccjy.wechat;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.ccjy.wechat.activity.AddFriendActivity;
import com.ccjy.wechat.activity.BaseActivity;
import com.ccjy.wechat.activity.ChatDetailsActivity;
import com.ccjy.wechat.activity.SetGroupActivity;
import com.ccjy.wechat.fragment.ContactsListFragment;
import com.ccjy.wechat.fragment.MainButtonFragment;
import com.ccjy.wechat.fragment.MessageListFragment;
import com.ccjy.wechat.fragment.MySelfListFragment;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import java.util.HashMap;

/**
 * 主页面
 */

public class MainActivity extends BaseActivity implements EMConnectionListener {
    private TextView main_textView;
    private TextView main_add_friend;
    private TextView connection; //连接监听
    private FragmentManager fm;
    private FragmentTransaction ft;
    private MainButtonFragment mainButtonFragment; //主页面 导航按钮
    private MessageListFragment messageListFragment; //消息列表页面
    private ContactsListFragment contactsListFragment;//联系人页面
    private MySelfListFragment mySelfListFragment; //设置页面
    public static final String MESSAGE_FRAGMENT = "MESSAGE_FRAGMENT";
    public static final String CONTACTS_FRAGMENT = "CONTACTS_FRAGMENT";
    public static final String MYSELF_FRAGMENT = "MYSELF_FRAGMENT";
    private HashMap<String, String> textMap = new HashMap<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EMClient.getInstance().addConnectionListener(this);
        initView();
        initFragment();
        main_textView = (TextView) findViewById(R.id.main_textView);
        main_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().logout(true);
                intent2Login();
                finish();
                Toast.makeText(MainActivity.this, "已成功退出账号", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(this);
    }

    private void initView() {
        connection = (TextView) findViewById(R.id.connection);
        main_add_friend = (TextView) findViewById(R.id.main_add_friend);
        main_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initFragment() {
        mainButtonFragment = new MainButtonFragment();
        messageListFragment = new MessageListFragment();
        contactsListFragment = new ContactsListFragment();
        mySelfListFragment = new MySelfListFragment();
        replace(MESSAGE_FRAGMENT);
    }

    public void replace(String str) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (str) {
            case MESSAGE_FRAGMENT:
                ft.replace(R.id.message_list_fragment, messageListFragment);
                ft.replace(R.id.main_button_fragment, mainButtonFragment);
                break;
            case CONTACTS_FRAGMENT:
                ft.replace(R.id.message_list_fragment, contactsListFragment);
                break;
            case MYSELF_FRAGMENT:
                ft.replace(R.id.message_list_fragment, mySelfListFragment);
                break;
        }
        ft.commit();
    }






    //跳转到消息详情页面
    public void intent2ChatDetails(String userName) {
        Intent intent = new Intent("android.intent.action.CHAT_DETAILS");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(ChatDetailsActivity.USERNAME, userName);
        if (!TextUtils.isEmpty(textMap.get(userName))) {
            intent.putExtra("text", textMap.get(userName));
        }
        startActivityForResult(intent, 1);
    }

    public void intent2SetGroup(){
        Intent intent =new Intent(this, SetGroupActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                try {
                    textMap.put(data.getStringExtra(ChatDetailsActivity.USERNAME), data.getStringExtra("text"));
                    if (TextUtils.isEmpty(data.getStringExtra("text"))) {
                        textMap.remove(data.getStringExtra(ChatDetailsActivity.USERNAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                messageListFragment.setChatText(textMap);
                break;
        }
    }

    @Override
    public void onConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connection.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDisconnected(final int i) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (i == EMError.USER_REMOVED) {
                    // 显示帐号已经被移除
                } else if (i == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                } else {
                    if (NetUtils.hasNetwork(MainActivity.this)) {
                        //连接不到聊天服务器
                    } else {
                        connection.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
