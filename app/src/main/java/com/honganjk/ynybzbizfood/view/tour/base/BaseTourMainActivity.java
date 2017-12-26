package com.honganjk.ynybzbizfood.view.tour.base;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.utils.ui.DrawableUtils;
import com.honganjk.ynybzbizfood.view.tour.base.VeiwPager.CustomViewPager;
import com.honganjk.ynybzbizfood.view.tour.base.fragment.MyFragmentPagerAdapter;
import com.honganjk.ynybzbizfood.view.tour.classify.fragment.ClassifyFragment;
import com.honganjk.ynybzbizfood.view.tour.home.fragment.TourHomeFragment;
import com.honganjk.ynybzbizfood.view.tour.me.MeFragment;
import com.honganjk.ynybzbizfood.view.tour.order.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-11-06.
 */

public class BaseTourMainActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpActivity<V, T> {


    private static String broadFlag = "Buttom_BroadFlag";
    @BindView(R.id.BaseMentViewPager)
    CustomViewPager BaseMentViewPager;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private List<Fragment> listFragment = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_tourbase;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        NavigationBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void parseIntent(Intent intent) {

    }

    @Override
    public T initPressenter() {
        return null;
    }

    private void NavigationBar() {
        listFragment.add(new TourHomeFragment());
        listFragment.add(ClassifyFragment.newInstance());
        listFragment.add(new OrderFragment());
        listFragment.add(new MeFragment());
        //可以设置角标的,需求
        BaseMentViewPager.setAdapter(new tourViewPagerAdapter(getSupportFragmentManager(), listFragment));
        BaseMentViewPager.setOffscreenPageLimit(0);//设置ViewPager的缓存界面数,默认缓存为2
        BaseMentViewPager.setScanScroll(false);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(DrawableUtils.getStateSelectDrawable(R.mipmap.home_green, R.mipmap.home_grey),
                        "首页"))
                .addItem(new BottomNavigationItem(DrawableUtils.getStateSelectDrawable(R.mipmap.classify_tour_green, R.mipmap.classify_tour_grey),
                        "分类"))
                .addItem(new BottomNavigationItem(DrawableUtils.getStateSelectDrawable(R.mipmap.order_tour_green, R.mipmap.order_tour_grey),
                        "订单"))
                .addItem(new BottomNavigationItem(DrawableUtils.getStateSelectDrawable(R.mipmap.my_green, R.mipmap.my_grey),
                        "我的"));
        bottomNavigationBar.initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int i) {
                Intent intent2 = new Intent(broadFlag);
                intent2.putExtra("position", i);
                sendBroadcast(intent2);
                switch (i) {
                    case 0:
                        BaseMentViewPager.setCurrentItem(0);
                        break;
                    case 1:
                        BaseMentViewPager.setCurrentItem(1);
                        break;
                    case 2:
                        BaseMentViewPager.setCurrentItem(2);
                        break;
                    case 3:
                        BaseMentViewPager.setCurrentItem(3);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int i) {

            }

            @Override
            public void onTabReselected(int i) {

            }
        });
    }

    private class tourViewPagerAdapter extends MyFragmentPagerAdapter {
        List<Fragment> list = new ArrayList<>();

        public tourViewPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> listFragment) {
            super(supportFragmentManager);
            this.list = listFragment;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
