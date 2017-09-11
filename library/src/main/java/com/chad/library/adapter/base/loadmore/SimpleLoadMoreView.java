package com.chad.library.adapter.base.loadmore;


import com.chad.library.R;

/**
 * Created by BlingBling on 2016/10/11.
 */

public final class SimpleLoadMoreView extends LoadMoreView {
    //获得布局的Id
    @Override public int getLayoutId() {
        return R.layout.quick_view_load_more;
    }
    //获得加载的布局Id
    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }
    //获得加载失败时布局的Id
    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }
    //获得加载完成时布局的Id
    @Override protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
