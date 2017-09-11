package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
/*
* 在X轴方向从左往右加载动画效果
 * */
public class SlideInLeftAnimation implements BaseAnimation {
  @Override
  public Animator[] getAnimators(View view) {
    return new Animator[] {
            //动画从X轴左边加载进来，动画的初始（Item左边缘）坐标位于-view的宽度处，初始隐藏，然后侧滑进入主界面
        ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
    };
  }
}
