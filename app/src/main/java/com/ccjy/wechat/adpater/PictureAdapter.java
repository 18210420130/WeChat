package com.ccjy.wechat.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ccjy.wechat.R;
import com.ccjy.wechat.fragment.PictureFragment;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dell on 2017/4/11.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> list;
    private static HashSet<String> checkList =new HashSet<>();//用来存被选中的图片

    public static HashSet<String> getCheckList() {
        return checkList;
    }

    public PictureAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.item_picture_image);
            checkBox= (CheckBox) itemView.findViewById(R.id.item_picture_checkBox);

        }
    }

    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_picture_fragment,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PictureAdapter.ViewHolder holder, int position) {
        final String path = list.get(position);
        //设置图片
        Glide.with(context).load(path).override(200,200)
                .into(holder.image);
        //设置复选框监听，把选中的添加到集合中
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkList.add(path);
                }else{
                    checkList.remove(path);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
