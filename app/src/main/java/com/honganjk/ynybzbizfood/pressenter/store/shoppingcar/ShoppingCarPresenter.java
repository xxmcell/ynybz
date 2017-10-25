package com.honganjk.ynybzbizfood.pressenter.store.shoppingcar;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.shoppingcar.interfaces.IShoppingCarParentInterfaces;

import java.util.List;


/**
 * 陪护-首页-p
 */
public class ShoppingCarPresenter extends BasePresenter<IShoppingCarParentInterfaces.IShoppingCarInterface> {

    /**
     * 浏览购物车商品
     * 接口：/token/carts.json
     * 使用方: APP
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/carts.json
     * <p>
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
     * start	必选,int	分页参数，开始位置，以0为起始
     * size	必选,int	分页参数，单页展示数量
     *
     * @param isFist
     */
    public void getData(final boolean isFist) {
        if (isFist) {
            mvpView.clearPagingData();
        }

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<ShoppingcarData>>(builder) {
            @Override
            public void onSuccess(HttpResult<ShoppingcarData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (isFist) {
                        mvpView.clearData();
                    }
                    mvpView.getHttpData((List<ShoppingcarData.ObjsBean>) mvpView.getValidData(result.getData().getObjs()));
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("start", mvpView.getPageindex() * mvpView.getPageCount());
        param.addParam("size", mvpView.getPageCount());
        HttpRequest.executePostStore(httpCallBack, "/token/carts.json", param);
    }


    /**
     * 产品的数量增加或者减少
     * <p>
     * 、加入购物车（增加数量）
     * 接口：/token/addCart.json
     * <p>
     * 从购物车中删除（减少数量）
     * /token/delCart.json
     * <p>
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
     *
     * @param isAdd 不是添加数量
     * @param bid
     * @param type
     * @param num
     */
    public void addAndSubtractNumber(final boolean isAdd, int bid, int type, int num) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.addAndSubtractNumberHttp(isAdd);
                } else {
                    showMsg(result);
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("bid", bid);
        param.addParam("type", type);
        param.addParam("num", num);
        if (isAdd) {
            HttpRequest.executePostStore(httpCallBack, "/token/addCart.json", param);
        } else {
            HttpRequest.executePostStore(httpCallBack, "/token/delCart.json", param);
        }
    }

    public void deleteCarts(int bid, int type,String deleteAll){
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {

                } else {
                    showMsg(result);
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        if(bid==0&&type==0){
            param.addParam("bids",deleteAll);
        }else  {
            String bids="";
            bids=bid+"-"+type+";";
            param.addParam("bids", bids);
        }
        HttpRequest.executePostStore(httpCallBack, "/token/delCarts.json", param);
    }
}
