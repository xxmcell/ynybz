package com.honganjk.ynybzbizfood.view.store.base.activity;

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
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.utils.ui.DrawableUtils;
import com.honganjk.ynybzbizfood.view.home.activity.MainHomeActivity;
import com.honganjk.ynybzbizfood.view.store.classify.activity.ClassifyActivity;
import com.honganjk.ynybzbizfood.view.store.home.activity.HomeActivity;
import com.honganjk.ynybzbizfood.view.store.my.activity.MyActivity;
import com.honganjk.ynybzbizfood.view.store.shoppingcar.activity.ShoppingCarActivity;

import butterknife.BindView;

/**
 * 说明:商城的主类
 * 作者： 杨阳; 创建于：  2017-06-29  10:16
 */
public abstract class BaseStoreMainActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpActivity<V, T> implements AHBottomNavigation.OnTabSelectedListener {
    @BindView(R.id.bottom_navigation_bar)
    public AHBottomNavigation ahBottomNavigation;
    private long exitTime = 0;
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
        ahBottomNavigation.setAccentColor(getResources().getColor(R.color.main_store));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.home),
                DrawableUtils.getStateSelectDrawable(R.mipmap.ic_store_home_green, R.mipmap.ic_store_home_gray)));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.classify),
                DrawableUtils.getStateSelectDrawable(R.mipmap.ic_store_classify_green, R.mipmap.ic_store_classify_gray)));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.shoppingCar),
                DrawableUtils.getStateSelectDrawable(R.mipmap.ic_store_shoppingcar_green, R.mipmap.ic_store_shoppingcar_gray)));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.my),
                DrawableUtils.getStateSelectDrawable(R.mipmap.ic_store_my_green, R.mipmap.ic_store_my_gray)));


        ahBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white)); //默认背景
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
            switch (position) {
                case 0:
                    HomeActivity.startUI(this);
                    break;
                case 1:
                    ClassifyActivity.startUI(this);
                    break;
                case 2:
                    ShoppingCarActivity.startUI(this);
                    break;
                case 3:
                    MyActivity.startUI(this);
                    break;
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
        ActivityManager.getInstance().exitActivity(ClassifyActivity.class);
        ActivityManager.getInstance().exitActivity(ShoppingCarActivity.class);
        ActivityManager.getInstance().exitActivity(MyActivity.class);
    }
}
