package com.honganjk.ynybzbizfood.pressenter.shitang.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.BaseHttpResponse;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;

/**
 * 注释说明: 支付的Presenter 类
 * 阳：2017/3/13-17:10
 */
public class PayPresenter extends BasePresenter<OrderParentInterfaces.IPay> {

    /**
     * 余额支付
     * 接口：/pay/overage.action
     *
     * @param amount 必选，double	要支付的金额，单位为元，精确到分
     * @param oid    必选，int	要支付的订单id
     * @param type   可选,int 订单类型，1-餐饮订单，2-护理订单，默认值为1
     */
    public void balancePay(double amount, int oid, int type) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack<HttpResult<Double>> httpCallBack = new HttpCallBack<HttpResult<Double>>(builder) {
            @Override
            public void onSuccess(HttpResult<Double> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.balancePay(true, result.getData());
                    return;
                }
                mvpView.showWarningSnackbar(result.getMsg());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("amount", amount);
        param.addParam("oid", oid);
        param.addParam("type", type);
        HttpRequest.executePost(httpCallBack, "/pay/overage.action", param);

    }

    /**
     * 商城 余额支付
     * 接口：/pay/overage.json
     * 使用方: web,app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/pay/overage.json
     * 测试
     * 生产
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * header示例：
     * code: 8952
     * token: e8a69bf65aefc23d0f360ab695e9eac7
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * oid	必选，int	要支付的订单id
     *
     * @param oid
     */
    public void storeBalancePay(int oid) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack<HttpResult<Double>> httpCallBack = new HttpCallBack<HttpResult<Double>>(builder) {
            @Override
            public void onSuccess(HttpResult<Double> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.balancePay(true, result.getData());
                    return;
                }
                mvpView.showWarningSnackbar(result.getMsg());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("oid", oid);
        HttpRequest.executePostStore(httpCallBack, "/pay/overage.json", param);

    }


    /**
     * 实时获取用户余额
     * 接口：/user/getOverage.action
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     */
    public void getBalance() {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack<HttpResult<Double>> httpCallBack = new HttpCallBack<HttpResult<Double>>(builder) {
            @Override
            public void onSuccess(HttpResult<Double> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.getBalance(true, result.getData());
                    return;
                }
                mvpView.showWarningSnackbar(result.getMsg());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        HttpRequest.executePost(httpCallBack, "/user/getOverage.action", param);

    }


    /**
     * 获取餐饮订单的支付要素
     * 接口：/pay/getCharge.action
     * 参数 	约束	说明
     * pay	必选,int	支付渠道：1-支付宝；2-微信；3-银联
     * oid	必选,int	餐饮订单id
     */

    public void otherPay(int payType, int oid) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder()
                .setHttpHead(HeadType.LOGIN_HEAD)//添加头部类型，如没有头部则不用写
                .setShowLoadding(true);
        HttpCallBack httpCallBack = new HttpCallBack<BaseHttpResponse>(buider) {
            @Override
            public void onSuccess(BaseHttpResponse result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    try {
                        mvpView.otherPay(result.getJSONObject().getJSONObject("data").toString());

                    } catch (Exception e) {
                        mvpView.showErrorSnackbar(result.getMsg());
                        e.printStackTrace();
                    }
                    return;
                } else {
                    mvpView.showErrorSnackbar(result.getMsg());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("pay", payType);
        param.addParam("oid", oid);
        HttpRequest.executePost(httpCallBack, "/pay/getCharge.action", param);

    }


    /**
     * 获取护理订单的支付要素
     * 接口：/pay/getNurseCharge.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * <p>
     * 参数 	约束	说明
     * pay	必选,int	支付渠道：1-支付宝；2-微信；3-银联
     * oid	必选,int	护理订单id
     */

    public void otherPayNurse(int pay, int oid) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder()
                .setHttpHead(HeadType.LOGIN_HEAD)
                .setShowLoadding(true);
        HttpCallBack httpCallBack = new HttpCallBack<BaseHttpResponse>(buider) {
            @Override
            public void onSuccess(BaseHttpResponse result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    try {
                        mvpView.otherPay(result.getJSONObject().getJSONObject("data").toString());

                    } catch (Exception e) {
                        mvpView.showErrorSnackbar(result.getMsg());
                        e.printStackTrace();
                    }
                    return;
                } else {
                    mvpView.showErrorSnackbar(result.getMsg());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("pay", pay);
        param.addParam("oid", oid);
        HttpRequest.executePost(httpCallBack, "/pay/getNurseCharge.action", param);

    }

    /**
     * 商城产品
     *
     * @param pay
     * @param oid 获取保健品订单的支付要素
     *            接口：/token/getCharge.json
     *            使用方: web,app
     *            请求方式: http—get/post
     *            请求地址:
     *            环境	地址
     *            开发	http://bjpsc.honganjk.com/token/getCharge.json
     *            测试
     *            生产
     *            header说明
     *            参数 	约束	说明
     *            code	必选 	单一用户唯一码,用户登录接口返回
     *            token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     *            header示例：
     *            code: 8952
     *            token: e8a69bf65aefc23d0f360ab695e9eac7
     *            参数说明 (默认字符串类型)
     *            参数 	约束	说明
     *            pay	必选,int	支付渠道：1-支付宝app;2-微信app;3-银联;4-微信公众号
     *            oid	必选,int	订单id
     *            openId	可选	pay=4时的必填参数，用户在商户  appid 下的唯一标识。
     */
    public void storePayNurse(int pay, int oid) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder()
                .setHttpHead(HeadType.LOGIN_HEAD)
                .setShowLoadding(true);
        HttpCallBack httpCallBack = new HttpCallBack<BaseHttpResponse>(buider) {
            @Override
            public void onSuccess(BaseHttpResponse result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    try {
                        mvpView.otherPay(result.getJSONObject().getJSONObject("data").toString());

                    } catch (Exception e) {
                        mvpView.showErrorSnackbar(result.getMsg());
                        e.printStackTrace();
                    }
                    return;
                } else {
                    mvpView.showErrorSnackbar(result.getMsg());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("pay", pay);
        param.addParam("oid", oid);
        HttpRequest.executePostStore(httpCallBack, "/token/getCharge.json", param);

    }


}
