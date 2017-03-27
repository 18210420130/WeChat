package com.ccjy.wechat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccjy.wechat.R;
import com.ccjy.wechat.adpater.MessageListAdpater;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/3/23.
 * 消息列表list列表
 */

public class MessageListFragment extends Fragment{
    private List<EMConversation> list =new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_message_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
    }

    //给列表加载 会话 数据
    private void initData(){
        //清空list集合
        list.clear();
        //获取所有会话
        Map<String, EMConversation> conversations = EMClient
                .getInstance()
                .chatManager()
                .getAllConversations();
        //把map集合转成list集合
        for (EMConversation msg:conversations.values()){
            list.add(msg);
        }

        //给list集合排序
        Comparator comp =new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                EMConversation c1 = (EMConversation) o1;
                EMConversation c2 = (EMConversation) o2;
                if (c1.getLastMessage().getMsgTime() < c2.getLastMessage().getMsgTime())
                    return 1;
                else if (c1.getLastMessage().getMsgTime()==c2.getLastMessage().getMsgTime())
                    return 0;
                else if (c1.getLastMessage().getMsgTime()<c2.getLastMessage().getMsgTime())
                    return -1;
                return 0;
            }
        };
        Collections.sort(list,comp);
    }

    private void initRecyclerView(View view){
     RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.message_list_recyclerView);
        initData();
        MessageListAdpater messageListAdpater =new MessageListAdpater(getActivity(),list);
        LinearLayoutManager llm =new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(messageListAdpater);
    }
}
