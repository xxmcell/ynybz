package com.honganjk.ynybzbizfood.pressenter.store.collect;

import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.pressenter.common.CommonPresent;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.store.collect.interfaces.ICollectParentInterfaces;

import java.util.List;


/**
 * 陪护-首页-p
 */
public class CollectPresenter extends CommonPresent<ICollectParentInterfaces.ICollectInterface> {
    /**
     * 查看收藏的保健品
     * 接口：/token/keeps.json
     * 使用方: APP
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/token/keeps.json
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
     */
    public void getData(final boolean isFirst) {
        if (isFirst) {
            mvpView.clearPagingData();
        }

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<StoreHomeData>>(builder) {
            @Override
            public void onSuccess(HttpResult<StoreHomeData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (isFirst) {
                        mvpView.clearData();
                    }
                    mvpView.getHttpData((List<StoreHomeData.ObjsBean>) mvpView.getValidData(result.getData().getObjs()));
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("start", mvpView.getPageCount() * mvpView.getPageindex());
        param.addParam("size", mvpView.getPageCount());
        HttpRequest.executePostStore(httpCallBack, "/token/keeps.json", param);
    }


}
