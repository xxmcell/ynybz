package com.honganjk.ynybzbizfood.utils.http.httpquest;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.iview.IProgressView;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;

/**
 * Created by Administrator on 2016/8/9.
 */

public abstract class HttpProgressCallBack<ResultType> extends HttpCallBack<ResultType> implements ProgressCallBack {
    //下载或者上传 回调的频率  ms
    public long rate = 500;

    protected boolean isShowProgress = true;

    public HttpProgressCallBack(Buider buider) {
        super(buider);
        isShowProgress = buider.isShowProgress;
    }

    public void onStart(boolean isCompress) {
        LogUtils.e("onStart2");
        super.onStart();

        onLoading(0, 100, false, true, isCompress);
    }

    @Override
    public void onStart() {
        onStart(false);
        LogUtils.e("onStart");
    }

    @Override
    public long getRate() {
        return rate;
    }

    @Override
    public void setRate(long rate) {
        this.rate = rate;
    }


    @Override
    protected void dismissUi() {
        super.dismissUi();
        if (isShowProgress && basePresenter != null && basePresenter.mvpView != null
                && basePresenter.mvpView instanceof IProgressView) {
            ((IProgressView) basePresenter.mvpView).dismissProgressDialog();
        }
        LogUtils.e("dismissUi");
    }

    @Override
    public void onLoading(long progress, long total, boolean done, boolean isUpdate) {
        LogUtils.e("onLoading");

        onLoading(progress, total, done, isUpdate, false);
    }


    public void onLoading(long progress, long total, boolean done, boolean isUpdate, boolean isCompress) {
        if (done) {
            dismissUi();
            return;
        }
        LogUtils.e("onLoading");
        if (basePresenter != null && basePresenter.mvpView != null
                && basePresenter.mvpView instanceof IProgressView) {
            int percentage = (int) (progress * 100.0 / total);
            ((IProgressView) basePresenter.mvpView).upLoading(percentage, done, isUpdate, isCompress);
        }
    }

    public static class Buider extends Builder {
        protected boolean isShowProgress = true;

        public Buider(BasePresenter basePresenter) {
            super(basePresenter);
        }

        public void setShowProgress(boolean showProgress) {
            isShowProgress = showProgress;
        }


    }
}
