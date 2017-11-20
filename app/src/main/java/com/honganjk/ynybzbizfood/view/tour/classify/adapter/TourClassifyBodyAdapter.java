package com.honganjk.ynybzbizfood.view.tour.classify.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017-11-16.
 */

public class TourClassifyBodyAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private List<ClassifyTourBean.Data.Objs> objsList;

    public TourClassifyBodyAdapter(Context context, List<ClassifyTourBean.Data.Objs> objsList) {
        this.context = context;
        this.objsList = objsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_tour_classify_body, null);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).bindView(position);
        }
    }

    @Override
    public int getItemCount() {
        return objsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBg;
        TextView tvName;
        TextView tvFeature;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivBg = itemView.findViewById(R.id.iv_bg);
            tvName = itemView.findViewById(R.id.tv_name);
            tvFeature = itemView.findViewById(R.id.tv_feature);
        }

        public void bindView(int position) {
            itemView.setTag(position);
            Picasso.with(context).load(objsList.get(position).getImg()).into(ivBg);
            tvName.setText(objsList.get(position).getName());
            tvFeature.setText(objsList.get(position).getFeature());
        }
    }

    public void changeUI(List<ClassifyTourBean.Data.Objs> objsBeanList) {
        objsList.clear();
        objsList.addAll(objsBeanList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}
