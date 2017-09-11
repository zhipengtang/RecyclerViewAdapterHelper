package com.chad.baserecyclerviewadapterhelper.loadmore;


import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * Created by BlingBling on 2016/10/15.
 */
//自定义加载更多的View
public final class CustomLoadMoreView extends LoadMoreView {
    //获取布局Id
    @Override public int getLayoutId() {
        return R.layout.view_load_more;
    }
    //获取加载中的布局Id
    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }
    //获取加载失败的布局Id
    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }
    //获取加载结束的布局Id
    @Override protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
