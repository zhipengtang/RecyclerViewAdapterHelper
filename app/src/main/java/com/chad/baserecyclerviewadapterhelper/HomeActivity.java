package com.chad.baserecyclerviewadapterhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.baserecyclerviewadapterhelper.adapter.HomeAdapter;
import com.chad.baserecyclerviewadapterhelper.entity.HomeItem;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
/*
* HomeActivity 活动*/
public class HomeActivity extends AppCompatActivity {
    //定义Activity数组，用于后面点击事件Intent跳转使用
    private static final Class<?>[] ACTIVITY = {AnimationUseActivity.class, MultipleItemUseActivity.class, HeaderAndFooterUseActivity.class, PullToRefreshUseActivity.class, SectionUseActivity.class, EmptyViewUseActivity.class, ItemDragAndSwipeUseActivity.class, ItemClickActivity.class, ExpandableUseActivity.class, DataBindingUseActivity.class,UpFetchUseActivity.class};
    //定义每一个Item的标题数组
    private static final String[] TITLE = {"Animation", "MultipleItem", "Header/Footer", "PullToRefresh", "Section", "EmptyView", "DragAndSwipe", "ItemClick", "ExpandableItem", "DataBinding", "UpFetchData"};
    //定义Item的图片数组
    private static final int[] IMG = {R.mipmap.gv_animation, R.mipmap.gv_multipleltem, R.mipmap.gv_header_and_footer, R.mipmap.gv_pulltorefresh, R.mipmap.gv_section, R.mipmap.gv_empty, R.mipmap.gv_drag_and_swipe, R.mipmap.gv_item_click, R.mipmap.gv_expandable, R.mipmap.gv_databinding,R.drawable.gv_up_fetch};
    private ArrayList<HomeItem> mDataList;//定义一个ArrayList接受的类型为HomeItem实体类
    private RecyclerView mRecyclerView;

   /* @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                // 进行资源释放操作
                break;
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
        initAdapter();
    }
//初始化网格布局
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);//获取RecyclerView控件
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//设置mRecyclerView的布局管理模式，这里设置成网格布局，每列为两个Item

    }

    @SuppressWarnings("unchecked")
    private void initAdapter() {
        //home_item_view使用CardView
        BaseQuickAdapter homeAdapter = new HomeAdapter(R.layout.home_item_view, mDataList);//定义主页用到的适配器,并把mdataList数据放到适配器中
        homeAdapter.openLoadAnimation();
        View top = getLayoutInflater().inflate(R.layout.top_view, (ViewGroup) mRecyclerView.getParent(), false);//获取主页顶部图片视图
        homeAdapter.addHeaderView(top);//把图片View加入适配器当中
        //设置适配器当中每一个Item的点击事件
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HomeActivity.this, ACTIVITY[position]);//根据点击那个事件跳转到对应的Activity
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(homeAdapter);//设置适配器模式为homeAdapter
    }
//初始化数据
    private void initData() {
        mDataList = new ArrayList<>();
        /*遍历每一个Item
        * 分别对每一个Item的标题，活动，图片进行初始化*/
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            /*
            * 对HomeItem分别设置标题 Activity 以及背景图片*/
            item.setTitle(TITLE[i]);
            item.setActivity(ACTIVITY[i]);
            item.setImageResource(IMG[i]);
            mDataList.add(item);//把Item添加到mDataList链表当中
        }
    }

}
