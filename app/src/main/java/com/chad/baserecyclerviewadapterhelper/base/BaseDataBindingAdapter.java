package com.chad.baserecyclerviewadapterhelper.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;


/**
 * Created by tysheng
 * Date: 2017/5/11 14:39.
 * Email: tyshengsx@gmail.com
 */
//数据绑定适配器
public abstract class BaseDataBindingAdapter<T, Binding extends ViewDataBinding> extends BaseQuickAdapter<T, BaseBindingViewHolder<Binding>> {


    public BaseDataBindingAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);//构造函数继承父类的方法
    }
    /*
    * 构造函数参数为传入的数据集合*/
    public BaseDataBindingAdapter(@Nullable List<T> data) {
        super(data);
    }
/*
* 构造函数参数为传入的布局Id*/
    public BaseDataBindingAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected BaseBindingViewHolder<Binding> createBaseViewHolder(View view) {
        return new BaseBindingViewHolder<>(view);//返回绑定的ViewHolder
    }
   //将数据和ViewHolder中的数据进行绑定
    @Override
    protected BaseBindingViewHolder<Binding> createBaseViewHolder(ViewGroup parent, int layoutResId) {
        //动态数据绑定
        Binding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        View view;
        if (binding == null) {//如果还没有进行数据绑定就先获取对应Item上面的View
            view = getItemView(layoutResId, parent);//获取View
        } else {
            view = binding.getRoot();//和根View进行绑定
        }
        BaseBindingViewHolder<Binding> holder = new BaseBindingViewHolder<>(view);
        holder.setBinding(binding);//设置绑定
        return holder;//返回ViewHolder
    }
   //数据转换
    @Override
    protected void convert(BaseBindingViewHolder<Binding> helper, T item) {
        convert(helper.getBinding(), item);
        helper.getBinding().executePendingBindings();//延迟绑定数据
    }
    //定义抽象类绑定Item
    protected abstract void convert(Binding binding, T item);
}
