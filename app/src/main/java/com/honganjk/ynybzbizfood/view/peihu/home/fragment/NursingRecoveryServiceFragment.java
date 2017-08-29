package com.honganjk.ynybzbizfood.view.peihu.home.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendDetailsData;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.widget.autoloadding.LoadState;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 说明:护工与康复师-服务内容
 * 360621904@qq.com 杨阳 2017/4/11  11:16
 */
@SuppressLint("ValidFragment")
public class NursingRecoveryServiceFragment extends BaseFragment {
    ArrayList<NurserCommendDetailsData.ItemsBean> mDatas = new ArrayList<>();
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    CommonAdapter adapter;

    @SuppressLint("ValidFragment")
    public NursingRecoveryServiceFragment() {
    }


    public static NursingRecoveryServiceFragment getInstance() {
        return new NursingRecoveryServiceFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_nursing_recovery_service;
    }

    @Override
    public void initView() {
        switchRoot.getRecyclerView().getSwipeRefreshLayout().setEnabled(false);
        switchRoot.getRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        switchRoot.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(activity).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build());
        switchRoot.getRecyclerView().setAdapter(adapter = new CommonAdapter<NurserCommendDetailsData.ItemsBean>(activity, R.layout.item_nursing_service, mDatas) {
            @Override
            public void convert(ViewHolder holder, NurserCommendDetailsData.ItemsBean itemsBean) {
                holder.setText(R.id.title, itemsBean.getTitle());
                holder.setText(R.id.content, itemsBean.getContent());
            }
        });
    }

    public void setDatas(List<NurserCommendDetailsData.ItemsBean> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
        switchRoot.getRecyclerView().setAutoloaddingNoData("共为您加载" + mDatas.size() + "条数据");
        switchRoot.getRecyclerView().getLoaddFooterView().setStatus(LoadState.NoData);

    }


}
