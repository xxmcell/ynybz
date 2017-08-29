package com.honganjk.ynybzbizfood.pressenter.shitang.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.FavorableListData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class CarteenDetailPresenter extends BasePresenter<HomeParentInterface.carteenDetail> {

    /**
     * 获取餐厅详情
     *
     * @param id
     */
    public void getCarteenDetail(final int id, final int uid) {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setShowLoadding(true);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<BusinessDetailsData>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<BusinessDetailsData> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            LogUtils.e(result.toString());
                            mvpView.carteenDetail(result.getData());
                            return;
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                if (uid != -1) {
                    param.addParam("uid", uid);
                }
                param.addParam("id", id);
                HttpRequest.executePost(httpCallBack, "/bz/get.action", param);
            }
        };
    }


    /**
     * 商户页获取满减方案
     * 接口：/bz/checkFavor.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * mac	必选 	客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
     * ticket	必选	短时段内的请求令牌,获取ticket接口返回
     * secret	必选	动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * bid	必选,int	商户id
     */
    public void getFavorable(final int bid) {

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)
                        .setShowLoadding(true);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<FavorableListData>>>(buider) {

                    @Override
                    public void onSuccess(HttpResult<List<FavorableListData>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            LogUtils.e(result.toString());
                            mvpView.getFavorableList(result.getData());
                            return;
                        }
                    }
                };

                HttpRequestParam param = new HttpRequestParam();
                param.addParam("bid", bid);
                HttpRequest.executePost(httpCallBack, "/bz/checkFavor.action", param);
            }
        };


    }


}
