package com.ccjy.wechat.utils;


import android.content.Context;

/**
 * Created by dell on 2017/3/28.
 */

public class SPUtils {

    private static final String SP_USERNAME="userName"; //存储的所有用户名
    private static final String SP_PASSWORD="password"; //存储的所有用户密码
    private static final String LAST_LOGIN_USERNAME="last_userName"; //最后一次登录的用户名
    private static final String LAST_LOGIN_PASSWORD="last_password";//最后一次登录的用户密码

    //设置最后一次登录使用的用户名
    public static void  setLastLoginUserName(Context context, String userName){
        context.getSharedPreferences(SP_USERNAME,Context.MODE_PRIVATE)
                .edit()
                .putString(LAST_LOGIN_USERNAME,userName)
                .apply();
    }
    //获取到最后一次登录使用的用户名
    public static String getlastLoginUserName(Context context){
        return context.getSharedPreferences(SP_USERNAME,Context.MODE_PRIVATE).getString(LAST_LOGIN_USERNAME,"");
    }

    //设置最后一次登录使用的用户密码
    public static void setLastLoginPassword(Context context,String userName){
        context.getSharedPreferences(SP_PASSWORD,Context.MODE_PRIVATE)
                .edit()
                .putString(LAST_LOGIN_PASSWORD,userName)
                .apply();
    }
    //获取最后一次使用的用户密码
    public static String getlastLoginPassword(Context context){
        return context.getSharedPreferences(SP_PASSWORD,Context.MODE_PRIVATE)
                .getString(LAST_LOGIN_PASSWORD,"");
    }


}
