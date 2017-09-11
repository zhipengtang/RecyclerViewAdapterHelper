package com.chad.baserecyclerviewadapterhelper.adapter;

import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.baserecyclerviewadapterhelper.entity.Status;
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
//内嵌适配器
public class NestAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    public NestAdapter() {//内嵌适配器构造函数，继承父类的方法并且初始化数据
        super( R.layout.layout_nest_item, DataServer.getSampleData(20));
    }
    //数据转换
    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.addOnClickListener(R.id.tweetText);//添加监听器
        switch (helper.getLayoutPosition()%//根据Item在不同的位置设置不同的背景图片
                3){
            case 0:
                helper.setImageResource(R.id.img,R.mipmap.animation_img1);
                break;
            case 1:
                helper.setImageResource(R.id.img,R.mipmap.animation_img2);
                break;
            case 2:
                helper.setImageResource(R.id.img,R.mipmap.animation_img3);
                break;
        }
        helper.setText(R.id.tweetName,"Hoteis in Rio de Janeiro");//设置转换的文本
        String msg="\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";
        //设置对应Item文本部分的文本信息
        ( (TextView)helper.getView(R.id.tweetText)).setText(SpannableStringUtils.getBuilder(msg).append("landscapes and nedes").setClickSpan(clickableSpan).create());
        ( (TextView)helper.getView(R.id.tweetText)).setMovementMethod(LinkMovementMethod.getInstance());//设置移动的方法
    }
    /*
    *  在Item的点击范围类添加onclick（）事件，当Item被点击后作出动作*/
    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            //利用ToastUtils工具类当中的showShortToast打印一条短的吐司
            ToastUtils.showShortToast("事件触发了 landscapes and nedes");
        }

        @Override
        public void updateDrawState(TextPaint ds) {//更新绘图状态
            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
            ds.setUnderlineText(true);//设置包含下划线
        }
    };
}
