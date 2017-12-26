package com.honganjk.ynybzbizfood.view.tour.classify.adapter;

import android.content.Context;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourBean;

import java.util.List;

/**
 * Created by Administrator on 2017-11-16.
 */

public class TourClassifyBodyAdapter extends CommonAdapter<ClassifyTourBean.Data.Objs>{
    private Context mContext;

    public TourClassifyBodyAdapter(Context context, List<ClassifyTourBean.Data.Objs> datas) {
        super(context, R.layout.item_tour_classify_body, datas);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, ClassifyTourBean.Data.Objs objs) {
        holder.setImageBitmapRound(R.id.iv_bg, objs.getImg(), 5);
        holder.setText(R.id.tv_name, objs.getName());
        holder.setText(R.id.tv_feature, objs.getFeature());

    }

    @Override
    public void convert(ViewHolder holder, List<ClassifyTourBean.Data.Objs> t) {

    }
}
