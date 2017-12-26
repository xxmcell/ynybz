package com.honganjk.ynybzbizfood.view.tour.detail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;
import com.honganjk.ynybzbizfood.utils.adapter.ViewPagerAdapter;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.view.tour.detail.fragment.DateFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.sdk.android.impl.KernelContext.context;

/**
 * 日期选择页
 * Created by Administrator on 2017-11-29.
 */

public class SelectDataActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    private ArrayList<Fragment> fragmentDatas = new ArrayList<>();
    private ArrayList<TourDetailBean.DataBean.Formats> formatsList;

    public static void startUiForResult(Activity activity, List<TourDetailBean.DataBean.Formats> formatsList, int requestCode) {
        Intent intent = new Intent(context, SelectDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("formatsList", (Serializable) formatsList);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_select_data;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        String[] titles = {DateUtils.getCurrentTime(DateUtils.TIME_YM), DateUtils.getNextMonth()};
        fragmentDatas.add(DateFragment.newInstance(DateUtils.getCurrentTime(DateUtils.TIME_YM),formatsList));
        fragmentDatas.add(DateFragment.newInstance(DateUtils.getNextMonth(),formatsList));

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_99), getResources().getColor(R.color.main_color));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.gray_f8));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentDatas, titles);
        viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPage);
    }

    @Override
    public void parseIntent(Intent intent) {
        //bundle.putSerializable("formatsList", (Serializable) formatsList);
        formatsList = (ArrayList<TourDetailBean.DataBean.Formats>) intent.getSerializableExtra("formatsList");
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
