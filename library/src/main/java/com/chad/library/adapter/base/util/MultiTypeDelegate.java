package com.chad.library.adapter.base.util;

import android.support.annotation.LayoutRes;
import android.util.SparseIntArray;

import java.util.List;

import static com.chad.library.adapter.base.BaseMultiItemQuickAdapter.TYPE_NOT_FOUND;

/**
 * help you to achieve multi type easily
 * <p>
 * Created by tysheng
 * Date: 2017/4/6 08:41.
 * Email: tyshengsx@gmail.com
 * <p>
 *
 * more information: https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/968
 */

public abstract class MultiTypeDelegate<T> {

    private static final int DEFAULT_VIEW_TYPE = -0xff;//默认View类型
    private SparseIntArray layouts;//用于存储布局
    private boolean autoMode, selfMode;//自动模式，自有模式
    /*
    * 构造函数，参数为布局*/
    public MultiTypeDelegate(SparseIntArray layouts) {
        this.layouts = layouts;
    }
   /*
   * 构造函数，参数为空*/
    public MultiTypeDelegate() {
    }
//获取默认的View类型
    public final int getDefItemViewType(List<T> data, int position) {
        T item = data.get(position);//获取Position这个位置上面的View
        return item != null ? getItemType(item) : DEFAULT_VIEW_TYPE;//如果Item不为空，则设置Item为默认的View类型
    }

    /**
     * get the item type from specific entity.
     *
     * @param t entity
     * @return item type
     */
    //获取Item类型
    protected abstract int getItemType(T t);
   //获取View的布局Id
    public final int getLayoutId(int viewType) {
        return this.layouts.get(viewType,TYPE_NOT_FOUND);
    }
   //增加Item类型
    private void addItemType(int type, @LayoutRes int layoutResId) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(type, layoutResId);
    }

    /**
     * auto increase type vale, start from 0.
     *
     * @param layoutResIds layout id arrays
     * @return MultiTypeDelegate
     */
    /*
    * 自动增加Item类型值，默认为0开始*/
    public MultiTypeDelegate registerItemTypeAutoIncrease(@LayoutRes int... layoutResIds) {
        autoMode = true;
        checkMode(selfMode);
        for (int i = 0; i < layoutResIds.length; i++) {
            addItemType(i, layoutResIds[i]);
        }
        return this;
    }

    /**
     * set your own type one by one.
     *
     * @param type        type value
     * @param layoutResId layout id
     * @return MultiTypeDelegate
     */
    /*
    *
     * 设置注册Item类型 */
    public MultiTypeDelegate registerItemType(int type, @LayoutRes int layoutResId) {
        selfMode = true;
        checkMode(autoMode);
        addItemType(type, layoutResId);
        return this;
    }
   /*
   * 校验Item Mode */
    private void checkMode(boolean mode) {
        if (mode) {
            throw new RuntimeException("Don't mess two register mode");
        }
    }
}
