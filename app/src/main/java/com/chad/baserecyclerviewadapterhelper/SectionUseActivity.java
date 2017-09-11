package com.chad.baserecyclerviewadapterhelper;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.baserecyclerviewadapterhelper.adapter.SectionAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.baserecyclerviewadapterhelper.entity.MySection;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionUseActivity extends BaseActivity {//继承使用BaseActivity自定义的Back和Title
    private RecyclerView mRecyclerView;
    private List<MySection> mData;//定义MySection类型的数据集合，用于存取数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_uer);//使用activity_section_uer线性布局排版每一个Item
        setBackBtn();
        setTitle("Section Use");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);//找到recyclerView控件
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));//设置每一个Item当中使用网格布局管理，vertical方向为2列
        mData = DataServer.getSampleData();//获取数据源
        SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content, R.layout.def_section_head, mData);
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MySection mySection = mData.get(position);
                if (mySection.isHeader)//如果是点击Item当中的头部，使用Toast打印出点击了头部的提示
                    Toast.makeText(SectionUseActivity.this, mySection.header, Toast.LENGTH_LONG).show();
                else//如果点击了图片打印出点击了CymChad项
                    Toast.makeText(SectionUseActivity.this, mySection.t.getName(), Toast.LENGTH_LONG).show();
            }
        });
        //Item内部小项点击事件  Section中的More
        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(SectionUseActivity.this, "onItemChildClick" + position, Toast.LENGTH_LONG).show();
            }
        });
        //使用sectionAdapter适配器
        mRecyclerView.setAdapter(sectionAdapter);
    }


}
