package com.honganjk.ynybzbizfood.view.shitang.collect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMainActivity;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;
import com.honganjk.ynybzbizfood.pressenter.shitang.collect.CollectPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.common.adapter.BusinessListAdapter;
import com.honganjk.ynybzbizfood.view.shitang.collect.interfaces.CollectParentInterfaces;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.CarteenDetailActivity;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * 说明:食堂-我的收藏
 * 2017/3/2-18:51
 */
public class CollectActivity extends BaseMainActivity<CollectParentInterfaces.ICollect, CollectPresenter>
        implements CollectParentInterfaces.ICollect , SuperRecyclerView.ListSwipeViewListener{
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    ArrayList<BusinessData> mDatas = new ArrayList<>();
    CommonAdapter adapter;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, CollectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public CollectPresenter initPressenter() {
        return new CollectPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_classify_common;
    }


    @Override
    public void initView() {
        super.initView();
        toolbar.setTitle("我的收藏");
        toolbar.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        adapter = new BusinessListAdapter(this, mDatas);

        switchRoot.setOnLoaddingListener(this);
        switchRoot.setOnRefreshListener(this);
        switchRoot.getRecyclerView().setLoadMoreEnabled(true);
        switchRoot.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        switchRoot.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_5).build());
        switchRoot.setAdapter(adapter);
        presenter.getHttpData(true,userData.getLongitude(), userData.getLatitude());

        adapter.setOnItemClickListener(new OnItemClickListener<BusinessData>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, BusinessData data, int position) {
                CarteenDetailActivity.startUI(CollectActivity.this, data.getBid());
            }
        });
    }

    @Override
    public void parseIntent(Intent intent) {

    }

    @Override
    public int currentItem() {
        return 1;
    }


    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return switchRoot.getSwipeRefreshLayout();
    }

    @Override
    public void onRefresh() {
        presenter.getHttpData(true,userData.getLongitude(), userData.getLatitude());
    }

    @Override
    public void clearData() {
        mDatas.clear();
    }

    @Override
    public void getHttpData(List<BusinessData> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getHttpData(true,userData.getLongitude(), userData.getLatitude());
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getHttpData(true,userData.getLongitude(), userData.getLatitude());
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
    public void onLoadding() {
//        presenter.getHttpData(false,userData.getLongitude(), userData.getLatitude());
    }
}
