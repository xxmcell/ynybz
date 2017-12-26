package com.honganjk.ynybzbizfood.view.tour.classify.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourDetialBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.pressenter.tour.classify.TourClassifyDetailActivityPresenter;
import com.honganjk.ynybzbizfood.view.tour.classify.adapter.TourClassifyDetailAdapter;
import com.honganjk.ynybzbizfood.view.tour.classify.interfaces.MyClassifyPrenterInrerfaces;
import com.honganjk.ynybzbizfood.view.tour.detail.activity.DetailActivity;
import com.honganjk.ynybzbizfood.view.tour.home.activity.TourHomeSearchActivity;
import com.honganjk.ynybzbizfood.widget.PopupPulldown;
import com.honganjk.ynybzbizfood.widget.autoloadding.OnLoaddingListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.R.id.hot_recommend;

/**
 * 旅游分类详情
 * Created by Administrator on 2017-11-17.
 */

public class TourClassifyDetailActivity extends BaseMvpActivity<MyClassifyPrenterInrerfaces.MyClassifyInrerfaces, TourClassifyDetailActivityPresenter>
        implements MyClassifyPrenterInrerfaces.MyClassifyInrerfaces {

    @BindView(R.id.im_classifyback)
    ImageView imClassifyback;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.llclassify_search)
    LinearLayout llclassifySearch;

    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    @BindView(hot_recommend)
    CheckBox hotRecommend;  //热门推荐
    @BindView(R.id.stroke_days)
    CheckBox strokeDays;    //行程天数
    @BindView(R.id.single_budget)
    CheckBox singleBudget;  //单人预算

    private ClassifyTourBean.Data.Objs objs;
    private int did;
    private List<ObjsBean> objsList = new ArrayList<>();
    private TourClassifyDetailAdapter adapter;

    PopupPulldown pp;   //筛选(带动画)
    ArrayList<PopupPulldown.PullDownData> mFiltrareDatas = new ArrayList<>(); //筛选数据源

    public static void startUi(Context context, ClassifyTourBean.Data.Objs objs) {
        Intent intent = new Intent(context, TourClassifyDetailActivity.class);
        intent.putExtra("objs", objs);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_tour_classify_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        switchRoot.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        switchRoot.setOnRefreshListener(this);
        switchRoot.setOnLoaddingListener(new OnLoaddingListener() {
            @Override
            public void onLoadding() {
                presenter.getData(false, did);
            }
        });
        adapter = new TourClassifyDetailAdapter(this, objsList);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object data, int position) {
                DetailActivity.startUi(TourClassifyDetailActivity.this, objsList.get(position));
            }
        });
        switchRoot.setAdapter(adapter);
        presenter.getData(true, did);

        pp = new PopupPulldown(this, mFiltrareDatas);


    }

    @Override
    public void parseIntent(Intent intent) {
        objs = (ClassifyTourBean.Data.Objs) intent.getSerializableExtra("objs");
        did = objs.getId();

    }

    @OnClick({R.id.im_classifyback, R.id.llclassify_search, hot_recommend, R.id.stroke_days, R.id.single_budget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_classifyback:
                finish();
                break;
            case R.id.llclassify_search:
                TourHomeSearchActivity.startUI(this);
                break;
            case hot_recommend://热门推荐
                selectHotRecommend();
                break;
            case R.id.stroke_days://行程天数
                selectStrokeDays();
                break;
            case R.id.single_budget://单人预算
                selectSingleBudget();
                break;
        }
    }

    /**
     * 选择单人预算
     * lower	可选,int	预算价格下限
     * upper	可选,int	预算价格上限
     */
    private int lower = 0;
    private int upper = 0;

    private void selectSingleBudget() {
        mFiltrareDatas.clear();
        PopupPulldown.PullDownData pullDownData0 = new PopupPulldown.PullDownData(0, "单人预算");
        mFiltrareDatas.add(pullDownData0);
        PopupPulldown.PullDownData pullDownData1 = new PopupPulldown.PullDownData(1, "100-1000");
        mFiltrareDatas.add(pullDownData1);
        PopupPulldown.PullDownData pullDownData2 = new PopupPulldown.PullDownData(2, "1000-5000");
        mFiltrareDatas.add(pullDownData2);
        PopupPulldown.PullDownData pullDownData3 = new PopupPulldown.PullDownData(3, "5000-10000");
        mFiltrareDatas.add(pullDownData3);
        pp.showPopupWindow((View) singleBudget.getParent());
        pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
            @Override
            public void onClick(int id, String content) {
                if (!TextUtils.equals(content, "单人预算")) {
                    String[] split = content.split("-");
                    lower = Integer.parseInt(split[0]);
                    upper = Integer.parseInt(split[1]);
                    singleBudget.setText(content);
                    presenter.getDataWithCondition(true, did, num, lower, upper, sort, null);
                }else{
                    singleBudget.setText(content);
                    lower = 0;
                    upper = 0;
                    presenter.getDataWithCondition(true, did, num, lower, upper, sort, null);
                }
            }
        });
    }

    private int num = 0;

    /**
     * 选择行程天数
     */
    private void selectStrokeDays() {
        mFiltrareDatas.clear();
        PopupPulldown.PullDownData pullDownData0 = new PopupPulldown.PullDownData(0, "行程天数");
        mFiltrareDatas.add(pullDownData0);
        PopupPulldown.PullDownData pullDownData1 = new PopupPulldown.PullDownData(1, "1");
        mFiltrareDatas.add(pullDownData1);
        PopupPulldown.PullDownData pullDownData2 = new PopupPulldown.PullDownData(2, "2");
        mFiltrareDatas.add(pullDownData2);
        PopupPulldown.PullDownData pullDownData3 = new PopupPulldown.PullDownData(3, "3");
        mFiltrareDatas.add(pullDownData3);
        pp.showPopupWindow((View) strokeDays.getParent());
        pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
            @Override
            public void onClick(int id, String content) {
                num = id;
                strokeDays.setText(content);
                presenter.getDataWithCondition(true, did, num, lower, upper, sort, null);
            }
        });
    }

    private int sort = 0;

    /**
     * 选择排序方式  排序，1-按积分降序；2-按销量降序
     */

    private void selectHotRecommend() {
        mFiltrareDatas.clear();
        PopupPulldown.PullDownData pullDownData0 = new PopupPulldown.PullDownData(0, "热门推荐");
        mFiltrareDatas.add(pullDownData0);
        PopupPulldown.PullDownData pullDownData1 = new PopupPulldown.PullDownData(1, "评分最高");
        mFiltrareDatas.add(pullDownData1);
        PopupPulldown.PullDownData pullDownData2 = new PopupPulldown.PullDownData(2, "销量最高");
        mFiltrareDatas.add(pullDownData2);
        pp.showPopupWindow((View) hotRecommend.getParent());
        pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
            @Override
            public void onClick(int id, String content) {
                sort = id;
                hotRecommend.setText(content);
                presenter.getDataWithCondition(true, did, num, lower, upper, sort, null);
            }
        });
    }

    @Override
    public TourClassifyDetailActivityPresenter initPressenter() {
        return new TourClassifyDetailActivityPresenter();
    }


    @Override
    public void getHttpData(ClassifyTourDetialBean.Data datas) {

        if (datas != null) {
            if (datas.getObjs() != null) {
                objsList.clear();
                objsList.addAll(getValidData(datas.getObjs()));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        presenter.getData(true, did);
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return switchRoot.getSwipeRefreshLayout();
    }

    @Override
    public StatusChangListener getStatusChangListener() {
        return null;
    }

    @Override
    public <T> Collection<T> getValidData(Collection<T> c) {
        return switchRoot.getPagingHelp().getValidData(c);
    }

    @Override
    public void clearPagingData() {
        switchRoot.getPagingHelp().clear();
    }

    @Override
    public int getPageindex() {
        return switchRoot.getPagingHelp().getPageindex();
    }

    @Override
    public int getPageCount() {
        return switchRoot.getPagingHelp().getPageCount();
    }

    @Override
    public void clearData() {
        objsList.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
