package com.honganjk.ynybzbizfood.pressenter.store.order;

import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;

import java.util.List;

/**
 * 说明:商城-我的订单
 * 作者： 杨阳; 创建于：  2017-07-11  15:28
 */
public class StoreOrderPresenter extends StoreOrderParentPresenter<StoreOrderParentInterfaces.IOrder> {


    private List list;

    public StoreOrderPresenter(int requestCode) {
        super(requestCode);
    }

    /**
     * 浏览订单
     * 接口：/token/orders.json
     * 使用方: web,app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/orders.json
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
     * state	可选,int	要查询的订单状态,0-待付款，1-待发货，2-待收货，3-待评价，4-正常结束
     * start	必选,int	分页参数，开始位置，以0为起始
     * size	必选,int	分页参数，单页展示数量
     */
    public void getHttpData(final boolean isFirst, int state) {

        if (isFirst) {
            mvpView.clearPagingData();
        }

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<StoreOrderData2>>(builder) {
            @Override
            public void onSuccess(HttpResult<StoreOrderData2> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (isFirst) {
                        mvpView.clearData();
                    }
                    mvpView.getHttpData(result.getData().getTotal(), (List<StoreOrderData2.ObjsBean>) mvpView.getValidData(result.getData().getObjs()));

                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        if(state!=-1){
            param.addParam("state", state);
        }
        param.addParam("start", mvpView.getPageindex() * mvpView.getPageCount());
        param.addParam("size", mvpView.getPageCount());

        HttpRequest.executePostStore(httpCallBack, "/token/orders.json", param);

    }

}
