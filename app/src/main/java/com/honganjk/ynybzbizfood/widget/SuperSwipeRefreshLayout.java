package com.honganjk.ynybzbizfood.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.honganjk.ynybzbizfood.utils.ui.UiUtils;


/**
 * **
 * 创建时间: 12:49 Administrator
 *
 * <p>
 * 功能介绍：d
 */


public class SuperSwipeRefreshLayout extends SwipeRefreshLayout {

    OnRefreshListener mListener;
    View view = null;
    boolean isMOVE = false;

    public SuperSwipeRefreshLayout(Context context) {
        super(context);
        UiUtils.setColorSchemeResources(this);
    }

    public SuperSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        UiUtils.setColorSchemeResources(this);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (isMOVE) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setViewPager(View view) {
        this.view = view;
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        isMOVE = true;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isMOVE = false;
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void setRefreshing(final boolean refreshing) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                SuperSwipeRefreshLayout.super.setRefreshing(refreshing);
                if (refreshing) {
                    mListener.onRefresh();
                }
            }
        }, 400);

    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mListener = listener;
        super.setOnRefreshListener(listener);
    }
}
