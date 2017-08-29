package com.honganjk.ynybzbizfood.view.shitang.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseListFragment;
import com.honganjk.ynybzbizfood.mode.javabean.common.EvaluateData;
import com.honganjk.ynybzbizfood.pressenter.shitang.home.EvaluatePresenter;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.shitang.home.adapter.EvaluateAdapter;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 店家-评价
 */
public class EvaluateFragment extends BaseListFragment<HomeParentInterface.evaluate, EvaluatePresenter>
        implements HomeParentInterface.evaluate {

    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_grade_progress)
    TextView tvGradeProgress;
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
//    @BindView(R.id.recyclerView)
//    RecyclerViewAutoLoadding recyclerView;

    private int id = -1;

    private EvaluateAdapter adapter;

    private EvaluateData datas = new EvaluateData();
    private ArrayList<EvaluateData.ListBean> evaluates = new ArrayList<>();

    private int curPage = 0;
    private int pageSize = 10;

    @SuppressLint("ValidFragment")
    public EvaluateFragment(int id) {
        this.id = id;
    }

    public EvaluateFragment() {

    }

    @Override
    public EvaluatePresenter initPressenter() {
        return new EvaluatePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_evaluate;
    }

    @Override
    public void initView() {
        super.initView();
        presenter.getEvaluate(true, id);
    }

    @Override
    public Object getAdapter() {
        adapter = new EvaluateAdapter(getContext(), evaluates);
        return adapter;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(
                getContext()).size((int) getResources().getDimension(R.dimen.dp_10)).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    int progressWidth = 0;

    @Override
    public void evaluate(EvaluateData data, List<EvaluateData.ListBean> datas) {
        this.datas = data;
        evaluates.addAll(datas);
        tvRank.setText(String.format(getResources().getString(R.string.tv_grade), data.getScore() + ""));
        tvGrade.setText(String.format(getResources().getString(R.string.tv_rank), data.getRank() + ""));
        tvGradeProgress.setText(String.format(getResources().getString(R.string.tv_grade_num), data.getScore() + ""));

        // 0:50  1-22 2-39 3-39 4-39 5-39
        if(progressWidth==0){
            progressWidth = tvGradeProgress.getWidth();
        }
        int begin = DimensUtils.dip2px(getContext(),52)-progressWidth;
        int leftMargin;
        int topMargin = DimensUtils.dip2px(getContext(),15);
        if(data.getScore()<1){
            leftMargin = (int) (begin+data.getScore()*22);
        }else{
            leftMargin = (int) (begin+DimensUtils.dip2px(getContext(),22)+(data.getScore()-1)*
                                DimensUtils.dip2px(getContext(),40));
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvGradeProgress.getLayoutParams();
        params.setMargins(leftMargin,topMargin,0,0);
        tvGradeProgress.setLayoutParams(params);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearData() {
        evaluates.clear();
    }

    @Override
    public void onLoadding() {
        presenter.getEvaluate(false, id);
    }

    @Override
    public void onRefresh() {
        presenter.getEvaluate(true, id);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getEvaluate(true, id);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getEvaluate(true, id);
    }
}
