package com.honganjk.ynybzbizfood.view.tour.order.adapter;


import android.content.Context;
import android.view.View;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderTourBean;
import com.honganjk.ynybzbizfood.view.tour.detail.activity.OnLinePayActivity;

import java.util.List;

/**
 *
 * Created by Administrator on 2017-11-27.
 */

public class TourOrderAdapter extends CommonAdapter<OrderTourBean.Data.Objs> {

    Context mContext;
    List<OrderTourBean.Data.Objs> mDatas;
    public TourOrderAdapter(Context context, List<OrderTourBean.Data.Objs> datas) {

        super(context,R.layout.item_tour_order_payment, datas);
        mContext = context;
        mDatas = datas;
    }

    public TourOrderAdapter(Context context, int layoutId, OrderTourBean.Data.Objs datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, OrderTourBean.Data.Objs objs) {
        if(objs!=null){
            holder.setText(R.id.tv_order_sn,"丨"+objs.getSn());
            holder.setText(R.id.tv_title,objs.getTitle());
            holder.setText(R.id.tv_date, objs.getOutsetTime());
            //订单状态，0-待付款，1-待进行，2-待评价，3-正常结束，4-退款中，5-退款完成，6-退款被拒
            final OrderTourBean.Data.Objs mObjs = objs;
            switch (objs.getState()){
                case 0:
                    holder.setText(R.id.tv_state,"待付款");
                    holder.getView(R.id.tv_to_pay).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_to_pay).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OnLinePayActivity.StartUi(mContext,mObjs.getId()+"",mObjs.getPrice()+"",mObjs.getTitle());
                        }
                    });

                    break;
                case 1:
                    holder.setText(R.id.tv_state,"待进行");
                    holder.getView(R.id.tv_to_pay).setVisibility(View.GONE);
                    break;
                case 2:
                    holder.setText(R.id.tv_state,"待评价");
                    holder.getView(R.id.tv_to_pay).setVisibility(View.GONE);
                    break;
                case 3:
                    holder.setText(R.id.tv_state,"正常结束");
                    holder.getView(R.id.tv_to_pay).setVisibility(View.GONE);
                    break;
                case 4:
                    holder.setText(R.id.tv_state,"退款中");
                    holder.getView(R.id.tv_to_pay).setVisibility(View.GONE);
                    break;
                case 5:
                    holder.setText(R.id.tv_state,"退款完成");
                    holder.getView(R.id.tv_to_pay).setVisibility(View.GONE);
                    break;
                case 6:
                    holder.setText(R.id.tv_state,"退款被拒");
                    holder.getView(R.id.tv_to_pay).setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public void convert(ViewHolder holder, List<OrderTourBean.Data.Objs> t) {

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
