package com.chad.library.adapter.base.callback;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;

/**
 * Created by luoxw on 2016/6/20.
 */
/*
* ItemToucherHelper为support 库中的一个类，同于Item点击*/
public class ItemDragAndSwipeCallback extends ItemTouchHelper.Callback {

//    private static final String TAG = ItemDragAndSwipeCallback.class.getSimpleName();

    BaseItemDraggableAdapter mAdapter;//可拖拽适配器

    float mMoveThreshold = 0.1f;//开始移动Item
    float mSwipeThreshold = 0.7f;//开始删除Item

    int mDragMoveFlags =  ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//拖拽移动的方向
    int mSwipeMoveFlags = ItemTouchHelper.END;//删除标志
//Item拖拽回调函数
    public ItemDragAndSwipeCallback(BaseItemDraggableAdapter adapter) {
        mAdapter = adapter;
    }
    @Override
    public boolean isLongPressDragEnabled() {
        return false;//初始化为长按拖拽不可用
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return mAdapter.isItemSwipeEnable();
    }

    /*
    * 选中Item时根据是拖拽还是侧滑删除Item做出不同的动作*/
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG//如果用户对Item做出的是拖拽操作并且View没有被创建
                && !isViewCreateByAdapter(viewHolder)) {
            mAdapter.onItemDragStart(viewHolder);//开始拖动Item
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_dragging_support, true);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE//侧滑删除操作
                && !isViewCreateByAdapter(viewHolder)) {
            mAdapter.onItemSwipeStart(viewHolder);//开始侧滑移除Item
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_swiping_support, true);
        }
        super.onSelectedChanged(viewHolder, actionState);//Item选中
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (isViewCreateByAdapter(viewHolder)) {//Item已经创建执行完成则不做任何动作，直接返回
            return;
        }
        //拖拽tag不为空并且Tag中包含了数据，返回的Tag布尔型为True
        if (viewHolder.itemView.getTag(R.id.BaseQuickAdapter_dragging_support) != null
                && (Boolean)viewHolder.itemView.getTag(R.id.BaseQuickAdapter_dragging_support)) {
            mAdapter.onItemDragEnd(viewHolder);
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_dragging_support, false);
        }
        //侧滑删除tag不为空并且Tag中包含了数据，返回的Tag布尔型为True
        if (viewHolder.itemView.getTag(R.id.BaseQuickAdapter_swiping_support) != null
                && (Boolean)viewHolder.itemView.getTag(R.id.BaseQuickAdapter_swiping_support)) {
            mAdapter.onItemSwipeClear(viewHolder);
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_swiping_support, false);
        }
    }

  /*
  * 获取移动标志*/
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (isViewCreateByAdapter(viewHolder)) {
            return makeMovementFlags(0, 0);
        }

        return makeMovementFlags(mDragMoveFlags, mSwipeMoveFlags);
    }

/*判断Item是否在移动，如果源Item类型不等于目标Item类型则表示没有移动*/
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder source, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, source, fromPos, target, toPos, x, y);
        mAdapter.onItemDragMoving(source, target);//从源到目标target移动Item
    }
//判断Item是否被删除
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (!isViewCreateByAdapter(viewHolder)) {//view没有被适配器创建，表示此时View需要删除
            mAdapter.onItemSwiped(viewHolder);
        }
    }

  /*获得开始移动位置*/
    @Override
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return mMoveThreshold;
    }
/*
* 获得开始删除位置*/
    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return mSwipeThreshold;
    }

    /**
     * Set the fraction that the user should move the View to be considered as swiped.
     * The fraction is calculated with respect to RecyclerView's bounds.
     * <p>
     * Default value is .5f, which means, to swipe a View, user must move the View at least
     * half of RecyclerView's width or height, depending on the swipe direction.
     *
     * @param swipeThreshold A float value that denotes the fraction of the View size. Default value
     * is .8f .
     */
    /*设置删除开始位置*/
    public void setSwipeThreshold(float swipeThreshold) {
        mSwipeThreshold = swipeThreshold;
    }


    /**
     * Set the fraction that the user should move the View to be considered as it is
     * dragged. After a view is moved this amount, ItemTouchHelper starts checking for Views
     * below it for a possible drop.
     *
     * @param moveThreshold A float value that denotes the fraction of the View size. Default value is
     * .1f .
     */
    public void setMoveThreshold(float moveThreshold) {
        mMoveThreshold = moveThreshold;
    }

    /**
     * <p>Set the drag movement direction.</p>
     * <p>The value should be ItemTouchHelper.UP, ItemTouchHelper.DOWN, ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT or their combination.</p>
     * You can combine them like ItemTouchHelper.UP | ItemTouchHelper.DOWN, it means that the item could only move up and down when dragged.
     * @param dragMoveFlags the drag movement direction. Default value is ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT.
     */
    /*
    * 设置是否开始移动*/
    public void setDragMoveFlags(int dragMoveFlags) {
        mDragMoveFlags = dragMoveFlags;
    }

    /**
     * <p>Set the swipe movement direction.</p>
     * <p>The value should be ItemTouchHelper.START, ItemTouchHelper.END or their combination.</p>
     * You can combine them like ItemTouchHelper.START | ItemTouchHelper.END, it means that the item could swipe to both left or right.
     * @param swipeMoveFlags the swipe movement direction. Default value is ItemTouchHelper.END.
     */
    public void setSwipeMoveFlags(int swipeMoveFlags) {
        mSwipeMoveFlags = swipeMoveFlags;//侧滑移动标志
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE//如果此时的状态为删除状态为滑动删除
                && !isViewCreateByAdapter(viewHolder)) {
            View itemView = viewHolder.itemView;

            c.save();//保存画布的状态
            if (dX > 0) {//当向右移动删除时，绘制矩形
                c.clipRect(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + dX, itemView.getBottom());
                c.translate(itemView.getLeft(), itemView.getTop());
            } else {//当向左移动删除时，绘制矩形
                c.clipRect(itemView.getRight() + dX, itemView.getTop(),
                        itemView.getRight(), itemView.getBottom());
                c.translate(itemView.getRight() + dX, itemView.getTop());
            }

            mAdapter.onItemSwiping(c, viewHolder, dX, dY, isCurrentlyActive);
            c.restore();//恢复画布的状态

        }
    }
    /*
    * 判断View是否是由适配器创建的*/
    private boolean isViewCreateByAdapter(RecyclerView.ViewHolder viewHolder) {
        int type = viewHolder.getItemViewType();//获取View类型
        if (type == mAdapter.HEADER_VIEW || type == mAdapter.LOADING_VIEW
                || type == mAdapter.FOOTER_VIEW || type == mAdapter.EMPTY_VIEW) {
            return true;//如果为上面几种类型的View表示是由适配器创建的View类型
        }
        return false;//返回False表示不是由适配器创建

    }
}
