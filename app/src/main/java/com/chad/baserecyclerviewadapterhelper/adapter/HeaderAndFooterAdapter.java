package com.chad.baserecyclerviewadapterhelper.adapter;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.baserecyclerviewadapterhelper.entity.Status;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HeaderAndFooterAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {

    public HeaderAndFooterAdapter(int dataSize) {
        super(R.layout.item_header_and_footer, DataServer.getSampleData(dataSize));
    }
    //通过Position的值设置Item的源图片
    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        //因为PAGE_SIZE为3所以这里设置为对3求余
        switch (helper.getLayoutPosition()%
                3){
            case 0://为第一个Item时设置animation_img1为Resource Image
                helper.setImageResource(R.id.iv,R.mipmap.animation_img1);
                break;
            case 1://为第二个Item时设置animation_img2为Resource Image
                helper.setImageResource(R.id.iv,R.mipmap.animation_img2);
                break;
            case 2://为第三个Item时设置animation_img3为Resource Image
                helper.setImageResource(R.id.iv,R.mipmap.animation_img3);
                break;
        }
    }


}
