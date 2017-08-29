package com.honganjk.ynybzbizfood.pressenter.shitang.my;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.AddressBean;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

import java.util.List;


/**
 * 说明: 添加地址
 * 2017/3/15-16:00
 */
public class SelectAddressPresenter extends BasePresenter<MyParentInterfaces.ISelectAddress> {

    public void getUserAddress(boolean isFirst) {
        if (isFirst){
            mvpView.clearPagingData();
        }
        /**
         * 浏览收货地址
         */
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(false)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<AddressBean>>>(builder) {

            @Override
            public void onSuccess(HttpResult<List<AddressBean>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.clearData();
                    mvpView.showAddress((List<AddressBean>) mvpView.getValidData(result.getData()));
                    return;
                }
                mvpView.showWarningSnackbar(result.getMsg());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        HttpRequest.executePost(httpCallBack, "/user/addrs.action", param);
    }

    /**
     * 删除收货地址
     *
     * @param id
     */
    public void deleAddress(int id) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {

            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.clearData();
                    mvpView.isDeleAddress(result.getData());
                    return;
                }
                mvpView.showWarningSnackbar(result.getMsg());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("id", id);
        HttpRequest.executePost(httpCallBack, "/user/delAddr.action", param);
    }
}
