package com.honganjk.ynybzbizfood.view.shitang.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * Created by Administrator on 2017/3/4 0004.
 */

/**
 * 常吃
 */
public class CommonFragment extends BaseListFragment<HomeParentInterface.home, HomePresenter>
        implements HomeParentInterface.home {

    @BindView(R.id.switchRoot)
    SuperRecyclerView swipe;

    ArrayList<BusinessData> mDatas = new ArrayList<>();
    BusinessListAdapter adapter;


    @Override
    public int getContentView() {
        return R.layout.fragment_business_list;
    }

    @Override
    public void initView() {
        super.initView();
        adapter.setOnItemClickListener(new OnItemClickListener<BusinessData>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, BusinessData data, int position) {
                CarteenDetailActivity.startUI(getActivity(), data.getId());
            }
        });

        presenter.getCommonListData(userData.getLongitude(), userData.getLatitude());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onRefresh() {
        presenter.getCommonListData(userData.getLongitude(), userData.getLatitude());
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
        presenter.getCommonListData(userData.getLongitude(), userData.getLatitude());
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getCommonListData(userData.getLongitude(), userData.getLatitude());
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getCommonListData(userData.getLongitude(), userData.getLatitude());
    }


    @Override
    public void businessList(BusinessData datas) {

    }

    @Override
    public void commonList(List<BusinessData> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
        listSwipeView.getRecyclerView().setAutoloaddingNoData("共为您加载" + mDatas.size() + "条数据");
        listSwipeView.getRecyclerView().getLoaddFooterView().setStatus(LoadState.NoData);
    }
}
