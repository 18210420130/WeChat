package com.ccjy.wechat.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.hyphenate.chat.EMClient;

/**
 * Created by dell on 2017/3/23.
 */

public class SplashActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 *判断之前是否登录过，如果登录过，就直接跳转到消息列表页面，
                 * 如果之前没有登录过  或者之前登录过 没有的话跳到登陆页面
                 */

                if (EMClient.getInstance().isLoggedInBefore()){

                }
            }
        })
    }

}
