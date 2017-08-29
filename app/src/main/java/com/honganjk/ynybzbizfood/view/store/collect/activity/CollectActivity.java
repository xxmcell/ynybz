package com.honganjk.ynybzbizfood.view.store.collect.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseListActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.pressenter.store.collect.CollectPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.classify.adapter.StoreClassifyAdapter;
import com.honganjk.ynybzbizfood.view.store.collect.interfaces.ICollectParentInterfaces;
import com.honganjk.ynybzbizfood.view.store.home.activity.ProductDetailsActivity;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明:商城-分类页面
 * 作者： 杨阳; 创建于：  2017-06-29  10:21
 * <p>
 * 黑空星云
 */
public class CollectActivity extends BaseListActivity<ICollectParentInterfaces.ICollectInterface, CollectPresenter>
        implements ICollectParentInterfaces.ICollectInterface {
    ArrayList<StoreHomeData.ObjsBean> mDatas = new ArrayList<>();
    StoreClassifyAdapter adapter;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, CollectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.store_activity_collect;
    }

    @Override
    public void initView() {
        toolbar.setTitle("我的收藏");
        toolbar.setNavigationIcon(R.drawable.material_arrwos_white_green);
        toolbar.setBack(this);

        adapter = new StoreClassifyAdapter(this, mDatas);
        listSwipeView.setOnRefreshListener(this);
        listSwipeView.setOnLoaddingListener(this);
        listSwipeView.getRecyclerView().setLayoutManager(new GridLayoutManager(this, 2));
        listSwipeView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener<StoreHomeData.ObjsBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, StoreHomeData.ObjsBean data, int position) {
                ProductDetailsActivity.startUI(mActivity, data.getId());
            }
        });
        presenter.getData(true);
    }

    @Override
    public void parseIntent(Intent intent) {
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter = new StoreClassifyAdapter(this, mDatas);
    }

    @Override
    public CollectPresenter initPressenter() {
        return new CollectPresenter();
    }


    @Override
    public void onRefresh() {
        presenter.getData(true);
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return listSwipeView.getSwipeRefreshLayout();
    }

    @Override
    public void clearData() {
        mDatas.clear();
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
