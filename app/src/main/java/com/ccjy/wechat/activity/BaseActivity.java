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

    public void errorToast(int errCode){
        String str ="";
        switch (errCode){
            case 1:
                str="账号不能为空";
                break;
            case 2:
                str="密码不能为空";
                break;
            case 3:
                str="验证码不能为空";
                break;
            case 4:
                str="账号或者密码输入有误";
                break;
            case 5:
                str="两次输入的密码不一致";
                break;
            case 6:
                str="请入的内容不合法";
                break;
        }
        toastShow(this,str);
    }





        }
