package com.honganjk.ynybzbizfood.pressenter.shitang.order;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.CreateOrderSucceedData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.FavorableData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.OrderCommitData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.DefaultAddressData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/13.
 */

public class QueRenXiaDinDanPresenter extends BasePresenter<OrderParentInterfaces.IQueRenXiaDan> {

    /**
     * 查询配送费
     * 接口：/user/getFare.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * bid	必选,int	商户id
     * aid	必选,int	地址id
     *
     * @param bid
     */
    public void theShoppingFee(int bid, int aid) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<Integer>> httpCallBack = new HttpCallBack<HttpResult<Integer>>(builder) {
            @Override
            public void onSuccess(HttpResult<Integer> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.getTheShoppingFee(result.getData());
                    return;
                }
                ((BaseActivity) mvpView).showInforSnackbar(result.getMsg());

            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("bid", bid);
        param.addParam("aid", aid);
        HttpRequest.executePost(httpCallBack, "/user/getFare.action", param);

    }


    /**
     * 、获取上次收货地址
     * 接口：/user/lastAddr.action
     *
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     *
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
     *
     * @param bid 商户id，新需求里为必选参数
     */
    public void getDefaultAddress(int bid) {
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
        param.addParam("bid", bid);
        HttpRequest.executePost(httpCallBack, "/user/lastAddr.action", param);

    }


    /**
     * 提交订单
     * <p>
     * 65、订餐下单（新需求，取代原有两接口）
     * 接口：/order/create.action
     * header示例：
     * code: 8952
     * token: e8a69bf65aefc23d0f360ab695e9eac7
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * aid	必选，int	配送地址的Id
     * time	必选	配送(自提)时间
     * startTime	必选，long	配送(自提)开始时间，unix-ms时间戳
     * endTime	必选，long	配送(自提)截至时间，unix-ms时间戳
     * remark	可选	订单备注
     * bid	必选，int	下单的商户id
     * type	必选，int	餐饮类型，0-早餐，1-中晚餐
     * mid	可选，int	中晚餐的菜单id，type=1时必选
     * self	必选，int	是否自提，0-否，1-是
     * dids	必选	所点菜品及分数，格式如下：
     * 菜品id-份数；菜品id-份数；菜品之间用分号间隔，菜品id和份数用-间隔，实例：1-2;3-2   表示菜品1点了2份，菜品3点了2份
     * fid	可选，int	优惠方案id，使用价格优惠时必传
     * <p>
     * 返回说明
     * 正常时的返回JSON数据包示例：
     * {
     * "code": “A00000”,
     * "msg": "ok",
     * "data": 1	//订单id
     */
    public void commitOrder(OrderCommitData data) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<CreateOrderSucceedData>> httpCallBack = new HttpCallBack<HttpResult<CreateOrderSucceedData>>(builder) {
            @Override
            public void onSuccess(HttpResult<CreateOrderSucceedData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.isXiandan(result.getData());
                    return;
                }
                mvpView.showWarningSnackbar(result.getMsg());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("aid", data.getAid());
        param.addParam("time", data.getTime());
        param.addParam("startTime", data.getStartTime());
        param.addParam("endTime", data.getEndTime());
        param.addParam("remark", data.getRemark());
        param.addParam("bid", data.getBid());
        param.addParam("type", data.getType());
        if (data.getType() == 1) {
            param.addParam("mid", data.getMid());
        }
        param.addParam("self", data.getSelf());
        param.addParam("dids", data.getDids());
        param.addParam("fid", data.getFid());
        HttpRequest.executePost(httpCallBack, "/order/create.action", param);

    }


    /**
     * 获取优惠方案
     * <p>
     * 用户下单时的默认优惠方案
     * <p>
     * 接口：/user/loadFavor.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * bid	必选,int	商户id
     * price	必选，double	订单优惠前的价格(不包含配送费及包装费)
     *
     * @param bid   商户id
     * @param price 订单优惠前的价格(不包含配送费及包装费)
     */
    public void getFavorable(int bid, double price) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<FavorableData>> httpCallBack = new HttpCallBack<HttpResult<FavorableData>>(builder) {
            @Override
            public void onSuccess(HttpResult<FavorableData> result) {
                super.onSuccess(result);
                if (result.isSucceed() && result.getData() != null) {
                    mvpView.getFavorableInfo(result.getData());
                    return;
                }
                if (!result.isSucceed()) {
                    mvpView.showWarningSnackbar(result.getMsg());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("bid", bid);
        param.addParam("price", price);
        HttpRequest.executePost(httpCallBack, "/user/loadFavor.action", param);

    }


    /**
     * 获取餐饮的类型的名称
     *
     * @param cateringType 餐饮的类型
     * @return
     */
    public String getCateringContext(int cateringType) {
        String str = "";
        if (cateringType == 1) {
            str = "今日午餐";
        } else if (cateringType == 2) {
            str = "今日晚餐";
        } else if (cateringType == 3) {
            str = "明日午餐";
        } else if (cateringType == 4) {
            str = "明日晚餐";
        } else if (cateringType == 0) {
            str = "早餐";
        }
        return str;
    }

    /**
     * 获取的订单的总价
     *
     * @param data
     * @return
     */
    public double getSumPrice(ArrayList<FoodData.DishsBeanX.DishsBean> data) {
        double priceSum = 0;
        for (int x = 0; x < data.size(); x++) {
            int num = data.get(x).getNum();
            double price = data.get(x).getPrice();
            priceSum += num * price;
        }
        return priceSum;
    }

    /**
     * 获取的订单的总包装费
     *
     * @param data
     * @return
     */
    public double getFee(ArrayList<FoodData.DishsBeanX.DishsBean> data) {
        double priceSum = 0;
        for (int x = 0; x < data.size(); x++) {
            int num = data.get(x).getNum();
            double price = data.get(x).getFee();
            priceSum += num * price;
        }
        return priceSum;
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


}
