package com.honganjk.ynybzbizfood.pressenter.store.refund;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundRequestData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.refund.view.IRefundParent;


/**
 * 说明:产品详情
 * 作者： 杨阳; 创建于：  2017-07-07  14:01
 */
public class RefundPresenter extends BasePresenter<IRefundParent.IRefund> {
    /**
     * 、处置订单
     * 接口：/token/handleOrder.json
     * 使用方: app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/handleOrder.json
     * 测试
     * 生产
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * header示例：
     * code: 8952
     * token: e8a69bf65aefc23d0f360ab695e9eac7
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选，int	订单Id
     * remark	可选	退款/退货说明
     * reach	可选，int	是否已送达，0-否，1-是，默认为0
     * type	必选，int	处置类型，1-确认收货，2-申请退款，3-申请退货
     * reason	可选，int	退款/退货说明原因，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货，默认为1
     * money	可选，double	退款金额，需小于订单总价
     */
    public void getData(RefundRequestData data) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<ProductDetailsData>>(builder) {
            @Override
            public void onSuccess(HttpResult<ProductDetailsData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
//                    mvpView.getData(result.getData());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", data.getId());
        param.addParam("remark", data.getRemark());
        param.addParam("reach", data.getReach());
        param.addParam("type", data.getType());
        param.addParam("reason", data.getReason());
        param.addParam("money", data.getMoney());
        HttpRequest.executePostStore(httpCallBack, "/token/handleOrder.json", param);
    }
}
