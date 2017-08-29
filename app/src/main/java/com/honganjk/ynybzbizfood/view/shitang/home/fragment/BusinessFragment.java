package com.honganjk.ynybzbizfood.view.shitang.home.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseListFragment;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;
import com.honganjk.ynybzbizfood.pressenter.shitang.home.HomePresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.common.adapter.BusinessListAdapter;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.CarteenDetailActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;
import com.honganjk.ynybzbizfood.widget.autoloadding.LoadState;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;

import java.util.ArrayList;
import java.util.List;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;


/**
 * 推荐
 */
public class BusinessFragment extends BaseListFragment<HomeParentInterface.home, HomePresenter>
        implements HomeParentInterface.home, OnRetryClickListion {
    ArrayList<BusinessData> mDatas = new ArrayList<>();
    BusinessListAdapter adapter;

    @Override
    public int getContentView() {
        return R.layout.fragment_business_list;
    }

    @Override
    public void initView() {
        super.initView();
        listSwipeView.setOnRefreshListener(this);


        presenter.getBusinessListData(userData.getLongitude(), userData.getLatitude(), userData.getCurrentCity());
        adapter.setOnItemClickListener(new OnItemClickListener<BusinessData>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, BusinessData data, int position) {
                CarteenDetailActivity.startUI(getActivity(), data.getId());
            }
        });


    }

    @Override
    public Object getAdapter() {
        adapter = new BusinessListAdapter(getActivity(), mDatas);
        return adapter;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(getActivity()).colorResId(R.color.clr_divide).sizeResId(R.dimen.dp_10).build();
    }


    @Override
    public void onRefresh() {
        presenter.getBusinessListData(userData.getLongitude(), userData.getLatitude(), userData.getCurrentCity());

    }

    @Override
    public HomePresenter initPressenter() {
        return new HomePresenter();
    }

    @Override
    public void clearData() {
        mDatas.clear();
    }

    @Override
    public void onLoadding() {
        presenter.getBusinessListData(userData.getLongitude(), userData.getLatitude(), userData.getCurrentCity());
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getBusinessListData(userData.getLongitude(), userData.getLatitude(), userData.getCurrentCity());
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getBusinessListData(userData.getLongitude(), userData.getLatitude(), userData.getCurrentCity());
    }


    @Override
    public void businessList(BusinessData datas) {
        mDatas.clear();
        mDatas.add(datas);
        adapter.notifyDataSetChanged();

        listSwipeView.getRecyclerView().setAutoloaddingNoData("共为您加载" + mDatas.size() + "条数据");
        listSwipeView.getRecyclerView().getLoaddFooterView().setStatus(LoadState.NoData);
    }

    @Override
    public void commonList(List<BusinessData> datas) {

    }
}
