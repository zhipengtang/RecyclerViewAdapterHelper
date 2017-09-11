package com.chad.library.adapter.base.listener;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * create by: allen on 16/8/3.
 */
//Item长按事件监听器
public abstract class OnItemLongClickListener extends SimpleClickListener {



   //单击Item监听器
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
   //Item长按监听
    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemLongClick( adapter,  view,  position);
    }
    //子Item单击监听事件
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
    //子Item长按监听事件
    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }
    public abstract void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position);
}
