package com.honganjk.ynybzbizfood.pressenter.store.order;

import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderDetailsData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;

/**
 * 说明:商城-我的订单
 * 作者： 杨阳; 创建于：  2017-07-11  15:28
 */
public class StoreOrderDetailsPresenter extends StoreOrderParentPresenter<StoreOrderParentInterfaces.IOrderDetails> {
    public StoreOrderDetailsPresenter(int requestCode) {
        super(requestCode);
    }

    /**
     * 订单详情
     * 接口：/token/orderDetail.json
     * 使用方: web,app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/orderDetail.json
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
     * id	必选,int	要查询的订单id
     */
    public void getData(int id) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<StoreOrderDetailsData>>(builder) {
            @Override
            public void onSuccess(HttpResult<StoreOrderDetailsData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.getData(result.getData());
                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        HttpRequest.executePostStore(httpCallBack, "/token/orderDetail.json", param);
    }


}
