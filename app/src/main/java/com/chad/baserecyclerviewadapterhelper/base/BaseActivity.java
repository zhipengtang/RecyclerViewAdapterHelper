package com.chad.baserecyclerviewadapterhelper.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.baserecyclerviewadapterhelper.R;
import com.orhanobut.logger.Logger;

/**
 * 文 件 名: BaseActivity
 * 创 建 人: Allen
 * 创建日期: 16/12/24 15:33
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
/*
* 创建基类Activity，以后所有Item的Activity都要继承BaseActivity
* 这样Item页面的顶部就自动包含Back  和 title */
public class BaseActivity extends AppCompatActivity {
    /**
     * 日志输出标志getSupportActionBar().
     **/
    private TextView title;
    private ImageView back;
    protected final String TAG = this.getClass().getSimpleName();//获取类名
//设置标题
    protected void setTitle(String msg) {
        if (title != null) {
            title.setText(msg);
        }
    }

    /**
     * sometime you want to define back event
     */
    //为Back添加点击效果
    protected void setBackBtn() {
        if (back != null) {
            back.setVisibility(View.VISIBLE);//设置back为可见状态
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();// 结束当前Activity
                }
            });
        }else {
            Logger.t(TAG).e("back is null ,please check out");//打印日志
        }

    }
//back点击监听器
    protected void setBackClickListener(View.OnClickListener l) {
        if (back != null) {
            back.setVisibility(View.VISIBLE);//
            back.setOnClickListener(l);
        }else {
            Logger.t(TAG).e("back is null ,please check out");
        }

    }

    private LinearLayout rootLayout;//定义线性布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
        initToolbar();
    }
//初始化顶部工具栏
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//获取工具栏
        if (toolbar != null) {//如果工具栏不为空则设置工具栏
            setSupportActionBar(toolbar);
        }
        /*如果顶部ActionBar不为空，设置隐藏ActionBar*/
        if (getSupportActionBar() != null) {
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        //获取Back和title的View
        back = (ImageView) findViewById(R.id.img_back);
        title = (TextView) findViewById(R.id.title);
    }


    @Override
    public void setContentView(int layoutId) {//设置Activity的View的布局通过LayoutId传入参数
        setContentView(View.inflate(this, layoutId, null));
    }

    //设置Activity布局
    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);//查找对应的布局文件
        if (rootLayout == null) return;//如果根布局为空则直接返回
        //在根布局当中添加View
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();//初始化工具栏
    }
}
