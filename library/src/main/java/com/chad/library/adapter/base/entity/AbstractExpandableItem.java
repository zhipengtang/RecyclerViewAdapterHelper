package com.chad.library.adapter.base.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A helper to implement expandable item.</p>
 * <p>if you don't want to extent a class, you can also implement the interface IExpandable</p>
 * Created by luoxw on 2016/8/9.
 */
public abstract class AbstractExpandableItem<T> implements IExpandable<T> {
    protected boolean mExpandable = false;//mExpandable 保存当前的状态值，默认为false
    protected List<T> mSubItems;//mSubItems 存储数据bean集合

    //判断Item是否可以扩展
    @Override
    public boolean isExpanded() {
        return mExpandable;
    }

    //设置Item是否可以扩展
    @Override
    public void setExpanded(boolean expanded) {
        mExpandable = expanded;
    }
     //获取子Item的数据
    @Override
    public List<T> getSubItems() {
        return mSubItems;
    }
    //判断是否还有子项Item
    public boolean hasSubItem() {
        return mSubItems != null && mSubItems.size() > 0;//子Item不为空并且Item的数目大于0
    }
    //将subItem放入List集合当中
    public void setSubItems(List<T> list) {
        mSubItems = list;
    }

    //获取特定Position的SubItem
    public T getSubItem(int position) {
        if (hasSubItem() && position < mSubItems.size()) {
            return mSubItems.get(position);
        } else {
            return null;
        }
    }
    //获取特定SubItem的position
    public int getSubItemPosition(T subItem) {
        return mSubItems != null ? mSubItems.indexOf(subItem) : -1;
    }
    //增加SubItem
    public void addSubItem(T subItem) {
        if (mSubItems == null) {
            mSubItems = new ArrayList<>();
        }
        mSubItems.add(subItem);
    }
    //在一个特定的Position添加SubItem
    public void addSubItem(int position, T subItem) {
        if (mSubItems != null && position >= 0 && position < mSubItems.size()) {
            mSubItems.add(position, subItem);
        } else {
            addSubItem(subItem);
        }
    }
    //判断Item是否存在
    public boolean contains(T subItem) {
        return mSubItems != null && mSubItems.contains(subItem);
    }
    //删除一个subItem
    public boolean removeSubItem(T subItem) {
        return mSubItems != null && mSubItems.remove(subItem);
    }
    //删除特定位置的SubItem,成功删除返回True,否则返回False
    public boolean removeSubItem(int position) {
        if (mSubItems != null && position >= 0 && position < mSubItems.size()) {
            mSubItems.remove(position);
            return true;
        }
        return false;
    }
}
