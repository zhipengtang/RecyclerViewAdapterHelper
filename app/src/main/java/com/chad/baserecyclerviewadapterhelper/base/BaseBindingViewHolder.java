package com.chad.baserecyclerviewadapterhelper.base;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by tysheng
 * Date: 2017/5/11 14:42.
 * Email: tyshengsx@gmail.com
 */
/*数据绑定类
* 继承BaseViewHolder*/
public class BaseBindingViewHolder<Binding extends ViewDataBinding> extends BaseViewHolder {
    private Binding mBinding;

    public BaseBindingViewHolder(View view) {
        super(view);
    }

    public Binding getBinding() {//获取数据绑定
        return mBinding;
    }

    public void setBinding(Binding binding) {//设置数据绑定
        mBinding = binding;
    }
}
