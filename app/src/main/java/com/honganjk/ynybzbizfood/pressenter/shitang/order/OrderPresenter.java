package com.honganjk.ynybzbizfood.pressenter.shitang.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;

import java.util.List;

/**
 * 说明:我的订单P类
 * 360621904@qq.com 杨阳 2017/3/6  14:27
 * <p>
 * 用户浏览自己的点餐订单
 * 接口：/order/select.action
 * header说明
 * 参数 	约束	说明
 * code	必选 	单一用户唯一码,用户登录接口返回
 * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
 * 参数说明 (默认字符串类型)
 * 参数 	约束	说明
 * state	可选,int	要查询的订单状态,
 * 0:待付款; 2:待收货; 5:待评价;4:已完成;
 * type	可选,int	食堂类型，1-社区食堂、2-营养餐
 * start	可选,int	分页参数，开始位置，以0为起始
 * size	可选,int	分页参数，单页展示数量
 * 如start或size有一个为空，则不分页
 */
public class OrderPresenter extends BasePresenter<OrderParentInterfaces.IOrder> {

    public void getHttpData(final boolean isFirst, int state) {
        if (isFirst) {
            mvpView.clearPagingData();
        }

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)

                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<OrderData>>>(builder) {
            @Override
            public void onSuccess(HttpResult<List<OrderData>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (isFirst) {
                        mvpView.clearData();
                    }
                    mvpView.getHttpData((List<OrderData>) mvpView.getValidData(result.getData()));

                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
//        param.addParam("type", 1);
        param.addParam("state", state);
        param.addParam("start", mvpView.getPageindex() * mvpView.getPageCount());

        param.addParam("size", mvpView.getPageCount());
        HttpRequest.executePost(httpCallBack, "/order/select.action", param);
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



}
