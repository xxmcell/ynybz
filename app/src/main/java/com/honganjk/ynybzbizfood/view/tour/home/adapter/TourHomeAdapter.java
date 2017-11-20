package com.honganjk.ynybzbizfood.view.tour.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.tour.home.HomeTourBean;
import com.honganjk.ynybzbizfood.view.tour.DiyView.GradientTextView;
import com.honganjk.ynybzbizfood.view.tour.base.adapter.BaseAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by Administrator on 2017-11-15.
 */

public class TourHomeAdapter extends BaseAdapter<HomeTourBean.DataBean.ObjsBean> {
    private Context mContext;
    public static final int HOT_ACTIVITY = 1;
    public static final int NORMAL = 2;

    public TourHomeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == HOT_ACTIVITY) {
            holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_tour_home_hot, parent, false));
        } else if (viewType == NORMAL) {
            holder = new BodyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_tour_home_body, parent, false), viewType);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HOT_ACTIVITY;
        }  else {
            return NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).bind(getDataSet().get(position));
        } else {
            ((BodyViewHolder) holder).bind(getDataSet().get(position));
        }
    }

    class BodyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView tvName;
        TextView tvPrice;

        public BodyViewHolder(View itemView, int viewType) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.iv_picture);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }

        public void bind(HomeTourBean.DataBean.ObjsBean objsBean) {
            ivPicture.setImageDrawable(mContext.getDrawable(R.mipmap.bg));
            ivPicture.setTag(objsBean.img);
            try {
                RequestCreator load = Picasso.with(mContext).load(objsBean.img);
                if (ivPicture.getTag().equals(objsBean.img)){
                    load.into(ivPicture);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            tvName.setText(objsBean.title);
            tvPrice.setText(objsBean.price);
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public ImageView next;
        public TextView tvPrice;
        public TextView tvNum;
        public GradientTextView hot;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            picture = itemView.findViewById(R.id.picture);
            next = itemView.findViewById(R.id.next);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvNum = itemView.findViewById(R.id.tv_num);
            hot = itemView.findViewById(R.id.hot_activity);
            hot.setLeftColor(Color.YELLOW);
            hot.setRightColor(Color.RED);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });
        }

        public void bind(HomeTourBean.DataBean.ObjsBean objsBean) {
            name.setText(objsBean.title);
            try {
                Picasso.with(mContext).load(objsBean.img).into(picture);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvPrice.setText(objsBean.price);
        }
    }
}
