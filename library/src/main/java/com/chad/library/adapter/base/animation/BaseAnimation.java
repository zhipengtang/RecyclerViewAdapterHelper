package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.view.View;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
/*
* 定义基本动画接口*/
//渐显、缩放、从下到上，从左到右、从右到左
public interface  BaseAnimation{
    Animator[] getAnimators(View view);//Animator为安卓系统内置类，返回动画视图数组

}
