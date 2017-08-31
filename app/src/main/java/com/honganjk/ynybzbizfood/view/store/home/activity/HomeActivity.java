package com.honganjk.ynybzbizfood.view.store.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.pressenter.store.home.HomePresenter;
import com.honganjk.ynybzbizfood.view.store.base.activity.BaseStoreMainActivity;
import com.honganjk.ynybzbizfood.view.store.home.adapter.StoreHomeAdapter;
import com.honganjk.ynybzbizfood.view.store.home.interfaces.IHomeParentInterfaces;
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

/**
 * 说明:商城-主页面
 * 作者： 杨阳; 创建于：  2017-06-29  10:21
 */
public class HomeActivity extends BaseStoreMainActivity<IHomeParentInterfaces.IHomeInterface, HomePresenter> implements CBViewHolderCreator, IHomeParentInterfaces.IHomeInterface, SuperRecyclerView.ListSwipeViewListener {

    @BindView(R.id.advertisement)
    ConvenientBanner advertisement;  //轮播工具
    List<BannerData> advertisementDatas = new ArrayList<>();


    @BindView(R.id.switchRoot)       //展示商品工具
    SuperRecyclerView switchRoot;
    ArrayList<StoreHomeData.ObjsBean> mDatas = new ArrayList<>();
    StoreHomeAdapter adapter;


    @BindView(R.id.im_homesearch)  //搜索
    LinearLayout mysearch;

    public static void startUI(Activity activity) {  //入口
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {  // TODO: 2017-08-30
        return R.layout.store_activity_home;
    }

    @Override
    public void initView() {
        super.initView();

        adapter = new StoreHomeAdapter(this, mDatas);
        switchRoot.setOnRefreshListener(this);
        switchRoot.setOnLoaddingListener(this);
        switchRoot.getRecyclerView().setLayoutManager(new GridLayoutManager(this, 3));
//        switchRoot.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_8).colorResId(R.color.gray_ee).build());
        switchRoot.setAdapter(adapter);


        advertisement.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        advertisement.setPages(this, advertisementDatas);
        advertisement.startTurning(Global.ADVERTISEMENT_TIME);

        presenter.getAdvertisement();
        presenter.getData(true);

        mysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeSearchActivity.startUI(mActivity);
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener<StoreHomeData.ObjsBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, StoreHomeData.ObjsBean data, int position) {
            ProductDetailsActivity.startUI(mActivity,data.getId());
            }
        });


        //返回
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public HomePresenter initPressenter() {
        return new HomePresenter();
    }

    @Override
    public int currentItem() {
        return 0;
    }

    @Override
    public int getStatusBarPaddingTop() {
        return 0;
    }

    @Override
    public int getStatusBarResource() {
        return R.color.translucent;
    }


    @Override
    public void onRefresh() {
        presenter.getData(true);
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
    public void getAdvertisement(List<String> data) {
        advertisementDatas.clear();
        for (int i = 0; i < data.size(); i++) {
            BannerData d = new BannerData(data.get(i), 0);
            advertisementDatas.add(d);
        }
        advertisement.notifyDataSetChanged();
    }

    @Override
    public void onLoadding() {
        presenter.getData(false);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getData(true);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getData(true);
    }

    @Override
    public void getHttpData(List<StoreHomeData.ObjsBean> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
    }
}
