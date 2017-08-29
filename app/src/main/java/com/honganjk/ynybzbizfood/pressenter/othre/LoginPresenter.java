package com.honganjk.ynybzbizfood.pressenter.othre;

import android.content.Context;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.LoginData.LoginData;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpManager;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.ui.ScreenInfoUtils;
import com.honganjk.ynybzbizfood.view.common.iview.IloginView;
import com.tencent.android.tpush.XGPushManager;

import java.util.HashMap;
import java.util.Map;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * 说明: 登录的P类
 * 2017/3/3-14:43
 * <p>
 * 2、获取短信验证码
 * 接口： /sms/getValiCode.action
 * 使用方: web、app，单一手机号一分钟内仅能获取一次短信验证码，验证码有效期为1分钟
 * header说明
 * 无
 * 参数示例：
 * “mobile”： “18658130532”
 * 参数说明 (默认字符串类型)
 * mobile	必选	需要获取验证码的手机号
 */
public class LoginPresenter extends BasePresenter<IloginView> {
    /**
     * 获取验证码
     *
     * @param phone
     */
    public void getVerification(String phone) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<String>>(buider) {
            @Override
            public void onSuccess(HttpResult<String> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.getVerification(true);
                } else {
                    mvpView.showErrorSnackbar(result.getMsg());
                }
            }
        };
        //请求接口
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);

        //正式环境
        if (HttpManager.BASE_HOST.startsWith("https")) {
            HttpRequest.getVerification(httpCallBack, "https://bzapi.honganjk.com/sms/getValiCode.action", map);
            //开发环境
        } else {
            HttpRequest.getVerification(httpCallBack, "http://bz.honganjk.com/sms/getValiCode.action", map);
        }


    }

    /**
     * 登录
     *
     * @param phone
     * @param code  3、用户登录
     *              接口：/user/login.action
     *              header说明
     *              无
     *              参数说明 (默认字符串类型)
     *              参数 	约束	说明
     *              mobile	必选	用户手机号
     *              valiCode	必选	验证码，用户手机收到的短信验证码
     *              参数示例：
     *              “account”：“18658130532”
     *              “valiCode”：“3579”
     *              返回说明
     *              正常时的返回JSON数据包示例：
     *              {
     *              "msg": "OK",
     *              "code": "A00000",
     *              "data": {
     *              "code": "8952",   //该用户的唯一码，之后的接口请求header中使用，建议前端解析为数字类型数据存储在本地
     *              "token": "e8a69bf65aefc23d0f360ab695e9eac7" //该用户的当前时段的token，之后的接口请求header中使用
     *              }
     */
    public void login(String phone, String code) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<LoginData>>(buider) {

            @Override
            public void onSuccess(HttpResult<LoginData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    LoginData data = result.getData();
                    userData.setCode(data.getCode());
                    userData.setToken(data.getToken());
                    userData.setLogin(true);
                    userData.save(userData);
//                    mvpView.login(true);

                    /**
                     *绑定推送别名
                     * 开发环境： dev_
                     * 生产环境：ynybz_
                     */
                    //生产环境
                    if (HttpManager.BASE_HOST.startsWith("https")) {
                        XGPushManager.registerPush((BaseActivity) mvpView, "ynybz_" + userData.getCode());
                        //开发环境
                    } else {
                        XGPushManager.registerPush((BaseActivity) mvpView, "dev_" + userData.getCode());
                    }
                    setUserInfo((BaseActivity) mvpView);
                    return;
                }
                mvpView.showErrorSnackbar(result.getMsg());

            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("mobile", phone);
        param.addParam("valiCode", code);
        HttpRequest.executePost(httpCallBack, "/user/login.action", param);
    }

    /**
     * 微信绑定手机号
     * <p>
     * 接口：/user/binding.action
     * 请求方式: http—get/post
     * header说明
     * 无
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * wechatId	必选	微信方唯一编码
     * mobile	必选	手机号
     * valiCode	必选	短信验证码
     * img	可选	微信头像，如不传，使用默认头像
     * name	可选	微信昵称，如不传，使用默认昵称
     * 返回说明
     * 正常时的返回JSON数据包示例：
     * {
     * "msg": "ok",
     * "code": "A00000",
     * "data": true
     * }
     *
     * @param wXId
     * @param phone
     * @param code
     */
    public void boundWX(String wXId, String phone, String code) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(buider) {

            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.boundWX(true);
                    return;
                }
                mvpView.showErrorSnackbar(result.getMsg());

            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("wechatId", wXId);
        param.addParam("mobile", phone);
        param.addParam("valiCode", code);
        HttpRequest.executePost(httpCallBack, "/user/binding.action", param);
    }


    /**
     * 收集客户端信息
     * 接口：/common/clientData.action
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * header示例：
     * code: 8952
     * token: e8a69bf65aefc23d0f360ab695e9eac7
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * appVersion	必选	app版本号
     * mac	必选	手机唯一标识，可用mac地址
     * factory	可选，int	手机厂商，手机厂商，1-苹果；2-三星；3-小米；4-华为；5-魅族；6-oppo;7-vivo;8-中兴；9-乐视；10-联想；11-google；12-索尼；13-其它
     * model	可选	手机型号，如huaweip8，xiaomi5
     * osType	可选	操作系统类型，1-android；2-ios；3-wp；4-其它
     * osVersion	可选	原生操作系统版本，如ios10.1，android6.0
     * factoryVersion	可选	厂家定制的操作系统版本，如miui6.0
     * cpu	可选	处理器型号，如mtk8274
     * memory	可选	内存，如3G
     * storage	可选	存储，如32G
     * screen	可选	屏幕分辨率，如720p
     * cid	可选，int	登录app的账户code
     */
    public void setUserInfo(Context context) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(buider) {

            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.login(true);
                    return;
                }
                mvpView.showErrorSnackbar(result.getMsg());

            }
        };
/**
 * appVersion	必选	app版本号
 * mac	必选	手机唯一标识，可用mac地址
 * factory	可选，int	手机厂商，手机厂商，1-苹果；2-三星；3-小米；4-华为；5-魅族；6-oppo;7-vivo;8-中兴；9-乐视；10-联想；11-google；12-索尼；13-其它
 * model	可选	手机型号，如huaweip8，xiaomi5
 * osType	可选	操作系统类型，1-android；2-ios；3-wp；4-其它
 * osVersion	可选	原生操作系统版本，如ios10.1，android6.0
 * factoryVersion	可选	厂家定制的操作系统版本，如miui6.0
 * cpu	可选	处理器型号，如mtk8274
 * memory	可选	内存，如3G
 * storage	可选	存储，如32G
 * screen	可选	屏幕分辨率，如720p
 * cid	可选，int	登录app的账户code
 */
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("appVersion", DeviceUtil.getVersionCode());
        param.addParam("mac", userData.getMac());
        param.addParam("factory", judgeBrand(DeviceUtil.getDeviceBrand()));
        param.addParam("model", DeviceUtil.getInstance(context).getSystemModel());
        param.addParam("osType", 1);
        param.addParam("osVersion", DeviceUtil.getInstance(context).getSystemVersion());
        param.addParam("factoryVersion", DeviceUtil.getInstance(context).getSystemVersion());
        param.addParam("cpu", android.os.Build.CPU_ABI);
        param.addParam("memory", DeviceUtil.formatFileSize(DeviceUtil.getTotalMemorySize(), true));
        param.addParam("storage", DeviceUtil.formatFileSize(DeviceUtil.getAvailableInternalMemorySize(), true));
        param.addParam("screen", new ScreenInfoUtils().getDensityDpi());
        param.addParam("cid", UserInfo.userData.getCode());
        HttpRequest.executePost(httpCallBack, "/common/clientData.action", param);

    }

    /**
     * 手机厂商，手机厂商，1-苹果；2-三星；3-小米；4-华为；5-魅族；6-oppo;7-vivo;8-中兴；9-乐视；10-联想；11-google；12-索尼；13-其它
     *
     * @param name
     * @return
     */
    private int judgeBrand(String name) {
        name = name.toLowerCase().trim();
        if (name.contains("三星")) {
            return 2;
        } else if (name.contains("小米")) {
            return 3;
        } else if (name.contains("华为")) {
            return 4;
        } else if (name.contains("魅族")) {
            return 5;
        } else if (name.contains("oppo")) {
            return 6;
        } else if (name.contains("vivo")) {
            return 7;
        } else if (name.contains("中兴")) {
            return 8;
        } else if (name.contains("乐视")) {
            return 9;
        } else if (name.contains("联想")) {
            return 10;
        } else if (name.contains("google")) {
            return 11;
        } else if (name.contains("索尼")) {
            return 12;
        }
        return 13;
    }


}
