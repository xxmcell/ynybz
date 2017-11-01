package com.honganjk.ynybzbizfood.pressenter.store.home;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.DefaultAddressData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.PlaceTheOrderData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomePayData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.home.interfaces.IHomeParentInterfaces;


/**
 * 说明:
 * 360621904@qq.com 杨阳 2017/4/11  17:22
 */
public class StoreSubscribePresenter extends BasePresenter<IHomeParentInterfaces.IStoreSubscribe> {


    /**
     * 、获取上次收货地址
     * 接口：/user/lastAddr.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * bid	必选,int	商户id，新需求里为必选参数
     * 返回说明
     * 正常时的返回JSON数据包示例：
     * {
     * "msg": "ok",
     * "code": "A00000",
     * "data": {
     * "id": 1,				//收货地址id
     * "distance": 1,			//地址到商户的距离，单位公里
     * "fare": 10,			//配送费，单位元
     * "name": "qaz",			//收货人名字
     * "sex": 1,				//收货人性别 1-先生；2-女士
     * "contact": "13967193365",	//收货联系电话
     * "longitude": 31,			//收货地址经度
     * "latitude": 121,			//收货地址纬度
     * "address": "wsx"			//收货地址
     */
    public void getDefaultAddress() {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<DefaultAddressData>> httpCallBack = new HttpCallBack<HttpResult<DefaultAddressData>>(builder) {
            @Override
            public void onSuccess(HttpResult<DefaultAddressData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.getDefaultAddress(result.getData());
                    return;
                }
                ((BaseActivity) mvpView).showInforSnackbar(result.getMsg());

            }
        };
        HttpRequestParam param = new HttpRequestParam();
        HttpRequest.executePost(httpCallBack, "/user/lastAddr.action", param);

    }


    /**
     * 下单
     * 接口：/token/bjpOrder.json
     * 使用方: app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/bjpOrder.action
     *      http://bjpsc.honganjk.com/token/bjpOrder.action
     * 测试
     * 生产
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * header示例：
     * code: 8952
     * token: e8a69bf65aefc23d0f360ab695e9eac7
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * aid	必选，int	配送地址的Id
     * remark	可选	订单备注
     * bid	必选，int	保健品id
     * type	必选，int	保健品规格类型
     * num	必选，int	购买数量
     * fare	必选，int	运费，0=包邮
     */
    public void commitOrder(PlaceTheOrderData data, String bids) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<StoreHomePayData>>(builder) {
            @Override
            public void onSuccess(HttpResult<StoreHomePayData> result) {
                super.onSuccess(result);
                if (result.isSucceed() ) {
                    mvpView.placeTheOrderIsSucceed(result.getData());
                    return;
                }
                mvpView.showErrorSnackbar(result.getMsg());

            }
        };


        HttpRequestParam param = new HttpRequestParam();

        if(bids !=""){
            param.addParam("bids",bids);
            param.addParam("cart",1);
        }else {

            param.addParam("bids", data.getBids()+"-"+data.getType()+"-"+data.getNum()+";");
            param.addParam("cart",0);
        }
        param.addParam("aid", data.getAddressid());
        if (null!=data.getRemark()&&!"".equals(data.getRemark())){
            param.addParam("remark", data.getRemark());
        }
        param.addParam("fare", data.getFare());

        HttpRequest.executePostStore(httpCallBack, "/token/bjpOrder.json", param);

    }


    /**
     * 弹窗提示
     *
     * @param activity
     * @param data
     */
    public void setHint(Activity activity, String data) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(activity)
                .title("提示")
                .positiveText("确定")
                .content(data)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })
                .build();
        materialDialog.show();
    }


    public void delCarts(String bids) {

    }
}
