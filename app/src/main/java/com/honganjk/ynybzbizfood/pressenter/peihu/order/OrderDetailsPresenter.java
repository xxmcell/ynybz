package com.honganjk.ynybzbizfood.pressenter.peihu.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.order.OrderDetailsBean;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.peihu.order.interfaces.OrderParentInterfaces;


/**
 * 说明:
 * 360621904@qq.com 杨阳 2017/4/11  17:22
 */
public class OrderDetailsPresenter extends BasePresenter<OrderParentInterfaces.IOrderDetails> {


    /**
     * 获取单一护理订单详情
     * 接口：/nurse/getOrder.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,登录接口返回
     * token	必选	单一用户短时段内的请求令牌,登录接口返回
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选,int	订单id
     */
    public void getData(final int id) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<OrderDetailsBean>>(builder) {
            @Override
            public void onSuccess(HttpResult<OrderDetailsBean> result) {
                super.onSuccess(result);
                if (result.isSucceed() && result.getData() != null) {
                    mvpView.getData(result.getData());
                    return;
                }
                ((BaseActivity) mvpView).showInforSnackbar(result.getMsg());

            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        HttpRequest.executePost(httpCallBack, "/nurse/getOrder.action", param);

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
