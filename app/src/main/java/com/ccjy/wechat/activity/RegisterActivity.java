package com.ccjy.wechat.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ccjy.wechat.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by dell on 2017/3/27.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_reg_userName,et_reg_password,et_reg_password1;
    private Button btn_reg_register,btn_reg_cancel; //注册  取消注册
    private String userName,password,password1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView(){
        et_reg_userName= (EditText) findViewById(R.id.et_reg_userName);
        et_reg_password= (EditText) findViewById(R.id.et_reg_password);
        et_reg_password1= (EditText) findViewById(R.id.et_reg_password1);
        btn_reg_register= (Button) findViewById(R.id.btn_reg_register);
        btn_reg_cancel= (Button) findViewById(R.id.btn_reg_cancel);
        btn_reg_register.setOnClickListener(this);
        btn_reg_cancel.setOnClickListener(this);
    }
    private void getString(){
        userName = et_reg_userName.getText().toString();
        password = et_reg_password.getText().toString();
        password1 = et_reg_password1.getText().toString();
    }

    private int getRecode(){
        int recode = 0;
        if (TextUtils.isEmpty(userName)){
            return 1;
        }
        if (TextUtils.isEmpty(password)){
            return 2;
        }
        if (TextUtils.isEmpty(password1)){
            return 3;
        }
        if (!password.equals(password1)){
            return 4;
        }
        errorToast(recode);
        return 0;
     }
    private void register(){
        Intent intent =new Intent();
        try {
            EMClient.getInstance().createAccount(userName, password);
           intent.putExtra("NAME",userName);
            intent.putExtra("PASS",password);
            setResult(RESULT_OK,intent);
            finish();
        } catch (HyphenateException e) {
            e.printStackTrace();
            intent.putExtra("NAME",userName);
            intent.putExtra("PASS",password);
            setResult(RESULT_CANCELED,intent);
            finish();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reg_register:
                getString();
                getRecode();
                register();
                userName="";
                password="";
                password1="";
                break;
            case R.id.btn_reg_cancel:
                setDialog();
                break;
        }
    }

    //如果取消注册，弹出dialog
    private void setDialog() {
        new AlertDialog.Builder(this)
                .setTitle("取消注册")
                .setMessage("您确定要取消注册吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent2Login();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toastShow(RegisterActivity.this,"取消注册");
                    }
                })
                .show();
    }
}
