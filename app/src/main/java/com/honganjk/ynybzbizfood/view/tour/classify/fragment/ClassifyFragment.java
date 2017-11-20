package com.honganjk.ynybzbizfood.view.tour.classify.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.fragment.MyBaseFragment;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourBean;
import com.honganjk.ynybzbizfood.pressenter.tour.classify.ClassifyTourPresent;
import com.honganjk.ynybzbizfood.pressenter.tour.classify.interfa.TourClassifyParentinterfaces;
import com.honganjk.ynybzbizfood.view.tour.base.BaseTourMainActivity;
import com.honganjk.ynybzbizfood.view.tour.classify.activity.TourClassifyDetailActivity;
import com.honganjk.ynybzbizfood.view.tour.classify.adapter.TourClassifyBodyAdapter;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017-11-07.
 */

public class ClassifyFragment extends MyBaseFragment<TourClassifyParentinterfaces.IClassifyInterface, ClassifyTourPresent>
        implements TourClassifyParentinterfaces.IClassifyInterface {
    Activity activity;
    Unbinder unbinder;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.classify_search)
    LinearLayout classifySearch;
    @BindView(R.id.btn_tuijian)
    Button btnTuijian;
    @BindView(R.id.btn_zhoubian)
    Button btnZhoubian;
    @BindView(R.id.btn_quanguo)
    Button btnQuanguo;
    @BindView(R.id.btn_japan)
    Button btnJapan;
    @BindView(R.id.btn_dongnanya)
    Button btnDongnanya;
    @BindView(R.id.btn_haidao)
    Button btnHaidao;
    @BindView(R.id.btn_gangaotai)
    Button btnGangaotai;
    @BindView(R.id.btn_zhongdongfei)
    Button btnZhongdongfei;
    @BindView(R.id.btn_youlunyou)
    Button btnYoulunyou;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.rv_tour_classify_body)
    RecyclerView rvTourClassifyBody;
    @BindView(R.id.btn_group)
    LinearLayout btnGroup;
    private List<ClassifyTourBean.Data.Objs> objsList = new ArrayList<>();
    private TourClassifyBodyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_tour_classify, null);
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

    int type = 1;

    @Override
    public void onResume() {
        presenter.getData(true, type);
        changeBtnBg(type);
        super.onResume();
    }

    public void init() {
        rvTourClassifyBody.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new TourClassifyBodyAdapter(getContext(), objsList);
        adapter.setOnItemClickListener(new TourClassifyBodyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(),TourClassifyDetailActivity.class);
                ClassifyTourBean.Data.Objs objs = objsList.get(position);
                intent.putExtra("objs",objs);
                startActivity(intent);
            }
        });

        rvTourClassifyBody.setAdapter(adapter);

    }

    public <V extends BaseView> ClassifyFragment(BaseTourMainActivity vtBaseTourMainActivity) {
        this.activity = vtBaseTourMainActivity;
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
    }

    @Override
    public int getPageindex() {
        return 0;
    }

    @Override
    public int getPageCount() {
        return 20;
    }

    @Override
    public void getHttpData(ClassifyTourBean.Data datas) {
        objsList = datas.getObjs();
        adapter.changeUI(objsList);
    }

    @Override
    public void onRefresh() {
        presenter.getData(true, type);
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
    public ClassifyTourPresent initPressenter() {
        return new ClassifyTourPresent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    String[] titles = {"推荐","周边","全国","日本","东南亚","海岛","港澳台","中东非","邮轮游"};
    public void changeBtnBg(int type) {
        for (int i = 0; i < 9; i++) {
            if (i == type - 1) {
                tvType.setText(titles[i]);
                btnGroup.getChildAt(i).setBackgroundColor(Color.WHITE);
                btnGroup.getChildAt(i).setClickable(false);
            } else {
                btnGroup.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.bg_gray));
                btnGroup.getChildAt(i).setClickable(true);

            }
        }
    }

    @OnClick({R.id.iv_back, R.id.classify_search, R.id.btn_tuijian, R.id.btn_zhoubian, R.id.btn_quanguo, R.id.btn_japan, R.id.btn_dongnanya, R.id.btn_haidao, R.id.btn_gangaotai, R.id.btn_zhongdongfei, R.id.btn_youlunyou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //分类，1-推荐；2-周边；3-全国；4-日本；5-东南亚；6-海岛；7-港澳台；8-中东非；9-邮轮游
            case R.id.iv_back:
                break;
            case R.id.classify_search:
                break;
            case R.id.btn_tuijian:
                type = 1;
                changeBtnBg(type);
                presenter.getData(true, type);
                break;
            case R.id.btn_zhoubian:
                type = 2;
                changeBtnBg(type);
                presenter.getData(true, type);
                break;
            case R.id.btn_quanguo:
                type = 3;
                changeBtnBg(type);
                presenter.getData(true, type);
                break;
            case R.id.btn_japan:
                type = 4;
                changeBtnBg(type);
                presenter.getData(true, type);;
                break;
            case R.id.btn_dongnanya:
                type = 5;
                changeBtnBg(type);
                presenter.getData(true, type);
                break;
            case R.id.btn_haidao:
                type = 6;
                changeBtnBg(type);
                presenter.getData(true, type);
                break;
            case R.id.btn_gangaotai:
                type = 7;
                changeBtnBg(type);
                presenter.getData(true, type);
                break;
            case R.id.btn_zhongdongfei:
                type = 8;
                changeBtnBg(type);
                presenter.getData(true, type);
                break;
            case R.id.btn_youlunyou:
                type = 9;
                changeBtnBg(type);
                presenter.getData(true, type);
                break;
        }
    }
}
