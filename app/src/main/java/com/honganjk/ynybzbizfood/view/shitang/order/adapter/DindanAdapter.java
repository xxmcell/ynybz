package com.honganjk.ynybzbizfood.view.shitang.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/13.
 */

public class DindanAdapter extends RecyclerView.Adapter<DindanAdapter.DinDanViewHolder> {
    private ArrayList<FoodData.DishsBeanX.DishsBean> mDindanInfo;
    private Context context;

    public DindanAdapter(Context context, ArrayList<FoodData.DishsBeanX.DishsBean> mDindanInfo) {
        this.mDindanInfo = mDindanInfo;
        this.context = context;
    }

    @Override
    public DinDanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_dindan, null);
        return new DinDanViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mDindanInfo.size();
    }

    @Override
    public void onBindViewHolder(DinDanViewHolder holder, int position) {
        FoodData.DishsBeanX.DishsBean dishsBean = mDindanInfo.get(position);
        holder.tv_name.setText(dishsBean.getName());
        holder.tv_num.setText(dishsBean.getNum()+"");
        holder.Ttv_price.setText(dishsBean.getPriceStr());

    }

    class DinDanViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_num,Ttv_price;

        public DinDanViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            Ttv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
