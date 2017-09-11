package com.chad.baserecyclerviewadapterhelper.entity;

import com.chad.baserecyclerviewadapterhelper.adapter.ExpandableItemAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */
/*
* 用于Expandable Activity 这个活动 Perosn 用于Level1的下一层级*/
public class Person implements MultiItemEntity{
    //Person的构造函数
    public Person(String name, int age) {
        this.age = age;//Person的年龄
        this.name = name;//Person的名字
    }

    public String name;
    public int age;
    //获取Item类型
    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_PERSON;
    }
}