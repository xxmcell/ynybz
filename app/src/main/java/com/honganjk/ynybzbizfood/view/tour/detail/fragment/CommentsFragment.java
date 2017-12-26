package com.honganjk.ynybzbizfood.view.tour.detail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.fragment.MyBaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.DetailCommentsBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;
import com.honganjk.ynybzbizfood.pressenter.tour.detail.TourDetailCommentPresenter;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.tour.detail.interfaces.TourDetailInterface;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;
import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static android.R.attr.id;

/**
 * Created by Administrator on 2017-11-22.
 */

public class CommentsFragment extends MyBaseFragment<TourDetailInterface.DetailCommentsInterface, TourDetailCommentPresenter>
        implements TourDetailInterface.DetailCommentsInterface, SuperRecyclerView.ListSwipeViewListener {

    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_good)
    TextView tvGood;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.tv_bad)
    TextView tvBad;
    @BindView(R.id.tv_imgs)
    TextView tvImgs;
    @BindView(R.id.rl_num)
    RelativeLayout rlNum;
    @BindView(R.id.switchRoot)
    SuperRecyclerView switchRoot;
    Unbinder unbinder;

    private CommonAdapter adapter;
    //private TourDetailBean.DataBean.Comments comments;
    private int tid;
    private int type = 0;  //评论类型，1-好评，2-中评，3-差评，4-有图，空值为全部评论
    private List<DetailCommentsBean.Data.ObjsBean> objsBeanList = new ArrayList<>();

    public CommentsFragment() {
    }

    public static CommentsFragment getInstance(int tid) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tid",tid);
        fragment.setArguments(bundle);
        return fragment;
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
    public void getHttpData(DetailCommentsBean.Data data) {
        if (data != null&&data.getObjs().size()!=0) {
            objsBeanList = data.getObjs();
        }else{
            objsBeanList.clear();
            DetailCommentsBean.Data.ObjsBean objsBean = new DetailCommentsBean.Data.ObjsBean();
            objsBean.setAvatar("https://hajk.image.alimmdn.com/bz/head.jpg");
            objsBean.setContent("测试6");
            objsBean.setName("qaz");
            objsBean.setScore(3);
            objsBean.setTime("1466504329000");
            objsBeanList.add(objsBean);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tour_detail_comments, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public void init() {
        tid = getArguments().getInt("tid",-1);
        TourDetailBean.DataBean.Comments comments = (TourDetailBean.DataBean.Comments) getArguments().getSerializable("comments");
        if (comments==null){
            comments = new TourDetailBean.DataBean.Comments();
            comments.setAll(1);
        }else if (comments.getAll()==0){
            comments.setAll(1);
        }

        switchRoot.setOnLoaddingListener(this);
        switchRoot.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        switchRoot.getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.gray_e1).sizeResId(R.dimen.dp_1).build());

        if (comments == null || comments.getAll() == 0) {//数据空
            rlNum.setVisibility(View.GONE);
            ArrayList<String> strings = new ArrayList<>();
            strings.add("暂无评价");
            switchRoot.setAdapter(adapter = new CommonAdapter<String>(getContext(), R.layout.store_empty, strings) {
                @Override
                public void convert(ViewHolder holder, String s) {
                    TextView view = holder.getView(R.id.tv_empty);
                    view.setTextColor(getResources().getColor(R.color.main_red));
                    holder.setText(R.id.tv_empty, s);
                }

                @Override
                public void convert(ViewHolder holder, List<String> t) {

                }
            });
        } else {
            rlNum.setVisibility(View.VISIBLE);
            tvAll.setText("全部(" + comments.getAll() + ")");
            tvGood.setText("好评(" + comments.getGood() + ")");
            tvBad.setText("差评(" + comments.getBad() + ")");
            tvNormal.setText("中评(" + comments.getNormal() + ")");
            tvImgs.setText("有图(" + comments.getImgs() + ")");
            presenter.getCommentsData(tid, type);
            switchRoot.setAdapter(adapter = new CommonAdapter<DetailCommentsBean.Data.ObjsBean>(getContext(), R.layout.item_evaluate_2, objsBeanList) {

                @Override
                public void convert(ViewHolder holder, DetailCommentsBean.Data.ObjsBean objsBean) {
                    holder.setImageBitmap(R.id.avatar, objsBean.getAvatar());
                    holder.setText(R.id.name, objsBean.getName());
                    ((SimpleRatingBar) holder.getView(R.id.grade)).setNumberOfStars(objsBean.getScore());
                    holder.setText(R.id.time, DateUtils.dateToString(objsBean.getTime(),DateUtils.TIME));
            //        holder.setText(R.id.contant,"测试");
                }

                @Override
                public void convert(ViewHolder holder, List<DetailCommentsBean.Data.ObjsBean> t) {

                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean isLogin(boolean isToLogin) {
        return false;
    }

    @Override
    public void showDialog(String msg, boolean isCancelable) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void clearData() {

    }

    @Override
    public LoadingAndRetryManager getLoadingAndRetryManager() {
        return null;
    }

    @Override
    public void addSubscription(Subscription s) {

    }

    @Override
    public CompositeSubscription getCompositeSubscription() {
        return null;
    }

    @Override
    public void showErrorSnackbar(String result) {

    }

    @Override
    public void showErrorSnackbar(String result, boolean isFinish) {

    }

    @Override
    public void showWarningSnackbar(String result, boolean isFinish) {

    }

    @Override
    public void showWarningSnackbar(String result) {

    }

    @Override
    public void showSnackbar(String result, int type, boolean isFinish) {

    }

    @Override
    public StatusChangListener getStatusChangListener() {
        return null;
    }

    @Override
    public <T> Collection<T> getValidData(Collection<T> c) {
        return null;
    }

    @Override
    public void clearPagingData() {

    }

    @Override
    public TourDetailCommentPresenter initPressenter() {
        return new TourDetailCommentPresenter();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadding() {

    }

    @Override
    public void onRetryClick(ContextData data) {

    }

    @Override
    public void onEmptyClick(ContextData data) {

    }

    @OnClick({R.id.tv_all, R.id.tv_good, R.id.tv_normal, R.id.tv_bad, R.id.tv_imgs})
    public void onViewClicked(View view) {
        //评论类型，1-好评，2-中评，3-差评，4-有图，空值为全部评论
        switch (view.getId()) {
            case R.id.tv_all:
                type = 0;
                presenter.getCommentsData(id, type);
                break;
            case R.id.tv_good:
                type = 1;
                presenter.getCommentsData(id, type);
                break;
            case R.id.tv_normal:
                type = 2;
                presenter.getCommentsData(id, type);
                break;
            case R.id.tv_bad:
                type = 3;
                presenter.getCommentsData(id, type);
                break;
            case R.id.tv_imgs:
                type = 4;
                presenter.getCommentsData(id, type);
                break;
        }
    }
}
