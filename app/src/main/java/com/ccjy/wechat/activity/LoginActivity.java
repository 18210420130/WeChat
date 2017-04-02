package com.ccjy.wechat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ccjy.wechat.MainActivity;
import com.ccjy.wechat.R;
import com.ccjy.wechat.utils.SPUtils;
import com.ccjy.wechat.view.CustomDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by dell on 2017/3/23.
 * 登录页面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_login_icon;
    private EditText et_login_userName, et_login_password;
    private ImageView iv_delete_username, iv_delete_password;
    private Button bt_login, bt_register;  //登录按钮 注册按钮
    private CheckBox rem_pw, auto_login;  //记住密码  自动登录
    private CustomDialog customDialog;
    private String user, pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    //初始化控件
    private void initView() {
        iv_login_icon = (ImageView) findViewById(R.id.iv_login_icon);
        iv_delete_username = (ImageView) findViewById(R.id.iv_delete_username);
        iv_delete_password = (ImageView) findViewById(R.id.iv_delete_password);
        et_login_userName = (EditText) findViewById(R.id.et_login_userName);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);

        rem_pw = (CheckBox) findViewById(R.id.rem_pw);
        auto_login = (CheckBox) findViewById(R.id.auto_login);

        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        customDialog = new CustomDialog(this, R.style.CustomDialog);

        et_login_userName.setText(SPUtils.getlastLoginUserName(this));
        et_login_password.setText(SPUtils.getlastLoginPassword(this));
        et_login_userName.setSelection(et_login_userName.getText().toString().length());
        et_login_password.setSelection(et_login_password.getText().toString().length());


    }

    private void getLogin() {
        user = et_login_userName.getText().toString();
        pass = et_login_password.getText().toString();
        int reCode;
        reCode = getReCode(user, pass);
        switch (reCode) {
            case 0:
                login(user, pass);
                customDialog.show();
                break;
            default:
                errorToast(reCode);
                break;
        }
    }

    private void login(final String userName, final String password) {
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                intent2Main();
                finish();
                customDialog.cancel();
                SPUtils.setLastLoginUserName(LoginActivity.this, userName);
                SPUtils.setLastLoginPassword(LoginActivity.this, password);
                Log.e("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("main", "登录聊天服务器失败！");
                customDialog.cancel();
            }
        });
    }


    private int getReCode(String userName, String password) {
        int reCode = 0;
        if (TextUtils.isEmpty(userName)) {
            return 1;
        }
        if (TextUtils.isEmpty(password)) {
            return 2;
        }
        return reCode;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete_username:
                break;
            case R.id.iv_delete_password:
                break;
            case R.id.bt_login:
                getLogin();
                break;
            case R.id.bt_register:
                int requestCode = 1;
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, requestCode);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (requestCode == RESULT_OK) {
                    data.getStringExtra("NAME");
                    data.getStringExtra("PASS");
                } else {
                    toastShow(LoginActivity.this, "注册失败");
                }
                break;
        }
    }
}
