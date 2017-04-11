package com.ccjy.wechat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ccjy.wechat.MainActivity;
import com.ccjy.wechat.R;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by dell on 2017/3/23.
 * 我(个人信息设置) 的列表页
 */

public class MySelfListFragment extends Fragment implements EMMessageListener {
    private EditText et_userName;
    private EditText et_content;
    private Button send;
    private EMMessage message;
    private  Button setGroup; //创建群组

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_myself_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //注册消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);


        et_userName = (EditText) view.findViewById(R.id.et_userName);
        et_content = (EditText) view.findViewById(R.id.et_content);
        send = (Button) view.findViewById(R.id.send);
        setGroup = (Button) view.findViewById(R.id.setGroup);
        setGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.intent2SetGroup();
            }
        });



        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此


        //发送消息
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = et_userName.getText().toString();
                String content = et_content.getText().toString();
                message = EMMessage.createTxtSendMessage(content, userName);
                EMClient.getInstance().chatManager().sendMessage(message);
            }
        });

    }


    @Override
    public void onMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }
}
