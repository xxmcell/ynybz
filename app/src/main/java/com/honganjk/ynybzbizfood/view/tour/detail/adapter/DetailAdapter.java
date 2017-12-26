package com.honganjk.ynybzbizfood.view.tour.detail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;

import java.util.List;

import static com.honganjk.ynybzbizfood.R.id.ll;

/**
 * 旅游详情-详情
 * Created by Administrator on 2017-11-28.
 */

public class DetailAdapter extends RecyclerView.Adapter {

    private static final int TYPE_STAR = 1;
    private static final int TYPE_PROFILE = 2;
    private static final int TYPE_TRIP = 3;
    private Context mContext;
    TourDetailBean.DataBean dataBean;
    private final List<TourDetailBean.DataBean.Details> details;


    public DetailAdapter(Context context, TourDetailBean.DataBean dataBean) {
        mContext = context;
        this.dataBean = dataBean;
        details = dataBean.getDetails();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        if (viewType == TYPE_STAR) {
            viewHolder = new StarHolder(LayoutInflater.from(mContext).inflate(R.layout.item_detail_star, parent, false));
        } else if (viewType == TYPE_PROFILE) {

            viewHolder = new ProfileHolder(LayoutInflater.from(mContext).inflate(R.layout.item_detail_profile, parent, false));
        } else {
            viewHolder = new TripHolder(LayoutInflater.from(mContext).inflate(R.layout.item_detail_trip, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof StarHolder) {
            ((StarHolder) holder).bindView();
        } else if (holder instanceof ProfileHolder) {
            ((ProfileHolder) holder).bindView();
        } else {
            ((TripHolder) holder).bindView(position - 2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_STAR;
        } else if (position == 1) {
            return TYPE_PROFILE;
        }
        return TYPE_TRIP;
    }

    @Override
    public int getItemCount() {
        if (dataBean != null) {
            return 2 + details.size();
        } else {
            return 2;
        }

    }

    /**
     * 行程亮点
     */
    public class StarHolder extends ViewHolder {

        private PopupWindow mPopupWindow;
        LinearLayout starGrop;
        private boolean isShowMore = false;
        public StarHolder(View itemView) {
            super(itemView);

        }

        public void bindView() {
            starGrop = itemView.findViewById(ll);
            String feature = dataBean.getFeature();
            String[] split = feature.split("</br>");
            if (starGrop.getChildCount()>=split.length){
                return;
            }
            if (split.length <= 3) {
                itemView.findViewById(R.id.see_all).setVisibility(View.GONE);
                for (int i = 0; i < split.length; i++) {
                    View view = View.inflate(mContext, R.layout.item_tour_star, null);
                    ((TextView) view.findViewById(R.id.tv)).setText(split[i]);
                    starGrop.addView(view);
                }

            } else {
                itemView.findViewById(R.id.see_all).setVisibility(View.VISIBLE);
                for (int i = 0; i < split.length; i++) {
                    View view = View.inflate(mContext, R.layout.item_tour_star, null);
                    ((TextView) view.findViewById(R.id.tv)).setText(split[i]);
                    starGrop.addView(view);
                    if (i>2){
                        starGrop.getChildAt(i).setVisibility(View.GONE);
                    }
                }
                itemView.findViewById(R.id.see_all).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     changeUi();
                    }
                });
            }
        }
        public void changeUi(){
            if (!isShowMore){
                for (int i = 3; i < starGrop.getChildCount(); i++) {
                    starGrop.getChildAt(i).setVisibility(View.VISIBLE);
                }
            }else{
                for (int i = 3; i < starGrop.getChildCount(); i++) {
                    starGrop.getChildAt(i).setVisibility(View.GONE);
                }
            }
            isShowMore = !isShowMore;
        }
    }

    /**
     * 产品概要
     */
    public class ProfileHolder extends ViewHolder {

        public ProfileHolder(View itemView) {
            super(itemView);
        }

        public void bindView() {
            ((TextView)itemView.findViewById(R.id.tv_sights)).setText("共"+dataBean.getViewpoint()+"个丨"+dataBean.getViewpoint()+"个风景名胜区类");
            ((TextView)itemView.findViewById(R.id.tv_catering)).setText("共"+dataBean.getTeam()+"次用餐丨"+dataBean.getSelf()+"次自理");
            ((TextView)itemView.findViewById(R.id.tv_activity)).setText("共"+dataBean.getFree()+"次");
        }
    }

    /**
     * 行程
     */
    public class TripHolder extends ViewHolder {

        public TripHolder(View itemView) {
            super(itemView);
        }

        public void bindView(int position) {
            GlideUtils.show(itemView.findViewById(R.id.iv), details.get(position).getImg());
        }
    }
}
