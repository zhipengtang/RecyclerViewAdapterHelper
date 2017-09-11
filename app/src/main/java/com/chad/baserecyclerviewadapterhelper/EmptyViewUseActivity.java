package com.chad.baserecyclerviewadapterhelper;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.baserecyclerviewadapterhelper.adapter.QuickAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;

public class EmptyViewUseActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;//定义mRecyclerView
    private QuickAdapter mQuickAdapter;//定义mQuickAdapter适配器
    private View notDataView;//定义View
    private View errorView;//定义错误View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();
        setTitle("EmptyView Use");
        setContentView(R.layout.activity_empty_view_use);//加载布局
        findViewById(R.id.btn_reset).setOnClickListener(this);//Reset按钮点击事件
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);//设置适配器是否有固定的尺寸
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置适配器为线性布局
      //动态加载未载入数据时的布局
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        //单击未载入数据时的布局界面时会刷新界面，加载Item数据
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();//刷新界面
            }
        });
        //加载未成功弹出的Network error布局，click Retry
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        initAdapter();//初始化适配器
        onRefresh();
    }

    private void initAdapter() {
        mQuickAdapter = new QuickAdapter(0);//设置初始Item数据为0，表示没有Item
        mRecyclerView.setAdapter(mQuickAdapter);//设置使用mQuickAdapter适配器
    }

    @Override
    public void onClick(View v) {
        mError = true;//设置产生错误
        mNoData = true;//标记无数据状态
        mQuickAdapter.setNewData(null);//设置数据为空
        onRefresh();//刷新数据
    }

    private boolean mError = true;
    private boolean mNoData = true;
//刷新数据的函数，用于界面数据的刷新
    private void onRefresh() {
        //刷新时使用loading_view这个布局，布局中间含有一个加载圈圈
        mQuickAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) mRecyclerView.getParent());
        //延迟加载，因为使用本地Item数据模拟网络数据的加载，所以使用延迟，使得效果看起来就像是网络加载一样
        new Handler().postDelayed(new Runnable() {
            //开启内部线程，防止主线程阻塞
            @Override
            public void run() {
                if (mError) {//如果网络错误继续设置布局VIew为网络错误界面
                    mQuickAdapter.setEmptyView(errorView);
                    mError = false;
                } else {
                    if (mNoData) {
                        mQuickAdapter.setEmptyView(notDataView);//没有数据时设置无数据布局
                        mNoData = false;
                    } else {
                        mQuickAdapter.setNewData(DataServer.getSampleData(10));//加载Item数据，并且Item数据量为10个
                    }
                }
            }
        }, 1000);//延迟1秒钟执行
    }
}
