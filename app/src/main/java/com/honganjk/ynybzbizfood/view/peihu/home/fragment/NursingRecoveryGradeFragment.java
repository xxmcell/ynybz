package com.honganjk.ynybzbizfood.view.peihu.home.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendDetailsData;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.widget.autoloadding.LoadState;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 说明:护工与康复师-评价列表
 * 360621904@qq.com 杨阳 2017/4/11  13:54
 */
@SuppressLint("ValidFragment")
public class NursingRecoveryGradeFragment extends BaseFragment {
    ArrayList<NurserCommendDetailsData.ObjsBean> mDatas = new ArrayList<>();
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    @BindView(R.id.number)
    TextView number;
    CommonAdapter adapter;

    @SuppressLint("ValidFragment")
    public NursingRecoveryGradeFragment() {
    }


    public static NursingRecoveryGradeFragment getInstance() {
        return new NursingRecoveryGradeFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_nursing_recovery_grade;
    }

    @Override
    public void initView() {
        switchRoot.getRecyclerView().getSwipeRefreshLayout().setEnabled(false);
        switchRoot.getRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        switchRoot.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(activity).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build());
        switchRoot.getRecyclerView().setAdapter(adapter = new CommonAdapter<NurserCommendDetailsData.ObjsBean>(activity, R.layout.item_evaluate_2, mDatas) {
            @Override
            public void convert(ViewHolder holder, NurserCommendDetailsData.ObjsBean data) {
                holder.setImageBitmapCircle(R.id.avatar, data.getImg());
                holder.setText(R.id.name, data.getName());
                holder.setText(R.id.time, data.getTimeStr());
                holder.setText(R.id.content, data.getContent());
                SimpleRatingBar sb =  holder.getView(R.id.grade);
                sb.setRating(data.getScore());
            }
        });
    }

    public void setDatas(List<NurserCommendDetailsData.ObjsBean> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
        number.setText("评价（" + datas.size() + "）");
        switchRoot.getRecyclerView().setAutoloaddingNoData("共为您加载" + mDatas.size() + "条数据");
        switchRoot.getRecyclerView().getLoaddFooterView().setStatus(LoadState.NoData);
    }


}
