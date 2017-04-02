package com.ccjy.wechat.callbreak;

import android.view.View;

/**
 * Created by dell on 2017/3/28.
 *
 */

public interface MessageListOnItemClickListener {
    void onItemClick(int postion);
    void onLongClick();
    void deleteItem(int postion);
}
