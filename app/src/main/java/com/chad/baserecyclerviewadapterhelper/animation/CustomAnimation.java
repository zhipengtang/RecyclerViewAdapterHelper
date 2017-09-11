package com.chad.baserecyclerviewadapterhelper.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
//自定义动画
public class CustomAnimation implements BaseAnimation {

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),//设置动画在X轴方向的动画效果，动画开始时为1f,然后膨胀成为1.1f,然后再缩小为1f;
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)//设置动画在Y轴方向的动画效果，动画开始时为1f,然后膨胀成为1.1f,然后再缩小为1f;
        };
    }
}
