package com.honganjk.ynybzbizfood.pressenter.store.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsTypeData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.ui.SnackbarUtil;
import com.honganjk.ynybzbizfood.view.store.home.interfaces.IHomeParentInterfaces;

import java.util.List;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;


/**
 * 说明:产品详情
 * 作者： 杨阳; 创建于：  2017-07-07  14:01
 */
public class ProductDetailsPresenter extends BasePresenter<IHomeParentInterfaces.IProductDetails> {
    /**
     * 保健品详情
     * 接口：/ticket/bjpDetail.json
     * 使用方: APP
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/ticket/bjpDetail.json
     * 测试
     * 生产
     * header说明
     * 参数 	约束	说明
     * mac	必选 	客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
     * ticket	必选	短时段内的请求令牌,获取ticket接口返回
     * secret	必选	动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
     * header示例：
     * mac: ac:f7:f3:84:5c:da
     * ticket: 0ecf734e864d25801468461080386
     * secret: 18f1a2b7fea857032c686a9b2957904e
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选,int	保健品id
     * <p>
     * uid	可选,int	当前登录用户id
     */
    public void getData(final int id) {

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<ProductDetailsData>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<ProductDetailsData> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {

                            mvpView.getData(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("id", id);
                if (userData.isLogin()) {
                    param.addParam("uid", userData.getCode());
                }
                HttpRequest.executePostStore(httpCallBack, "/ticket/bjpDetail.json", param);
            }
        };

    }


    /**
     * 保健品规格
     * 接口：/ticket/bjpFormats.json
     * 使用方: APP
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/ticket/bjpFormats.json
     * 测试
     * 生产
     * header说明
     * 参数 	约束	说明
     * mac	必选 	客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
     * ticket	必选	短时段内的请求令牌,获取ticket接口返回
     * secret	必选	动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
     * header示例：
     * mac: ac:f7:f3:84:5c:da
     * ticket: 0ecf734e864d25801468461080386
     * secret: 18f1a2b7fea857032c686a9b2957904e
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选,int	保健品id
     *
     * @param id
     */
    public void getProductType(final int id) {

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<ProductDetailsTypeData>>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<List<ProductDetailsTypeData>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.getProductType(result.getData());
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("id", id);
                HttpRequest.executePostStore(httpCallBack, "/ticket/bjpFormats.json", param);
            }
        };

    }

    /**
     * 收藏保健品
     * 接口：/token/keepBjp.json
     * 使用方: app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/keepBjp.action
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
     * bid	必选，int	保健品Id
     *
     * @param bid
     */
    public void collect(final int bid) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.collect(result.getData());
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("bid", bid);
        HttpRequest.executePostStore(httpCallBack, "/token/keepBjp.json", param);
    }

    /**
     * 取消收藏保健品
     * 接口：/token/cancelKeepBjp.json
     * 使用方: app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/cancelKeepBjp.json
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
     * bid	必选，int	保健品Id
     *
     * @param bid
     */
    public void cancleCollect(final int bid) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.collect(!result.getData());
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("bid", bid);
        HttpRequest.executePostStore(httpCallBack, "/token/cancelKeepBjp.json", param);
    }

    /**
     * 、加入购物车（增加数量）
     * 接口：/token/addCart.json
     * 使用方: app
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/addCart.json
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
     * bid	必选，int	保健品Id
     * type	必选，int	保健品规格code
     * num
     * 必选，int	数量
     */
    public void addShopingCar(int did, int type, int num) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.addShoppingCar(true);
                    mvpView.showSnackbar("添加购物车成功", SnackbarUtil.Info, false);

                }
            }


        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("bid", did);
        param.addParam("type", type);
        param.addParam("num", num);
        HttpRequest.executePostStore(httpCallBack, "/token/addCart.json", param);
    }


}
