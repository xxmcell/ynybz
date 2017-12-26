package com.honganjk.ynybzbizfood.view.tour.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseListActivity;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.home.HomeTourBean;
import com.honganjk.ynybzbizfood.pressenter.tour.home.HomeTourPresenter;
import com.honganjk.ynybzbizfood.pressenter.tour.home.interfa.TourHomeParentinterfaces;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.tour.classify.adapter.TourClassifyDetailAdapter;
import com.honganjk.ynybzbizfood.view.tour.detail.activity.DetailActivity;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果页面
 * Created by Administrator on 2017-12-07.
 */

public class TourSearchResultActivity extends BaseListActivity<TourHomeParentinterfaces.IHomeInterface, HomeTourPresenter>
        implements TourHomeParentinterfaces.IHomeInterface {


    List<ObjsBean> list = new ArrayList<>();
    private String keyword;
    private TourClassifyDetailAdapter adapter;

    public static void startUi(Context context,String keyword) {
        Intent intent = new Intent(context, TourSearchResultActivity.class);
        intent.putExtra("keyword",keyword);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        super.initView();
        presenter.getSearchData(true,keyword);
        listSwipeView.getRecyclerView().setLoadMoreEnabled(true);
        findViewById(R.id.im_searchback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onLoadding() {
        presenter.getSearchData(false,keyword);
    }

    @Override
    public void getHttpData(HomeTourBean.DataBean datas) {

    }

    @Override
    public void getSearchHttpData(List<ObjsBean> objsBeanList) {
        list.addAll(objsBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new TourClassifyDetailAdapter(this,list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object data, int position) {
                Intent intent = new Intent(TourSearchResultActivity.this, DetailActivity.class);
                intent.putExtra("objs",list.get(position));
                startActivity(intent);
            }
        });
        return adapter;
    }

    @Override
    public void onRefresh() {
        presenter.getSearchData(true,keyword);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getSearchData(true,keyword);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getSearchData(true,keyword);
    }

    @Override
    public void clearData() {
        list.clear();
    }

    @Override
    public HomeTourPresenter initPressenter() {
        return new HomeTourPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_tour_search_result;
    }

    @Override
    public void parseIntent(Intent intent) {
        keyword = intent.getStringExtra("keyword");
    }
}
