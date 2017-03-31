package com.ccjy.wechat;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ccjy.wechat.activity.BaseActivity;
import com.ccjy.wechat.activity.ChatDetailsActivity;
import com.ccjy.wechat.fragment.ContactsListFragment;
import com.ccjy.wechat.fragment.MainButtonFragment;
import com.ccjy.wechat.fragment.MessageListFragment;
import com.ccjy.wechat.fragment.MySelfListFragment;
import com.hyphenate.chat.EMClient;

/**
 * 主页面
 */

public class MainActivity extends BaseActivity {
    private TextView main_textView;
    public static SearchView main_searchView; //搜索框
    private FragmentManager fm;
    private FragmentTransaction ft;
    private MainButtonFragment mainButtonFragment; //主页面 导航按钮
    private MessageListFragment messageListFragment; //消息列表页面
    private ContactsListFragment contactsListFragment;//联系人页面
    private MySelfListFragment mySelfListFragment; //设置页面

    public static final String MESSAGE_FRAGMENT = "MESSAGE_FRAGMENT";
    public static final String CONTACTS_FRAGMENT = "CONTACTS_FRAGMENT";
    public static final String MYSELF_FRAGMENT = "MYSELF_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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



    private void initView() {
        main_searchView = (SearchView) findViewById(R.id.main_searchView);

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

    //跳转到聊天详情页面
    public void intent2ChatDetails() {
        Intent intent = new Intent(this, ChatDetailsActivity.class);
        startActivity(intent);
    }


}
