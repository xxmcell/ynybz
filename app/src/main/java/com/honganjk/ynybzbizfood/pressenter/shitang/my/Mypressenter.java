package com.honganjk.ynybzbizfood.pressenter.shitang.my;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.LoginData.UserInfoData;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * Created by Administrator on 2017/3/6.
 */

public class Mypressenter extends BasePresenter<MyParentInterfaces.IMy> {
    public void getUserInfo() {

        HttpCallBack.Builder buider = new HttpCallBack.Builder()
                .setHttpHead(HeadType.LOGIN_HEAD)
                .setShowLoadding(true);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<UserInfoData>>(buider) {

            @Override
            public void onSuccess(HttpResult<UserInfoData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    userData.setName(result.getData().getName());
                    userData.setMobile(result.getData().getMobile());
                    userData.setImg(result.getData().getImg());
                    userData.setBalance(result.getData().getBalance());
                    userData.setImaddrNumg(result.getData().getAddrNum());
                    UserInfo.save(userData);
                    mvpView.getUserInfo(result.getData());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        HttpRequest.executePost(httpCallBack, "/user/homeUser.action", param);
    }


}
