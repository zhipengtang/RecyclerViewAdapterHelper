package com.chad.library.adapter.base.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by AllenCoder on 2016/8/03.
 * A convenience class to extend when you only want to OnItemChildLongClickListener for a subset
 * of all the SimpleClickListener. This implements all methods in the
 * {@link SimpleClickListener}
 **/
public abstract class OnItemChildLongClickListener extends SimpleClickListener {

   //Item点击后执行的函数
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
    //Item长按后执行的函数
    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
    //子Item点击后执行的函数
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
    //子Item长按后执行的函数
    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemChildLongClick(adapter,view,position);
    }
    //抽象函数，子类中必须实现
    public abstract void onSimpleItemChildLongClick(BaseQuickAdapter adapter, View view, int position);
}
