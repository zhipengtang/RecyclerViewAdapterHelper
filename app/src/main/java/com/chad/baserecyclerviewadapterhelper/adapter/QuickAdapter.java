package com.chad.baserecyclerviewadapterhelper.adapter;

import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.baserecyclerviewadapterhelper.entity.Status;
import com.chad.baserecyclerviewadapterhelper.transform.GlideCircleTransform;
import com.chad.baserecyclerviewadapterhelper.util.SpannableStringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class QuickAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
   /*
   * 构造函数 其中dataSize传入的参数表示这个适配器当中初始化的数据量大小*/
    public QuickAdapter(int dataSize) {
        super(R.layout.layout_animation, DataServer.getSampleData(dataSize));
    }
   /*
   * 通过switch方法设置在不同位置上的Item的背景图片 */
    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        switch (helper.getLayoutPosition() %
                3) {
            case 0:
                helper.setImageResource(R.id.img, R.mipmap.animation_img1);
                break;
            case 1:
                helper.setImageResource(R.id.img, R.mipmap.animation_img2);
                break;
            case 2:
                helper.setImageResource(R.id.img, R.mipmap.animation_img3);
                break;
        }
        helper.setText(R.id.tweetName, "Hoteis in Rio de Janeiro");//设置Item tweetName部分的文本信息

        helper.setText(R.id.tweetText, "O ever youthful,O ever weeping");//设置Item tweetText部分的文本信息

    }


}
