package com.honganjk.ynybzbizfood.view.peihu.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseListFragment;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendData;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserTypeData;
import com.honganjk.ynybzbizfood.pressenter.peihu.home.NursingRecoveryPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.peihu.collect.adapter.NurserHomeAdapter;
import com.honganjk.ynybzbizfood.view.peihu.home.activity.NursingRecoveryDetailsActivity;
import com.honganjk.ynybzbizfood.view.peihu.home.activity.NursingRecoveryManagerActivity;
import com.honganjk.ynybzbizfood.view.peihu.home.interfaces.IHomeParentInterfaces;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 注释说明: 护工与康复师列表类
 * 阳：2017/4/1-11:40
 */
@SuppressLint("ValidFragment")
public class NursingRecoveryFragment extends BaseListFragment<IHomeParentInterfaces.INursingRecover, NursingRecoveryPresenter>
        implements IHomeParentInterfaces.INursingRecover {
    NursingRecoveryManagerActivity managerActivity;
    //服务的分类
    @BindView(R.id.type)
    RecyclerView typeList;
    ArrayList<NurserTypeData> serviceDatas = new ArrayList<>();

    Unbinder unbinder;
    private int mType;//护理人员类型，1-全天工，2-钟点工
    private int mSortType;//排序类型，1-按销量,2-按评分
    private ArrayList<NurserCommendData> mDatas = new ArrayList<>();
    private NurserHomeAdapter adapter;
    private CommonAdapter serviceAdapter;
    //服务项的点击位置
    private int clickPosition = 0;
    //服务项
    private int mServiceId = -1;

    @SuppressLint("ValidFragment")
    public NursingRecoveryFragment() {
    }

    /**
     * @param type     护理人员类型，1-全天工，2-钟点工
     * @param sortType 排序类型，1-按销量,2-按评分
     */
    public NursingRecoveryFragment(int type, int sortType) {
        this.mType = type;
        this.mSortType = sortType;
    }

    /**
     * @param type     护理人员类型，1-全天工，2-钟点工
     * @param sortType 排序类型，1-按销量,2-按评分
     */
    public static NursingRecoveryFragment getInstance(int type, int sortType) {
        return new NursingRecoveryFragment(type, sortType);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_nusing_list;
    }

    @Override
    public void initView() {
        super.initView();
        managerActivity = (NursingRecoveryManagerActivity) activity;

        serviceDatas.add(new NurserTypeData(-1, "全部", true));
        typeList.setLayoutManager(new LinearLayoutManager(activity));
        typeList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.main_gray2).sizeResId(R.dimen.dp_0_5).build());


        adapter.setOnItemClickListener(new OnItemClickListener<NurserCommendData>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NurserCommendData data, int position) {
                NursingRecoveryDetailsActivity.startUI(activity,  data.getId(),mType);
            }
        });


        serviceAdapter = new CommonAdapter<NurserTypeData>(managerActivity, R.layout.item_service_type, serviceDatas) {
            @Override
            public void convert(ViewHolder holder, NurserTypeData data) {
                TextView tv = holder.getView(R.id.describe);
                tv.setText(data.getLabel());
                holder.getConvertView().setSelected(data.isSelect());
                holder.getView(R.id.mark).setVisibility(data.isSelect() ? View.VISIBLE : View.INVISIBLE);

            }
        };
        //类型的点击
        serviceAdapter.setOnItemClickListener(new OnItemClickListener<NurserTypeData>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NurserTypeData data, int position) {
                serviceDatas.get(clickPosition).setSelect(false);
                serviceAdapter.notifyItemChanged(clickPosition);

                data.setSelect(!data.isSelect());
                serviceAdapter.notifyItemChanged(position);
                clickPosition = position;
                mServiceId = data.getId();

                presenter.getNursingTherapistDatas(true, mType, mSortType, managerActivity.mFiltrate, mServiceId);
            }
        });
        typeList.setAdapter(serviceAdapter);

        presenter.getService(mType);
        presenter.getNursingTherapistDatas(true, mType, mSortType, managerActivity.mFiltrate, mServiceId);
    }


    @Override
    public CommonAdapter getAdapter() {
        return adapter = new NurserHomeAdapter(activity, mDatas);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == activity.REQUEST_CODE && resultCode == activity.RESULT_OK) {
            presenter.getNursingTherapistDatas(true, mType, mSortType, managerActivity.mFiltrate, mServiceId);
        }
    }


    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(activity).colorResId(R.color.main_gray2).sizeResId(R.dimen.dp_5).build();
    }

    @Override
    public void onRefresh() {
        presenter.getNursingTherapistDatas(true, mType, mSortType, managerActivity.mFiltrate, mServiceId);
    }

    @Override
    public NursingRecoveryPresenter initPressenter() {
        return new NursingRecoveryPresenter();
    }

    @Override
    public void getNursingTherapistDatas(List<NurserCommendData> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void serviceType(List<NurserTypeData> datas) {
        serviceDatas.addAll(datas);
        serviceAdapter.notifyDataSetChanged();

    }

    @Override
    public void clearData() {
        mDatas.clear();
    }

    @Override
    public void onLoadding() {
        presenter.getNursingTherapistDatas(false, mType, mSortType, managerActivity.mFiltrate, mServiceId);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getNursingTherapistDatas(true, mType, mSortType, managerActivity.mFiltrate, mServiceId);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getNursingTherapistDatas(true, mType, mSortType, managerActivity.mFiltrate, mServiceId);
    }

    /**
     * 获取筛选的数据
     */
    public void getFiltrateData() {
        presenter.getNursingTherapistDatas(true, mType, mSortType, managerActivity.mFiltrate, mServiceId);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
