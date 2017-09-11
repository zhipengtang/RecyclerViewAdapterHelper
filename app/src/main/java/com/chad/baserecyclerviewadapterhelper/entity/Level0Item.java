package com.chad.baserecyclerviewadapterhelper.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.baserecyclerviewadapterhelper.adapter.ExpandableItemAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */
/*
* 用于ExpandableItem Activity 活动*/
public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {
    public String title;//Level0 的标题
    public String subTitle;//Level0的子标题
    //level0的构造函数
    public Level0Item( String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }
    //获取Level类型
    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }
    //获取Level层级
    @Override
    public int getLevel() {
        return 0;
    }
}
