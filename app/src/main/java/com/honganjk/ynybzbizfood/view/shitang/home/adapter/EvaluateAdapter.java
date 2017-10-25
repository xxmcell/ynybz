package com.honganjk.ynybzbizfood.view.shitang.home.adapter;

import android.content.Context;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.common.EvaluateData;
import com.honganjk.ynybzbizfood.widget.EvaluationView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

public class EvaluateAdapter extends CommonAdapter<EvaluateData.ListBean> {

    public EvaluateAdapter(Context context, List<EvaluateData.ListBean> datas) {
        super(context, R.layout.item_evaluate, datas);
    }

    @Override
    public void convert(ViewHolder holder, EvaluateData.ListBean data) {
        holder.setText(R.id.tv_name,data.getName());
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        String time = simple.format(data.getTime());
        holder.setText(R.id.tv_time,time);
        holder.setText(R.id.tv_content,data.getContent());

        holder.setImageBitmapCircle(R.id.iv_avatar,data.getImg());
        EvaluationView evaluationView = holder.getView(R.id.view_star);
        evaluationView.setTouchEnable(false);
        evaluationView.setmFocusNum(data.getScore());

    }

    @Override
    public void convert(ViewHolder holder, List<EvaluateData.ListBean> t) {

    }

}
