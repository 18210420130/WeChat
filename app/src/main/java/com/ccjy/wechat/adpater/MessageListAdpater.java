package com.ccjy.wechat.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccjy.wechat.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/23.
 * 消息列表页 适配器
 */

public class MessageListAdpater extends RecyclerView.Adapter<MessageListAdpater.MyViewHolder> {
    private Context context;
    private List<EMConversation> list = new ArrayList();

    public MessageListAdpater(Context context, List<EMConversation> list) {
        this.context = context;
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView userName, content, sendTime, unread; //好友昵称，文本，发送时间，未读数

        public MyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_message_list_icon);
            userName = (TextView) itemView.findViewById(R.id.item_message_list_userName);
            content = (TextView) itemView.findViewById(R.id.item_message_list_content);
            sendTime = (TextView) itemView.findViewById(R.id.item_message_list_sendTime);
            unread = (TextView) itemView.findViewById(R.id.item_message_list_unread);
        }
    }

    @Override
    public MessageListAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MessageListAdpater.MyViewHolder holder, int position) {
       //获取当前item的下标数据
        EMConversation msg =  list.get(position);

        //设置用户名
        holder.userName.setText(msg.getUserName());
        //设置文本消息
        setMessageContent(holder, msg);
        //设置发送时间

        // 收到最后一条消息的 时间
        long lastSendTime = msg.getLastMessage().getMsgTime();
        //现在 距离收到最后一条消息的时间
        long t = System.currentTimeMillis() - lastSendTime;
        //设置消息未读数
        setMessageUnread(holder, msg);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //设置文本消息方法 获取到最后一条发送的文本消息 如果有内容  就设置 ，如果没有 就显示 空
    private void setMessageContent(MyViewHolder holder, EMConversation msg) {
        String message ;
        try {
            EMTextMessageBody txtBody = (EMTextMessageBody) msg.getLastMessage().getBody();
            message = txtBody.getMessage();
        }catch (Exception e){
            message="";
            e.printStackTrace();
        }
        holder.content.setText(message);
    }


    //设置消息未读数的方法    如果未读数大于0，就设置可见，反之 设置不可见
    private void setMessageUnread(MyViewHolder holder, EMConversation msg) {
        int unreadMsgCount = msg.getUnreadMsgCount();
        if (unreadMsgCount>0){
            holder.unread.setVisibility(View.VISIBLE);
            try {
                holder.unread.setText( String.valueOf(unreadMsgCount));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            holder.unread.setVisibility(View.GONE);
        }
    }


}
