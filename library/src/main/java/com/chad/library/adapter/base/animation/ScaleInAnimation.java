package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
//缩放动态动画加载效果类
public class ScaleInAnimation implements BaseAnimation {

  private static final float DEFAULT_SCALE_FROM = .5f;//设置默认SCALE加载大小 默认初始加载大小为0.5f
  private final float mFrom;

  public ScaleInAnimation() {
    this(DEFAULT_SCALE_FROM);
  }

  public ScaleInAnimation(float from) {
    mFrom = from;
  }

  //设置Item从X ,Y  两个方向同时缩放，X方向从mFrom放大到1f；Y方向从mFrom放大到1f;
  @Override
  public Animator[] getAnimators(View view) {
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
    ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
    return new ObjectAnimator[] { scaleX, scaleY };
  }
}
