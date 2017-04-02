package com.ccjy.wechat.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.ccjy.wechat.callbreak.LoadListener;

/**
 * Created by dell on 2017/3/31.
 */

public class MyRefreshLayout extends SwipeRefreshLayout {
    private RecyclerView recyclerView;
    private boolean loading = false;

    public MyRefreshLayout(Context context) {
        super(context);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int childCount = getChildCount();
        if (childCount > 0) {
            View childAt = getChildAt(0);
            if (childAt instanceof RecyclerView) {
                recyclerView = (RecyclerView) childAt;
                //设置滑动监听
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.e("OnScrollListener", "newState = " + newState);
                        if (newState == 0) {
                            canLoad();
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });
            }
        }
    }

    //加载数据
    private void load() {
        if (loadListener != null) {
            setLoadingView(true);
            loadListener.load();
        }
    }

    /**
     * 是否可以加载更多数据 的条件：
     * 1.是否是listView的最底部
     * 2.是否正在加载
     */
    private void canLoad() {
        //如果是在最底部 并且 正在加载
        if (isBottom() && !loading) {
            //就加载数据
            load();
        }
    }

    //加载更多时 view是否可见
    public void setLoadingView(boolean isLoading) {
        loading = isLoading;
        loadListener.setFootView(isLoading);

    }

    private boolean isBottom() {
        LinearLayoutManager linearManager = null;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //判断是当前layoutManager是否为LinearLayoutManager
        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
        if (layoutManager instanceof LinearLayoutManager) {
            linearManager = (LinearLayoutManager) layoutManager;
            //获取最后一个可见view的位置
            linearManager.findLastVisibleItemPosition();
        }
        if (recyclerView != null && recyclerView.getAdapter().getItemCount() > 0) {
            if (linearManager.findLastVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1) {
                return true;
            }
        }
        return false;
    }


    private LoadListener loadListener;

    public void setLoadListener(LoadListener loadListener) {
        this.loadListener = loadListener;
    }
}
