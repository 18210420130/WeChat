package com.ccjy.wechat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ccjy.wechat.R;

/**
 * Created by dell on 2017/3/29.
 */

public class ChatDetailsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        initView();
    }
    private void initView(){
       RecyclerView recyclerView= (RecyclerView) findViewById(R.id.chat_details_activity_recyclerView);
        EditText content= (EditText) findViewById(R.id.chat_details_fragment_content);
        TextView send = (TextView) findViewById(R.id.chat_details_activity_send);
    }
}
