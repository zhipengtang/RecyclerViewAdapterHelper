package com.chad.baserecyclerviewadapterhelper.util;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.BaseMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class ClickableMovementMethod extends BaseMovementMethod {

    private static ClickableMovementMethod sInstance;

    public static ClickableMovementMethod getInstance() {
        if (sInstance == null) {//没有创建Movement实例，此时创建Movement实例
            sInstance = new ClickableMovementMethod();
        }
        return sInstance;
    }
//点击事件
    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {//向上移动或者下移
            /*
            * 获取点击事件X,Y在屏幕当中的坐标值*/
            int x = (int) event.getX();
            int y = (int) event.getY();
            /*
            * 先获得初始化值，然后再往X,和Y方向分别移动*/
            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();
            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);//线性垂直布局
            int off = layout.getOffsetForHorizontal(line, x);//水平偏移量
            //可点击范围
            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
            if (link.length > 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                } else {
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                }
                return true;//返回可以点击
            } else {
                Selection.removeSelection(buffer);//删除选中的Item
            }
        }

        return false;//返回不可点击
    }
    //初始化Item
    @Override
    public void initialize(TextView widget, Spannable text) {
        Selection.removeSelection(text);//移除选中的Selection item
    }
}
