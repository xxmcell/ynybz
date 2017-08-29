package com.honganjk.ynybzbizfood.pressenter.peihu.order;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.ServiceBean;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.SubscribeOrderBean;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.ValideDataBean;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.DefaultAddressData;
import com.honganjk.ynybzbizfood.utils.http.GsonHelper;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.view.peihu.order.interfaces.OrderParentInterfaces;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 * 说明:
 * 360621904@qq.com 杨阳 2017/4/11  17:22
 */
public class SubscribePresenter extends BasePresenter<OrderParentInterfaces.ISubscribe> {


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
     * 、获取预置的服务项目
     * 接口：/nurse/items.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * mac	必选 	客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
     * ticket	必选	短时段内的请求令牌,获取ticket接口返回
     * secret	必选	动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * type	可选，int	项目类型，1-全天项目；2-钟点项目
     *
     * @param type
     */
    public void getData(final int type) {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack<HttpResult<List<ServiceBean>>> httpCallBack = new HttpCallBack<HttpResult<List<ServiceBean>>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<List<ServiceBean>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.getData(result.getData().get(0));
                            return;
                        }
                        ((BaseActivity) mvpView).showInforSnackbar(result.getMsg());

                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("type", type);
                HttpRequest.executePost(httpCallBack, "/nurse/items.action", param);
            }
        };
    }


    /**
     * 获取可约时间(新需求)
     * 接口：/nv/getAvailable.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * mac	必选 	客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
     * ticket	必选	短时段内的请求令牌,获取ticket接口返回
     * secret	必选	动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选,int	护工id
     * 参数示例：无
     *
     * @param id
     */
    public void getSubscribeTime(final int id) {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Object>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<Object> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            try {
                                JSONObject jsonObject = result.getJSONObject().getJSONObject("data");
                                JSONObject times = jsonObject.optJSONObject("times");
                                if (times != null) {
                                    ValideDataBean da = GsonHelper.getGson().fromJson(times.toString(), ValideDataBean.class);
                                    mvpView.getTherapistValidTime(da);
                                } else {
                                    JSONArray times2 = jsonObject.getJSONArray("times");
                                    if (times2 != null) {
                                        int[] data2 = GsonHelper.getGson().fromJson(times2.toString(), int[].class);
                                        mvpView.getNursingValidTime(data2);
                                    }
                                }
                                return;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ((BaseActivity) mvpView).showInforSnackbar("该服务人员没有可约时间");

                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("id", id);
                HttpRequest.executePost(httpCallBack, "/nv/getAvailable.action", param);
            }
        };
    }

    /**
     * 提交订单
     * 护理订单下单
     * 接口：/nurse/order.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * aid	必选，int	配送地址的Id
     * remark	可选	订单备注
     * week	可选，int	周几，参数范围1234567，下钟点订单时必传该参数，     * 下全天订单时不能传该参数
     * start	必选，int	开始时段，全天订单时值为1-31；钟点订单时值为70-195之间的5的倍数
     * size	必选，int	时段值，全天订单时表示多少天；钟点订单时表示多少个时段
     * self	必选，int	自理能力值，1-完全自理；2-半自理；3-完全不自理
     * tid	必选，int	服务项目id
     * nid	必选，int	护理人员id
     */
    public void commitOrder(SubscribeOrderBean data) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Integer>>(builder) {
            @Override
            public void onSuccess(HttpResult<Integer> result) {
                super.onSuccess(result);
                if (result.isSucceed() && result.getData() > 0) {
                    mvpView.placeTheOrderIsSucceed(true, result.getData());
                    return;
                }
                mvpView.showErrorSnackbar(result.getMsg());

            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("aid", data.getAddressid());
        param.addParam("remark", data.getRemark());
        param.addParam("self", data.getSelfType());
        param.addParam("tid", data.getServiceId());
        param.addParam("nid", data.getUserId());
        //护工请求参数
        if (data.getType() == 1) {
            param.addParam("start", data.getmNursingStartTime());
            param.addParam("size", data.getmNursingServiceTime().length);
            HttpRequest.executePost(httpCallBack, "/nurse/order.action", param);

            /**
             * 康复师请求参数
             *
             * 71、钟点订单下单(56全天下单功能保留)
             * /nurse/orderPeriod.action
             * aid	必选，int	配送地址的Id
             remark	可选	订单备注
             week	必选，int	周几，参数范围1234567，下钟点订单时必传该参数，
             下全天订单时不能传该参数
             size	必选，int	1,2,3,4,5对应7:00--9:00, 9:30--11:30, 12:00--14:00, 16:30--18:30, 17:00--19:00
             self	必选，int	自理能力值，1-完全自理；2-半自理；3-完全不自理
             tid	必选，int	服务项目id
             nid	必选，int	护理人员id
             */
        } else {
            param.addParam("week", data.getWeek());
            param.addParam("size", data.getTime());
            HttpRequest.executePost(httpCallBack, "/nurse/orderPeriod.action", param);
        }

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

    /**
     * 可选择的有效时间
     *
     * @param validTime  有效的时间数组
     * @param selectTime 选择的时间
     * @return
     */
    public int[] getEndNursingValidTime(int[] validTime, int selectTime) {
        //数组排序并复制一份
        Arrays.sort(validTime);
        int[] validTime2 = Arrays.copyOf(validTime, validTime.length);
        //记录有效时间
        ArrayList<Integer> valid = new ArrayList<>();
        //获取当前时间与本月天数
        int maxDay = DateUtils.getMaxDay(Calendar.getInstance());
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        //临时记录
        int temp = selectTime < currentDay ? (selectTime + maxDay) : selectTime;
        //小于当前时间为下一个月，加上当月的天数更改数组
        for (int i = 0; i < validTime2.length; i++) {
            if (validTime2[i] < currentDay) {
                validTime2[i] = validTime2[i] + maxDay;
            }
        }
        //重新排序更改后的数组
        Arrays.sort(validTime2);
        for (int i = 0; i < validTime2.length; i++) {
            //添加有效时间
            if (validTime2[i] >= selectTime && (validTime2[i] - temp == 1 || validTime2[i] - temp == 0)) {
                valid.add(validTime2[i] > maxDay ? validTime2[i] - maxDay : validTime2[i]);
                temp = validTime2[i];
            }
        }
        //集合转数组并返回
        int[] validData = new int[valid.size()];
        for (int i = 0; i < valid.size(); i++) {
            validData[i] = valid.get(i);
        }
        return validData;
    }

    /**
     * 获取有效的时间数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public int[] getSelectValidNumber(int startTime, int endTime) {
        int maxDay = DateUtils.getMaxDay(Calendar.getInstance());
        ArrayList<Integer> valid = new ArrayList<>();
        if (startTime > endTime) {
            for (int i = startTime; i <= (endTime + maxDay); i++) {
                if (i > maxDay) {
                    valid.add(i - maxDay);
                } else {
                    valid.add(i);
                }
            }
        } else {
            for (int i = startTime; i <= endTime; i++) {
                valid.add(i);
            }
        }
        //集合转数组并返回
        int[] validData = new int[valid.size()];
        for (int i = 0; i < valid.size(); i++) {
            validData[i] = valid.get(i);
        }
        return validData;
    }

}
