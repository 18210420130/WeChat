package com.ccjy.wechat.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ccjy.wechat.R;
import java.util.List;

/**
 * Created by dell on 2017/4/5.
 */

public class AddFriendAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public AddFriendAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_addfriend_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.setView(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.friend_userName.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        private TextView friend_userName;

        private void setView(View view) {
            friend_userName = (TextView) view.findViewById(R.id.item_add_friend_userName);
        }
    }

    public void notify(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }


}
