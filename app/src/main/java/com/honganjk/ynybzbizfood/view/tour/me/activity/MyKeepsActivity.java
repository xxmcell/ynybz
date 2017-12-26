package com.honganjk.ynybzbizfood.view.tour.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseListActivity;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.pressenter.tour.me.MyKeepsPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.tour.classify.adapter.TourClassifyDetailAdapter;
import com.honganjk.ynybzbizfood.view.tour.detail.activity.DetailActivity;
import com.honganjk.ynybzbizfood.view.tour.me.interfaces.TourMeInterface;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏页面
 * Created by Administrator on 2017-12-12.
 */

public class MyKeepsActivity extends BaseListActivity<TourMeInterface.MyKeepsInrerface, MyKeepsPresenter>
        implements TourMeInterface.MyKeepsInrerface {

    private int total = 0;

    public static void startUiForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, MyKeepsActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    private List<ObjsBean> dataList = new ArrayList<>(); //条目集合
    private TourClassifyDetailAdapter adapter;

    @Override
    public void onLoadding() {
        presenter.getData(false);
    }

    @Override
    public void clearData() {
        dataList.clear();
    }


    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new TourClassifyDetailAdapter(this, dataList);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object data, int position) {
                DetailActivity.startUiForResult(mActivity, dataList.get(position), REQUEST_CODE);
            }
        });
        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                presenter.getData(true);
                break;
        }
    }

    @Override
    public void initView() {
        super.initView();
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        presenter.getData(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("keepNum", total);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {
        presenter.getData(true);
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
    public MyKeepsPresenter initPressenter() {
        return new MyKeepsPresenter();
    }

    @Override
    public void getKeeps(List<ObjsBean> dataList) {
        if (dataList!=null&&dataList.size()!=0){
            total = dataList.size();
            this.dataList.addAll(dataList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_my_keeps;
    }

    @Override
    public void parseIntent(Intent intent) {

    }
}
