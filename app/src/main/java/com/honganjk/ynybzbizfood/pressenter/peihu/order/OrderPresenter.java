package com.honganjk.ynybzbizfood.pressenter.peihu.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.order.NurserOrderdData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.peihu.order.interfaces.OrderParentInterfaces;

import java.util.List;

/**
 * 说明:陪护-我的订单
 * 360621904@qq.com 杨阳 2017/3/24  14:19
 */
public class OrderPresenter extends BasePresenter<OrderParentInterfaces.IOrder> {
    /**
     * 用户浏览自己的护理订单
     * 接口：/nurse/orders.action
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * state	必选,int	要查询的订单状态,
     * 1:待服务; 2:服务中;4:已完成; 7:待接单
     * type	可选,int	护理类型，1-全天、2-钟点
     * start	必选,int	分页参数，开始位置，以0为起始
     * size	必选,int	分页参数，单页展示数量
     *
     * @param isFirst
     * @param state   收藏的护理人员类型，1-全天工，2-钟点工
     */
    public void getHttpData(int type, final boolean isFirst, int state) {
        if (isFirst) {
            mvpView.clearPagingData();
        }

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<NurserOrderdData>>(builder) {
            @Override
            public void onSuccess(HttpResult<NurserOrderdData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (isFirst) {
                        mvpView.clearData();
                    }
                    mvpView.getHttpData(result.getData().getTotal(), (List<NurserOrderdData.ObjsBean>) mvpView.getValidData(result.getData().getObjs()));

                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        if (type != 0) {
            param.addParam("type", type);
        }
        param.addParam("state", state);
        param.addParam("start", mvpView.getPageindex() * mvpView.getPageCount());
        param.addParam("size", mvpView.getPageCount());
        HttpRequest.executePost(httpCallBack, "/nurse/orders.action", param);
    }


    /**
     * 确认完成订单
     * 接口： /nurse/handleOrder.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,登录接口返回
     * token	必选	单一用户短时段内的请求令牌,登录接口返回
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选,int	数据id
     * state	必选,int	处置订单状态值，9-确认完成服务；8-取消订单
     *
     * @param id    订单id
     * @param state 处理类型
     */
    public void confirmCompleted(int id, int state) {


        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.confirmCompleted(true);
                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("state", state);
        param.addParam("id", id);
        HttpRequest.executePost(httpCallBack, "/nurse/handleOrder.action", param);
    }

    /**
     * 用户删除护理订单
     * 接口：/nurse/delete.action
     * 使用方: web,app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://ur.honganjk.com/nurse/delete.action
     * 测试
     * 生产	https://urapi.honganjk.com/nurse/delete.action
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * header示例：
     * code: 8952
     * token: e8a69bf65aefc23d0f360ab695e9eac7
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选，int	护理订单Id
     */
    public void deleteOrder(int id) {


        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.deleteOrder(true);
                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        HttpRequest.executePost(httpCallBack, "/nurse/delete.action", param);
    }


    /**
     * 取消订单
     *
     * 处置护理订单
     * 接口： /nurse/handleOrder.action
     * 使用方: web、app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://ur.honganjk.com/nurse/handleOrder.action
     * 测试
     * 生产	https://urapi.honganjk.com/nurse/handleOrder.action
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,登录接口返回
     * token	必选	单一用户短时段内的请求令牌,登录接口返回
     * header示例：
     * code: 8952
     * token: e8a69bf65aefc23d0f360ab695e9eac7
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选,int	数据id
     * state	必选,int	处置订单状态值， 8-取消订单
     * reason	必选	取消订单的原因
     *
     * @param id
     */
    public void cancleOrder(int id, String reason) {


        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.cancleOrder(true);
                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        param.addParam("state", 8);
        param.addParam("reason", reason);
        HttpRequest.executePost(httpCallBack, "/nurse/handleOrder.action", param);
    }
}
