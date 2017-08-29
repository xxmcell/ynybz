package com.honganjk.ynybzbizfood.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.LoginData.LoginData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.common.activity.LoginActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * 说明:微信登录回调的类
 * 360621904@qq.com 杨阳 2017/3/5  14:19
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    //和微信通信的openapi接口
    public static IWXAPI WX_API;
    public static final String appId = "wxfd6710a5c2e59cd7";
    public static final String secret = "402b44223b5a8bc167f6f85fdf766894";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("onCreate");
        WX_API.handleIntent(getIntent(), this);

    }

    public static void startWXUI(Activity context, int resultCode) {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "ynybz";
        WX_API.sendReq(req);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        LogUtils.e("onReq");
    }


    /**
     * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
     *
     * @param resp ERR_OK = 0(用户同意)
     *             ERR_AUTH_DENIED = -4（用户拒绝授权）
     *             ERR_USER_CANCEL = -2（用户取消）
     */
    @Override
    public void onResp(BaseResp resp) {
        LogUtils.e("onResp");
        switch (resp.errCode) {
            //用户同意
            case BaseResp.ErrCode.ERR_OK:
                getAccess_token(((SendAuth.Resp) resp).code);
                break;
            //用户取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.getToastLong("您已经取消登录");
                finish();
                break;
            //用户拒绝授权
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtils.getToastLong("您已经拒绝授权");
                finish();
                break;
            default:
                ToastUtils.getToastLong("绑定失败");
        }
    }

    /**
     * 通过code获取access_token
     *
     * @param code 参数说明
     *             参数	是否必须	说明
     *             appid	是	应用唯一标识，在微信开放平台提交应用审核通过后获得
     *             secret	是	应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
     *             code	是	填写第一步获取的code参数
     *             grant_type	是	填authorization_code
     */
    public void getAccess_token(final String code) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder();
        HttpCallBack httpCallBack = new HttpCallBack<ResponseBody>(buider) {
            @Override
            public void onSuccess(ResponseBody result) {
                super.onSuccess(result);
                try {
                    String str = result.string().toString();
                    if (!StringUtils.isBlank(str)) {
                        JSONObject jsonObject = new JSONObject(str);
                        str = jsonObject.getString("unionid");
                        login(str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getToastLong("绑定失败");
                    finish();
                }
            }
        };

        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", secret);
        map.put("code", code);
        map.put("grant_type", "authorization_code");
        HttpRequest.getWXToken(httpCallBack, "https://api.weixin.qq.com/sns/oauth2/access_token", map);
    }


    /**
     * 微信登录
     * 接口：/user/wxLogin.action
     * 使用方: web，APP
     * 请求方式: http—get/post
     * 请求地址:
     * header说明
     * 无
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * wechatId	必选	微信方的唯一编码
     * 返回说明
     * 未绑定手机号时返回JSON数据包示例：
     * {
     * "code": “A00000”,
     * "msg": "ok",
     * "data": null	//如是此结果，跳转至绑定界面
     */
    public void login(final String wechatId) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder();

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<LoginData>>(buider) {

            @Override
            public void onSuccess(HttpResult<LoginData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (result.getData() == null) {
                        LoginActivity.startUI(WXEntryActivity.this, wechatId);
                    } else {
                        LoginData data = result.getData();
                        userData.setCode(data.getCode());
                        userData.setToken(data.getToken());
                        userData.setLogin(true);
                        userData.save(userData);
                        ToastUtils.getToastLong("登录成功");
                        sendBroadcast(new Intent(Global.LOGIN_SUCCEED));
                    }
                }
                finish();
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("wechatId", wechatId);
        HttpRequest.executePost(httpCallBack, "/user/wxLogin.action", param);
    }

}