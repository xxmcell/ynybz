package com.honganjk.ynybzbizfood.view.tour.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.view.tour.DiyView.GradientTextView;
import com.honganjk.ynybzbizfood.view.tour.detail.activity.DetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28.
 */

public class HomeTourAdapter extends CommonAdapter<ObjsBean> {
    Context mContext;

    public HomeTourAdapter(Context context, List<ObjsBean> datas) {
        super(context, R.layout.item_tour_home_body, datas);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, final ObjsBean objsBean) {
        if (holder.getmPosition() > 0) {
            holder.getView(R.id.tv).setVisibility(View.GONE);
        }else{
            ((GradientTextView)holder.getView(R.id.tv)).setLeftColor(Color.YELLOW);
            ((GradientTextView)holder.getView(R.id.tv)).setRightColor(Color.BLUE);
        }
        holder.setImageBitmap(R.id.iv_picture, objsBean.getImg());
        holder.setText(R.id.tv_name, objsBean.getTitle());
        holder.setText(R.id.tv_price, objsBean.getPrice());
        holder.setOnClickListener(R.id.ll, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("objs", objsBean);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void convert(ViewHolder holder, List<ObjsBean> t) {

    }
}
