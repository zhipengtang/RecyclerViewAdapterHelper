package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class AlphaInAnimation implements BaseAnimation {//透明度效果类

    private static final float DEFAULT_ALPHA_FROM = 0f;//设置默认动画值为0f
    private final float mFrom;
//构造函数中传入默认动画开始的透明度
    public AlphaInAnimation() {
        this(DEFAULT_ALPHA_FROM);
    }

    public AlphaInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};//安卓动画内置类，第一个参数为对象名，第二个为属性名，后面的参数为可变参数，如果values…参数只设置了一个值的话，那么会假定为目的值
   //透明度从0f变到1f
    }
}
