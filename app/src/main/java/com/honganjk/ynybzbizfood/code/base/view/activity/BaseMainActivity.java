package com.honganjk.ynybzbizfood.code.base.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.ActivityManager;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.utils.ui.DrawableUtils;
import com.honganjk.ynybzbizfood.view.home.activity.MainHomeActivity;
import com.honganjk.ynybzbizfood.view.shitang.collect.activity.CollectActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.HomeActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.MyActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.OrderActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/15.
 */
public abstract class BaseMainActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpActivity<V, T> implements AHBottomNavigation.OnTabSelectedListener {
    @BindView(R.id.bottom_navigation_bar)
    public AHBottomNavigation ahBottomNavigation;

    private static String broadFlag = "Buttom_BroadFlag";
    private MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();
    public static boolean isMess = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter(broadFlag);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void initView() {
        ahBottomNavigation.setAccentColor(getResources().getColor(R.color.main_color));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.home),
                DrawableUtils.getStateSelectDrawable(R.mipmap.home_green, R.mipmap.home_grey)));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.collect),
                DrawableUtils.getStateSelectDrawable(R.mipmap.collect_green, R.mipmap.collect_grey)));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.order),
                DrawableUtils.getStateSelectDrawable(R.mipmap.order_green, R.mipmap.order_grey)));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.my),
                DrawableUtils.getStateSelectDrawable(R.mipmap.my_green, R.mipmap.my_grey)));


        ahBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        ahBottomNavigation.setCurrentItem(currentItem(), true);
        ahBottomNavigation.setForceTitlesDisplay(true);
        ahBottomNavigation.setOnTabSelectedListener(this);
        ahBottomNavigation.setBehaviorTranslationEnabled(false);
//        ahBottomNavigation.setTitleTextSize(25,20);//设置文字的大小，选中时和没有选中时。


    }


    @Override
    public void parseIntent(Intent intent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ahBottomNavigation.setCurrentItem(currentItem(), false);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public abstract int currentItem();

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (position != currentItem()) {
            Intent intent = new Intent(broadFlag);
            intent.putExtra("position", position);
            sendBroadcast(intent);

            if (position == 0) {
                if (isMess) {
                    HomeActivity.startUI(this);
                } else {
                    com.honganjk.ynybzbizfood.view.peihu.home.activity.HomeActivity.startUI(this);
                }
            } else if (position == 1) {
                if (isMess) {
                    if (isLogin(true)) {
                        CollectActivity.startUI(this);
                    }
                } else {
                    if (isLogin(true)) {
                        com.honganjk.ynybzbizfood.view.peihu.collect.activity.CollectActivity.startUI(this);

                    }
                }
            } else if (position == 2) {
                if (isMess) {
                    if (isLogin(true)) {
                        OrderActivity.startUI(this);
                    }
                } else {
                    if (isLogin(true)) {
                        com.honganjk.ynybzbizfood.view.peihu.order.activity.OrderActivity.startUI(this);
                    }
                }
            } else if (position == 3) {
                MyActivity.startUI(this);
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ahBottomNavigation.setCurrentItem(intent.getIntExtra("position", 0), false);
        }
    }

    @Override
    public void onBackPressed() {

        MainHomeActivity.startUI(this);
        ActivityManager.getInstance().exitActivity(HomeActivity.class);
        ActivityManager.getInstance().exitActivity(CollectActivity.class);
        ActivityManager.getInstance().exitActivity(OrderActivity.class);
        ActivityManager.getInstance().exitActivity(MyActivity.class);

        ActivityManager.getInstance().exitActivity(com.honganjk.ynybzbizfood.view.peihu.home.activity.HomeActivity.class);
        ActivityManager.getInstance().exitActivity(com.honganjk.ynybzbizfood.view.peihu.collect.activity.CollectActivity.class);
        ActivityManager.getInstance().exitActivity(com.honganjk.ynybzbizfood.view.peihu.order.activity.OrderActivity.class);

    }
}
