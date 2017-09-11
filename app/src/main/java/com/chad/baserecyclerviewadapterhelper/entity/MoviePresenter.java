package com.chad.baserecyclerviewadapterhelper.entity;

import android.view.View;
import android.widget.Toast;

/**
 * Created by luoxiongwen on 16/10/24.
 */
//用于DataBindingUse Actvity 活动实体
public class MoviePresenter {
    //购票方法
    public void buyTicket(View view, Movie movie) {
        //点击Item当中的购物车用Toast弹出对话
        Toast.makeText(view.getContext(), "buy ticket: " + movie.name, Toast.LENGTH_SHORT).show();
    }
}
