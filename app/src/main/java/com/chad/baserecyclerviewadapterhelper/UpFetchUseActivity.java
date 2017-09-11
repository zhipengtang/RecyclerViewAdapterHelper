package com.chad.baserecyclerviewadapterhelper;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.baserecyclerviewadapterhelper.adapter.UpFetchAdapter;
import com.chad.baserecyclerviewadapterhelper.base.BaseActivity;
import com.chad.baserecyclerviewadapterhelper.entity.Movie;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tysheng
 * Date: 2017/5/25 10:41.
 * Email: tyshengsx@gmail.com
 */
/*
* 向上滑动获取Item的Activity*/
public class UpFetchUseActivity extends BaseActivity {
    RecyclerView mRecyclerView;//定义RecyclerView控件
    UpFetchAdapter mAdapter;//定义向上滑动获取Item的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();
        setTitle("UpFetch Use");//设置Activvity的标题
        setContentView(R.layout.activity_data_binding_use);//使用数据绑定Layout

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mAdapter = new UpFetchAdapter();//实例化适配器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置mRecyclerView的布局为线性布局
        mRecyclerView.setAdapter(mAdapter);//设置使用的适配器类型
        mAdapter.setNewData(genData());//更新数据
        mAdapter.setUpFetchEnable(true);//设置数据可以Fetch
        /**
         * start fetch when scroll to position 2, default is 1.
         * 默认是从Position 为1开始获取数据，这里设置从Position为2开始
         */
        mAdapter.setStartUpFetchPosition(2);//设置初始化上拉下载的位置
        mAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                startUpFetch();//滑动屏幕向上开始获取Item数据
            }
        });
    }

    private int count;
//开始向上滑动屏幕获取数据的函数
    private void startUpFetch() {
        count++;//Item数量++
        /**
         * set fetching on when start network request.
         */
        mAdapter.setUpFetching(true);//在适配器当中设置上滑动屏幕获取Item这个功能可用
        /**
         * get data from internet.
         */
        mRecyclerView.postDelayed(new Runnable() {//设置加载延迟效果
            @Override
            public void run() {
                //把加载的数据添加到适配器中
                mAdapter.addData(0, genData());
                /**
                 * set fetching off when network request ends.
                 */
                mAdapter.setUpFetching(false);//数据加载完后停止Fetching
                /**
                 * set fetch enable false when you don't need anymore.
                 */
                if (count > 5) {
                    mAdapter.setUpFetchEnable(false);//如果Item数量大于5了则暂停加载数据，等待下一轮加载数据
                }
            }
        }, 300);//设置延迟0.3秒加载数据
    }

//生产Moive数据 ，Movie数据用List数组存储
    private List<Movie> genData() {
        ArrayList<Movie> list = new ArrayList<>();//用于存取Movie数据的List集合
        Random random = new Random();//随机发生器
        for (int i = 0; i < 10; i++) {
            String name = "Chad";
            int price = random.nextInt(10) + 10;//利用随机发生器生成Movie的价格
            int len = random.nextInt(80) + 60;//生成Movie的长度
            Movie movie = new Movie(name, len, price, "He was one of Australia's most distinguished artistes");
            list.add(movie);//把Movie添加到List结合当中
        }
        return list;//返回List 集合
    }
}
