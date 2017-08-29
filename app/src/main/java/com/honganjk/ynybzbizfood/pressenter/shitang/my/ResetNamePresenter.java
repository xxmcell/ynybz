package com.honganjk.ynybzbizfood.pressenter.shitang.my;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;

/**
 * Created by Administrator on 2017/3/7.
 */

public class ResetNamePresenter extends BasePresenter<MyParentInterfaces.IRename> {
    private String TAG = "ResetNamePresenter";
    /**
     * 编辑用户信息（头像、昵称）
     * 接口：/user/edit.action
     * img	可选	头像url
     * name	可选	昵称
     */
    public void saveNewName(String imagurl,String newName) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);


        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                if(result.isSucceed()) {
                    Boolean bool = result.getData();
                    mvpView.saveNewname(bool);

                }
            }
        };


        HttpRequestParam param = new HttpRequestParam();
        param.addParam("img", imagurl);
        param.addParam("name", newName);

        HttpRequest.executePost(httpCallBack, "/user/edit.action", param);

    }
}
