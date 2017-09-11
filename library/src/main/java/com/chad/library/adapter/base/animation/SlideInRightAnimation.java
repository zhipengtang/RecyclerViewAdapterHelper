package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */

/*
* 在X轴方向从右往左加载动画效果
 * */
public class SlideInRightAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                //动画从X轴右边加载进来，动画的初始（Item右边缘）坐标位于view的宽度处，初始隐藏，然后侧滑进入主界面
                ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
        };
    }
}
