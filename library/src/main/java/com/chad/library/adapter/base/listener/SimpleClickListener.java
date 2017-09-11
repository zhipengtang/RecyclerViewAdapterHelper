package com.chad.library.adapter.base.listener;

import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Set;

import static com.chad.library.adapter.base.BaseQuickAdapter.EMPTY_VIEW;
import static com.chad.library.adapter.base.BaseQuickAdapter.FOOTER_VIEW;
import static com.chad.library.adapter.base.BaseQuickAdapter.HEADER_VIEW;
import static com.chad.library.adapter.base.BaseQuickAdapter.LOADING_VIEW;

/**
 * Created by AllenCoder on 2016/8/03.
 * <p>
 * This can be useful for applications that wish to implement various forms of click and longclick and childView click
 * manipulation of item views within the RecyclerView. SimpleClickListener may intercept
 * a touch interaction already in progress even if the SimpleClickListener is already handling that
 * gesture stream itself for the purposes of scrolling.
 *
 * @see RecyclerView.OnItemTouchListener
 */
/*
* •首先我们发现SimpleClickListener 实现了RecyclerView.OnItemTouchListener 接口,
* OnItemTouchListener接口是recyclerview提供的一个监听item被点击的监听器*/
public abstract class SimpleClickListener implements RecyclerView.OnItemTouchListener {
    public static String TAG = "SimpleClickListener";

    private GestureDetectorCompat mGestureDetector;//手势检测辅助类，相对于GestureDetectorCompat 有更好的兼容性，且api使用相同。
    private RecyclerView recyclerView;//存储recyclerview实例对象，后面在获取adapter和viewholder时用到
    protected BaseQuickAdapter baseQuickAdapter;
    private boolean mIsPrepressed = false;//控件被按下标标识
    private boolean mIsShowPress = false;//控件press状态标识
    private View mPressedView = null;//被点击的控件
   //Touch 事件拦截
    /*
    * •初始化recyclerView、baseQuickAdapter、mGestureDetector实例
      •如果点击事件最终没被消费掉，则恢复被touch而进入press状态中的控件状态。
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (recyclerView == null) {//如果为空就为recyclerView赋值rv
            this.recyclerView = rv;
            this.baseQuickAdapter = (BaseQuickAdapter) recyclerView.getAdapter();
            //手势检测
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener(recyclerView));
        } else if (recyclerView != rv) {//不为空则重新赋值
            this.recyclerView = rv;
            this.baseQuickAdapter = (BaseQuickAdapter) recyclerView.getAdapter();
           //手势检测
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener(recyclerView));
        }
        //如果作用于View为空，表示按下去了但是没有进行任何手势动作
        if (!mGestureDetector.onTouchEvent(e) && e.getActionMasked() == MotionEvent.ACTION_UP && mIsShowPress) {
            if (mPressedView != null) {//被按的View不为空
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(mPressedView);
                if (vh == null || !isHeaderOrFooterView(vh.getItemViewType())) {
                    mPressedView.setPressed(false);//设置为false
                }
            }
            mIsShowPress = false;
            mIsPrepressed = false;
        }
        return false;
    }
//手势触碰事件
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }
    //禁用触碰事件
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
    //Item手势监听事件监听器
    /*
    * •onDown 按下时触发
    •onShowPress 处于press状态时触发
    •onSingleTapUp 单击事件触发
     •onLongPress 长点击事件press状态触发
     down->showpress->up
*/
    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        private RecyclerView recyclerView;
        //判断是否按下
        @Override
        public boolean onDown(MotionEvent e) {
            mIsPrepressed = true;
            mPressedView = recyclerView.findChildViewUnder(e.getX(), e.getY());//找到当前按下的View

            super.onDown(e);
            return false;
        }

        @Override
        //Touch了还没有滑动时触发
        public void onShowPress(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {
//                mPressedView.setPressed(true);
                mIsShowPress = true;
            }
            super.onShowPress(e);
        }

        ItemTouchHelperGestureListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {//已经点击，并且点击的View不为空
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    return false;
                }
                final View pressedView = mPressedView;
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(pressedView);
                //如果是头部或者底部的Position则返回false
                if (isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                    return false;
                }
                //获得点击的子View集合
                Set<Integer> childClickViewIds = vh.getChildClickViewIds();//获取添加了点击事件的view 的ids
                Set<Integer> nestViewIds = vh.getNestViews();//获取内嵌了recyclerview的view容器 的ids
                if (childClickViewIds != null && childClickViewIds.size() > 0) {//如果有view添加了点击事件监听
                    for (Integer childClickViewId : childClickViewIds) {//对子View进行遍历
                        View childView = pressedView.findViewById(childClickViewId);//根据ids获得添加了点击事件的view
                        if (childView != null) {
                            if (inRangeOfView(childView, e) && childView.isEnabled()) {//如果当前View是已经点击子View的范围内，并且有效
                                if (nestViewIds != null && nestViewIds.contains(childClickViewId)) {
                                    return false;//如果父View不为空，并且包含子View返回false
                                }
                                setPressViewHotSpot(e, childView);//修改childview的状态
                                childView.setPressed(true);//设置其press为true
                                onItemChildClick(baseQuickAdapter, childView, vh.getLayoutPosition() - baseQuickAdapter.getHeaderLayoutCount());//调用onItemChildClick回调事件。
                                resetPressedView(childView);//重置childview的状态
                                return true;//表示该事件已被消费
                            } else {
                                childView.setPressed(false);//如果不出于点击范围内或者isEable=false
                            }
                        }
                    }
                    setPressViewHotSpot(e, pressedView);//设置点击VIew事件的热点
                    mPressedView.setPressed(true);
                    for (Integer childClickViewId : childClickViewIds) {
                        View childView = pressedView.findViewById(childClickViewId);
                        if (childView != null) {//如果View不为空则表示未点击过，设置childView点击事件为false
                            childView.setPressed(false);
                        }
                    }
                    onItemClick(baseQuickAdapter, pressedView, vh.getLayoutPosition() - baseQuickAdapter.getHeaderLayoutCount());
                } else {
                    setPressViewHotSpot(e, pressedView);
                    mPressedView.setPressed(true);
                    //对子View进行遍历
                    if (childClickViewIds != null && childClickViewIds.size() > 0) {
                        for (Integer childClickViewId : childClickViewIds) {
                            View childView = pressedView.findViewById(childClickViewId);
                            if (childView != null) {
                                childView.setPressed(false); //重置为false，表示未按过
                            }
                        }
                    }
                    onItemClick(baseQuickAdapter, pressedView, vh.getLayoutPosition() - baseQuickAdapter.getHeaderLayoutCount());
                }
                resetPressedView(pressedView);

            }
            return true;
        }
        //重置按过的View
        private void resetPressedView(final View pressedView) {
            if (pressedView != null) {
                pressedView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pressedView != null) {
                            pressedView.setPressed(false);
                        }

                    }
                }, 50);
            }

            mIsPrepressed = false;
            mPressedView = null;
        }
        //长按事件
        @Override
        public void onLongPress(MotionEvent e) {
            boolean isChildLongClick = false;//子Item长按事件初始设置为false
            //当recyclerView正在滚动时不触发Click事件
            if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                return;
            }

            if (mIsPrepressed && mPressedView != null) {//当View不为空且View被点击
                mPressedView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(mPressedView);//子Item Holder
                if (!isHeaderOrFooterPosition(vh.getLayoutPosition())) {//如果不是头部或者尾部的位置
                    Set<Integer> longClickViewIds = vh.getItemChildLongClickViewIds();//获得长按事件集合
                    Set<Integer> nestViewIds = vh.getNestViews();//获得包裹View集合
                    if (longClickViewIds != null && longClickViewIds.size() > 0) {//如果点击的ViewId存在，并且集合当中还有元素
                        for (Integer longClickViewId : longClickViewIds) {
                            View childView = mPressedView.findViewById(longClickViewId);
                            if (inRangeOfView(childView, e) && childView.isEnabled()) {//如果点击的View是在View集合中，并且scroll已经停止
                                if (nestViewIds != null && nestViewIds.contains(longClickViewId)) {
                                    isChildLongClick = true;//包裹的父View存在，并且包含子Item的长按事件，则设置子View已被长按
                                    break;
                                }
                                setPressViewHotSpot(e, childView);//Motion e ChildView 设置点击焦点
                                onItemChildLongClick(baseQuickAdapter, childView, vh.getLayoutPosition() - baseQuickAdapter.getHeaderLayoutCount());
                                childView.setPressed(true);
                                mIsShowPress = true;
                                isChildLongClick = true;
                                break;
                            }
                        }
                    }
                    if (!isChildLongClick) {//如果是父Item长按
                        onItemLongClick(baseQuickAdapter, mPressedView, vh.getLayoutPosition() - baseQuickAdapter.getHeaderLayoutCount());
                        setPressViewHotSpot(e, mPressedView);
                        mPressedView.setPressed(true);
                        if (longClickViewIds != null) {//长按事件发生后执行下面代码
                            for (Integer longClickViewId : longClickViewIds) {
                                View childView = mPressedView.findViewById(longClickViewId);
                                if (childView != null) {//如果子View不为空，先设置子View为false，表示子View没有按过
                                    childView.setPressed(false);
                                }
                            }
                        }
                        mIsShowPress = true;
                    }
                }
            }
        }
    }

    private void setPressViewHotSpot(final MotionEvent e, final View mPressedView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**
             * when   click   Outside the region  ,mPressedView is null
             */
            if (mPressedView != null && mPressedView.getBackground() != null) {
                mPressedView.getBackground().setHotspot(e.getRawX(), e.getY() - mPressedView.getY());
            }
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    public abstract void onItemClick(BaseQuickAdapter adapter, View view, int position);

    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     *
     * @param view     The view whihin the AbsListView that was clicked
     * @param position The position of the view int the adapter
     * @return true if the callback consumed the long click ,false otherwise
     */
    public abstract void onItemLongClick(BaseQuickAdapter adapter, View view, int position);

    public abstract void onItemChildClick(BaseQuickAdapter adapter, View view, int position);

    public abstract void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position);
    //判断手势事件是否在View这个范围内
    public boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];//View Location的X Y两个点的坐标轴
        //如果View为空此时的Motion事件肯定是不在View当中的
        if (view == null || !view.isShown()) {
            return false;
        }
        view.getLocationOnScreen(location);//获得当前的View在屏幕当中的位置
        int x = location[0];//View的X轴坐标
        int y = location[1];//View的Y轴坐标
        /*
        * 如果事件在View这个矩形框图之外表示Motion事件肯定没有在View中点击成功*/
        if (ev.getRawX() < x
                || ev.getRawX() > (x + view.getWidth())
                || ev.getRawY() < y
                || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }
    //判断Position是否是头或者尾部的位置
    private boolean isHeaderOrFooterPosition(int position) {
        /**
         *  have a headview and EMPTY_VIEW FOOTER_VIEW LOADING_VIEW
         */
        if (baseQuickAdapter == null) {
            if (recyclerView != null) {
                baseQuickAdapter = (BaseQuickAdapter) recyclerView.getAdapter();
            } else {
                return false;
            }
        }
        int type = baseQuickAdapter.getItemViewType(position);
        //如果此时的View为空或者头部VIEW/尾部View或者正在加载中则判定此时的View一定是头部或者尾部的View
        return (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW);
    }
   //判断是头部或者尾部的View
    private boolean isHeaderOrFooterView(int type) {
        return (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW);
    }
}


