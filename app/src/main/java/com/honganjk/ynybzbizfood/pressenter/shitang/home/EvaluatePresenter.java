package com.honganjk.ynybzbizfood.pressenter.shitang.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.EvaluateData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;

import java.util.List;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

public class EvaluatePresenter extends BasePresenter<HomeParentInterface.evaluate> {
    /**
     * 获取评论
     */
    public void getEvaluate(final boolean isFirst, final int bid){
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {


                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setLoadingStatus(mvpView)
                        .setShowLoadding(true);

                if(isFirst){
                    mvpView.clearPagingData();
                }

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<EvaluateData>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<EvaluateData> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if(isFirst){
                                mvpView.clearData();
                            }
                            LogUtils.e(result.toString());

                            mvpView.evaluate(result.getData(), (List<EvaluateData.ListBean>) mvpView.getValidData(result.getData().getList()));
                            return;
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("bid", bid);
                param.addParam("start", mvpView.getPageindex()*mvpView.getPageCount());
                param.addParam("size", mvpView.getPageCount());

                HttpRequest.executePost(httpCallBack, "/bz/comments.action", param);
            }
        };
    }
}
