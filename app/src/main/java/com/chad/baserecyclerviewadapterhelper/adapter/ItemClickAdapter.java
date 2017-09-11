package com.chad.baserecyclerviewadapterhelper.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.entity.ClickEntity;
import com.chad.baserecyclerviewadapterhelper.util.Utils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 *Item点击事件适配器
 */
public class ItemClickAdapter extends BaseMultiItemQuickAdapter<ClickEntity, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    NestAdapter nestAdapter;//Item内部嵌套适配器

    public ItemClickAdapter(List<ClickEntity> data) {
        super(data);
        addItemType(ClickEntity.CLICK_ITEM_VIEW, R.layout.item_click_view);//单击Item
        addItemType(ClickEntity.CLICK_ITEM_CHILD_VIEW, R.layout.item_click_childview);//单击Item子项按钮
        addItemType(ClickEntity.LONG_CLICK_ITEM_VIEW, R.layout.item_long_click_view);//长按Item
        addItemType(ClickEntity.LONG_CLICK_ITEM_CHILD_VIEW, R.layout.item_long_click_childview);//长按item Button
        addItemType(ClickEntity.NEST_CLICK_ITEM_CHILD_VIEW, R.layout.item_nest_click);//长按Item Check

    }

/*
* Item点击事件判断*/
    @Override
    protected void convert(final BaseViewHolder helper, final ClickEntity item) {
        switch (helper.getItemViewType()) {
            case ClickEntity.CLICK_ITEM_VIEW://单击的是Item VIEW
                helper.addOnClickListener(R.id.btn);
                break;
            case ClickEntity.CLICK_ITEM_CHILD_VIEW://单击的是子Item View
                helper.addOnClickListener(R.id.iv_num_reduce).addOnClickListener(R.id.iv_num_add)
                        .addOnLongClickListener(R.id.iv_num_reduce).addOnLongClickListener(R.id.iv_num_add);
                // set img data
                break;
            case ClickEntity.LONG_CLICK_ITEM_VIEW://长按Item View
                helper.addOnLongClickListener(R.id.btn);
                break;
            case ClickEntity.LONG_CLICK_ITEM_CHILD_VIEW://长按Item的子View
                helper.addOnLongClickListener(R.id.iv_num_reduce).addOnLongClickListener(R.id.iv_num_add)
                        .addOnClickListener(R.id.iv_num_reduce).addOnClickListener(R.id.iv_num_add);
                break;
            case ClickEntity.NEST_CLICK_ITEM_CHILD_VIEW://单击了Nest View
                helper.setNestView(R.id.item_click); // u can set nestview id
                final RecyclerView recyclerView = helper.getView(R.id.nest_list);
                //设置为线性布局
                recyclerView.setLayoutManager(new LinearLayoutManager(helper.itemView.getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);

                nestAdapter = new NestAdapter();
                nestAdapter.setOnItemClickListener(this);
                nestAdapter.setOnItemChildClickListener(this);
                recyclerView.setAdapter(nestAdapter);//设置适配器
                break;
        }
    }
/*
* 子项Item点击事件*/
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(Utils.getContext(), "childView click", Toast.LENGTH_SHORT).show();
    }


    /*Item 点击事件*/
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Logger.d("嵌套RecycleView item 收到: " + "点击了第 " + position + " 一次");
        Toast.makeText(Utils.getContext(), "嵌套RecycleView item 收到: " + "点击了第 " + position + " 一次", Toast.LENGTH_SHORT).show();
    }
}
