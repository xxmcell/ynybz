package com.honganjk.ynybzbizfood.view.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.ActivityManager;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.pressenter.home.MainHomePresenter;
import com.honganjk.ynybzbizfood.utils.animator.AnimUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.view.health.activity.HealthManagerActivity;
import com.honganjk.ynybzbizfood.view.home.iview.MainHomeView;
import com.honganjk.ynybzbizfood.view.other.view.IOtherView;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.HomeActivity;

import com.honganjk.ynybzbizfood.view.tour.base.BaseTourMainActivity;
import com.honganjk.ynybzbizfood.widget.SuperSwipeRefreshLayout;
import com.honganjk.ynybzbizfood.widget.banner.ConvenientBanner;
import com.honganjk.ynybzbizfood.widget.banner.NetworkImageHolderView;
import com.honganjk.ynybzbizfood.widget.banner.holder.CBViewHolderCreator;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.code.Global.SERVER_PHONE;
import static com.honganjk.ynybzbizfood.code.base.view.activity.BaseMainActivity.isMess;


/**
 * **
 * 创建时间:2016/9/19　17:04
 * 功能介绍：首页
 */
public class MainHomeActivity extends BaseMvpActivity<MainHomeView, MainHomePresenter> implements
        MainHomeView, CBViewHolderCreator, OnRetryClickListion, IOtherView.IAdverdisement, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.advertisement)
    ConvenientBanner advertisement;
    List<BannerData> advertisementDatas = new ArrayList<>();
    @BindView(R.id.swipeRefresh)
    SuperSwipeRefreshLayout swipeRefresh;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, MainHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main_home;
    }

    @Override
    public MainHomePresenter initPressenter() {
        return new MainHomePresenter();
    }

    @Override
    public void clearData() {
    }

    @Override
    public void initView() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitle(getResources().getString(R.string.app_name_simple));
        toolbar.addActions(1, "联系", R.drawable.material_phone);
        toolbar.setTitleColor(R.color.gray_33);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DeviceUtil.callByPhone(MainHomeActivity.this, SERVER_PHONE);
                return false;
            }
        });
        swipeRefresh.setOnRefreshListener(this);
        AnimUtils.pressAnimationListener(findViewById(R.id.yyc));
        AnimUtils.pressAnimationListener(findViewById(R.id.lrph));
        AnimUtils.pressAnimationListener(findViewById(R.id.bottomView));
        // 测试     旅游/商城
//        AnimUtils.pressAnimationListener(findViewById(R.id.tv_travel));
        AnimUtils.pressAnimationListener(findViewById(R.id.onlinestore));
        AnimUtils.pressAnimationListener(findViewById(R.id.lvyou));
        presenter.getAdvertisement(1);
        presenter.judgeVersion();
    }

    @Override
    public void parseIntent(Intent intent) {
    }

    @Override
    public Object createHolder() {
        return new NetworkImageHolderView<BannerData>() {
            @Override
            public void onItemClicklistener(View item, int position, BannerData data) {
                data.transformPage(MainHomeActivity.this);
            }
        };
    }

    public void onRetryClick(ContextData data) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInfo.setUserLocation(null);
    }

    @Override
    public void onEmptyClick(ContextData data) {
    }

    @OnClick({R.id.yyc, R.id.lrph, R.id.bottomView, R.id.onlinestore,R.id.lvyou})
    public void onClick(final View view) {
        AnimUtils.pressAnimationListener(view, new AnimUtils.OnClickListenerCallback() {
            @Override
            public void clickCallback(boolean isOnClick) {
                switch (view.getId()) {
                    //营养餐
                    case R.id.yyc:
                        isMess = true;
                        HomeActivity.startUI(MainHomeActivity.this);
                        break;
                    //陪护
                    case R.id.lrph:
                        isMess = false;
                        com.honganjk.ynybzbizfood.view.peihu.home.activity.HomeActivity.startUI(MainHomeActivity.this);
                        break;
                    case R.id.bottomView:
                        if (isLogin(true)) {
                            HealthManagerActivity.startUI(MainHomeActivity.this);
                        }
                        break;
                    //商城
                    case R.id.onlinestore:
                        com.honganjk.ynybzbizfood.view.store.home.activity.HomeActivity.startUI(mActivity);
                        break;
                    //旅游
                    case R.id.lvyou:

                        Intent intent= new Intent(mActivity, BaseTourMainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    @Override
    public void getAdvertisement(List<BannerData> data) {
        advertisementDatas.clear();
        advertisementDatas.addAll(data);
        advertisement.setPages(this, advertisementDatas);
        advertisement.startTurning(Global.ADVERTISEMENT_TIME);
    }

    @Override
    public void onBackPressed() {
        ActivityManager.getInstance().isExitApplication(this);
    }

    @Override
    public int getStatusBarResource() {
        return R.color.gray_99;
    }


    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefresh;
    }

    @Override
    public void onRefresh() {
        presenter.getAdvertisement(1);
    }
}
