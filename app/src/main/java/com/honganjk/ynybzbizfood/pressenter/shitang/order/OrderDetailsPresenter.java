package com.honganjk.ynybzbizfood.pressenter.shitang.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderDetailsData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;

/**
 * 说明:我的订单P类
 * 360621904@qq.com 杨阳 2017/3/6  14:27
 */
public class OrderDetailsPresenter extends BasePresenter<OrderParentInterfaces.IOrderDetails> {
    /**
     * 获取订单详情
     * 接口：/order/getDetail.action
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选，int	要查询的订单id
     */
    public void getHttpData(int id) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(false)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                .setSwipeRefreshLayout(mvpView.getSwipeRefreshLayout())
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<OrderDetailsData>>(builder) {
            @Override
            public void onSuccess(HttpResult<OrderDetailsData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {

                    mvpView.getHttpData(result.getData());
                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        HttpRequest.executePost(httpCallBack, "/order/getDetail.action", param);
    }

    /**
     * 用户删除点餐订单
     * @param id （未支付、已完成）
     *           接口：/order/delete.action
     *           参数 	约束	说明
     *           code	必选 	单一用户唯一码,用户登录接口返回
     *           token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     *           header示例：
     *           code: 8952
     *           token: e8a69bf65aefc23d0f360ab695e9eac7
     *           参数说明 (默认字符串类型)
     *           参数 	约束	说明
     *           id	必选，int	订单Id
     */
    public void cancelOrder(int id) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.cancelOrder(result.getData());
                } else {
                    mvpView.showWarningSnackbar(result.getMsg());
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        HttpRequest.executePost(httpCallBack, "/order/delete.action", param);
    }


    /**
     * 用户确认收货
     * 接口：/order/userReceipt.action
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选，int	订单Id
     *
     * @param id
     */
    public void commitOrder(int id) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.commitOrder(result.getData());
                } else {
                    mvpView.showWarningSnackbar(result.getMsg());


                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        HttpRequest.executePost(httpCallBack, "/order/userReceipt.action", param);
    }


}
