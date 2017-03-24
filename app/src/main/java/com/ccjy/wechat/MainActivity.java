package com.ccjy.wechat;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ccjy.wechat.activity.BaseActivity;
import com.ccjy.wechat.activity.LoginActivity;
import com.ccjy.wechat.fragment.ContactsListFragment;
import com.ccjy.wechat.fragment.MainButtonFragment;
import com.ccjy.wechat.fragment.MessageListFragment;
import com.ccjy.wechat.fragment.MySelfListFragment;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * 主页面
 */

public class MainActivity extends BaseActivity {
   private TextView main_textView;
     public static  SearchView main_searchView; //搜索框
    private FragmentManager fm;
    private  FragmentTransaction ft;
    private MainButtonFragment mainButtonFragment; //主页面 导航按钮
    private  MessageListFragment messageListFragment; //消息列表页面
    private  ContactsListFragment contactsListFragment;
   private MySelfListFragment mySelfListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        main_textView= (TextView) findViewById(R.id.main_textView);
        main_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().logout(true);
                intentTo(MainActivity.this,new Intent(MainActivity.this, LoginActivity.class));
                Toast.makeText(MainActivity.this,"已成功退出账号",Toast.LENGTH_SHORT).show();
            }
        });

    }
   private void initView(){
       main_searchView= (SearchView) findViewById(R.id.main_searchView);

   }
    private void initFragment(){
      replace(0);


    }
    public void replace(int i){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        mainButtonFragment =new MainButtonFragment();
        messageListFragment=new MessageListFragment();
        contactsListFragment =new ContactsListFragment();
        mySelfListFragment =new MySelfListFragment();
        switch (i){
            case 0:
                ft.replace(R.id.message_list_fragment,messageListFragment);
                ft.replace(R.id.main_button_fragment,mainButtonFragment);
                break;
            case 1:
                ft.replace(R.id.message_list_fragment,contactsListFragment);
                break;
            case 2:
                ft.replace(R.id.message_list_fragment,mySelfListFragment);
                break;
        }
        ft.addToBackStack(null);
        ft.commit();

    }
}
