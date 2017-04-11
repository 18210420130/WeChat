package com.ccjy.wechat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ccjy.wechat.R;

/**
 * Created by dell on 2017/4/7.
 * 创建群组
 */

public class SetGroupActivity extends BaseActivity implements View.OnClickListener {
    private EditText group_userName;
    private Button setGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgroup);
        initView();
    }
    private void initView(){
        group_userName= (EditText) findViewById(R.id.et_group_userName);
        setGroup= (Button) findViewById(R.id.btn_setGroup);
        setGroup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
