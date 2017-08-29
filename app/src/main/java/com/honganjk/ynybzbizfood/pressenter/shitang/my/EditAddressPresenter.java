package com.honganjk.ynybzbizfood.pressenter.shitang.my;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;


public class EditAddressPresenter extends BasePresenter<MyParentInterfaces.IEditAddress> {
    public void commitData(int id,String inputPhone, String iputName, int sex, String address, double latitude, double longitude) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<String>> httpCallBack = new HttpCallBack<HttpResult<String>>(builder){
            @Override
            public void onSuccess(HttpResult<String> result) {
                super.onSuccess(result);
                if(result.isSucceed()) {
                    mvpView.isEditSccess(true);
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("contact", inputPhone);
        param.addParam("id", id);
        param.addParam("name", iputName);
        param.addParam("sex", sex);
        param.addParam("address", address);
        param.addParam("longitude", latitude);
        param.addParam("latitude", longitude);
        HttpRequest.executePost(httpCallBack, "/user/editAddr.action", param);
    }

}
