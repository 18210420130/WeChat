package com.ccjy.wechat.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccjy.wechat.R;
import com.ccjy.wechat.callbreak.MessageListOnItemClickListener;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/23.
 * 消息列表页 适配器
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {
    private Context context;
    private List<EMConversation> list = new ArrayList();
    private MessageListOnItemClickListener message;

    public MessageListAdapter(Context context, List<EMConversation> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MessageListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MessageListAdapter.MyViewHolder holder, final int position) {
       //获取当前item的下标数据
        final EMConversation msg =  list.get(position);
        //设置用户名
        holder.userName.setText(msg.getUserName());
        //设置文本消息
        setMessageContent(holder, msg);
        //设置收到最后一条消息的时间
        holder.sendTime.setText(getLastMsgTime(msg));
        //设置消息未读数
        setMessageUnread(holder, msg);
        //给item设置点击事件
        holder.message_list_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.onItemClick(v,list.get(position).getAllMsgCount());
            }
        });
        //给item的侧滑菜单 menu键 设置点击事件
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout message_list_lay;
        private ImageView icon;
        private TextView userName, content, sendTime, unread; //好友昵称，文本，发送时间，未读数
        private Button delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            message_list_lay= (LinearLayout) itemView.findViewById(R.id.message_list_lay);
            icon = (ImageView) itemView.findViewById(R.id.item_message_list_icon);
            userName = (TextView) itemView.findViewById(R.id.item_message_list_userName);
            content = (TextView) itemView.findViewById(R.id.item_message_list_content);
            sendTime = (TextView) itemView.findViewById(R.id.item_message_list_sendTime);
            unread = (TextView) itemView.findViewById(R.id.item_message_list_unread);
            delete= (Button) itemView.findViewById(R.id.message_list_item_delete_menu);
        }
    }

    //毫秒转分钟
    private int hao2fen(long time){
     return (int) (time/1000/60);
    }
    //分钟转小时
    private int fen2xiaoshi(long time){
        return (int) (time/60);
    }
    //小时转天
    private int xiaoshi2tian(long time){
        return (int) (time/24);
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
    //设置收到最后一条消息的时间
    private String getLastMsgTime(EMConversation msg) {
        // 收到最后一条消息的 时间
        long lastSendTime = msg.getLastMessage().getMsgTime();
        //现在 距离收到最后一条消息的时间
        long t = System.currentTimeMillis() - lastSendTime;
        int m = hao2fen(t); //把毫秒转成分钟
        //如果m 大于60分钟，
        if (m > 60) {
            //把 分钟 转成 小时  如果大于24小时 就把 小时 转成 天，反正 就把分钟转成小时
           if (fen2xiaoshi(m)>24){
               return xiaoshi2tian(fen2xiaoshi(m))+"天前";
           }
            return fen2xiaoshi(m)+"小时前";
        } else {
            //如果m大于1分钟 就返回 分钟前。否则 返回 刚刚
            if (m > 1) {
                return m + "分钟前";
            } else
                return "刚刚";
        }
    }




    public void setOnItemClickListener(MessageListOnItemClickListener message){
        this.message=message;
    }
}
