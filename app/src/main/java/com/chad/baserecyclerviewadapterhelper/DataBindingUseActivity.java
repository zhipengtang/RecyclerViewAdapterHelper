package com.chad.baserecyclerviewadapterhelper;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.baserecyclerviewadapterhelper.adapter.DataBindingUseAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.entity.Movie;
import com.chad.baserecyclerviewadapterhelper.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBindingUseActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    DataBindingUseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();//设置回退键
        setTitle("DataBinding Use");//设置Activity
        setContentView(R.layout.activity_data_binding_use);//使用数据绑定Layout

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);//找到RecyclerView控件
        mAdapter = new DataBindingUseAdapter(R.layout.item_movie, genData());//传入绑定的布局Id,和数据实例化适配器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置RecyclerView使用的控件为线性布局
        mRecyclerView.setAdapter(mAdapter);//设置mAdapter为要使用的是适配器
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShortToast("onItemClick");//item点击时用Toast打印“onItemClick”提示用户Item已经被点击了
            }
        });
        mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShortToast("onItemChildLongClick");//Item子部件长按打印出onItemChildLongClick
                return true;
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShortToast("onItemLongClick");//Item长按打印出onItemLongClick
                return true;
            }
        });
    }

//用于Item数据生成
    private List<Movie> genData() {
        ArrayList<Movie> list = new ArrayList<>();//用于存储Item Movie数据的List
        Random random = new Random();//随机发生器
        for (int i = 0; i < 10; i++) {//循环产生10个Item
            String name = "Chad";
            int price = random.nextInt(10) + 10;//随机生成Item中Movie的价格
            int len = random.nextInt(80) + 60;//Movie播放的时长
            Movie movie = new Movie(name, len, price, "He was one of Australia's most distinguished artistes");
            list.add(movie);//向List中添加Movie Item数据
        }
        return list;//返回数据集合列表
    }
}
