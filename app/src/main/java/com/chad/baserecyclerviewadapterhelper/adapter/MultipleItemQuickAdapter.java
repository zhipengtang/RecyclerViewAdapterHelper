package com.chad.baserecyclerviewadapterhelper.adapter;

import android.content.Context;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.entity.MultipleItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(Context context, List data) {
        super(data);
        //为适配器添加文本，图片，文本图片等Item类型
        addItemType(MultipleItem.TEXT, R.layout.item_text_view);
        addItemType(MultipleItem.IMG, R.layout.item_image_view);
        addItemType(MultipleItem.IMG_TEXT, R.layout.item_img_text_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        //获取View类型并对每种View类型进行执行不同的设置
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT://当为TEXT类型的时候设置文本为item.getCount()
                helper.setText(R.id.tv, item.getContent());
                break;
            case MultipleItem.IMG_TEXT:
                switch (helper.getLayoutPosition() %
                        2) {
                    case 0://如果是左边的图片则设置图片为animation_img1
                        helper.setImageResource(R.id.iv, R.mipmap.animation_img1);
                        break;
                    case 1://如果是右边的图片则设置图片为animation_img2
                        helper.setImageResource(R.id.iv, R.mipmap.animation_img2);
                        break;

                }
                break;
        }
    }

}
