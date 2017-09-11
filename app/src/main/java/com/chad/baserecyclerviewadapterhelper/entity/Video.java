package com.chad.baserecyclerviewadapterhelper.entity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
/*
* 视频类*/
public class Video {
    private String img;//用于设置背景图片
    private String name;//视频实体名称

    public Video(String img, String name) {
        this.img = img;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
