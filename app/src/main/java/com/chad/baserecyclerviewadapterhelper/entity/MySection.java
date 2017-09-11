package com.chad.baserecyclerviewadapterhelper.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
/*MySection 实体类，用于Section当中的数据类型
* isMore标识Header中是否还有更多的内容显示*/
public class MySection extends SectionEntity<Video> {
    private boolean isMore;//判断是否还有内容和isHeader配合使用
    public MySection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public MySection(Video t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }
    //设置是否还有更多View可展开
    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
