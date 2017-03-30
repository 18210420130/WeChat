package com.ccjy.wechat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccjy.wechat.MainActivity;
import com.ccjy.wechat.R;
import com.ccjy.wechat.adpater.MessageListAdapter;
import com.ccjy.wechat.callbreak.MessageListOnItemClickListener;
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
    private MessageListAdapter messageListAdpater;
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
      final SwipeRefreshLayout message_swipe= (SwipeRefreshLayout) view.findViewById(R.id.message_swipe);
        //设置刷新时动画的颜色，可以设置4个
        message_swipe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        message_swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        message_swipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        initData();
        //设置下拉刷新事件
        message_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                message_swipe.setRefreshing(false);
                messageListAdpater.notifyDataSetChanged();

            }
        });
         messageListAdpater =new MessageListAdapter(getActivity(),list);
        messageListAdpater.setOnItemClickListener(new MessageListOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                MainActivity activity = (MainActivity) getActivity();
               activity.intent2ChatDetails();

            }
        });

        LinearLayoutManager llm =new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(messageListAdpater);

    }
}
