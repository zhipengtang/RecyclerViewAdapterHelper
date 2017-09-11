package com.chad.baserecyclerviewadapterhelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
// 欢迎页面的一个活动
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);//设置欢迎页面活动的布局
        /*
        * 延迟加载HomeActivity
        * 等待两秒钟跳转到首页HomeActivity*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();//结束当前Activity
            }
        },2000);
    }
}
