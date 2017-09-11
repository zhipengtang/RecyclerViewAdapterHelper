package com.chad.library.adapter.base.entity;

import java.io.Serializable;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
/*
* Section实体类 继承Serializable表示可以序列化*/
public abstract class SectionEntity<T> implements Serializable {
    public boolean isHeader;//判断是否为头部Item
    public T t;//传入的类型
    public String header;//头部文本
    // 构造函数
    public SectionEntity(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = null;
    }
    //构造函数 传入类型为T类型
    public SectionEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
    }
}
