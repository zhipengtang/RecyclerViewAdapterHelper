package com.chad.baserecyclerviewadapterhelper.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.baserecyclerviewadapterhelper.BR;
import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.entity.Movie;
import com.chad.baserecyclerviewadapterhelper.entity.MoviePresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by luoxiongwen on 16/10/24.
 */

public class DataBindingUseAdapter extends BaseQuickAdapter<Movie, DataBindingUseAdapter.MovieViewHolder> {

    private MoviePresenter mPresenter;//电影演出类

    public DataBindingUseAdapter(int layoutResId, List<Movie> data) {
        super(layoutResId, data);

        mPresenter = new MoviePresenter();//实例化一个电影表演类
    }

    @Override
    protected void convert(MovieViewHolder helper, Movie item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.movie, item);//绑定电影
        binding.setVariable(BR.presenter, mPresenter);//绑定表演演员
        binding.executePendingBindings();
        //img1和img2分别交替使用
        switch (helper.getLayoutPosition() %
                2) {
            case 0://位置对2求余为0时使用m_img1
                helper.setImageResource(R.id.iv, R.mipmap.m_img1);
                break;
            case 1://位置对2求余为1时使用m_img2
                helper.setImageResource(R.id.iv, R.mipmap.m_img2);
                break;

        }
    }

    /*  @Override
      protected MovieViewHolder createBaseViewHolder(View view) {
          return new MovieViewHolder(view);
      }
  */
    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
       //动态加载布局
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {//未绑定时重新进行绑定
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();//获得Item的根View
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    public static class MovieViewHolder extends BaseViewHolder {

        public MovieViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {//获取绑定了的View
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
