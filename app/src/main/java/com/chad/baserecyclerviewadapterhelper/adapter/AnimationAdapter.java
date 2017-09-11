package com.chad.baserecyclerviewadapterhelper.adapter;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.baserecyclerviewadapterhelper.entity.Status;
import com.chad.baserecyclerviewadapterhelper.util.ClickableMovementMethod;
import com.chad.baserecyclerviewadapterhelper.util.SpannableStringUtils;
import com.chad.baserecyclerviewadapterhelper.util.ToastUtils;
import com.chad.baserecyclerviewadapterhelper.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * 文 件 名: AnimationAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 15:33
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
//动画适配器类
public class AnimationAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    public AnimationAdapter() {
       //添加动画布局，并传入由DataServer生成的SampleData
        super(R.layout.layout_animation, DataServer.getSampleData(100));
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        //为Animation中的Item添加点击事件
        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetName);
        //在每一个界面当中放入3个Item,根据Item在界面中的位置初始化每一个Item的图片
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
        //设置Item中的文本
        helper.setText(R.id.tweetName, "Hoteis in Rio de Janeiro");
        String msg = "\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";
        ((TextView) helper.getView(R.id.tweetText)).setText(SpannableStringUtils.getBuilder(msg).append("landscapes and nedes").setClickSpan(clickableSpan).create());
        ((TextView) helper.getView(R.id.tweetText)).setMovementMethod(ClickableMovementMethod.getInstance());//设置移动方式
        ((TextView) helper.getView(R.id.tweetText)).setFocusable(false);//设置焦点
        ((TextView) helper.getView(R.id.tweetText)).setClickable(false);//设置是否可以点击
        ((TextView) helper.getView(R.id.tweetText)).setLongClickable(false);//设置长按不可用
    }
    //ClickableSpan为安卓系统内置类，表示在点击范围内做出点击事件反应
    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            ToastUtils.showShortToast("事件触发了 landscapes and nedes");//点击Item中的“landsacps an nedes会触发这个事件”
        }
        //更新绘图状态
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
            ds.setUnderlineText(true);//设置下划线为true 表示包含下划线
        }
    };
}
