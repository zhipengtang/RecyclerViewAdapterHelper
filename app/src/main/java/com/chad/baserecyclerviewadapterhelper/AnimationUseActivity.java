package com.chad.baserecyclerviewadapterhelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.baserecyclerviewadapterhelper.adapter.AnimationAdapter;
import com.chad.baserecyclerviewadapterhelper.animation.CustomAnimation;
import com.chad.baserecyclerviewadapterhelper.entity.Status;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kyleduo.switchbutton.SwitchButton;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * <p>
 * modify by AllenCoder
 */
//动画Animation活动类
public class AnimationUseActivity extends Activity {
    private RecyclerView mRecyclerView;//定义mRecyclerView变量
    private AnimationAdapter mAnimationAdapter;//定义动画适配器
    private ImageView mImgBtn;//定义图片View
    private int mFirstPageItemCount = 3;//第一页的Item数量设置为3个

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_use);//使用布局
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);//设置mRecyclerView每一页的Item是否有固定的大小
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置mRecyclerView的布局为线性管理布局
        initAdapter();//初始化适配器
        initMenu();//初始化菜单
        initView();//初始化View
    }

    private void initView() {

        mImgBtn = (ImageView) findViewById(R.id.img_back);
        mImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                finish();//结束当前的Activity
            }
        });
    }

    private void initAdapter() {
        mAnimationAdapter = new AnimationAdapter();//实例化动画适配器类
        mAnimationAdapter.openLoadAnimation();//开启加载动画
        mAnimationAdapter.setNotDoAnimationCount(mFirstPageItemCount);//设置初始时Item的数量为3个
        //为Item添加点击事件
        mAnimationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                Status status = (Status) adapter.getItem(position);//获取点击了那个Item
               //利用Swith判断点击了Item下的哪个部分然后对Item下的图片，Name,Text,做出不同的响应
                switch (view.getId()) {
                    case R.id.img:
                        content = "img:" + status.getUserAvatar();
                        Toast.makeText(AnimationUseActivity.this, content, Toast.LENGTH_LONG).show();
                        break;
                    case R.id.tweetName:
                        content = "name:" + status.getUserName();
                        Toast.makeText(AnimationUseActivity.this, content, Toast.LENGTH_LONG).show();
                        break;
                    case R.id.tweetText:
                        content = "tweetText:" + status.getUserName();
                        Toast.makeText(AnimationUseActivity.this, content, Toast.LENGTH_LONG).show();
                        // you have set clickspan .so there should not solve any click event ,just empty
                        break;

                }
            }
        });
        mRecyclerView.setAdapter(mAnimationAdapter);//在AnimationUseActivity中使用mAnimationAdapter适配器
    }

    //初始化页面顶部的动画选择菜单
    private void initMenu() {
        //使用项目库当中的MaterialSpinner获取下拉列表的Id;
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        //设置Item载入的模式
        spinner.setItems("AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "Custom");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
               //根据下拉列表当中选择的不同载入模式让Item载入做出不同的载入效果
                switch (position) {
                    case 0:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                        break;
                    case 1:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        break;
                    case 2:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                        break;
                    case 3:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                        break;
                    case 4:
                        mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                        break;
                    case 5:
                        mAnimationAdapter.openLoadAnimation(new CustomAnimation());
                        break;
                    default:
                        break;
                }
                mRecyclerView.setAdapter(mAnimationAdapter);
            }
        });
        //初始化是否第一次装载Item，默认初始化为对所有载入都使用列表当中选择的载入效果
        mAnimationAdapter.isFirstOnly(false);//init firstOnly state
        SwitchButton switchButton = (SwitchButton) findViewById(R.id.switch_button);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                //判断是否选择了isFirstOnly按钮
                if (isChecked) {
                    //如果选择了则设置为true，此时只有第一次载入Item时使用这个动画效果
                    mAnimationAdapter.isFirstOnly(true);
                } else {
                    //未选择设置为false，此时对每次载入Item都使用一个动画效果
                    mAnimationAdapter.isFirstOnly(false);
                }
                mAnimationAdapter.notifyDataSetChanged();//设置使得列表当中选择的动画效果生效
            }
        });

    }

}
