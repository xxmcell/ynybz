package com.honganjk.ynybzbizfood.view.health.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.jiankang.RepostData;
import com.honganjk.ynybzbizfood.pressenter.jiankang.HealthRepostPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.utils.ui.divider.VerticalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.health.view.IHealthManagerParentView;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 说明:健康报告
 * 作者： 阳2012; 创建于：  2017/5/9  16:11
 */
public class HealthReportActivity extends BaseMvpActivity<IHealthManagerParentView.IHealthReport, HealthRepostPresenter>
        implements IHealthManagerParentView.IHealthReport,OnRetryClickListion{
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    private int mOrderId;
    private ArrayList<RepostData> mDatas = new ArrayList<>();

    public static void startUI(Activity activity, int orderId) {
        Intent intent = new Intent(activity, HealthReportActivity.class);
        intent.putExtra("orderId", orderId);
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
        toolbar.setTitle("健康报告");
        toolbar.setBack(this);


        switchRoot.getSwipeRefreshLayout().setEnabled(false);
        switchRoot.getRecyclerView().setLoadMoreEnabled(false);
        switchRoot.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        switchRoot.getRecyclerView().setAdapter(adapter);
//        presenter.getData(431);
        presenter.getData(mOrderId);

    }

    CommonAdapter adapter = new CommonAdapter<RepostData>(this, R.layout.item_repost, mDatas) {

        @Override
        public void convert(ViewHolder holder, RepostData data) {
            holder.setText(R.id.content, data.getTitle());

            RecyclerView recyclerView = holder.getView(R.id.recyclerView);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(HealthReportActivity.this).sizeResId(R.dimen.dp_3).colorResId(R.color.gray_ee).build());
            recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(HealthReportActivity.this).sizeResId(R.dimen.dp_3).colorResId(R.color.gray_ee).build());
            recyclerView.setLayoutManager(new GridLayoutManager(HealthReportActivity.this, 4));

            recyclerView.setAdapter(new CommonAdapter<RepostData.ItemsBean>(HealthReportActivity.this, R.layout.item_repost_itme, data.getItems()) {
                @Override
                public void convert(ViewHolder holder, RepostData.ItemsBean itemsBean) {
                    TextView tv = holder.getView(R.id.content);
                    tv.setText(itemsBean.getTitle());
                    if (itemsBean.isOwn()) {
                        tv.setTextColor(getResources().getColor(R.color.main_color));
                        Drawable drawable=getResources().getDrawable(R.drawable.ic_frame);
                        tv.setBackground(drawable);
                    } else {
                        tv.setTextColor(getResources().getColor(R.color.gray_66));
                        tv.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }

                @Override
                public void convert(ViewHolder holder, List<RepostData.ItemsBean> t) {

                }
            });
        }

        @Override
        public void convert(ViewHolder holder, List<RepostData> t) {

        }
    };


    @Override
    public HealthRepostPresenter initPressenter() {
        return new HealthRepostPresenter();
    }


    @Override
    public void parseIntent(Intent intent) {
        mOrderId = intent.getIntExtra("orderId", mOrderId);
    }

    @Override
    public void clearData() {

    }

    @Override
    public void ListData(List<RepostData> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getData(mOrderId);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getData(mOrderId);
    }
}
