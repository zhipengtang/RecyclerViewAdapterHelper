package com.chad.baserecyclerviewadapterhelper;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.baserecyclerviewadapterhelper.adapter.MultipleItemQuickAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.baserecyclerviewadapterhelper.entity.MultipleItem;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemUseActivity extends BaseActivity {
    private RecyclerView mRecyclerView;//定义RecyclerView控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置MultioleItemUseActivity布局
        setContentView(R.layout.activity_multiple_item_use);
        //设置标题
        setTitle("MultipleItem Use");
        //使用Back定义按钮
        setBackBtn();
        //使用rv_list布局样式定义mRecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        //获取源数据，数据类型为实体类MultipleItem
        final List<MultipleItem> data = DataServer.getMultipleItemData();
        //定义MutipleItemUseActivity使用MultipleItemQuickAdapter适配器
        final MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(this, data);
       //活动使用网格布局管理器
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
       //设置布局
        mRecyclerView.setLayoutManager(manager);
        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();//返回具体位置上面的返回尺寸
            }
        });
        //使用multipleItemAdapter适配器
        mRecyclerView.setAdapter(multipleItemAdapter);
    }


}
