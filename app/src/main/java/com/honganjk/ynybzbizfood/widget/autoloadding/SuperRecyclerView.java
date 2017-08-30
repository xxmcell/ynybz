package com.honganjk.ynybzbizfood.widget.autoloadding;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.ui.UiUtils;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/28.   自带刷新,加载更多回调
 */
public class SuperRecyclerView extends RelativeLayout {
    @BindView(R.id.swipe)
    public SwipeRefreshLayout swipeView;
    @BindView(R.id.list_swipe_target)
    RecyclerViewAutoLoadding recyclerView;

    public SuperRecyclerView(Context context) {
        this(context, null);
    }

    public SuperRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initView();
        }
    }


    private void initView() {
        UiUtils.getInflaterView(getContext(), R.layout.base_swipe_recycle, this, true);
        ButterKnife.bind(this, this);
        /**
         * 设置集合view的刷新view
         */
        recyclerView.setSwipeRefreshLayout(swipeView);
        UiUtils.setColorSchemeResources(swipeView);

    }


    /**
     * 设置加载更多的回调
     *
     * @param onLoaddingListener
     */
    public void setOnLoaddingListener(OnLoaddingListener onLoaddingListener) {
        recyclerView.setOnLoaddingListener(onLoaddingListener);

    }

    /**
     * 设置下拉刷新的回调
     *
     * @param listener
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        if (swipeView != null) {
            swipeView.setOnRefreshListener(listener);
        }
    }


    /**
     * 设置适配器
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获取pagingHelp
     */
    public PagingHelp getPagingHelp() {
        return recyclerView.getPagingHelp();
    }

    /**
     * 获取分页的有效数据
     */
    public <T> Collection<T> getValidData(Collection<T> c) {
        return getPagingHelp().getValidData(c);
    }


    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeView;
    }


    public ConfigChang getConfigChang() {
        if (recyclerView instanceof ConfigChang) {
            return (ConfigChang) recyclerView;
        } else {
            return null;
        }
    }

    /**
     * 刷新Swp
     *
     * @return
     */
    public void setRefreshing(boolean refreshing) {
        swipeView.setRefreshing(refreshing);
    }


    public RecyclerViewAutoLoadding getRecyclerView() {
        return recyclerView;
    }


    public StatusChangListener getStatusChangListener() {
        return recyclerView.getStatusChangListener();
    }

    public interface ListSwipeViewListener extends SwipeRefreshLayout.OnRefreshListener, OnLoaddingListener, OnRetryClickListion {

    }

    public interface ListViewListener extends OnLoaddingListener, OnRetryClickListion {

    }


}
