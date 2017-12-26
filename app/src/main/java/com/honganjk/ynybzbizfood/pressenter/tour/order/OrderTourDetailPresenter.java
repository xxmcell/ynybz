package com.honganjk.ynybzbizfood.pressenter.tour.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderDetailBean;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.tour.order.interfaces.OrderTourPresentInterface;

/**
 * Created by Administrator on 2017-11-29.
 */

public class OrderTourDetailPresenter extends BasePresenter<OrderTourPresentInterface.IOrderDetailInterface> {
    /**
     * 获取旅行项目行程、酒店、门票、保险信息
     *
     * @param id
     */
    public void getData(final int id) {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<OrderDetailBean.Data>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<OrderDetailBean.Data> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.getHttpData(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("tid", id);
                HttpRequest.executePostTour(httpCallBack, "/ticket/travel.tour", param);
            }
        };
    }

    /**
     * 下单
     * 接口：/token/order.tour
     * 使用方: app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://tour.honganjk.com/token/order.tour
     * 测试
     * 生产
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * header示例：
     * code: 8952
     * token: e8a69bf65aefc23d0f360ab695e9eac7
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * tid	必选，int	旅行项目的Id
     * fid	必选，int	所选出发日期项id
     * users	必选	出行人员id，用英文分号间隔,格式如下：
     * 3;4;5
     * num	必选，int	出行人数
     * room	必选，int	房间数
     * type	必选，int	房间类型，0-随机，1-大床，2-标准间
     * safe	必选，int	是否购买保险，0-不买，1-买
     */
    public void createOrder(final int tid, final int fid, final String users, final int num, final int room, final int type, final int safe) {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setHttpHead(HeadType.LOGIN_HEAD);
                //httpResult中写入泛型,并重写httpCallBack中的onSuccess方法,从中把请求成功的结果取出来
                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<OrderBean>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<OrderBean> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.getOrder(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("tid", tid);
                param.addParam("fid", fid);
                param.addParam("users", users);
                param.addParam("num", num);
                param.addParam("room", room);
                param.addParam("type", type);
                param.addParam("safe", safe);
                HttpRequest.executePostTour(httpCallBack, "/token/order.tour", param);
            }
        };
    }
}
