package com.honganjk.ynybzbizfood.view.tour.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.DividerItemDecoration;
import com.honganjk.ynybzbizfood.code.base.view.fragment.MyBaseFragment;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.mode.javabean.tour.home.HomeTourBean;
import com.honganjk.ynybzbizfood.pressenter.tour.home.HomeTourPresenter;
import com.honganjk.ynybzbizfood.pressenter.tour.home.interfa.TourHomeParentinterfaces;
import com.honganjk.ynybzbizfood.view.tour.base.BaseTourMainActivity;
import com.honganjk.ynybzbizfood.view.tour.base.adapter.BaseAdapter;
import com.honganjk.ynybzbizfood.view.tour.base.adapter.LoadMoreAdapterWrapper;
import com.honganjk.ynybzbizfood.view.tour.home.adapter.TourHomeAdapter;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.banner.ConvenientBanner;
import com.honganjk.ynybzbizfood.widget.banner.NetworkImageHolderView;
import com.honganjk.ynybzbizfood.widget.banner.holder.CBViewHolderCreator;
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
 * Created by Administrator on 2017-11-07.
 */

public class HomeFragment extends MyBaseFragment<TourHomeParentinterfaces.IHomeInterface, HomeTourPresenter>
            implements CBViewHolderCreator, TourHomeParentinterfaces.IHomeInterface {

    Activity activity;
    List<HomeTourBean.DataBean.ObjsBean> objsBeans = new ArrayList<>();
    List<BannerData> advertisementDatas = new ArrayList<>();
    //轮播图
    @BindView(R.id.advertisement)
    ConvenientBanner advertisement;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.rv_body)
    RecyclerView rvBody;
    Unbinder unbinder;

    BaseAdapter mAdapter;

    private TourHomeAdapter tourHomeAdapter;

    public <V extends BaseView> HomeFragment(BaseTourMainActivity vtBaseTourMainActivity) {
        this.activity = vtBaseTourMainActivity;
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    int loadCount;
    public void init() {
        advertisement.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        advertisement.setPages(this, advertisementDatas);
        advertisement.startTurning(Global.ADVERTISEMENT_TIME);

        //创建被装饰者类实例
        tourHomeAdapter = new TourHomeAdapter(getContext());
        //创建装饰者实例，并传入被装饰者和回调接口
        mAdapter = new LoadMoreAdapterWrapper(tourHomeAdapter, new LoadMoreAdapterWrapper.OnLoad() {
            @Override
            public void load(int pagePosition, int pageSize, final LoadMoreAdapterWrapper.ILoadCallback callback) {
                //此处做网络操作，2s延迟，将拉取的数据更新到adpter中
                presenter.getData(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tourHomeAdapter.appendData(objsBeans);
//                        if (objsBeans.size() == LoadMoreAdapterWrapper.getmPageSize()) {
//                            callback.onSuccess();
//
//                        } else {
//                            callback.onFailure();
//                        }
                        callback.onSuccess();
                        if (loadCount++ == 3) {
                            callback.onFailure();
                        }
                    }
                }, 2000);
            }
        });
        rvBody.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBody.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        rvBody.setAdapter(mAdapter);

    }

    @Override
    public Object createHolder() {
        return new NetworkImageHolderView<BannerData>() {
            @Override
            public void onItemClicklistener(View item, int position, BannerData data) {
                //轮播图 图片点击

            }
        };
    }

    @Override
    public HomeTourPresenter initPressenter() {
        return new HomeTourPresenter();
    }

    @Override
    public void getHttpData(HomeTourBean.DataBean datas) {
        advertisementDatas.clear();
        objsBeans.clear();
        if (datas != null) {
            for (int i = 0; i < datas.getBanners().size(); i++) {
                advertisementDatas.add(new BannerData(datas.getBanners().get(i), i));

            }
            for (int i = 0; i < datas.getObjs().size(); i++) {
                objsBeans.add(datas.getObjs().get(i));
            }
            advertisement.notifyDataSetChanged();
        }
    }

    @Override
    public int getPageindex() {

        return ((LoadMoreAdapterWrapper) mAdapter).getmPagePosition() / LoadMoreAdapterWrapper.getmPageSize();
    }

    @Override
    public int getPageCount() {
        return LoadMoreAdapterWrapper.getmPageSize();
    }

    @Override
    public void clearData() {

    }

    @Override
    public void onRefresh() {
        presenter.getData(true);
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
        return null;
    }

    @Override
    public StatusChangListener getStatusChangListener() {
        return null;
    }

    @Override
    public <T> Collection<T> getValidData(Collection<T> c) {
        return null;
    }

    @Override
    public void clearPagingData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
