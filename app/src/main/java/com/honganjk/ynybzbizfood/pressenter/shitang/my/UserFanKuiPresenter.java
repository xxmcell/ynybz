package com.honganjk.ynybzbizfood.pressenter.shitang.my;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserFanKuiPresenter extends BasePresenter<MyParentInterfaces.IUserFangKui> {
    public void postUesrFanKuiData(String fanKui,String phone) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<Boolean>> httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.showData(result.getData());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("msg",fanKui);
        param.addParam("mobile",phone);
        HttpRequest.executePost(httpCallBack,"/common/addMsg.action",param);
    }
}
