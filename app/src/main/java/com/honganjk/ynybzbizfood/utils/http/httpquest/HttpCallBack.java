package com.honganjk.ynybzbizfood.utils.http.httpquest;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.BaseHttpResponse;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;
import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.adapter.rxjava.HttpException;

import static com.honganjk.ynybzbizfood.code.MyApplication.myApp;
import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;


public abstract class HttpCallBack<ResultType> {
    protected String hint = "";

    protected View[] enableView;
    protected boolean isCancelable = false;
    protected boolean isShowLoadding = true;
    //下拉刷新
    protected SwipeRefreshLayout swipeRefreshLayout;
    //自动加载刷新a
    protected StatusChangListener statusChangListener;
    //界面显示管理器（加载中，加载失败，加载成功）
    private LoadingAndRetryManager loadingAndRetryManager;

    protected BasePresenter basePresenter;
    protected BaseActivity baseActivity;
    /**
     * 指定头部类型
     * {@link HeadType}
     */
    public int mHeadType=0;


    public HttpCallBack(Builder buider) {
        hint = buider.hint;
        enableView = buider.enableView;
        isCancelable = buider.isCancelable;
        swipeRefreshLayout = buider.swipeRefreshLayout;
        statusChangListener = buider.statusChangListener;
        loadingAndRetryManager = buider.loadingAndRetryManager;
        basePresenter = buider.basePresenter;
        isShowLoadding = buider.isShowLoadding;
        baseActivity = buider.baseActivity;
        this.mHeadType = buider.mHeadType;
    }

    /**
     * 订阅开始时调用
     * 请求开始
     */
    public void onStart() {
        LogUtils.e("onStart");
        if (enableView != null) {
            for (View view : enableView) {
                view.setEnabled(false);
            }
        }
        if (isShowLoadding && basePresenter != null && basePresenter.mvpView != null) {
            basePresenter.mvpView.showDialog(hint, isCancelable);
        } else if (isShowLoadding && baseActivity != null) {
            baseActivity.showDialog(hint, isCancelable);
        } else if (loadingAndRetryManager != null) {
            loadingAndRetryManager.showLoading(null);
        }

    }

    /**
     * 请求完成
     */
    public void onCompleted() {
        LogUtils.e("onCompleted");
        if (enableView != null) {
            for (View view : enableView) {
                view.setEnabled(true);
            }
        }
        dismissUi();
    }

    protected void dismissUi() {
        if (isShowLoadding && basePresenter != null && basePresenter.mvpView != null) {
            basePresenter.mvpView.dismissDialog();
        } else if (isShowLoadding && baseActivity != null) {
            baseActivity.dismissDialog();
        }
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);

        }
    }

    /**
     * 请求出错
     *
     * @param error
     */
    public void onError(Throwable error) {
        LogUtils.e("onError");
        onCompleted();
        ContextData data = new ContextData();
        String errString = "";
        int errCode;
        if (error != null) {
            LogUtils.e("onError->error, error.toString()");
            errCode = -1;
        }
        if (!HttpLocalUtils.isNetworkAvailable(myApp)) {
            errString = "请检查网络设置";
            errCode = -2;
        } else if (error instanceof HttpException) { // 网络错误
            HttpException httpEx = (HttpException) error;
            if (httpEx.getMessage().contains("timed out")) {
                errString = "连接超时";
            } else if (httpEx.code() == 500) {
                errString = "服务君累趴下啦";
            } else if (httpEx.code() == 0) {
                errString = "服务君累趴下啦.";
            } else if (httpEx.code() == 403) {
                errString = "禁止访问";
            } else if (httpEx.code() == 404) {
                errString = "资源不存在";
            } else {
                //其他错误
                errString = "未知网络错误";
            }
            errCode = httpEx.code();

        } else if (error instanceof SocketException) {
            //java.net.SocketException: sendto failed: ECONNRESET (Connection reset by peer)
            //服务器的并发连接数超过了其承载量，服务器会将其中一些连接关闭；
            //如果知道实际连接服务器的并发客户数没有超过服务器的承载量，则有可能是中了病毒或者木马，引起网
            errString = "请求过于频繁,请稍后再试";
            errCode = -3;
        } else if (error instanceof SocketTimeoutException || error instanceof TimeoutException) {
            //超时
            errString = "连接超时";
            errCode = -4;

        } else if (error instanceof UnknownHostException) {
            errString = "请检查网络设置";
            errCode = -2;
        } else {
            errString = "未知的错误";
            errCode = -200;
        }

        data.setTitle(errString);
        data.setErrCode(errCode);
        if (basePresenter != null && basePresenter.mvpView != null) {
            basePresenter.mvpView.showErrorSnackbar(errString);
        } else if (baseActivity != null) {
            baseActivity.showErrorSnackbar(errString);
        }
        if (statusChangListener != null) {
            statusChangListener.failure();
        }
        if (loadingAndRetryManager != null) {
            loadingAndRetryManager.showRetry(data);
        }

    }

    /**
     * 请求成功
     *
     * @param result 创建Subscriber时的泛型类型
     */
    public void onSuccess(ResultType result) {
        onSuccess(result, true);
    }

    /**
     * @param result
     * @param isHanderError 是否对错误信息处理
     */
    public final void onSuccess(ResultType result, boolean isHanderError) {
        LogUtils.e("onSuccess");

        if (statusChangListener != null) {
            statusChangListener.complete();
        }
        if (loadingAndRetryManager != null) {
            if (result instanceof BaseHttpResponse && isHanderError) {
                if (!((BaseHttpResponse) result).isSucceed()) {
                    loadingAndRetryManager.showRetry(new ContextData(((BaseHttpResponse) result).getResult(), ((BaseHttpResponse) result).getStatusCodeMsg()));
                } else {
                    loadingAndRetryManager.showContent();
                }
            } else {
                loadingAndRetryManager.showContent();
            }
        }
    }


    public static class Builder {
        private String hint = null;

        private View[] enableView;
        private boolean isCancelable = false;
        private boolean isShowLoadding = true;
        //下拉刷新
        private SwipeRefreshLayout swipeRefreshLayout;
        //自动加载刷新
        private StatusChangListener statusChangListener;
        //界面显示管理器（加载中，加载失败，加载成功）
        private LoadingAndRetryManager loadingAndRetryManager;

        private BasePresenter basePresenter;
        private BaseActivity baseActivity;
        /**
         * 指定头部类型
         * {@link HeadType}
         */
        private int mHeadType=0;

        public Builder(BasePresenter basePresenter) {
            this.basePresenter = basePresenter;
        }

        public Builder(BaseActivity baseActivity) {
            this.baseActivity = baseActivity;
        }

        public Builder() {
        }

        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder setEnableView(View... view) {
            this.enableView = view;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        /**
         * 注释说明:指定头部类型
         * 阳：2017/3/14-10:03
         * {@link HeadType}
         */
        public Builder setHttpHead(HeadType type) {
            mHeadType = type.getKey();
            if (type.getKey() == HeadType.LOGIN_HEAD.getKey() && !userData.isLogin()) {
                ToastUtils.getToastLong("未登录，请先登录");
                mHeadType = HeadType.NULL_HEAD.getKey();
            }
            return this;
        }


        /**
         * 下拉刷新的监听
         *
         * @param swipeRefreshLayout
         * @return
         */
        public Builder setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
            this.swipeRefreshLayout = swipeRefreshLayout;
            return this;
        }

        /**
         * 底部状态改加载监听
         *
         * @param statusChangListener
         * @return
         */
        public Builder setStatusChangListener(StatusChangListener statusChangListener) {
            this.statusChangListener = statusChangListener;
            return this;
        }

        public Builder setBasePresenter(BasePresenter basePresenter) {
            this.basePresenter = basePresenter;
            return this;
        }

        /**
         * 重新加载与空加载的监听
         *
         * @param loadingAndRetryManager
         * @return
         */
        public Builder setLoadingAndRetryManager(LoadingAndRetryManager loadingAndRetryManager) {
            this.loadingAndRetryManager = loadingAndRetryManager;
            return this;
        }

        /**
         * 显示加载的监听
         *
         * @param showLoadding
         * @return
         */
        public Builder setShowLoadding(boolean showLoadding) {
            isShowLoadding = showLoadding;
            return this;
        }

        /**
         * 设置下位刷新，底加载，状态显示
         *
         * @param mvpView
         * @return
         */
        public Builder setLoadingStatus(BaseListView mvpView) {
            if (mvpView instanceof BaseSwipeView) {
                setSwipeRefreshLayout(((BaseSwipeView) mvpView).getSwipeRefreshLayout());
            }
            setStatusChangListener(mvpView.getStatusChangListener());
            setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());
            return this;
        }
    }


}
