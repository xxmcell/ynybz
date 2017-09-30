package com.honganjk.ynybzbizfood.view.store.order.adapter;

import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.view.store.classify.activity.ClassifyActivity;
import com.honganjk.ynybzbizfood.view.store.order.fragment.StoreOrderFragment;

import java.util.List;

/**
 * 说明:陪护-我的订单-适配器
 * 360621904@qq.com 杨阳 2017/3/24  14:17
 */
public class StoreOrderAdapter extends CommonAdapter<StoreOrderData2.ObjsBean> {
    StoreOrderFragment mContext;
    int type;


    public StoreOrderAdapter(StoreOrderFragment context, List<StoreOrderData2.ObjsBean> datas, int mtype) {

        super(context.getContext(), R.layout.store_item_order, datas);

        mContext = context;
    }

    private StoreOrderData2.ObjsBean.DetailsBean datas;
    private StoreOrderData2.ObjsBean.DetailsBean.ListBean Data;
    @Override
    public void convert(final ViewHolder holder, final StoreOrderData2.ObjsBean data) {

        for (int i = 0; i < data.getDetails().size(); i++) {
             datas = data.getDetails().get(i);
        }
        if(datas==null){
            return;
        }else {
        for (int i1 = 0; i1 < datas.getList().size(); i1++) {

            Data = datas.getList().get(i1);
        }
    }

        holder.setImageBitmapRound(R.id.picture, Data.getImg()
                 , 5);
        holder.setImageBitmapRound(R.id.businessLogo, datas.getIcon(), 5);
        holder.setText(R.id.name, datas.getFeature());
        holder.setText(R.id.type, Data.getTitle());
        holder.setText(R.id.text,"规格"+Data.getLabel());

        holder.setText(R.id.price, Data.getPrice());

        holder.setText(R.id.orderStatus, data.getStateStr());

        holder.setText(R.id.presentPrice, Data.getMoneyStr());
        Data.getPriceStr((TextView) holder.getView(R.id.originalPrice));
        holder.setText(R.id.number, Data.getNumStr());
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

        boundary1.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);
//        "全部", "0待付款", "1待发货", "2待收货", "3待评价"
       type=data.getState();
        if(type==0){
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.VISIBLE);
        }
        if(type==1){
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.GONE);
        }
        if(type==2){
            statusGray.setText("查看物流");
            statusGreen.setText("确认收货");
            statusGray.setVisibility(View.VISIBLE);
            statusGreen.setVisibility(View.VISIBLE);
        }
        if(type==3){
            statusGray.setText("查看物流");
            statusGreen.setText("去评价");
            statusGray.setVisibility(View.GONE);
            statusGreen.setVisibility(View.VISIBLE);
        }
        data.setViewShowStatus(statusGray, statusGreen, boundary1);

        //取消订单，删除订单,查看物流
        statusGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.buttonClickEvent(mContext.getActivity(), statusGray.getText().toString(), data,Data, mContext.presenter);
            }
        });

        //支付金额，确认收货，去评价
        statusGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.buttonClickEvent(mContext.getActivity(), statusGreen.getText().toString(),data,Data, mContext.presenter);
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
