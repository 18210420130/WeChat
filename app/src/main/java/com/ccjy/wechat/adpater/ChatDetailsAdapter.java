package com.ccjy.wechat.adpater;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.ccjy.wechat.R;
import com.ccjy.wechat.activity.ChatDetailsActivity;
import com.ccjy.wechat.utils.SPUtils;
import com.ccjy.wechat.view.GlideCircleTransform;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/29.
 */

public class ChatDetailsAdapter extends RecyclerView.Adapter<ChatDetailsAdapter.ViewHolder> {
    private Context context;
    private List<EMMessage> list = new ArrayList<>();
    private ArrayList<String> imageList;

    public ChatDetailsAdapter(Context context, List<EMMessage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ChatDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatDetailsAdapter.ViewHolder holder, int position) {
        EMMessage message = list.get(position);
        //设置时间的方法
        setMsgTime(holder, message);

        EMMessage.Type typeMsg = message.getType();
        switch (typeMsg) {
            case TXT:
                //设置消息内容
                setMsgContent(holder, message);
                break;
            case IMAGE:
                setMsgImage(holder,message);
                break;
            case VIDEO:
                break;
            case VOICE:
                break;
        }


}

    //设置消息内容
    private void setMsgContent(ViewHolder holder, EMMessage message) {
        EMTextMessageBody txt = (EMTextMessageBody) message.getBody();
        if (SPUtils.getlastLoginUserName(context).equals(message.getFrom())) {
            holder.layout_right.setVisibility(View.VISIBLE);
            holder.layout_left.setVisibility(View.GONE);
            holder.name_right.setText("我");
            //把消息设置为文本消息
            holder.content_right.setVisibility(View.VISIBLE);
            holder.content_left.setVisibility(View.GONE);
            holder.content_right.setText(txt.getMessage());
        } else {
            holder.layout_right.setVisibility(View.GONE);
            holder.layout_left.setVisibility(View.VISIBLE);
            holder.name_left.setText(message.getUserName());
            holder.content_left.setVisibility(View.VISIBLE);
            holder.content_right.setVisibility(View.GONE);
            holder.content_left.setText(txt.getMessage());
        }
    }

    //设置图片内容
    private void setMsgImage(ViewHolder holder,EMMessage message){
        EMImageMessageBody emImage= (EMImageMessageBody) message.getBody();
        if (SPUtils.getlastLoginUserName(context).equals(message.getFrom())){
            holder.layout_right.setVisibility(View.VISIBLE);
            holder.layout_left.setVisibility(View.GONE);
            holder.name_right.setText("我");
            holder.image_right.setVisibility(View.VISIBLE);
            holder.image_left.setVisibility(View.GONE);
            //显示 图片 本地的的url getLocalUrl()
            Glide.with(context)
                    .load(emImage.getLocalUrl())
                    .override(300,200)
                    .into(holder.image_right);
        }else{
            holder.layout_right.setVisibility(View.GONE);
            holder.layout_left.setVisibility(View.VISIBLE);
            holder.name_left.setText(message.getUserName());
            holder.image_right.setVisibility(View.GONE);
            holder.image_left.setVisibility(View.VISIBLE);
            //显示 图片 缩略图的url getThumbnailUrl()
            Glide.with(context)
                    .load(emImage.getThumbnailUrl())
                    .override(300,200)
                    .into(holder.image_left);
        }

    }



    //设置时间的方法
    private void setMsgTime(ViewHolder holder, EMMessage message) {
        //把时间设置为可见
        holder.time.setVisibility(View.VISIBLE);
        //实例化时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM_dd HH:mm");
        //获取到消息的时间  并格式化
        String time = simpleDateFormat.format(message.getMsgTime());
        //设置时间
        holder.time.setText(time);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout_left, layout_right;
        private ImageView icon_left, icon_right;
        private TextView name_left, name_right, content_left, content_right, time;
        private ImageView image_left,image_right;

        public ViewHolder(View itemView) {
            super(itemView);
            layout_left = (LinearLayout) itemView.findViewById(R.id.item_chat_details_layout_left);
            layout_right = (LinearLayout) itemView.findViewById(R.id.item_chat_details_layout_right);
            icon_left = (ImageView) itemView.findViewById(R.id.item_chat_details_icon_left);
            icon_right = (ImageView) itemView.findViewById(R.id.item_chat_details_icon_right);
            name_left = (TextView) itemView.findViewById(R.id.item_chat_details_name_left);
            name_right = (TextView) itemView.findViewById(R.id.item_chat_details_name_right);
            content_left = (TextView) itemView.findViewById(R.id.item_chat_details_content_left);
            content_right = (TextView) itemView.findViewById(R.id.item_chat_details_content_right);
            time = (TextView) itemView.findViewById(R.id.item_chat_details_time);
            image_left= (ImageView) itemView.findViewById(R.id.item_chat_details_image_left);
            image_right= (ImageView) itemView.findViewById(R.id.item_chat_details_image_right);

        }
    }


}
