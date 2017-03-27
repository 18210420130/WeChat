package com.ccjy.wechat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ccjy.wechat.MainActivity;
import com.ccjy.wechat.R;

/**
 * Created by dell on 2017/3/23.
 * 消息列表导航栏
 */

public class MainButtonFragment extends Fragment implements View.OnClickListener {
    private Button btn_message,btn_contacts,btn_myself;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_main_button,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
         btn_message = (Button) view.findViewById(R.id.main_button_message);
         btn_contacts = (Button) view.findViewById(R.id.main_button_contacts);
         btn_myself = (Button) view.findViewById(R.id.main_button_myself);

        btn_message.setOnClickListener(this);
        btn_contacts.setOnClickListener(this);
        btn_myself.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
     MainActivity activity= (MainActivity) getActivity();
        switch (v.getId()){
            case R.id.main_button_message:
                activity.replace(0);
                break;
            case R.id.main_button_contacts:
                activity.replace(1);
                break;
            case R.id.main_button_myself:
                activity.replace(2);
                break;

        }
    }
}
