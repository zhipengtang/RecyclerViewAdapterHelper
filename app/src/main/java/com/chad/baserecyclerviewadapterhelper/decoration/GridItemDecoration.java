package com.chad.baserecyclerviewadapterhelper.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable dividerDrawable;//分割线绘制
    private int orientation = LinearLayoutManager.VERTICAL;//获取垂直方向的布局

    public GridItemDecoration(Drawable divider) {//构造函数
        dividerDrawable = divider;
    }

    public GridItemDecoration(Context context, int resId) {
        dividerDrawable = context.getResources().getDrawable(resId);
    }

    public GridItemDecoration(Context context, int resId, int orientation) {
        dividerDrawable = context.getResources().getDrawable(resId);
        this.orientation = orientation;
    }
    //获取Item的偏移
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (dividerDrawable == null) {//如果分割线不可绘制则直接返回Null
            return;
        }

        if (parent.getChildLayoutPosition(view) < 1) {//如果子Item的位置小于1，直接返回
            return;
        }

        if (orientation == LinearLayoutManager.VERTICAL) {//如果在垂直方向上
            outRect.top = dividerDrawable.getIntrinsicHeight();
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {//如果在水平方向上
            outRect.left = dividerDrawable.getIntrinsicWidth();
        }
    }

    /**
     * @param c
     * @param parent
     * @param state
     */
    /*
    * Item绘制完成后执行的方法*/
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (dividerDrawable == null) {
            return;
        }
        int childCount = parent.getChildCount();//获取子Item的数量
        int rightV = parent.getWidth();//或Item的宽度
        for (int i = 0; i < childCount; i++) {//遍历子Item
            View child = parent.getChildAt(i);//获取i位置上面的对应的childView
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int leftV = parent.getPaddingLeft() + child.getPaddingLeft();//左边宽度等于父padding加上子padding的值
            int bottomV = child.getTop() - params.topMargin;//获取底部的宽度
            int topV = bottomV - dividerDrawable.getIntrinsicHeight();

            int topH = child.getTop() + params.topMargin;
            int bottomH = child.getBottom() + params.bottomMargin;
            int rightH = child.getLeft() - params.leftMargin;
            int leftH = rightH - dividerDrawable.getIntrinsicWidth();
            dividerDrawable.setBounds(leftH, topH, rightH, bottomH);//设置Item的边界线
            dividerDrawable.draw(c);//绘制Item
            dividerDrawable.setBounds(leftV, topV, rightV, bottomV);//设置边界线
            dividerDrawable.draw(c);//绘制Item
        }
    }


}
