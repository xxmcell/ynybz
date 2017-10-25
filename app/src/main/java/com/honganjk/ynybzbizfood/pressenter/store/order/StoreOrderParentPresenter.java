package com.honganjk.ynybzbizfood.pressenter.store.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;

/**
 * 说明:商城-我的订单
 * 作者： 杨阳; 创建于：  2017-07-11  15:28
 */
public abstract class StoreOrderParentPresenter<V extends BaseView> extends BasePresenter<V> {
    int requestCode = 999;

    public StoreOrderParentPresenter(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
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
    public void deleteOrder(StoreOrderData2.ObjsBean data,int type) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (mvpView instanceof StoreOrderParentInterfaces.IOrderParent) {
                        ((StoreOrderParentInterfaces.IOrderParent) mvpView).deleteOrder(true);
                    }

                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", data.getId());
        if(type==5){
            param.addParam("type",4);
        }else if(type==2){
            param.addParam("type",1);
        }

        HttpRequest.executePostStore(httpCallBack, "/token/handleOrder.json", param);
    }


    /**
     * 取消订单
     * <p>
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
                    if (mvpView instanceof StoreOrderParentInterfaces.IOrderParent) {
                        ((StoreOrderParentInterfaces.IOrderParent) mvpView).cancleOrder(true);
                    }

                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        param.addParam("state", 8);
        param.addParam("reason", reason);
        HttpRequest.executePostStore(httpCallBack, "/nurse/handleOrder.action", param);
    }
}
