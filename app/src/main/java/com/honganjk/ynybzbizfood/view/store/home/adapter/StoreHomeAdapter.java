package com.honganjk.ynybzbizfood.view.store.home.adapter;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;

import java.util.List;

/**
 * 说明:
 * 作者： 杨阳; 创建于：  2017-06-29  15:41
 */
public class StoreHomeAdapter extends CommonAdapter<StoreHomeData.ObjsBean> {
    BaseActivity mContext;

    public StoreHomeAdapter(BaseActivity context, List<StoreHomeData.ObjsBean> datas) {
        super(context, R.layout.store_item_home, datas);
        mContext = context;
    }
    @Override
    public void convert(ViewHolder holder, final StoreHomeData.ObjsBean data) {
        holder.setImageBitmapRound(R.id.picture, data.getImg(), 5);
        holder.setText(R.id.name, data.getTitle());
        holder.setText(R.id.price, data.getMoney());
    }

    @Override
    public void convert(ViewHolder holder, List<StoreHomeData.ObjsBean> t) {

    }
}
