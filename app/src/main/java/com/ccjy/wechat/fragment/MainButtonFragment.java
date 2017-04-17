package com.ccjy.wechat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import com.ccjy.wechat.MainActivity;
import com.ccjy.wechat.R;

/**
 * Created by dell on 2017/3/23.
 * 消息列表导航栏
 */

public class MainButtonFragment extends Fragment implements View.OnClickListener {
    private Button btn_message, btn_contacts, btn_myself;
    private ScaleAnimation scaleAnimation;//缩放动画


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_main_button, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        testScaleBy();
    }

    private void initView(View view) {
        btn_message = (Button) view.findViewById(R.id.main_button_message);
        btn_contacts = (Button) view.findViewById(R.id.main_button_contacts);
        btn_myself = (Button) view.findViewById(R.id.main_button_myself);

        btn_message.setOnClickListener(this);
        btn_contacts.setOnClickListener(this);
        btn_myself.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        MainActivity activity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.main_button_message:
                activity.replace(MainActivity.MESSAGE_FRAGMENT);
                NotificationsBackGroud(MainActivity.MESSAGE_FRAGMENT);
                btn_message.setAnimation(scaleAnimation);
                btn_message.startAnimation(scaleAnimation);
                break;
            case R.id.main_button_contacts:
                NotificationsBackGroud(MainActivity.MESSAGE_FRAGMENT);
                activity.replace(MainActivity.CONTACTS_FRAGMENT);
                btn_contacts.setAnimation(scaleAnimation);
                btn_contacts.startAnimation(scaleAnimation);
                break;
            case R.id.main_button_myself:
                NotificationsBackGroud(MainActivity.MESSAGE_FRAGMENT);
                activity.replace(MainActivity.MYSELF_FRAGMENT);
                btn_myself.setAnimation(scaleAnimation);
                btn_myself.startAnimation(scaleAnimation);
                break;

        }
    }

    //通知栏背景颜色
    private void NotificationsBackGroud(String str) {
        if (str == MainActivity.MESSAGE_FRAGMENT) {
            btn_message.setBackgroundResource(R.color.colorAccent);
            btn_contacts.setBackgroundResource(R.color.colorPrimary);
            btn_myself.setBackgroundResource(R.color.colorPrimary);
            btn_message.setAnimation(scaleAnimation);
            btn_message.startAnimation(scaleAnimation);
        }
        if (str == MainActivity.CONTACTS_FRAGMENT) {
            btn_message.setBackgroundResource(R.color.colorPrimary);
            btn_contacts.setBackgroundResource(R.color.colorAccent);
            btn_myself.setBackgroundResource(R.color.colorPrimary);
            btn_contacts.setAnimation(scaleAnimation);
            btn_contacts.startAnimation(scaleAnimation);
        }
        if (str == MainActivity.MYSELF_FRAGMENT) {
            btn_message.setBackgroundResource(R.color.colorPrimary);
            btn_contacts.setBackgroundResource(R.color.colorPrimary);
            btn_myself.setBackgroundResource(R.color.colorAccent);
            btn_myself.setAnimation(scaleAnimation);
            btn_myself.startAnimation(scaleAnimation);
        }
    }

    //缩放动画
    private void testScaleBy(){
        scaleAnimation=new ScaleAnimation(1.3f,1.6f,1.3f,1.6f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
    }
}
