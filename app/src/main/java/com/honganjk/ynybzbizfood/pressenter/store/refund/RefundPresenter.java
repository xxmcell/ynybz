package com.honganjk.ynybzbizfood.pressenter.store.refund;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
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
     * 接口：/token/refund.json
     * 使用方: web,app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/refund.json
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
     * id	必选,int	订单id
     * 返回说明
     * 正常时的返回JSON数据包示例：
     * {
     * "msg": "ok",
     * "code": "A00000",
     * "data": {
     * "code": null,			//快递单号
     * "express": null,			//快递公司名
     * "reason": 1,	//退款货原因code，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货
     * "money": 10.2,	//退款金额，double
     * <p>
     * "state": 10,	//退款状态，5-待发货申请退款，6-待收货申请退款，7-退款中，8-退款完成，9-待收货申请退货、10-待评价申请退货、
     * 11-已收货申请退货，12-退货中待买家发货，13-退货中-待卖家收货，14-退货退款完成，15-拒绝退款-待发货，16-拒绝退款-待收货，
     * 17-拒绝退货-待收货，18-拒绝退货-待评价，19-拒绝退货-已收货，20-待发货-取消退货，25-退货退款中
     * <p>
     * "time": 1499326517000,	//退款自动处理时间
     * "current": 1501575389316,	//当前时间戳
     * "type": 1		//1-退款，2-退货退款
     * }
     * }
     */
    public void setData(int type,RefundRequestData data, int mid) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<RefundRequestData>>(builder) {
            @Override
            public void onSuccess(HttpResult<RefundRequestData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.setHttpData(true);
                } else {
                    mvpView.setHttpData(false);
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", mid);
        param.addParam("reson", data.getReason());
        param.addParam("money", data.getMoney());
        //12为提交物流
        if(data.getState()==12){
            param.addParam("type", 6);
            param.addParam("express",data.getExpress()+"");
            param.addParam("code",data.getCode()+"");
        }else {
            //2,为退款,3为退货退款
            param.addParam("type",type);
        }
        HttpRequest.executePostStore(httpCallBack, "/token/handleOrder.json", param);

    }

    public void getData(int id) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<RefundRequestData>>(builder) {
            @Override
            public void onSuccess(HttpResult<RefundRequestData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.getHttpData(result.getData());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();

        param.addParam("id", id);
        HttpRequest.executePostStore(httpCallBack, "/token/refund.json", param);

    }
}
