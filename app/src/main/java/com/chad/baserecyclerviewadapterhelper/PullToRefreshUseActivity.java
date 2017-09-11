package com.chad.baserecyclerviewadapterhelper;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.baserecyclerviewadapterhelper.adapter.PullToRefreshAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.baserecyclerviewadapterhelper.loadmore.CustomLoadMoreView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class PullToRefreshUseActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private PullToRefreshAdapter pullToRefreshAdapter;//定义下拉刷新适配器
    private SwipeRefreshLayout mSwipeRefreshLayout;//刷新时使用的布局

    private static final int TOTAL_COUNTER = 18;//Item总数为18

    private static final int PAGE_SIZE = 6;

    private int delayMillis = 1000;//延时毫秒数

    private int mCurrentCounter = 0;//初始化Item Count数为0

    private boolean isErr;//网络错误标识
    private boolean mLoadMoreEndGone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        //移动侧滑布局
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
       //设置移动侧滑布局监听
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        //设置mRecyclerView的布局管理器为线性管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置Acticity的标题
        setTitle("Pull TO Refresh Use");
        setBackBtn();//使用自定义Back按钮
        initAdapter();//初始化适配器
        addHeadView();//添加头部View
    }

    private void addHeadView() {
        //运行时动态加载头部View布局
        View headView = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
        //设置headView为不可见
        headView.findViewById(R.id.iv).setVisibility(View.GONE);
        //设置文本为change laod view
        ((TextView) headView.findViewById(R.id.tv)).setText("change load view");
       //当点击头部hearderView时触发下面的事件
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadMoreEndGone = true;
                //加载Item View
                pullToRefreshAdapter.setLoadMoreView(new CustomLoadMoreView());
                //设置适配器为下拉刷新适配器
                mRecyclerView.setAdapter(pullToRefreshAdapter);
                Toast.makeText(PullToRefreshUseActivity.this, "change complete", Toast.LENGTH_LONG).show();
            }
        });
        pullToRefreshAdapter.addHeaderView(headView);//把headView加入到下拉刷新适配器当中
    }
//加载请求
    @Override
    public void onLoadMoreRequested() {
        //没有加载前设置刷新布局不可用
        mSwipeRefreshLayout.setEnabled(false);
        //当Item的数量小于18时允许继续加载Item
        if (pullToRefreshAdapter.getData().size() < PAGE_SIZE) {
            pullToRefreshAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
//                    pullToRefreshAdapter.loadMoreEnd();//default visible
                pullToRefreshAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
            } else {
               //如果网络有问题需要手动点击继续加载
                if (isErr) {
                    pullToRefreshAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                    mCurrentCounter = pullToRefreshAdapter.getData().size();
                    pullToRefreshAdapter.loadMoreComplete();
                } else {
                    //没有网络的时候提示用户网络有问题，加载失败
                    isErr = true;
                    Toast.makeText(PullToRefreshUseActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
                    pullToRefreshAdapter.loadMoreFail();

                }
            }
            //设置侧滑刷新布局生效
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    public void onRefresh() {
        pullToRefreshAdapter.setEnableLoadMore(false);
        //设置刷新延迟功能使得效果明显，本项目延迟1秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //加载数据
                pullToRefreshAdapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
               //网络错误设置为false，表示没有网络错误
                isErr = false;
                mCurrentCounter = PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                pullToRefreshAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    private void initAdapter() {
        pullToRefreshAdapter = new PullToRefreshAdapter();
        pullToRefreshAdapter.setOnLoadMoreListener(this, mRecyclerView);//设置加载监听器
        pullToRefreshAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);//设置加载SLIDEIN_LEFT类型的动画
//        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(pullToRefreshAdapter);
        //获取当前已经从网络上加载的Item数目
        mCurrentCounter = pullToRefreshAdapter.getData().size();
        //设置点击事件，当用户点击Item是通过Toast打印出一条提醒，显示当前点击的是第几个Item
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(PullToRefreshUseActivity.this, Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }


}
