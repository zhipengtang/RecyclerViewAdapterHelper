package com.chad.baserecyclerviewadapterhelper.entity;

/**
 * Created by luoxiongwen on 16/10/24.
 */
/*
* 用于DataBinding Use 活动 */
public class Movie {

    public String name;//电影名
    public int length;//影片持续时长
    public int price;//电影的价格
    public String content;//描述电影的内容

    public Movie(String name, int length, int price,String content) {
        this.length = length;
        this.name = name;
        this.price = price;
        this.content=content;
    }
}
