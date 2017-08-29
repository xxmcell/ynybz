package com.honganjk.ynybzbizfood.pressenter.jiankang;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.jiankang.HealthData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.health.view.IHealthManagerParentView;

import java.util.List;

/**
 * 说明:
 * 作者： 阳2012; 创建于：  2017/5/9  16:19
 */
public class HealthManagerPresenter extends BasePresenter<IHealthManagerParentView.IHealthManager> {
    /**
     * 浏览健康报告摘要
     * 接口：/nurse/healths.action
     * 请求地址:
     * 环境	地址
     * 开发	http://ur.honganjk.com/nurse/healths.action
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
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * start	必选,int	分页参数，开始位置，以0为起始
     * size	必选,int	分页参数，单页展示数量
     */
    public void getListData(final boolean isFirst) {
        if (isFirst) {
            mvpView.clearPagingData();
        }

        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setShowLoadding(false)
                .setHttpHead(HeadType.LOGIN_HEAD)
                .setLoadingStatus(mvpView);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<HealthData>>(buider) {
            @Override
            public void onSuccess(HttpResult<HealthData> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (isFirst) {
                        mvpView.clearData();
                    }
                    mvpView.ListData((List<HealthData.ObjsBean>) mvpView.getValidData(result.getData().getObjs()));

                } else {
                    mvpView.showErrorSnackbar(result.getMsg());
                }
            }
        };


        HttpRequestParam param = new HttpRequestParam();
        param.addParam("start", mvpView.getPageindex() * mvpView.getPageCount());
        param.addParam("size", mvpView.getPageCount());
        HttpRequest.executePost(httpCallBack, "/nurse/healths.action", param);


    }


}
