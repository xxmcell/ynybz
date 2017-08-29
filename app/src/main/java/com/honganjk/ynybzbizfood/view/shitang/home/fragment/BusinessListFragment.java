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
import com.honganjk.ynybzbizfood.pressenter.shitang.home.CommunityCarteenPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.common.adapter.BusinessListAdapter;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.CarteenDetailActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;
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

public class BusinessListFragment extends BaseListFragment<HomeParentInterface.communityCarteen, CommunityCarteenPresenter>
        implements HomeParentInterface.communityCarteen{

    @BindView(R.id.switchRoot)
    SuperRecyclerView swipe;

    ArrayList<BusinessData> mDatas = new ArrayList<>();
    BusinessListAdapter adapter;

    int businessType = 1;//1:社区食堂 2：营养餐 3：早餐
    int sortType = 0;   //0 : 距离   1：评分

    @Override
    public int getContentView() {
        return R.layout.fragment_business_list;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle bundle = getArguments();

        if(bundle!=null){
            sortType = getArguments().getInt("sortType");
            businessType = getArguments().getInt("businessType");
        }

        adapter.setOnItemClickListener(new OnItemClickListener<BusinessData>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, BusinessData data, int position) {
                CarteenDetailActivity.startUI(getActivity(), data.getId(),businessType);
            }
        });
        presenter.getCommunicationCarteenListData(false,userData.getLongitude(),userData.getLatitude(),userData.getCurrentCity(), businessType,sortType);
    }

    @Override
    public Object getAdapter() {
        adapter = new BusinessListAdapter(getActivity(),mDatas);
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
        presenter.getCommunicationCarteenListData(true,userData.getLongitude(),userData.getLatitude(),userData.getCurrentCity(),businessType,sortType);
    }

    @Override
    public CommunityCarteenPresenter initPressenter() {
        return new CommunityCarteenPresenter();
    }

    @Override
    public void businessList(List<BusinessData> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearData() {
        mDatas.clear();
    }

    @Override
    public void onLoadding() {
        presenter.getCommunicationCarteenListData(false,userData.getLongitude(),userData.getLatitude(),userData.getCurrentCity(),businessType,sortType);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getCommunicationCarteenListData(true,userData.getLongitude(),userData.getLatitude(),userData.getCurrentCity(),businessType,sortType);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getCommunicationCarteenListData(true,userData.getLongitude(),userData.getLatitude(),userData.getCurrentCity(),businessType,sortType);
    }
}
