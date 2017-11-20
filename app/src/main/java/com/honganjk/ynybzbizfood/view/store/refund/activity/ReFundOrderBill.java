package com.honganjk.ynybzbizfood.view.store.refund.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.utils.adapter.ViewPagerAdapter;
import com.honganjk.ynybzbizfood.view.store.order.fragment.StoreOrderFragment;
import com.honganjk.ynybzbizfood.widget.appbarlayout.SupperToolBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-09-22.
 */

public class ReFundOrderBill extends BaseMvpActivity {

    @BindView(R.id.toolbar)
    SupperToolBar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.bottom_navigation_bar)
    AHBottomNavigation bottomNavigationBar;

    ArrayList<Fragment> fragmentDatas = new ArrayList<>();


    @Override
    public int getContentView() {
        return R.layout.activity_store_refund_order;
    }

    @Override
    public void initView() {
        toolbar.setTitle("退/换货");
        toolbar.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fragmentDatas.add(StoreOrderFragment.getInstance(5));
        String title[]={""};
        tabLayout.setVisibility(View.GONE);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_99), getResources().getColor(R.color.main_color));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.gray_f8));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentDatas, title);
        viewPage.setOffscreenPageLimit(3);
        viewPage.setAdapter(adapter);

    }

    @Override
    public void parseIntent(Intent intent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public BasePresenter initPressenter() {
        return null;
    }

}
