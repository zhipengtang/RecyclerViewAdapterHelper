package com.chad.library.adapter.base;

import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class BaseSectionQuickAdapter<T extends SectionEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {


    protected int mSectionHeadResId;//mSectionHeadResId用来保存我们分组头的布局资源ids
    protected static final int SECTION_HEADER_VIEW = 0x00000444;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public BaseSectionQuickAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, data);
        this.mSectionHeadResId = sectionHeadResId;
    }
    //获取默认的ItemView类型
    /*
    * 根据我们当前位置的数据bean，判断当前节点的数据bean是不是分组头bean，
    * 如果是，返回SECTIONHEADERVIEW告诉BaseQuickAdapter，
    * 你要创建的viewholder是分组头类型的viewholder。
    * 否则返回0（0时RecyclerView.Adapter的缺省值)*/
    @Override
    protected int getDefItemViewType(int position) {
        return mData.get(position).isHeader ? SECTION_HEADER_VIEW : 0;
    }
    //创建默认的View Holder
    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_HEADER_VIEW)//根据返回的类型值，如果是SECTIONHEADERVIEW 那么我们就创建一个分组头viewholder返回。
            // 否则调用父类的方法按原流程走。
            return createBaseViewHolder(getItemView(mSectionHeadResId, parent));

        return super.onCreateDefViewHolder(parent, viewType);
    }
    //判断是否是固定类型
    @Override
    protected boolean isFixedViewType(int type) {
        return super.isFixedViewType(type)|| type == SECTION_HEADER_VIEW;
    }
    //绑定ViewHolder
    /*
    * 1、对我们的分组头进行特殊处理；

     2、增加一个分组头数据绑定的抽象方法的调用；
     */
    @Override
    public void onBindViewHolder(K holder, int positions) {
        switch (holder.getItemViewType()) {
            case SECTION_HEADER_VIEW:
                setFullSpan(holder);//设置充满空间
               /* \
               * getLayoutPosition是干什么用的呢，因为RecyclerView的item的布局和渲染其实是交给layoutManager来完成的，
               * 所以adapter中的item的位置可能跟data的index匹配不上，
               * 而getLayoutPosition将返回给我们当前viewholder在recyclerView中的最新位置信息。*/
                convertHead(holder, mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                break;
            default:
                super.onBindViewHolder(holder, positions);
                break;
        }
    }

    protected abstract void convertHead(K helper, T item);

}
