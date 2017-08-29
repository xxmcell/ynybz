package com.honganjk.ynybzbizfood.view.shitang.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.FavorableListData;
import com.honganjk.ynybzbizfood.pressenter.shitang.home.CarteenDetailPresenter;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.BreakfastFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.BusinessInfoFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.EvaluateFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.GoodsFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;
import com.honganjk.ynybzbizfood.widget.appbarlayout.SupperToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class CarteenDetailActivity extends BaseMvpActivity<HomeParentInterface.carteenDetail, CarteenDetailPresenter> implements HomeParentInterface.carteenDetail {
    @BindView(R.id.toolbar)
    SupperToolBar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> fragments = new ArrayList();
    private List<String> tabs = new ArrayList();
    private BusinessData data = new BusinessData();
    /**
     * 商户id
     */
    private int id = -1;
    private GoodsFragment goodsFragment;
    private BreakfastFragment breakfastFragment;
    private EvaluateFragment evaluateFragment;
    private BusinessInfoFragment businessInfoFragment;

    private int type = 1;   //

    public static void startUI(Activity activity, int id) {
        Intent intent = new Intent(activity, CarteenDetailActivity.class);
        intent.putExtra("id", id);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    public static void startUI(Activity activity, int id, int type) {
        Intent intent = new Intent(activity, CarteenDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    public CarteenDetailPresenter initPressenter() {
        return new CarteenDetailPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_carteendetail;
    }

    @Override
    public void initView() {

        toolbar.setBack(this);
        toolbar.setTitle(getResources().getString(R.string.tv_carteen));

        if (type == 1) {
            goodsFragment = new GoodsFragment(id);
            fragments.add(goodsFragment);
        } else {
            breakfastFragment = new BreakfastFragment(id);
            fragments.add(breakfastFragment);
        }

        evaluateFragment = new EvaluateFragment(id);
        fragments.add(evaluateFragment);
        businessInfoFragment = new BusinessInfoFragment();
        fragments.add(businessInfoFragment);


        tabs.add(getResources().getString(R.string.tv_goods));
        tabs.add(getResources().getString(R.string.tv_evaluate));
        tabs.add(getResources().getString(R.string.tv_carteen_detail));

        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.addTab(tablayout.newTab().setText(tabs.get(0)));
        tablayout.addTab(tablayout.newTab().setText(tabs.get(1)));
        tablayout.addTab(tablayout.newTab().setText(tabs.get(2)));

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(3);
        tablayout.setupWithViewPager(viewpager);
        tablayout.setTabsFromPagerAdapter(adapter);

        presenter.getCarteenDetail(id, UserInfo.userData.isLogin() ? UserInfo.userData.getCode() : -1);
        presenter.getFavorable(id);
    }

    @Override
    public void carteenDetail(BusinessDetailsData data) {

        toolbar.setTitle(data.getName());
        if (goodsFragment != null) {
            goodsFragment.update(data);
        }
        if (breakfastFragment != null) {
            breakfastFragment.update(data);
        }
        businessInfoFragment.update(data);
    }

    @Override
    public void getFavorableList(List<FavorableListData> datas) {
        //中晚餐Fragment 管理 类
        if (goodsFragment != null) {
            goodsFragment.setFavorableContent(datas);
        }
        //早餐Fragment 管理 类
        if (breakfastFragment != null) {
            breakfastFragment.setFavorableContent(datas);
        }
    }

    @Override
    public void clearData() {

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }


    }


    @Override
    public void parseIntent(Intent intent) {

        id = intent.getIntExtra("id", -1);
        type = intent.getIntExtra("type", 1);

        if (id == -1) {
            ToastUtils.getToastShort(getResources().getString(R.string.err_business_no_id));
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
