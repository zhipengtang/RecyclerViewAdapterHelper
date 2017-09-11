package com.chad.library.adapter.base.loadmore;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by BlingBling on 2016/11/11.
 */
//// 滑动最后一个Item的时候回调onLoadMoreRequested方法
public abstract class LoadMoreView {

    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;

    private int mLoadMoreStatus = STATUS_DEFAULT;//设置初始值为默认的状态
    private boolean mLoadMoreEndGone = false;//加载更多设置为false

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void convert(BaseViewHolder holder) {
        switch (mLoadMoreStatus) {
            case STATUS_LOADING://如果在加载中，则加载失败和结束都设置为false
                visibleLoading(holder, true);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_FAIL:
                visibleLoading(holder, false);
                visibleLoadFail(holder, true);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_END://加载成功的VisibleLoadEnd为真表示加载完成
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, true);
                break;
            case STATUS_DEFAULT://初始化默认情况下所有的Loading状态都为false
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
        }
    }
    //加载中设置为Visible
    private void visibleLoading(BaseViewHolder holder, boolean visible) {
        holder.setVisible(getLoadingViewId(), visible);
    }
   //加载失败
    private void visibleLoadFail(BaseViewHolder holder, boolean visible) {
        holder.setVisible(getLoadFailViewId(), visible);
    }
  //加载结束
    private void visibleLoadEnd(BaseViewHolder holder, boolean visible) {
        final int loadEndViewId=getLoadEndViewId();
        if (loadEndViewId != 0) {
            holder.setVisible(loadEndViewId, visible);
        }
    }

    public final void setLoadMoreEndGone(boolean loadMoreEndGone) {
        this.mLoadMoreEndGone = loadMoreEndGone;
    }

    public final boolean isLoadEndMoreGone(){
        if(getLoadEndViewId()==0){
            return true;
        }
        return mLoadMoreEndGone;}

    /**
     * No more data is hidden
     * @return true for no more data hidden load more
     * @deprecated Use {@link BaseQuickAdapter#loadMoreEnd(boolean)} instead.
     */
    @Deprecated
    public boolean isLoadEndGone(){return mLoadMoreEndGone;}

    /**
     * load more layout
     *
     * @return
     */
    public abstract @LayoutRes int getLayoutId();

    /**
     * loading view
     *
     * @return
     */
    protected abstract @IdRes int getLoadingViewId();

    /**
     * load fail view
     *
     * @return
     */
    protected abstract @IdRes int getLoadFailViewId();

    /**
     * load end view, you can return 0
     *
     * @return
     */
    protected abstract @IdRes int getLoadEndViewId();
}
