package com.ccjy.wechat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by dell on 2017/3/22.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //隐士跳转到登陆界面
    public void intent2Login(){
        Intent intent =new Intent("android.intent.action.LOGIN");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }
    //隐士跳转到主界面
    public void intent2Main(){
        Intent intent =new Intent("android.intent.action.CHAT");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }
    //intent显示跳转
    public void intentTo(Activity activity,Intent intent){
     startActivity(intent);
    }

    public static void toastShow(final Activity activity,final String message){
     activity.runOnUiThread(new Runnable() {
         @Override
         public void run() {
             Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
         }
     });
    }


}
