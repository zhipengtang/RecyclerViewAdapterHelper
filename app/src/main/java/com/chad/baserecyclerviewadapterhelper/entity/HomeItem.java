package com.chad.baserecyclerviewadapterhelper.entity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
/*
* HomeItem实体类*/
public class HomeItem {
    private String title;//设置主页当中Item的标题
    private Class<?> activity;//设置每一个Item的活动类型
    private int imageResource;//设置每一个Item的对应的图片

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getActivity() {
        return activity;
    }

    public void setActivity(Class<?> activity) {
        this.activity = activity;
    }
}