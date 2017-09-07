package com.honganjk.ynybzbizfood.view.store.classify.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.mode.javabean.store.classify.ClassifyRequestBean;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.pressenter.store.classify.ClassifyPresenter;
import com.honganjk.ynybzbizfood.view.store.base.activity.BaseStoreMainActivity;
import com.honganjk.ynybzbizfood.view.store.classify.adapter.StoreClassifyAdapter;
import com.honganjk.ynybzbizfood.view.store.classify.interfaces.IClassifyParentInterfaces;
import com.honganjk.ynybzbizfood.view.store.home.activity.ProductDetailsActivity;
import com.honganjk.ynybzbizfood.widget.PopupPulldown;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明:商城-搜索页面
 * 作者： 郑新; 创建于：  2017-06-29  10:21
 * <p>
 */
public class Store_SearchActivity extends BaseStoreMainActivity<IClassifyParentInterfaces.IClassifyInterface, ClassifyPresenter>
        implements IClassifyParentInterfaces.IClassifyInterface, SuperRecyclerView.ListSwipeViewListener {
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    ArrayList<StoreHomeData.ObjsBean> mDatas = new ArrayList<>();
    StoreClassifyAdapter adapter;
    @BindView(R.id.im_searchback)
    ImageView im_Searchback;
    @BindView(R.id.tv_keywordtitle)
    TextView tv_Keywordtitle;

    ArrayList<PopupPulldown.PullDownData> mFiltrareDatas = new ArrayList<>(); //筛选数据源
    PopupPulldown pp;   //筛选(带动画)
    ClassifyRequestBean requestBean;
    private String keyword;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, Store_SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.store_activity_search;
    }

    @Override
    public ClassifyPresenter initPressenter() {
        return new ClassifyPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        requestBean = new ClassifyRequestBean();
        keyword = getIntent().getStringExtra("keyword");
        if (!TextUtils.isEmpty(keyword)){
            requestBean.setKeyword(keyword);
            tv_Keywordtitle.setText(keyword);
            presenter.getSearchData(keyword);
        }

        adapter = new StoreClassifyAdapter(this, mDatas);
        switchRoot.setOnRefreshListener(this);
        switchRoot.setOnLoaddingListener(this);
        switchRoot.getRecyclerView().setLayoutManager(new GridLayoutManager(this, 2));
//        switchRoot.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_8).colorResId(R.color.gray_ee).build());
        switchRoot.setAdapter(adapter);
        pp = new PopupPulldown(mActivity, mFiltrareDatas);

        adapter.setOnItemClickListener(new OnItemClickListener<StoreHomeData.ObjsBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, StoreHomeData.ObjsBean data, int position) {
                ProductDetailsActivity.startUI(mActivity, data.getId());
            }
        });

//        presenter.getSearchData(keyword);

    }

    @Override
    public int currentItem() {
        return 1;
    }

    @Override
    public void onRefresh() {
        SearchreruestData(true);
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
    public void onLoadding() {  //延迟加载
//        reruestData(false);
        presenter.getSearchData(keyword);
    }

    @Override//数据加载失败点击重新加载
    public void onRetryClick(ContextData data) {
//        reruestData(true);
        presenter.getSearchData(keyword);
    }

    @Override //数据为空点击重新加载
    public void onEmptyClick(ContextData data) {
//        reruestData(true);
        presenter.getSearchData(keyword);
    }


    @Override
    public void getHttpData(List<StoreHomeData.ObjsBean> datas) {
            mDatas.addAll(datas);
            adapter.notifyDataSetChanged();
    }

    @Override
    public void filtrateClassify(List<PopupPulldown.PullDownData> datas) {
    }

    @Override
    public void filtrateBrand(List<PopupPulldown.PullDownData> datas) {
    }

    @Override
    public void addShoppingCar(boolean datas) {}

    @Override
    public void setSaerchData(ArrayList<StoreHomeData.ObjsBean> datas) {
    }

    @OnClick({R.id.im_searchback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_searchback:
//                onBackPressed();
                finish();
                break;
        }
    }

    //搜索数据刷新结果
    private void SearchreruestData(boolean isFirst) {
        requestBean.setFirstRequest(isFirst);
        presenter.getSearch_RefreshData(keyword,requestBean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
