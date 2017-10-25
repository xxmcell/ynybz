package com.honganjk.ynybzbizfood.view.common.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;
import com.honganjk.ynybzbizfood.utils.ui.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class BusinessListAdapter extends CommonAdapter<com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData> {

    public BusinessListAdapter(Context context, List datas) {
        super(context, R.layout.item_recycleview_carteen, datas);
    }


    @Override
    public void convert(ViewHolder holder, com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData data) {
        holder.setText(R.id.tv_carteen_name, data.getBname());
        holder.setText(R.id.tv_month_sales, String.format(mContext.getString((int) R.string.tv_month_sales), data.getSale()));
        holder.setText(R.id.tv_score, String.format(mContext.getString((int) R.string.tv_score), data.getScore()));
        holder.setText(R.id.tv_how_rank, String.format(mContext.getString((int) R.string.rank), data.getRank()));
        holder.setVisible(R.id.tv_time, false);
        holder.setText(R.id.tv_distance, String.format(mContext.getString((int) R.string.tv_distance), ((double) data.getDistance() / 1000)));
        holder.setText(R.id.tv_open_send_money, String.format(mContext.getString((int) R.string.tv_open_send_money), data.getLowest()));
        holder.setText(R.id.tv_send_money, String.format(mContext.getString((int) R.string.tv_open_send_money), data.getFare()));
        holder.setImageBitmapRound(R.id.iv_picture, data.getBimg(), 10);
        ((LinearLayout) holder.getView(R.id.ll_favors)).removeAllViews();

        if (data.getFavors().size() > 0) {
            holder.getView(R.id.view_divide).setVisibility(View.VISIBLE);

            for (BusinessData.Favors favors : data.getFavors()) {
                View view = UiUtils.getInflaterView(mContext, R.layout.item_favors);
                TextView textView = (TextView) view.findViewById(R.id.tv_favors);
                textView.setText(favors.getTitle());
                Drawable drawable = null;
                switch (favors.getType()) {
                    case 1:
                        drawable = mContext.getResources().getDrawable(R.drawable.ic_home);
                        // 这一步必须要做,否则不会显示.
                        break;
                    case 2:
                        drawable = mContext.getResources().getDrawable(R.drawable.ic_subtract);
                        break;
                }
                if (drawable != null) {
                    drawable.setBounds(0, 0, 40, 40);
                    textView.setCompoundDrawables(drawable, null, null, null);
                }
                ((LinearLayout) holder.getView(R.id.ll_favors)).addView(view);

            }
        } else {
            holder.getView(R.id.view_divide).setVisibility(View.GONE);
        }
    }

    @Override
    public void convert(ViewHolder holder, List<BusinessData> t) {

    }

}
