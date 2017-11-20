package com.honganjk.ynybzbizfood.view.tour.classify.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourBean;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 旅游分类详情
 * Created by Administrator on 2017-11-17.
 */

public class TourClassifyDetailActivity extends BaseActivity {

    @BindView(R.id.im_classifyback)
    ImageView imClassifyback;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.llclassify_search)
    LinearLayout llclassifySearch;
    @BindView(R.id.hot_recommend)
    TextView hotRecommend;
    @BindView(R.id.stroke_days)
    TextView strokeDays;
    @BindView(R.id.single_budget)
    TextView singleBudget;
    @BindView(R.id.switchRoot)
    RecyclerView switchRoot;
    private ClassifyTourBean.Data.Objs objs;
    private int did;

    @Override
    public int getContentView() {
        return R.layout.activity_tour_classify_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        ToastUtils.getToastShort(objs.getName());
        switchRoot.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void parseIntent(Intent intent) {
        objs = (ClassifyTourBean.Data.Objs) intent.getSerializableExtra("objs");
        did = objs.getId();

    }

    @OnClick({R.id.im_classifyback, R.id.llclassify_search, R.id.hot_recommend, R.id.stroke_days, R.id.single_budget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_classifyback:
                break;
            case R.id.llclassify_search:
                break;
            case R.id.hot_recommend:
                break;
            case R.id.stroke_days:
                break;
            case R.id.single_budget:
                break;
        }
    }
}
