package com.honganjk.ynybzbizfood.pressenter.shitang.my;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

/**
 * 接口：/user/address.action
 * 开发	http://ur.honganjk.com/user/address.action
 * 生产	https://urapi.honganjk.com/user/address.action
 * code	必选 	单一用户唯一码,用户登录接口返回
 * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
 * contact	可选	收货联系电话
 * name	可选	收货联系人
 * sex	可选,int	联系人性别，1:先生;2:女士
 * longitude	可选，double类型	商家位置经度
 * address	可选	商家地址
 * latitude	可选，double类型	商家位置纬度
 */

public class AddAddressPresenter extends BasePresenter<MyParentInterfaces.IAddAddress> {
    public void commitData(String inputPhone, String iputName, int sex, String address, double latitude, double longitude) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<String>> httpCallBack = new HttpCallBack<HttpResult<String>>(builder) {
            @Override
            public void onSuccess(HttpResult<String> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.isCommitSccess(true);
                } else {
                    mvpView.showWarningSnackbar(result.getMsg());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("contact", inputPhone);
        param.addParam("name", iputName);
        param.addParam("sex", sex);
        param.addParam("address", address);
        param.addParam("latitude", latitude);
        param.addParam("longitude", longitude);
        HttpRequest.executePost(httpCallBack, "/user/address.action", param);
    }

}
