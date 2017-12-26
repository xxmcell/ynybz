package com.honganjk.ynybzbizfood.view.tour.detail.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.tour.detail.adapter.DetailAdapter;

import butterknife.BindView;

/**
 * 旅游详情-产品详情
 * Created by Administrator on 2017-11-22.
 */

public class DetailsFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private DetailAdapter adapter;

    public DetailsFragment() {
    }

    public static DetailsFragment getInstance() {
        return new DetailsFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.store_fragment_product_details;
    }

    @Override
    public void initView() {

    }

    public void setAdapter(TourDetailBean.DataBean dataBean) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DetailAdapter(getContext(),dataBean);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).sizeResId(R.dimen.dp_8).colorResId(R.color.gray_ee).build());
        recyclerView.setAdapter(adapter);
    }

}
