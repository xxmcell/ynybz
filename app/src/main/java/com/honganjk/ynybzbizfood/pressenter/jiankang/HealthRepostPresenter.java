package com.honganjk.ynybzbizfood.pressenter.jiankang;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.jiankang.RepostData;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.health.view.IHealthManagerParentView;

import java.util.List;

/**
 * 说明:
 * 作者： 阳2012; 创建于：  2017/5/9  16:19
 */
public class HealthRepostPresenter extends BasePresenter<IHealthManagerParentView.IHealthReport> {
    /**
     * 获取健康报告详情
     * 接口：/nurse/healthDetail.action
     * 使用方: app
     * 请求方式: http—get
     * 请求地址:
     * 环境	地址
     * 开发	http://ur.honganjk.com/nurse/healthDetail.action
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
     * oid	必选,int	订单id
     */
    public void getData(int oid) {
        HttpCallBack.Builder buider = new HttpCallBack.Builder(this)
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD)
                .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager());

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<RepostData>>>(buider) {
            @Override
            public void onSuccess(HttpResult<List<RepostData>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    if (result.getData() != null && (result.getData().size() > 0)) {
                        mvpView.ListData(result.getData());
                    } else {
                        mvpView.showWarningSnackbar("此订单没有健康报告",true);
                    }

                } else {
                    mvpView.showErrorSnackbar(result.getMsg());
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addParam("oid", oid);
        HttpRequest.executePost(httpCallBack, "/nurse/healthDetail.action", param);


    }


}
