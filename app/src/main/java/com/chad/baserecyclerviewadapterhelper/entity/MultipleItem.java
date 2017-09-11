package com.chad.baserecyclerviewadapterhelper.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MultipleItem implements MultiItemEntity {
    public static final int TEXT = 1;//设置文本类型为1
    public static final int IMG = 2;//设置图片类型为2
    public static final int IMG_TEXT = 3;//设置文本图片类型为3
    public static final int TEXT_SPAN_SIZE = 3;//设置纯文本类型宽度占比75%
    public static final int IMG_SPAN_SIZE = 1;//设置纯图片类型宽度占比25%
    public static final int IMG_TEXT_SPAN_SIZE = 4;//设置图片文本混合类型宽度占比100%
    public static final int IMG_TEXT_SPAN_SIZE_MIN = 2;//设置小图片文本类型宽度占比50%
    private int itemType;//item类型
    private int spanSize;//尺寸大小
//构造函数 其中itemType表示Item类型，可以传入1,2,3；当包含文本类型时需要传入Content
    public MultipleItem(int itemType, int spanSize, String content) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.content = content;
    }
//不含文本类型的时候使用这个MutipleItem构造函数
    public MultipleItem(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }
    //获得Item占比
    public int getSpanSize() {
        return spanSize;
    }
    //设置Item占比
    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    private String content;
    //获取Item当中的文本
    public String getContent() {
        return content;
    }
    //设置Item当中的文本
    public void setContent(String content) {
        this.content = content;
    }
    //获取Item的类型，包含文本，图片，文本图片
    @Override
    public int getItemType() {
        return itemType;
    }
}
