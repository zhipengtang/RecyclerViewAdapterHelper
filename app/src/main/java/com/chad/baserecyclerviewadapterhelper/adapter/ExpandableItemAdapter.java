package com.chad.baserecyclerviewadapterhelper.adapter;

import android.util.Log;
import android.view.View;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.entity.Level0Item;
import com.chad.baserecyclerviewadapterhelper.entity.Level1Item;
import com.chad.baserecyclerviewadapterhelper.entity.Person;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by luoxw on 2016/8/9.
 */

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();
/*定义Item三层结构
* 分别为Level0 Level1 level_person*/
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {//构造函数，初始化添加数据
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
        addItemType(TYPE_PERSON, R.layout.item_expandable_lv2);
    }


/*
* 1，最外层进行viewholder 的类型判断进行数据绑定
  2，添加点击事件的监听
  3，当被点击时，判断当前的levelitem是不是展开的或折叠的，然后根据你的需要调用collapse或者expand进行折叠或展开操作。*/
    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0://根据Item的位置选择图片作为Item的头像图片
                switch (holder.getLayoutPosition() %
                        3) {
                    case 0:
                        holder.setImageResource(R.id.iv_head, R.mipmap.head_img0);
                        break;
                    case 1:
                        holder.setImageResource(R.id.iv_head, R.mipmap.head_img1);
                        break;
                    case 2:
                        holder.setImageResource(R.id.iv_head, R.mipmap.head_img2);
                        break;
                }
                final Level0Item lv0 = (Level0Item)item;
                holder.setText(R.id.title, lv0.title)//设置标题文本信息
                        .setText(R.id.sub_title, lv0.subTitle)//设置次标题文本信息
                        .setImageResource(R.id.iv, lv0.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);//如果Item可以扩展完成，设置背景图片为向下的箭头，如果Item还未扩展，设置背景图片为横向箭头
                holder.itemView.setOnClickListener(new View.OnClickListener() {//添加点击事件监听器
                            @Override
                            public void onClick(View v) {
                                int pos = holder.getAdapterPosition();//获取被点击Item的位置
                                Log.d(TAG, "Level 0 item pos: " + pos);
                                if (lv0.isExpanded()) {
                                    collapse(pos);//折叠子Item
                                } else {
//                            if (pos % 3 == 0) {
//                                expandAll(pos, false);
//                            } else {
                                    expand(pos);//扩展子Item
//                            }
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item)item;
                holder.setText(R.id.title, lv1.title)//设置标题文本信息
                        .setText(R.id.sub_title, lv1.subTitle)//设置次标题文本信息
                        .setImageResource(R.id.iv, lv1.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);//根据是否处于扩张状态设置背景图片
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//添加点击事件监听器
                        int pos = holder.getAdapterPosition();//获取被点击Item的对应位置pos
                        Log.d(TAG, "Level 1 item pos: " + pos);
                        if (lv1.isExpanded()) {//判断是否已经扩展，如果扩展点击后就隐藏SubItem
                            collapse(pos, false);//false表示不使用动画效果
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case TYPE_PERSON:
                final Person person = (Person)item;//获取item当中person部分的实例
                holder.setText(R.id.tv, person.name + " parent pos: " + getParentPosition(person));//Level1当中Person数据显示效果
                holder.itemView.setOnClickListener(new View.OnClickListener() {//person部分点击事件监听器
                  /*
                  * person点击事件。当person点击时就删除person*/
                    @Override
                    public void onClick(View view) {
                        int cp = getParentPosition(person); //获得Person的父位置
                        ((Level1Item)getData().get(cp)).removeSubItem(person);//删除子SubItem
                        getData().remove(holder.getLayoutPosition());//同时删除SubItem所占的Layout,释放空间
                        notifyItemRemoved(holder.getLayoutPosition());//通知Item已经被删除
                    }
                });
                break;
        }
    }
}
