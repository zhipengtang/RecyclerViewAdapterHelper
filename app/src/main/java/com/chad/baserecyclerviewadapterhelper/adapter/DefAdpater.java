package com.chad.baserecyclerviewadapterhelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.data.DataServer;
import com.chad.baserecyclerviewadapterhelper.entity.Status;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
/*
* 默认适配器类*/
public class DefAdpater extends RecyclerView.Adapter<DefAdpater.ViewHolder> {
    private final List<Status> sampleData = DataServer.getSampleData(100);//获取100个数据样本
    private Context mContext;//定义上下文mContext
    private LayoutInflater mLayoutInflater;//定义自动加载布局变量mLayoutInflater
    public DefAdpater(Context context) {//构造函数
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
/*
* onCreateViewHolder这个函数当中主要是用来创建ViewHolder的*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.layout_animation, parent, false);//自动加载布局，其中false表示不在父布局里面嵌套

        return new ViewHolder(item);//返回ViewHolder
    }
/*
* 绑定ViewHolder*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Status status = sampleData.get(position);//获取Position这个位置的状态
        holder.name.setText(status.getUserName());//设置holder的名字
        holder.text.setText(status.getText());//设置holder的文本信息
        holder.date.setText(status.getCreatedAt());//设置Holder数据部分的文本信息
    }

    @Override
    public int getItemCount() {
        return sampleData.size();//返回集合当中的数据总量
    }
    /*定义一个表示每一个Item的Holder，用来承载Item当中的数据
    * 包含有name data  text*/
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView date;
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tweetText);
            name = (TextView) itemView.findViewById(R.id.tweetName);
            date = (TextView) itemView.findViewById(R.id.tweetDate);

        }
    }
}