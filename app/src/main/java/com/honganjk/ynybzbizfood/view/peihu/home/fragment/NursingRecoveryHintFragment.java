package com.honganjk.ynybzbizfood.view.peihu.home.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 说明:护工与康复师-提示
 * 360621904@qq.com 杨阳 2017/4/11  11:16
 */
@SuppressLint("ValidFragment")
public class NursingRecoveryHintFragment extends BaseFragment {
    ArrayList<String> mDatas = new ArrayList<>();
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    CommonAdapter adapter;

    @SuppressLint("ValidFragment")
    public NursingRecoveryHintFragment() {
    }


    public static NursingRecoveryHintFragment getInstance() {
        return new NursingRecoveryHintFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_nursing_recovery_service;
    }

    @Override
    public void initView() {
        switchRoot.getRecyclerView().getSwipeRefreshLayout().setEnabled(false);
        switchRoot.getRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        switchRoot.getRecyclerView().setAdapter(adapter = new CommonAdapter<String>(activity, R.layout.item_nursing_service, mDatas) {
            @Override
            public void convert(ViewHolder holder, String data) {
                holder.setVisible(R.id.title, false);
//                ((TextView)holder.getView(R.id.content)).setTextSize(getResources().getDimension(R.dimen.text_xxm));
                holder.setText(R.id.content, data);
            }

            @Override
            public void convert(ViewHolder holder, List<String> t) {

            }
        });
    }

    public void setDatas(List<String> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();


    }


}
