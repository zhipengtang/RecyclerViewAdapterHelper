package com.chad.baserecyclerviewadapterhelper.adapter;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by luoxw on 2016/6/20.
 */
/*
*
* 拖拽适配器
* 用于Item拖动时的适配效果*/
public class ItemDragAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {
    public ItemDragAdapter(List data) {
        super(R.layout.item_draggable_view, data);
    }

    /*
    * 设置每一个Item的头部背景图片
    * 依次从上往下根据Item  Position的值设置Item头部图片
    * 当Item Position对3求余等于0时使用head_img0;等于1时使用head_img1，依此类推*/
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        switch (helper.getLayoutPosition() %
                3) {
            case 0:
                helper.setImageResource(R.id.iv_head, R.mipmap.head_img0);
                break;
            case 1:
                helper.setImageResource(R.id.iv_head, R.mipmap.head_img1);
                break;
            case 2:
                helper.setImageResource(R.id.iv_head, R.mipmap.head_img2);
                break;
        }
        helper.setText(R.id.tv, item);//设置Item的文本View
    }
}
