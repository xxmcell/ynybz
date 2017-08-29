package com.honganjk.ynybzbizfood.view.peihu.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMainActivity;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendData;
import com.honganjk.ynybzbizfood.pressenter.peihu.home.HomePresenter;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.peihu.collect.adapter.NurserHomeAdapter;
import com.honganjk.ynybzbizfood.view.peihu.home.interfaces.IHomeParentInterfaces;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.SelectAddressActivity;
import com.honganjk.ynybzbizfood.widget.AppBarStateChangeListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.banner.ConvenientBanner;
import com.honganjk.ynybzbizfood.widget.banner.NetworkImageHolderView;
import com.honganjk.ynybzbizfood.widget.banner.holder.CBViewHolderCreator;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.code.Global.SERVER_PHONE;
import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;


/**
 * 说明:陪护主页
 * 2017/3/2-18:51
 */
public class HomeActivity extends BaseMainActivity<IHomeParentInterfaces.IHomeInterface, HomePresenter> implements IHomeParentInterfaces.IHomeInterface
        , CBViewHolderCreator, SuperRecyclerView.ListSwipeViewListener {

    @BindView(R.id.advertisement)
    ConvenientBanner advertisement;
    List<BannerData> advertisementDatas = new ArrayList<>();

    @BindView(R.id.tv_location)
    TextView location;
    @BindView(R.id.toolB)
    Toolbar toolBar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    ArrayList<NurserCommendData> mDatas = new ArrayList<>();
    NurserHomeAdapter adapter;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.title)
    TextView title;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public HomePresenter initPressenter() {
        return new HomePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_home_peihu;
    }

    @Override
    public void parseIntent(Intent intent) {
    }

    @Override
    public void initView() {
        super.initView();

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //扩张时候的title颜色：
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.translucent));
        //收缩后在Toolbar上显示时的title的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.main_color));
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                //展开状态
                if (state == State.EXPANDED) {
                    toolBar.setNavigationIcon(R.drawable.ic_arrows_left);
                    title.setVisibility(View.GONE);
                    //折叠状态
                } else if (state == State.COLLAPSED) {
                    toolBar.setNavigationIcon(R.drawable.material_arrwos_white_left);
                    title.setVisibility(View.VISIBLE);

                    //中间状态
                } else {

                }
            }
        });

        location.setText(userData.getAddress());
        toolBar.setTitle(getResources().getString(R.string.app_name_simple));
        title.setText(userData.getAddress());
        adapter = new NurserHomeAdapter(this, mDatas);
        switchRoot.setOnRefreshListener(this);
        switchRoot.setOnLoaddingListener(this);
        switchRoot.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        switchRoot.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_8).colorResId(R.color.gray_ee).build());
        switchRoot.setAdapter(adapter);

        advertisement.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        advertisement.setPages(this, advertisementDatas);
        advertisement.startTurning(Global.ADVERTISEMENT_TIME);

        presenter.getAdvertisement(3);
        presenter.getRecommendData(true);

        adapter.setOnItemClickListener(new OnItemClickListener<NurserCommendData>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NurserCommendData data, int position) {
                NursingRecoveryDetailsActivity.startUI(HomeActivity.this,data.getId(),data.getType());
            }
        });
    }

    @Override
    public int currentItem() {
        return 0;
    }


    @Override
    public void onRefresh() {
        presenter.getRecommendData(true);
    }

    @Override
    public StatusChangListener getStatusChangListener() {
        return switchRoot.getStatusChangListener();
    }

    @Override
    public <T> Collection<T> getValidData(Collection<T> c) {
        return switchRoot.getPagingHelp().getValidData(c);
    }

    @Override
    public void clearPagingData() {
        switchRoot.getPagingHelp().clear();
    }

    @Override
    public int getPageindex() {
        return switchRoot.getPagingHelp().getPageindex();
    }

    @Override
    public int getPageCount() {
        return switchRoot.getPagingHelp().getPageCount();
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return switchRoot.getSwipeRefreshLayout();
    }

    @Override
    public void clearData() {
        mDatas.clear();
    }

    @Override
    public Object createHolder() {
        return new NetworkImageHolderView<BannerData>() {
            @Override
            public void onItemClicklistener(View item, int position, BannerData data) {
                data.transformPage(HomeActivity.this);
            }
        };
    }

    @Override
    public void getAdvertisement(List<BannerData> data) {
        advertisementDatas.clear();
        advertisementDatas.addAll(data);
        advertisement.notifyDataSetChanged();
    }

    @Override
    public void onLoadding() {
        presenter.getRecommendData(false);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getRecommendData(true);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getRecommendData(true);
    }

    @Override
    public void getRecommendData(List<NurserCommendData> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_location, R.id.nursing, R.id.recovery, R.id.vip})
    public void onClick(View v) {
        switch (v.getId()) {
            //地址选择
            case R.id.tv_location:
                SelectAddressActivity.startForResultUi(this,REQUEST_CODE);
                break;
            //护工
            case R.id.nursing:
                NursingRecoveryManagerActivity.startUI(this, 1);
                break;
            //康复师
            case R.id.recovery:
                NursingRecoveryManagerActivity.startUI(this, 2);
                break;
            //康复师
            case R.id.vip:
                MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                        .title("提示")
                        .positiveText("确定")
                        .negativeColorRes(R.color.gray)
                        .negativeText("取消")
                        .content("VIP定制服务请拨打"+SERVER_PHONE)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                DeviceUtil.callByPhone(HomeActivity.this, SERVER_PHONE);
                            }
                        })
                        .build();
                materialDialog.show();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String address = data.getStringExtra("address");
                if (!TextUtils.isEmpty(address)) {
                    location.setText(address);
                    title.setText(address);
                }
            }
        }
    }


    @Override
    public int getStatusBarPaddingTop() {
        return 0;
    }

    @Override
    public int getStatusBarResource() {
        return R.color.translucent;
    }


}
