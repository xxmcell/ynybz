package com.honganjk.ynybzbizfood.widget.slips;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 说明:
 *
 * 布局的样例
  <?xml version="1.0" encoding="utf-8"?>
 <com.lyxs916.utilslibrary.view.slips.SWSlipsLayout xmlns:android="http://schemas.android.com/apk/res/android"
 android:id="@+id/sll"
 android:layout_width="match_parent"
 android:layout_height="wrap_content"
 android:orientation="vertical">

 <LinearLayout
 android:layout_width="match_parent"
 android:layout_height="wrap_content"
 android:orientation="vertical">

 <TextView
 android:id="@+id/test"
 android:layout_width="match_parent"
 android:text="123456"
 android:layout_height="50dp"
 android:padding="@dimen/dp_10" />


 </LinearLayout>

 <LinearLayout
 android:id="@+id/linear"
 android:layout_width="160dp"
 android:layout_height="50dp">

 <TextView
 android:id="@+id/text_delete"
 android:layout_width="0dp"
 android:layout_height="match_parent"
 android:layout_weight="1"
 android:background="@android:color/black"
 android:gravity="center"
 android:text="置顶"
 android:textColor="@android:color/white" />

 <TextView
 android:id="@+id/texts"
 android:layout_width="0dp"
 android:layout_height="match_parent"
 android:layout_weight="1"
 android:background="@android:color/black"
 android:gravity="center"
 android:text="删除"
 android:textColor="@android:color/white" />
 </LinearLayout>
 </com.lyxs916.utilslibrary.view.slips.SWSlipsLayout>
 *
 */

public class SWSlipsLayout extends LinearLayout {

    private View hiddenView;
    private View itemView;
    private int hiddenViewWidth;
    private ViewDragHelper helper;
    private Status changeStatus = Status.Close;
    private float downX;
    private float downY;
    private float moveX;
    private float moveY;
    private float downIX;
    private float downIY;

    /**
     * status
     */
    public enum Status {
        Open, Close
    }

    public SWSlipsLayout(Context context) {
        super(context);
        initial();
    }

    public SWSlipsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    private void initial() {
        helper = ViewDragHelper.create(this, callback);
        setOrientation(HORIZONTAL);
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        public boolean tryCaptureView(View view, int arg1) {
            return view == itemView;
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == itemView) {
                if (left > 0) {
                    return 0;
                } else {
                    left = Math.max(left, -hiddenViewWidth);
                    return left;
                }
            }
            return 0;
        }

        public int getViewHorizontalDragRange(View child) {
            return hiddenViewWidth;
        }

        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            if (dx != 0) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (itemView == changedView) {
                hiddenView.offsetLeftAndRight(dx);
            } else {
                itemView.offsetLeftAndRight(dx);
            }
            if (itemView.getLeft() != 0) {
                SWSlipsManager.getInstance().setSwSlipeLayout(SWSlipsLayout.this);
            } else {
                SWSlipsManager.getInstance().clear();
            }
            if (itemView.getLeft() == 0 && changeStatus != Status.Close) {
                changeStatus = Status.Close;
            } else if (itemView.getLeft() == -hiddenViewWidth && changeStatus != Status.Open) {
                changeStatus = Status.Open;
            }
            invalidate();
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == itemView) {
                if (xvel == 0 && Math.abs(itemView.getLeft()) > hiddenViewWidth / 2.0f || xvel < 0) {
                    open();
                } else {
                    close();
                }
            }
        }

    };

    /**
     * slide close
     */
    public void close() {
        if (helper.smoothSlideViewTo(itemView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        SWSlipsManager.getInstance().clear();
    }

    /**
     * slide open
     */
    public void open() {
        SWSlipsManager.getInstance().setSwSlipeLayout(this);
        if (helper.smoothSlideViewTo(itemView, -hiddenViewWidth, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void computeScroll() {
        super.computeScroll();
        // start animation
        if (helper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean value = helper.shouldInterceptTouchEvent(event);
        //if you open is not the current item,close
        if (!SWSlipsManager.getInstance().haveOpened(this)) {
            SWSlipsManager.getInstance().close();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downIX = event.getX();
                downIY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moveY = event.getY();
                if (Math.abs(moveX - downIX) > 1 || Math.abs(moveY - downIY) > 1) {
                    value = true;
                }
                break;
        }
        return value;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (SWSlipsManager.getInstance().haveOpened(this)) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (SWSlipsManager.getInstance().haveOpened()) {
            getParent().requestDisallowInterceptTouchEvent(true);
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                float dx = Math.abs(moveX - downX);
                float dy = Math.abs(moveY - downY);
                if (dx > dy) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                downX = moveX;
                downY = moveY;
                break;
        }
        helper.processTouchEvent(event);
        return true;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new NullPointerException("you only need two child view!");
        }
        itemView = getChildAt(0);
        hiddenView = getChildAt(1);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        hiddenViewWidth = hiddenView.getMeasuredWidth();
    }
}