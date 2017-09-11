package com.chad.library.adapter.base;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    /**
     * layouts indexed with their types
     */
    private SparseArray<Integer> layouts;//使用优化过的SparseArray定义Integer类型的布局集合,存储我们的布局资源的ids

    private static final int DEFAULT_VIEW_TYPE = -0xff;
    public static final int TYPE_NOT_FOUND = -404;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseMultiItemQuickAdapter(List<T> data) {
        super(data);
    }
    //获取默认的View类型
    @Override
    protected int getDefItemViewType(int position) {
        Object item = mData.get(position);//获取对应Position的位置
        if (item instanceof MultiItemEntity) {
            return ((MultiItemEntity) item).getItemType();//返回Item类型
        }
        return DEFAULT_VIEW_TYPE;//返回默认的View类型
    }
    //设置默认的View类型
    protected void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addItemType(DEFAULT_VIEW_TYPE, layoutResId);
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType,TYPE_NOT_FOUND);
    }
    //添加Item
    /*
    * addItemType()将不同的布局资源的ids和对应的类型值存储起来。
      所以我们的创建多布局的时候，
      需要的构造函数中调用addItemType来添加不同的布局资源*/
    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {//当布局为空
            layouts = new SparseArray<>();//新建layouts
        }
        layouts.put(type, layoutResId);//把布局Id添加到layouts当中
    }
}


