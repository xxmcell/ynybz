package com.honganjk.ynybzbizfood.pressenter.store.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.BaseHttpRequest;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.home.interfaces.IHomeParentInterfaces;

import java.util.List;


/**
 * 陪护-首页-p
 */
public class HomePresenter extends BasePresenter<IHomeParentInterfaces.IHomeInterface> {
    /**
     * 浏览保健品
     * 接口：/ticket/bjps.json
     * 使用方: APP
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/ticket/bjps.json
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
     * start	必选,int	分页参数，开始位置，以0为起始
     * size	必选,int	分页参数，单页展示数量
     * type	可选,int	种类，由接口3动态获取
     * brand	可选,int	品牌，由接口4动态获取
     * sort	可选,int	排序方案，1-价格升序；2-价格降序；3-销量降序（首页推荐用此）
     */
    public void getData(final boolean isFist) {
        if (isFist) {
            mvpView.clearPagingData();
        }

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingStatus(mvpView)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<StoreHomeData>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<StoreHomeData> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if (isFist) {
                                mvpView.clearData();
                            }
                            mvpView.getHttpData((List<StoreHomeData.ObjsBean>) mvpView.getValidData(result.getData().getObjs()));
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("start", mvpView.getPageCount() * mvpView.getPageindex());
                param.addParam("size", mvpView.getPageCount());
                param.addParam("sort", 3);
                HttpRequest.executePostStore(httpCallBack, "/ticket/bjps.json", param);
            }
        };

    }


    /**
     * 首页轮播图
     * 接口：/ticket/homeBanner.json
     * 使用方: APP
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/ticket/homeBanner.json
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
     * 无
     */
    public void getAdvertisement() {

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {


                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setSwipeRefreshLayout(mvpView.getSwipeRefreshLayout())
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setShowLoadding(false);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<String>>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<List<String>> result) {
                        super.onSuccess(result);
                        try {
                            if (result.isSucceed()) {
                                mvpView.getAdvertisement(result.getData());
                            } else {
                                showMsg(result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                /**
                 * 获得轮播图片
                 *
                 * /nurse/activities.action
                 */
                HttpRequestParam param = new HttpRequestParam();
                BaseHttpRequest.executePostStore(httpCallBack, "/ticket/homeBanner.json", param);


            }
        };
    }

}
