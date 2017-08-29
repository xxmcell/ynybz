package com.honganjk.ynybzbizfood.code.base.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.widget.autoloadding.SuperRecyclerView;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;

import java.util.Collection;

import butterknife.BindView;

import static com.honganjk.ynybzbizfood.R.id.switchRoot;

/**
 * Created by Administrator on 2016/8/8.
 */

public abstract class BaseListFragment<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpFragment<V, T>
        implements SuperRecyclerView.ListSwipeViewListener {
    @BindView(switchRoot)
    protected SuperRecyclerView listSwipeView;

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return listSwipeView.getSwipeRefreshLayout();
    }

    public StatusChangListener getStatusChangListener() {
        return listSwipeView.getStatusChangListener();
    }

    @Override
    public void initView() {
        Object adapter = getAdapter();

        listSwipeView.getRecyclerView().addItemDecoration(
                getItemDecoration());
        listSwipeView.getRecyclerView().setLayoutManager(getLayoutManager());
        listSwipeView.setAdapter((CommonAdapter) adapter);
        listSwipeView.setOnRefreshListener(this);
        listSwipeView.setOnLoaddingListener(this);


    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    public abstract Object getAdapter();

    public abstract RecyclerView.ItemDecoration getItemDecoration();

    /**
     * 获取分页的有效数据
     */
    public <T> Collection<T> getValidData(Collection<T> c) {
        return listSwipeView.getPagingHelp().getValidData(c);
    }

    public void clearPagingData() {
        listSwipeView.getPagingHelp().clear();
    }

    public int getPageindex() {
        return listSwipeView.getPagingHelp().getPageindex();
    }

    public int getPageCount() {
        return listSwipeView.getPagingHelp().getPageCount();
    }
}
