package com.honganjk.ynybzbizfood.view.tour.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderDetailBean;

/**
 * Created by Administrator on 2017-11-30.
 */

public class TourOrderDetailAdapter extends RecyclerView.Adapter{

    public final int TYPE_PASSENGER = 0;
    public final int TYPE_TRAFFIC = 1;
    public final int TYPE_HOTEL = 2;
    public final int TYPE_TICKET = 3;
    public final int TYPE_INSURANCE = 4;
    Context mContext;
    OrderDetailBean.Data mData;

    public TourOrderDetailAdapter(Context context,OrderDetailBean.Data data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
