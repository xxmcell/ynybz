package com.honganjk.ynybzbizfood.view.health.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseListActivity;
import com.honganjk.ynybzbizfood.mode.javabean.jiankang.HealthData;
import com.honganjk.ynybzbizfood.pressenter.jiankang.HealthManagerPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.health.view.IHealthManagerParentView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明:健康管理
 * 作者： 阳2012; 创建于：  2017/5/9  16:11
 */
public class HealthManagerActivity extends BaseListActivity<IHealthManagerParentView.IHealthManager, HealthManagerPresenter>
        implements IHealthManagerParentView.IHealthManager {
    private CommonAdapter adapter;
    private ArrayList<HealthData.ObjsBean> mDatas = new ArrayList<>();

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, HealthManagerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_health_manager;
    }

    @Override
    public void initView() {
        super.initView();
        toolbar.setTitle("健康管理");
        toolbar.setBack(this);
        listSwipeView.setOnRefreshListener(this);
        listSwipeView.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new OnItemClickListener<HealthData.ObjsBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, HealthData.ObjsBean data, int position) {
                HealthReportActivity.startUI(HealthManagerActivity.this,data.getId());
            }
        });

        presenter.getListData(true);
    }


    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.translucent).sizeResId(R.dimen.dp_0_5).build();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter = new CommonAdapter<HealthData.ObjsBean>(this, R.layout.item_health, mDatas) {
            @Override
            public void convert(ViewHolder holder, HealthData.ObjsBean data) {
                holder.setText(R.id.time,data.getServiceTime());
                holder.setText(R.id.content,data.getTitle());
                holder.setText(R.id.name,data.getName());

                holder.setBackgroundColor(R.id.time,data.getTypeColor(HealthManagerActivity.this));
            }

            @Override
            public void convert(ViewHolder holder, List<HealthData.ObjsBean> t) {

            }


        };
    }

    @Override
    public void onRefresh() {
        presenter.getListData(true);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getListData(true);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getListData(true);
    }

    @Override
    public void onLoadding() {
        presenter.getListData(false);
    }

    @Override
    public HealthManagerPresenter initPressenter() {
        return new HealthManagerPresenter();
    }


    @Override
    public void parseIntent(Intent intent) {

    }

    @Override
    public void clearData() {

    }


    @Override
    public void ListData(List<HealthData.ObjsBean> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
    }
}
