package com.honganjk.ynybzbizfood.pressenter.peihu.collect;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.peihu.collect.interfaces.CollectParentInterfaces;

import java.util.List;

/**
 * 说明:说明:陪护-收藏
 * 360621904@qq.com 杨阳 2017/3/24  13:32
 */
public class CollectPresenter extends BasePresenter<CollectParentInterfaces.ICollect> {
    /**
     * 浏览收藏的护理人员
     * 接口：/nurse/collections.action
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * 参数说明 (默认字符串类型)
     * start	必选,int	分页参数，开始位置，以0为起始
     * size	必选,int	分页参数，单页展示数量
     * type	必选,int	收藏的护理人员类型，1-全天工，2-钟点工
     *
     * @param isFirst
     * @param state   收藏的护理人员类型，1-全天工，2-钟点工
     */
    public void getHttpData(final boolean isFirst, int state) {
        if (isFirst) {
            mvpView.clearPagingData();
        }

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<NurserCommendData>>>(builder) {
            @Override
            public void onSuccess(HttpResult<List<NurserCommendData>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (isFirst) {
                        mvpView.clearData();
                    }
                    mvpView.getHttpData((List<NurserCommendData>) mvpView.getValidData(result.getData()));

                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("type", state);
        param.addParam("start", mvpView.getPageindex() * mvpView.getPageCount());
        param.addParam("size", mvpView.getPageCount());
        HttpRequest.executePost(httpCallBack, "/nurse/collections.action", param);
    }


}
