package com.chad.library.adapter.base.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by AllenCoder on 2016/8/03.
 *
 *
 * A convenience class to extend when you only want to OnItemClickListener for a subset
 * of all the SimpleClickListener. This implements all methods in the
 * {@link SimpleClickListener}
 */
//Item单击事件监听器
public abstract   class OnItemClickListener extends SimpleClickListener {

    //Item单击
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemClick(adapter,view,position);
    }
    //Item长按
    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
    //Item单击
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
    //子Item长按
    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
    //抽象方法
    public abstract void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position);
}
