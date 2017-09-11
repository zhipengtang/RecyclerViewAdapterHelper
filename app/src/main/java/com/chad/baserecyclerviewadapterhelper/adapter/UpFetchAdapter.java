package com.chad.baserecyclerviewadapterhelper.adapter;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.base.BaseDataBindingAdapter;
import com.chad.baserecyclerviewadapterhelper.databinding.ItemMovieBinding;
import com.chad.baserecyclerviewadapterhelper.entity.Movie;

/**
 * Created by tysheng
 * Date: 2017/5/25 10:47.
 * Email: tyshengsx@gmail.com
 */
/*
* 上拉刷新适配器*/
public class UpFetchAdapter extends BaseDataBindingAdapter<Movie, ItemMovieBinding> {
    public UpFetchAdapter() {
        super(R.layout.item_movie, null);
    }
    //数据绑定后转换数据
    @Override
    protected void convert(ItemMovieBinding binding, Movie item) {
        binding.setMovie(item);//设置Movie ，把Item信息和Movie信息进行绑定
    }
}
