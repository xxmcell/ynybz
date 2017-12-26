package com.honganjk.ynybzbizfood.view.tour.classify.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;

import java.util.List;

/**
 * Created by Administrator on 2017-11-20.
 */

public class TourClassifyDetailAdapter extends CommonAdapter<ObjsBean> {

    private Context mContext;

    public TourClassifyDetailAdapter(Context context, List<ObjsBean> datas) {
        super(context, R.layout.item_tour_classify_detail, datas);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, ObjsBean objs) {
        holder.setImageBitmapRound(R.id.iv_picture, objs.getImg(), 5);
        holder.setText(R.id.tv_title, objs.getTitle());
        holder.setText(R.id.tv_price, objs.getPrice() + "");
        holder.setText(R.id.tv_sales, objs.getSales() + "人出游");
        String[] split = objs.getTags().split("-");
        /* <TextView
                android:paddingTop="@dimen/dp_1"
                android:paddingBottom="@dimen/dp_1"
                android:paddingLeft="@dimen/dp_5"
                android:paddingRight="@dimen/dp_5"
                android:id="@+id/tv_tag1"
                android:text="@string/wugouwu"
                android:textSize="10sp"
                android:background="@drawable/bg_shape_corner"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content" />*/
        for (int i = 0; i < split.length; i++) {
            if (i > 2) {
                return;
            }
            TextView textView = new TextView(mContext);
            textView.setPadding(mContext.getResources().getDimensionPixelSize(R.dimen.dp_5), mContext.getResources().getDimensionPixelSize(R.dimen.dp_1), mContext.getResources().getDimensionPixelSize(R.dimen.dp_5), mContext.getResources().getDimensionPixelSize(R.dimen.dp_1));
            textView.setBackground(mContext.getDrawable(R.drawable.bg_shape_corner));
            textView.setTextSize(10);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                layoutParams.setMargins(mContext.getResources().getDimensionPixelSize(R.dimen.dp_4), 0, 0, 0);
            }else{
                ((LinearLayout) holder.getView(R.id.ll_tag_group)).removeAllViews();
            }
            textView.setLayoutParams(layoutParams);
            textView.setText(split[i]);
            ((LinearLayout) holder.getView(R.id.ll_tag_group)).addView(textView);
        }
        holder.itemView.setTag(objs);
  //      holder.itemView.setOnClickListener(this);
    }

    @Override
    public void convert(ViewHolder holder, List<ObjsBean> t) {

    }
//
//    @Override
//    public void onClick(View view) {
//        if (mOnItemClickListener != null) {
//            mOnItemClickListener.onItemClick(view, (ObjsBean) view.getTag());
//        }
//    }
//
//    //    ivPicture = itemView.findViewById(R.id.iv_picture);
////    tvTitle = itemView.findViewById(R.id.tv_title);
////    tvPrice = itemView.findViewById(R.id.tv_price);
////    tvSales = itemView.findViewById(R.id.tv_sales);
////    tvReview = itemView.findViewById(R.id.tv_review);
////    tvTag1 = itemView.findViewById(R.id.tv_tag1);
////    tvTag2 = itemView.findViewById(R.id.tv_tag2);
////    tvTag3 = itemView.findViewById(R.id.tv_tag3);
////    tvDays = itemView.findViewById(R.id.tv_days);
//    private OnItemClickListener mOnItemClickListener = null;
//
//
//    public static interface OnItemClickListener {
//        void onItemClick(View view, ObjsBean objs);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mOnItemClickListener = listener;
//    }
}
