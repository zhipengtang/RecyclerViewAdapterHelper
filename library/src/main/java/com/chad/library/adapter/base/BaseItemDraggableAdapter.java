package com.chad.library.adapter.base;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import com.chad.library.R;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by luoxw on 2016/7/13.
 */
//Item拖拽适配器
public abstract class BaseItemDraggableAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    private static final int NO_TOGGLE_VIEW = 0;
    protected int mToggleViewId = NO_TOGGLE_VIEW;
    protected ItemTouchHelper mItemTouchHelper;
    protected boolean itemDragEnabled = false;  //Item拖拽初始值
    protected boolean itemSwipeEnabled = false;//Item删除初始值
    protected OnItemDragListener mOnItemDragListener;//Item拖拽适配器
    protected OnItemSwipeListener mOnItemSwipeListener;//Item删除适配器
    protected boolean mDragOnLongPress = true;//Item长按

    protected View.OnTouchListener mOnToggleViewTouchListener;//Item单击监听器
    protected View.OnLongClickListener mOnToggleViewLongClickListener;//Item长按监听器

    private static final String ERROR_NOT_SAME_ITEMTOUCHHELPER = "Item drag and item swipe should pass the same ItemTouchHelper";


    public BaseItemDraggableAdapter(List<T> data) {
        super(data);
    }

    public BaseItemDraggableAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);//
    }


    /**
     * To bind different types of holder and solve different the bind events
     *
     * @param holder
     * @param positions
     * @see #getDefItemViewType(int)
     */
    @Override
    public void onBindViewHolder(K holder, int positions) {
        super.onBindViewHolder(holder, positions);//绑定ViewHolder
        int viewType = holder.getItemViewType();//获取View 的类型

        if (mItemTouchHelper != null && itemDragEnabled && viewType != LOADING_VIEW && viewType != HEADER_VIEW
                && viewType != EMPTY_VIEW && viewType != FOOTER_VIEW) {
            if (mToggleViewId != NO_TOGGLE_VIEW) {//当View可以Toggle时
                View toggleView = ((BaseViewHolder) holder).getView(mToggleViewId);//获取Id
                if (toggleView != null) {
                    //设置ToggleView的标签，用于标记这个View
                    toggleView.setTag(R.id.BaseQuickAdapter_viewholder_support, holder);
                    if (mDragOnLongPress) {//如果是长按
                        //开启长按监听器
                        toggleView.setOnLongClickListener(mOnToggleViewLongClickListener);
                    } else {
                        //开启Touch监听器
                        toggleView.setOnTouchListener(mOnToggleViewTouchListener);
                    }
                }
            } else {
                holder.itemView.setTag(R.id.BaseQuickAdapter_viewholder_support, holder);
                holder.itemView.setOnLongClickListener(mOnToggleViewLongClickListener);
            }
        }
    }


    /**
     * Set the toggle view's id which will trigger drag event.
     * If the toggle view id is not set, drag event will be triggered when the item is long pressed.
     *
     * @param toggleViewId the toggle view's id
     */
    public void setToggleViewId(int toggleViewId) {
        mToggleViewId = toggleViewId;
    }

    /**
     * Set the drag event should be trigger on long press.
     * Work when the toggleViewId has been set.
     *
     * @param longPress by default is true.
     */
    //长按可折叠
    public void setToggleDragOnLongPress(boolean longPress) {
        mDragOnLongPress = longPress;
        if (mDragOnLongPress) {//如果检测到了长按事件
            mOnToggleViewTouchListener = null;
            mOnToggleViewLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {//长按监听器
                    if (mItemTouchHelper != null && itemDragEnabled) {//如果可以拖拽并且pressed不为空
                        //开始拖拽
                        mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag(R.id.BaseQuickAdapter_viewholder_support));
                    }
                    return true;
                }
            };
        } else {
            mOnToggleViewTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //如果手势动作是为按下的并且不是长按动作
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN
                            && !mDragOnLongPress) {
                        if (mItemTouchHelper != null && itemDragEnabled) {
                            //开始拖拽
                            mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag(R.id.BaseQuickAdapter_viewholder_support));
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            mOnToggleViewLongClickListener = null;
        }
    }

    /**
     * Enable drag items.
     * Use itemView as the toggleView when long pressed.
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper) {
        enableDragItem(itemTouchHelper, NO_TOGGLE_VIEW, true);
    }

    /**
     * Enable drag items. Use the specified view as toggle.
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     * @param toggleViewId    The toggle view's id.
     * @param dragOnLongPress If true the drag event will be trigger on long press, otherwise on touch down.
     */
    //设置那个Item可拖拽
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper, int toggleViewId, boolean dragOnLongPress) {
        itemDragEnabled = true;
        mItemTouchHelper = itemTouchHelper;
        setToggleViewId(toggleViewId);
        setToggleDragOnLongPress(dragOnLongPress);
    }

    /**
     * Disable drag items.
     */
    public void disableDragItem() {
        itemDragEnabled = false;
        mItemTouchHelper = null;
    }

    public boolean isItemDraggable() {
        return itemDragEnabled;
    }

    /**
     * <p>Enable swipe items.</p>
     * You should attach {@link ItemTouchHelper} which construct with {@link ItemDragAndSwipeCallback} to the Recycler when you enable this.
     */
    public void enableSwipeItem() {
        itemSwipeEnabled = true;
    }

    public void disableSwipeItem() {
        itemSwipeEnabled = false;
    }

    public boolean isItemSwipeEnable() {
        return itemSwipeEnabled;
    }

    /**
     * @param onItemDragListener Register a callback to be invoked when drag event happen.
     */
    //Item拖动监听器
    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        mOnItemDragListener = onItemDragListener;
    }

    public int getViewHolderPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() - getHeaderLayoutCount();//当前适配器的Position减去头部布局的Position
    }
    //开始拖动Item
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }
    //Item拖拽移动中
    public void onItemDragMoving(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        int from = getViewHolderPosition(source);//开始移动时的Position
        int to = getViewHolderPosition(target);//移动到目标的Position
        //拖拽时进行Item交换，利用集合的特性把Item 交换即可
        if (inRange(from) && inRange(to)) {
                if (from < to) {
                    for (int i = from; i < to; i++) {
                        Collections.swap(mData, i, i + 1);//交换Item数据
                    }
                } else {
                    for (int i = from; i > to; i--) {
                        Collections.swap(mData, i, i - 1);//交换Item数据
                    }
                }
                //通知Item
            notifyItemMoved(source.getAdapterPosition(), target.getAdapterPosition());//发出Item已经移动的通知
        }

        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragMoving(source, from, target, to);//监听器Item拖拽
        }
    }
    //Item拖拽结束
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragEnd(viewHolder, getViewHolderPosition(viewHolder));
        }
    }
    //Item删除监听器
    public void setOnItemSwipeListener(OnItemSwipeListener listener) {
        mOnItemSwipeListener = listener;
    }
    //Item开始删除
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwipeStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }
    //当Item通过侧滑Swipe掉后，此时必须删除View
    public void onItemSwipeClear(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.clearView(viewHolder, getViewHolderPosition(viewHolder));
        }
    }
    //当Item移除后
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {//监听器不为空，并且Item可移除
            mOnItemSwipeListener.onItemSwiped(viewHolder, getViewHolderPosition(viewHolder));
        }

        int pos = getViewHolderPosition(viewHolder);//获得当前ViewHolder的Pos

        if (inRange(pos)) {
            mData.remove(pos);//移除Pos位置的Item
            notifyItemRemoved(viewHolder.getAdapterPosition());//发出Item已删除通知
        }
    }
    //Item移除当中
    public void onItemSwiping(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            //Item滑动中
            mOnItemSwipeListener.onItemSwipeMoving(canvas, viewHolder, dX, dY, isCurrentlyActive);
        }
    }
    //判断Item是否在List数据集合当中
    private boolean inRange(int position) {
        return position >= 0 && position < mData.size();
    }
}
