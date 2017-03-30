package com.ccjy.wechat.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccjy.wechat.R;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/29.
 */

public class ChatDetailsAdapter extends RecyclerView.Adapter<ChatDetailsAdapter.ViewHolder> {
    private Context context;
    private List<EMMessage> list=new ArrayList<>();

    public ChatDetailsAdapter(Context context, List<EMMessage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ChatDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_details, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatDetailsAdapter.ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
      private LinearLayout layout_left,layout_right;
        private ImageView icon_left,icon_right;
        private TextView name_left,name_right,contnet_left,content_right;
        public ViewHolder(View itemView) {
            super(itemView);
            layout_left= (LinearLayout) itemView.findViewById(R.id.item_chat_details_layout_left);
            layout_right= (LinearLayout) itemView.findViewById(R.id.item_chat_details_layout_right);
            icon_left= (ImageView) itemView.findViewById(R.id.item_chat_details_icon_left);
            icon_right= (ImageView) itemView.findViewById(R.id.item_chat_details_icon_right);
            name_left= (TextView) itemView.findViewById(R.id.item_chat_details_name_left);
            name_right= (TextView) itemView.findViewById(R.id.item_chat_details_name_right);
            contnet_left= (TextView) itemView.findViewById(R.id.item_chat_details_content_left);
            content_right= (TextView) itemView.findViewById(R.id.item_chat_details_content_right);
        }
    }


}
