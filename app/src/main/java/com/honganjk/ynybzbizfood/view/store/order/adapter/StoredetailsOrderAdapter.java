package com.honganjk.ynybzbizfood.view.store.order.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.MyApplication;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;

/**
 * Created by Administrator on 2017-10-24.
 */

public class StoredetailsOrderAdapter extends RecyclerView.Adapter<StoredetailsOrderAdapter.MydetailsHolder>{
    StoreOrderData2.ObjsBean.DetailsBean detailsBean;


    public StoredetailsOrderAdapter(StoreOrderData2.ObjsBean.DetailsBean detailsBean) {
        this.detailsBean=detailsBean;
    }

    @Override
    public MydetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MydetailsHolder mydetailsHolder=new MydetailsHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.store_item_order_items,null,false));
        return mydetailsHolder;
    }

    @Override
    public void onBindViewHolder(MydetailsHolder holder, int position) {

        GlideUtils.show(holder.picture,detailsBean.getList().get(position).getImg());
        holder.type.setText(detailsBean.getList().get(position).getTitle());
        holder.presentPrice.setText("¥"+detailsBean.getList().get(position).getMoney());
        holder.text.setText(detailsBean.getList().get(position).getLabel());
        detailsBean.getList().get(position).getPriceStr(holder.originalPrice);
        holder.number.setText(detailsBean.getList().get(position).getNumStr());

    }

    @Override
    public int getItemCount() {
        return detailsBean.getList().size();
    }

    public class MydetailsHolder extends RecyclerView.ViewHolder{

        private final ImageView picture;
        private final TextView type;
        private final TextView text;
        private final TextView presentPrice;
        private final TextView originalPrice;
        private final TextView number;

        public MydetailsHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.picture);
            type = itemView.findViewById(R.id.type);
            text = itemView.findViewById(R.id.text);
            presentPrice = itemView.findViewById(R.id.presentPrice);
            originalPrice = itemView.findViewById(R.id.originalPrice);
            number = itemView.findViewById(R.id.number);
        }
    }
}
