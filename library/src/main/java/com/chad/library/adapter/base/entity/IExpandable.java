package com.chad.library.adapter.base.entity;

import java.util.List;

/**
 * implement the interface if the item is expandable
 * Created by luoxw on 2016/8/8.
 */
//Expandable 扩展接口类
/*
 * 可以看到，IExpandable 里面定义了四个接口方法：
isExpanded判断当前的bean是否已展开
setExoanded更新bean的当前状态
getSubItems返回下一级的数据集合
getLevel 返回当前item属于第几个层级， 第一级from 0*/
public interface IExpandable<T> {
    boolean isExpanded();
    void setExpanded(boolean expanded);
    List<T> getSubItems();
    /**
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     */
    int getLevel();//获取有多少层
}
