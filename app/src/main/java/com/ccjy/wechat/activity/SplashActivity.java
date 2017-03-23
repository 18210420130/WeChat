package com.ccjy.wechat.activity;

import android.os.Bundle;

import com.ccjy.wechat.R;
import com.hyphenate.chat.EMClient;

/**
 * Created by dell on 2017/3/23.
 * 开屏页面
 */

public class SplashActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intentDecide();
    }

    //跳转判断方法
    private void intentDecide() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 *判断之前是否登录过，如果登录过，就直接跳转到消息列表页面，
                 * 如果之前没有登录过  跳到登陆页面
                 */
                if (EMClient.getInstance().isLoggedInBefore()){
                    //开始加载数据的时间
                    long startTime = System.currentTimeMillis();
                    //加载所有群组消息
                    EMClient.getInstance().groupManager().loadAllGroups();
                    //加载所有个人聊天消息
                    EMClient.getInstance().chatManager().loadAllConversations();

                    //加载数据完成的时间 减去 开始加载时的时间 等于 加载所消耗的时间
                    long time = System.currentTimeMillis() - startTime;
                    try {
                        Thread.sleep(3000-time);
                        intent2Main();
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }else{
                    try {
                        Thread.sleep(3000);
                        //跳转到登陆页面
                        intent2Login();
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
