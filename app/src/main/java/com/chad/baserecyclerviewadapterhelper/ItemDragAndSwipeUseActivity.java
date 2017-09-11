package com.chad.baserecyclerviewadapterhelper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.chad.baserecyclerviewadapterhelper.adapter.ItemDragAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class ItemDragAndSwipeUseActivity extends BaseActivity {
    private static final String TAG = ItemDragAndSwipeUseActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;//定义RecyclerView
    private List<String> mData;//用于存取数据
    private ItemDragAdapter mAdapter;//定义适配器
    private ItemTouchHelper mItemTouchHelper;//定义Touch事件辅助工具
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;//定义拖拽和删除回调

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_touch_use);//设置初始布局，Item可以点击
        setBackBtn();//返回按钮
        setTitle("ItemDrag  And Swipe");//设置Activity标题
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);//找到RecyclerView控件
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置mRecyclerView的布局管理器为线性布局管理器
        mData = generateData(50);//利用自定义的Item数据发生器初始化50条Item数据
        OnItemDragListener listener = new OnItemDragListener() {
            /*
            * Item拖拽事件开始
            * */
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "drag start");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }
/*
* 拖拽事件移动中
* 在控制台打印出Item从哪个Position开始到哪个Position结束*/
            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                Log.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
            }
/*
* 拖拽事件结束*/
            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "drag end");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.BLACK);
            }
        };
        final Paint paint = new Paint();
        paint.setAntiAlias(true);//设置坑锯齿为True表示画出来的View更加清晰
        paint.setTextSize(20);//设置文本大小20sp
        paint.setColor(Color.BLACK);//字体颜色黑色
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            //侧滑删除Item开始
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "view swiped start: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }
            //删除View
            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.BLACK);
            }
//Item删除时控制台打印提示
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View Swiped: " + pos);
            }
/*
* Item在删除当中移动的效果*/
            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                //利用画布在Item移动删除时设置填充Item背景颜色，背景颜色为蓝色
                canvas.drawColor(ContextCompat.getColor(ItemDragAndSwipeUseActivity.this, R.color.color_light_blue));
//                canvas.drawText("Just some text", 0, 40, paint);
            }
        };

        mAdapter = new ItemDragAdapter(mData);
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);//设置与RecyclerView绑定

        //mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        mAdapter.enableSwipeItem();//设置Item可删除
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mAdapter.enableDragItem(mItemTouchHelper);//设置Item可拖拽
        mAdapter.setOnItemDragListener(listener);
//        mRecyclerView.addItemDecoration(new GridItemDecoration(this ,R.drawable.list_divider));

        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//                ToastUtils.showShortToast("点击了" + position);
//            }
//        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShortToast("点击了" + position);
            }
        });//Toast打印点击了哪个Item
    }
/*
* 数据发生器
* 用于生产Item数据*/
    private List<String> generateData(int size) {
        ArrayList<String> data = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            data.add("item " + i);//数据集合当中添加数据
        }
        return data;//返回数据
    }


}
