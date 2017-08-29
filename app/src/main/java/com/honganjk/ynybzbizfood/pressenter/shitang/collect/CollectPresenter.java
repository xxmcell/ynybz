package com.honganjk.ynybzbizfood.pressenter.shitang.collect;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.collect.interfaces.CollectParentInterfaces;

import java.util.List;

/**
 * 说明:收藏的P类
 * 360621904@qq.com 杨阳 2017/3/6  10:14
 * <p>
 * 浏览被收藏的商户(食堂)
 * 接口：/user/keeps.action
 * 使用方: web,app
 * 请求方式: http—get/post
 * header说明
 * 参数 	约束	说明
 * code	必选 	单一用户唯一码,用户登录接口返回
 * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
 * header示例：
 * code: 8952
 * token: e8a69bf65aefc23d0f360ab695e9eac7
 * 参数说明 (默认字符串类型)
 * 参数 	约束	说明
 * longitude	必选，double类型	用户当前位置经度
 * latitude	必选，double类型	用户当前位置纬度
 */
public class CollectPresenter extends BasePresenter<CollectParentInterfaces.ICollect> {

    public void getHttpData(final boolean isFirst, double longitude, double latitude) {
        if (isFirst ) {
            mvpView.clearPagingData();
        }
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD)
                .setLoadingStatus(mvpView);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<BusinessData>>>(builder) {
            @Override
            public void onSuccess(HttpResult<List<BusinessData>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (isFirst ) {
                        mvpView.clearData();
                    }
                    mvpView.getHttpData((List<BusinessData>) mvpView.getValidData(result.getData()));

                } else {
                    mvpView.showWarningSnackbar(result.getMsg());
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("longitude", longitude);
        param.addParam("latitude", latitude);
        HttpRequest.executePost(httpCallBack, "/user/keeps.action", param);
    }
}
