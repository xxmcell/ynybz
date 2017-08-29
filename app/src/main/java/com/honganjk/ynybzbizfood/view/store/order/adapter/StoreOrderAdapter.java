package com.honganjk.ynybzbizfood.view.store.order.adapter;

import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData;
import com.honganjk.ynybzbizfood.view.store.classify.activity.ClassifyActivity;
import com.honganjk.ynybzbizfood.view.store.order.fragment.StoreOrderFragment;

import java.util.List;

/**
 * 说明:陪护-我的订单-适配器
 * 360621904@qq.com 杨阳 2017/3/24  14:17
 */
public class StoreOrderAdapter extends CommonAdapter<StoreOrderData.ObjsBean> {
    StoreOrderFragment mContext;

    public StoreOrderAdapter(StoreOrderFragment context, List<StoreOrderData.ObjsBean> datas) {
        super(context.getContext(), R.layout.store_item_order, datas);
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final StoreOrderData.ObjsBean data) {
        holder.setImageBitmapRound(R.id.picture, data.getImg(), 5);
        holder.setImageBitmapRound(R.id.businessLogo, data.getIcon(), 5);
        holder.setText(R.id.name, data.getFeature());
        holder.setText(R.id.type, data.getTitle());
//        holder.setText(R.id.text, data.getState());
        holder.setText(R.id.price, data.getSumMoneyStr());
        holder.setText(R.id.orderStatus, data.getStateStr());

        holder.setText(R.id.presentPrice, data.getMoneyStr());
        data.getPriceStr((TextView) holder.getView(R.id.originalPrice));
        holder.setText(R.id.number, data.getNumStr());
        /**
         * 灰色按钮
         * 取消订单，删除订单,查看物流
         */
        final TextView statusGray = holder.getView(R.id.cancel);
        /**
         * 绿色按钮
         * 支付金额，确认收货，去评价
         */
        final TextView statusGreen = holder.getView(R.id.status);
        View boundary1 = holder.getView(R.id.boundary1);//分割线
        TextView price = holder.getView(R.id.price);//价格
        statusGray.setVisibility(View.VISIBLE);
        statusGreen.setVisibility(View.VISIBLE);
        boundary1.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
        data.setViewShowStatus(statusGray, statusGreen, boundary1);

        //取消订单，删除订单,查看物流
        statusGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.buttonClickEvent(mContext.getActivity(), statusGray.getText().toString(), data, mContext.presenter);
            }
        });

        //支付金额，确认收货，去评价
        statusGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.buttonClickEvent(mContext.getActivity(), statusGreen.getText().toString(), data, mContext.presenter);
            }
        });

        //进入分类
        holder.setOnClickListener(R.id.name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassifyActivity.startUI(mContext.getActivity());
            }
        });


    }


}
