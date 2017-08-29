package com.honganjk.ynybzbizfood.view.peihu.collect.adapter;

import android.content.Context;
import android.view.View;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendData;
import com.honganjk.ynybzbizfood.view.peihu.home.activity.NursingRecoveryManagerActivity;
import com.honganjk.ynybzbizfood.view.peihu.order.activity.SubscribeActivity;

import java.util.List;

/**
 * Created by admin on 2017/3/24.
 */

public class NurserHomeAdapter extends CommonAdapter<NurserCommendData> {
    BaseActivity mContext;

    public NurserHomeAdapter(BaseActivity context, List<NurserCommendData> datas) {
        super(context, R.layout.item_nurser_hoem, datas);
        mContext = context;
    }

    private NurserHomeAdapter(Context context, int layoutId, List<NurserCommendData> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final NurserCommendData data) {
        holder.setImageBitmapRound(R.id.picture, data.getImg(), 5);
        if (mContext instanceof NursingRecoveryManagerActivity) {
            holder.setText(R.id.name, data.getName());
        } else {
            holder.setText(R.id.name, data.getNameDetails());
        }
        holder.setText(R.id.text, data.getDetails());
        holder.setText(R.id.price, data.getPriceStr());

        holder.setOnClickListener(R.id.status, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext.isLogin(true)) {
                    SubscribeActivity.starUi(mContext, data.getId(), data.getType(),data.getImg());
                }
            }
        });

    }
}
