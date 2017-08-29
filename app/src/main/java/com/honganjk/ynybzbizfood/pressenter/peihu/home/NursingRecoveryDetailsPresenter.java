package com.honganjk.ynybzbizfood.pressenter.peihu.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendDetailsData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.peihu.home.interfaces.IHomeParentInterfaces;

import java.util.List;

/**
 * 注释说明: 护工和康复师
 * 阳：2017/4/1-11:30
 */
public class NursingRecoveryDetailsPresenter extends BasePresenter<IHomeParentInterfaces.INursingRecoveryDetails> {
    /**
     * 获取单一护理人员详情
     * 接口：/nv/getDetail.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * mac	必选 	客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
     * ticket	必选	短时段内的请求令牌,获取ticket接口返回
     * secret	必选	动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * id	必选,int	护理人员id
     * <p>
     * uid	可选,int	当前用户id
     */
    public void getData(final int id, final int uid) {

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {

                HttpCallBack.Builder builder = new HttpCallBack.Builder(NursingRecoveryDetailsPresenter.this)
                        .setShowLoadding(true)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<NurserCommendDetailsData>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<NurserCommendDetailsData> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            if (result.getData() != null) {
                                mvpView.getData(result.getData());
                                return;
                            }
                        }
                        showMsg(result);

                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("id", id);
                if (uid != -1) {
                    param.addParam("uid", uid);
                }
                HttpRequest.executePost(httpCallBack, "/nv/getDetail.action", param);
            }
        };

    }


    /**
     * 获取陪护温馨提示
     * 接口：/common/tips.action
     */
    public void getHint() {

        HttpCallBack.Builder builder = new HttpCallBack.Builder(NursingRecoveryDetailsPresenter.this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.NULL_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<String>>>(builder) {
            @Override
            public void onSuccess(HttpResult<List<String>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (result.getData() != null) {
                        mvpView.getHint(result.getData());
                        return;
                    }
                }
                showMsg(result);
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        HttpRequest.executePost(httpCallBack, "/common/tips.action", param);
    }

    /**
     * 关注护理人员
     * 接口：/nurse/keep.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * nid	必选，int	要关注的护理人员Id
     */
    public void setCollect(int id) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder(NursingRecoveryDetailsPresenter.this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (result.getData() != null) {
                        mvpView.setCollect(result.getData());
                        return;
                    }
                    showMsg(result);
                }
                showMsg(result);
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("nid", id);
        HttpRequest.executePost(httpCallBack, "/nurse/keep.action", param);
    }

    /**
     * 取消关注护理人员
     * 接口：/nurse/cancelKeep.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * nid	必选，int	要关注的护理人员Id
     */
    public void setCollectCancel(int id) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder(NursingRecoveryDetailsPresenter.this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (result.getData() != null) {
                        mvpView.setCollectCancel(result.getData());
                        return;
                    }
                }
                showMsg(result);
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("nid", id);
        HttpRequest.executePost(httpCallBack, "/nurse/cancelKeep.action", param);
    }


}
