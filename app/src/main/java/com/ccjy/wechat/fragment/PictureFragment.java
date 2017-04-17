package com.ccjy.wechat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ccjy.wechat.R;
import com.ccjy.wechat.activity.ChatDetailsActivity;
import com.ccjy.wechat.adpater.ChatDetailsAdapter;
import com.ccjy.wechat.adpater.PictureAdapter;
import com.ccjy.wechat.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by dell on 2017/4/11.
 */

public class PictureFragment extends Fragment implements View.OnClickListener {
    private  ArrayList<String> list;
    private PictureAdapter pictureAdapter;
    private RecyclerView recyclerView;
    private Button send;//发送按钮
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_picture,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = FileUtils.getAllImage(getActivity());
        initView(view);
    }

    private void initView(View view){
         recyclerView= (RecyclerView) view.findViewById(R.id.fragment_picture_recyclerView);
         pictureAdapter=new PictureAdapter(getActivity(),list);
        LinearLayoutManager llm =new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(pictureAdapter);
        send= (Button) view.findViewById(R.id.fragment_picture_send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_picture_send:
                HashSet<String> checkList = pictureAdapter.getCheckList();
               ChatDetailsActivity activity= (ChatDetailsActivity) getActivity();
                for (String str:checkList){
                    Log.e("checkList",str);
                    activity.sendImageMessage(str,false);
                }
                break;
        }

    }
}
