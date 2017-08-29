package com.honganjk.ynybzbizfood.pressenter.peihu.home;

import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendData;
import com.honganjk.ynybzbizfood.pressenter.common.CommonPresent;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.peihu.home.interfaces.IHomeParentInterfaces;

import java.util.List;


/**
 * 陪护-首页-p
 */
public class HomePresenter extends CommonPresent<IHomeParentInterfaces.IHomeInterface> {
    /**
     * 浏览推荐护理人员
     * 接口：/nv/recommend.action
     * 请求方式: http—get/post
     * header说明
     * 参数 	约束	说明
     * mac	必选 	客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
     * ticket	必选	短时段内的请求令牌,获取ticket接口返回
     * secret	必选	动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
     * 参数 	约束	说明
     * start	必选,int	分页参数，开始位置，以0为起始
     * size	必选,int	分页参数，单页展示数量
     * type	可选,int	城市标记，1-杭州，默认为1
     */
    public void getRecommendData(final boolean isFist) {
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

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<NurserCommendData>>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<List<NurserCommendData>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if (isFist) {
                                mvpView.clearData();
                            }
                            mvpView.getRecommendData((List<NurserCommendData>) mvpView.getValidData(result.getData()));
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("start", mvpView.getPageCount() * mvpView.getPageindex());
                param.addParam("size", mvpView.getPageCount());
                param.addParam("type", 1);
                HttpRequest.executePost(httpCallBack, "/nv/recommend.action", param);
            }
        };

    }


}
