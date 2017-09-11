package com.chad.baserecyclerviewadapterhelper;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.baserecyclerviewadapterhelper.adapter.ExpandableItemAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.entity.Level0Item;
import com.chad.baserecyclerviewadapterhelper.entity.Level1Item;
import com.chad.baserecyclerviewadapterhelper.entity.Person;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.Random;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */

/*
*可扩展的活动
* 继承基类BaseActivity*/
public class ExpandableUseActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    ExpandableItemAdapter adapter;
    ArrayList<MultiItemEntity> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();//设置返回键
        setTitle("ExpandableItem Activity");//设置Activity标题
        setContentView(R.layout.activity_expandable_item_use);//引入布局文件

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);//找到RecyclerView这个控件

        list = generateData();//初始化数据List
        adapter = new ExpandableItemAdapter(list);

        final GridLayoutManager manager = new GridLayoutManager(this, 3);//网格布局，子项横向布局包含3个列
        //点击扩展按钮事件
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {//获取每一个Item的spans
                return adapter.getItemViewType(position) == ExpandableItemAdapter.TYPE_PERSON ? 1 : manager.getSpanCount();
            }
        });

        mRecyclerView.setAdapter(adapter);//设置使用适配器
        // important! setLayoutManager should be called after setAdapter
        mRecyclerView.setLayoutManager(manager);//设置布局管理器
        adapter.expandAll();//所有事件选择扩展
    }

    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 9;//9个Item
        int lv1Count = 3;//每个Item下包含3个level分别为Level1 Level2 Level3
        int personCount = 5;//Level1 Level2 Level3下分别包含5个含有人名的网格SubItem

        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce"};
        Random random = new Random();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item("This is " + i + "th item in Level 0", "subtitle of " + i);
            for (int j = 0; j < lv1Count; j++) {
                Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
                for (int k = 0; k < personCount; k++) {
                    lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
            }
                lv0.addSubItem(lv1);//给子Item添加数据
            }
            res.add(lv0);//把LV1层的数据添加到Res目录
        }
        return res;//返回根List
    }
}
