package com.honganjk.ynybzbizfood.view.tour.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.view.fragment.MyBaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.home.HomeTourBean;
import com.honganjk.ynybzbizfood.pressenter.tour.home.HomeTourPresenter;
import com.honganjk.ynybzbizfood.pressenter.tour.home.interfa.TourHomeParentinterfaces;
import com.honganjk.ynybzbizfood.view.tour.home.activity.TourHomeSearchActivity;
import com.honganjk.ynybzbizfood.view.tour.home.adapter.HomeTourAdapter;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.banner.ConvenientBanner;
import com.honganjk.ynybzbizfood.widget.banner.NetworkImageHolderView;
import com.honganjk.ynybzbizfood.widget.banner.holder.CBViewHolderCreator;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;
import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017-11-28.
 */

public class TourHomeFragment extends MyBaseFragment<TourHomeParentinterfaces.IHomeInterface, HomeTourPresenter>
        implements CBViewHolderCreator, TourHomeParentinterfaces.IHomeInterface, SuperRecyclerView.ListSwipeViewListener {
    List<ObjsBean> objsBeans = new ArrayList<>();
    List<BannerData> advertisementDatas = new ArrayList<>();

    @BindView(R.id.advertisement)
    ConvenientBanner advertisement; //banner
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.im_homesearch)
    LinearLayout imHomesearch;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    Unbinder unbinder;
    private HomeTourAdapter adapter;

    public TourHomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init() {

        advertisement.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        advertisement.setPages(this, advertisementDatas);
        advertisement.startTurning(Global.ADVERTISEMENT_TIME);

        adapter = new HomeTourAdapter(getContext(), objsBeans);
        switchRoot.setOnRefreshListener(this);
        switchRoot.setOnLoaddingListener(this);
        switchRoot.getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
        switchRoot.setAdapter(adapter);

        presenter.getData(true);

        imHomesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TourHomeSearchActivity.startUI(getActivity());
            }
        });

    }

    @Override
    public void onRefresh() {
        presenter.getData(true);
    }

    @Override
    public Object createHolder() {
        return new NetworkImageHolderView<BannerData>() {
            @Override
            public void onItemClicklistener(View item, int position, BannerData data) {
                data.transformPage(getActivity());
            }
        };
    }

    @Override
    public boolean isLogin(boolean isToLogin) {
        return false;
    }

    @Override
    public void showDialog(String msg, boolean isCancelable) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void clearData() {
        objsBeans.clear();
    }

    @Override
    public LoadingAndRetryManager getLoadingAndRetryManager() {
        return null;
    }

    @Override
    public void addSubscription(Subscription s) {

    }

    @Override
    public CompositeSubscription getCompositeSubscription() {
        return null;
    }

    @Override
    public void showErrorSnackbar(String result) {

    }

    @Override
    public void showErrorSnackbar(String result, boolean isFinish) {

    }

    @Override
    public void showWarningSnackbar(String result, boolean isFinish) {

    }

    @Override
    public void showWarningSnackbar(String result) {

    }

    @Override
    public void showSnackbar(String result, int type, boolean isFinish) {

    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return switchRoot.getSwipeRefreshLayout();
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
    public void getHttpData(HomeTourBean.DataBean datas) {
        advertisementDatas.clear();
        if (datas != null) {
            for (int i = 0; i < datas.getBanners().size(); i++) {
                advertisementDatas.add(new BannerData(datas.getBanners().get(i), i));
            }
            objsBeans.addAll(getValidData(datas.getObjs()));
            advertisement.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getSearchHttpData(List<ObjsBean> objsBeanList) {

    }

    @Override
    public HomeTourPresenter initPressenter() {
        return new HomeTourPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
}
