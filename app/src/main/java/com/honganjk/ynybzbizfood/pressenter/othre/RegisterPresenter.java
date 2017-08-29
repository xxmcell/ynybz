package com.honganjk.ynybzbizfood.pressenter.othre;

import android.app.Activity;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.httpresponse.BaseHttpResponse;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.utils.http.GsonHelper;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.ui.SnackbarUtil;
import com.honganjk.ynybzbizfood.view.common.iview.IRegisterView;

import org.json.JSONObject;

import okhttp3.ResponseBody;

/**
 * Created by yang on 2016/8/17.
 */
public class RegisterPresenter extends BasePresenter<IRegisterView> {

    public void getHttpData(final Activity activity, String phone) {

        HttpCallBack.Builder buider = new HttpCallBack.Builder(this).setShowLoadding(true).setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());

        HttpCallBack httpCallBack = new HttpCallBack<BaseHttpResponse>(buider) {

            @Override
            public void onSuccess(BaseHttpResponse result) {
                super.onSuccess(result);

                //进行data处理
                if (result.isSucceed()) {
                    mvpView.clearData();
                    mvpView.isVerification(true);
                } else {
                    mvpView.isVerification(false);
                }

                SnackbarUtil.showLong(activity, result.getMsg(), SnackbarUtil.Info).show();

            }
        };

        // mvpView.addSubscription(HttpRequest.getRegisterVerificationCode(httpCallBack, phone));
    }


    public void getHttpUserData(final Activity context, String telphone, String code, String group, String location_name, String yaoqingNumber) {

        HttpCallBack.Builder buider = new HttpCallBack.Builder(this).setShowLoadding(false).setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());

        HttpCallBack httpCallBack = new HttpCallBack<ResponseBody>(buider) {

            @Override
            public void onSuccess(ResponseBody result) {
                super.onSuccess(result);

                try {
                    JSONObject jsonObject = new JSONObject(result.string().toString());
                    int status = jsonObject.getInt("Result");

                    if (status == 0) {
                        UserInfo userData = GsonHelper.getGson().fromJson(jsonObject.getJSONObject("model").toString(), UserInfo.class);
                        mvpView.receiverUserData(userData);

                        SnackbarUtil.showLong(context, "注册成功", SnackbarUtil.Info).show();
                    } else {
                        SnackbarUtil.showLong(context, jsonObject.getString("Msg"), SnackbarUtil.Info).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                mvpView.clearData();

            }
        };

        //  mvpView.addSubscription(HttpRequest.register(httpCallBack, telphone, code, group, location_name, yaoqingNumber, ff));
    }


}
