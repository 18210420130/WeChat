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

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.ccjy.wechat.MainActivity;
import com.ccjy.wechat.R;
import com.ccjy.wechat.adpater.MessageListAdapter;
import com.ccjy.wechat.callbreak.LoadListener;
import com.ccjy.wechat.callbreak.MessageListOnItemClickListener;
import com.ccjy.wechat.view.MyRefreshLayout;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/3/23.
 * 消息列表list列表
 */

public class MessageListFragment extends Fragment implements EMMessageListener {
    private List<EMConversation> list = new ArrayList<>();
    private MessageListAdapter messageListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;



    public void setChatText(HashMap<String, String> textMap) {
        messageListAdapter.setTextMap(textMap);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_message_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        //注册消息监听
        initRecyclerView(view);
    }

    //给列表加载 会话 数据
    private void initData() {
        //获取所有会话
        Map<String, EMConversation> conversations = EMClient
                .getInstance()
                .chatManager()
                .getAllConversations();
        //清空list集合
        list.clear();
        //把map集合转成list集合
        for (EMConversation msg : conversations.values()) {
            list.add(msg);
        }
        //给list集合排序
        setList();

    }

    //给list集合排序
    private void setList() {
        Comparator comp = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                EMConversation c1 = (EMConversation) o1;
                EMConversation c2 = (EMConversation) o2;
                if (c1.getLastMessage().getMsgTime() < c2.getLastMessage().getMsgTime())
                    return 1;
                else if (c1.getLastMessage().getMsgTime() == c2.getLastMessage().getMsgTime())
                    return 0;
                else if (c1.getLastMessage().getMsgTime() < c2.getLastMessage().getMsgTime())
                    return -1;
                return 0;
            }
        };
        Collections.sort(list, comp);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.message_list_recyclerView);
        //设置上拉刷新和下拉加载
        setRefreshLayout(view);
        //实例化适配器
        messageListAdapter = new MessageListAdapter(getActivity(), list);
        //给recyclerView的每个item设置点击事件
        messageListAdapter.setOnItemClickListener(new MessageListOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MainActivity activity = (MainActivity) getActivity();
                activity.intent2ChatDetails(list.get(position).getUserName());
            }

            @Override
            public void onLongClick() {

            }

            @Override
            public void deleteItem(int position) {
                delItem(position);
            }

            private void delItem(int id) {
                EMConversation messages = list.get(id);
                //删除和某个user会话，如果需要保留聊天记录，传false
                EMClient.getInstance().chatManager().deleteConversation(messages.getUserName(), false);
                list.remove(id);
                messageListAdapter.notifyDataSetChanged();
            }
        });
        //实例化线性管理者
        llm = new LinearLayoutManager(getActivity());
        //设置垂直滑动
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        //设置适配器
        recyclerView.setAdapter(messageListAdapter);
    }

    //设置上拉刷新和下拉加载
    private void setRefreshLayout(final View view) {
        final MyRefreshLayout message_swipe = (MyRefreshLayout) view.findViewById(R.id.message_swipe);
        //设置刷新时动画的颜色，可以设置4个
        message_swipe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        message_swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        message_swipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        //设置下拉刷新事件
        message_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                message_swipe.setRefreshing(false);
                messageListAdapter.notifyDataSetChanged();

            }
        });
        //设置上拉加载
        message_swipe.setLoadListener(new LoadListener() {
            @Override
            public void load() {
                message_swipe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messageListAdapter.notifyDataSetChanged();
                        message_swipe.setLoadingView(false);
                    }
                }, 2500);

            }

            @Override
            public void setFootView(boolean loading) {
                if (loading) {

                } else {
                    recyclerView.removeAllViews();
                }
            }
        });

    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        messageListAdapter.notifyDataSetChanged();
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
