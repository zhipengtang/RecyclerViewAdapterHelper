package com.chad.baserecyclerviewadapterhelper;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.baserecyclerviewadapterhelper.adapter.HeaderAndFooterAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HeaderAndFooterUseActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private HeaderAndFooterAdapter headerAndFooterAdapter;
    private static final int PAGE_SIZE = 3;//设置每页用3个Item填充

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();
        setTitle("HeaderAndFooter Use");//设置Activity的标题为“HeaderAndFooter”
        setContentView(R.layout.activity_header_and_footer_use);//设置使用的布局
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置RecyclerView使用线性管理布局
        initAdapter();//初始化适配器
        /*
        * 设置点击事件
        * 当头部的View被点击后就在适配器中加入hearView*/
        View headerView = getHeaderView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerAndFooterAdapter.addHeaderView(getHeaderView(1, getRemoveHeaderListener()), 0);
            }
        });
        headerAndFooterAdapter.addHeaderView(headerView);

        /*
        * 设置点击事件
        * 当底部的footer View被点击后就在适配器中加入FooterView*/
        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerAndFooterAdapter.addFooterView(getFooterView(1, getRemoveFooterListener()), 0);//当点击了底部Footer的View后就添加一个Footer View
            }
        });
        headerAndFooterAdapter.addFooterView(footerView, 0);
        //设置mRecyclerView的适配器为headAndFooterAdapter
        mRecyclerView.setAdapter(headerAndFooterAdapter);

    }


    private View getHeaderView(int type, View.OnClickListener listener) {
        //获得头部View实例
        View view = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
        if (type == 1) {
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            imageView.setImageResource(R.mipmap.rm_icon);//点击Header+后设置imageView的图片资源为一个含有删除图标的图片
        }
        view.setOnClickListener(listener);//设置开启监听器
        return view;
    }

    private View getFooterView(int type, View.OnClickListener listener) {
        //获得尾部footer View 实例
        View view = getLayoutInflater().inflate(R.layout.footer_view, (ViewGroup) mRecyclerView.getParent(), false);//动态加载底部View这个布局
        if (type == 1) {
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            imageView.setImageResource(R.mipmap.rm_icon);//设置图片为包含删除样式的图片
        }
        view.setOnClickListener(listener);//开启监听器
        return view;
    }

    private View.OnClickListener getRemoveHeaderListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerAndFooterAdapter.removeHeaderView(v);//点击事件触发后删除头部View
            }
        };
    }


    private View.OnClickListener getRemoveFooterListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerAndFooterAdapter.removeFooterView(v);//当用户点击删除尾部Footer View后在适配器中删除尾部View
            }
        };
    }
//初始化适配器
    private void initAdapter() {
        //实例化适配器
        headerAndFooterAdapter = new HeaderAndFooterAdapter(PAGE_SIZE);
       //加载动画
        headerAndFooterAdapter.openLoadAnimation();
        //设置使用HearAndFooterAdapter
        mRecyclerView.setAdapter(headerAndFooterAdapter);
//        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(HeaderAndFooterUseActivity.this, "" + Integer.toString(position), Toast.LENGTH_LONG).show();
//            }
//        });
        //设置Item的点击事件
        headerAndFooterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
               //利用Toast打印出点击了哪个Item当点击了第一个Item是打印出0，第二个打印1，第三个打印2
                Toast.makeText(HeaderAndFooterUseActivity.this, "" + Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });

    }

}
