package com.honganjk.ynybzbizfood.pressenter.shitang.order;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;

/**
 * Created by Administrator on 2017/3/11.
 */

public class WritingEvaluationPresenter extends BasePresenter<OrderParentInterfaces.IWritingEvaluation> {
    public void commitEvaluation(int bid, int oid, int score, String content) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<Boolean>> httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.isCommit(true);
                    return;
                }
                mvpView.showWarningSnackbar(result.getMsg());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("bid", bid);
        param.addParam("oid", oid);
        param.addParam("score", score);
        param.addParam("content", content);
        HttpRequest.executePost(httpCallBack, "/user/comment.action", param);

    }

    /**
     * 评论护理订单
     * 接口：/nurse/comment.action
     * <p>
     * 参数 	约束	说明
     * code	必选 	单一用户唯一码,用户登录接口返回
     * token	必选	单一用户短时段内的请求令牌,用户登录接口返回
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * oid	必选,int	所评论的订单id
     * score	必选	评分，限制1-2-3-4-5
     * content	可选
     * 评论内容
     *
     * @param oid     订单Id
     * @param score   分数
     * @param content 内容
     */
    public void commitNurse(int oid, int score, String content) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setHttpHead(HeadType.LOGIN_HEAD);
        HttpCallBack<HttpResult<Boolean>> httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.isCommit(true);
                    return;
                }
                mvpView.showWarningSnackbar(result.getMsg());
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("oid", oid);
        param.addParam("score", score);
        param.addParam("content", content);
        HttpRequest.executePost(httpCallBack, "/nurse/comment.action", param);

    }


}
