package com.honganjk.ynybzbizfood.pressenter.peihu.home;

import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendData;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserTherapistFiltrateData;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserTypeData;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.view.peihu.home.interfaces.IHomeParentInterfaces;

import java.util.List;

/**
 * 注释说明: 护工和康复师
 * 阳：2017/4/1-11:30
 */
public class NursingRecoveryPresenter extends BasePresenter<IHomeParentInterfaces.INursingRecover> {
    /**
     * 浏览护理人员
     * 接口：/nv/select.action
     * <p>
     * header说明
     * 参数 	约束	说明
     * mac	必选 	客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
     * ticket	必选	短时段内的请求令牌,获取ticket接口返回
     * secret	必选	动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
     * <p>
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     *
     * @param isFirst      是否第一次请求
     * @param type         护理人员类型，1-全天工，2-钟点工
     * @param sortType     排序类型，1-按销量,2-按评分
     * @param filtrateData 筛选的对象
     * @param tid          服务技能id，增加按技能检索条件
     */
    public void getNursingTherapistDatas(final boolean isFirst, final int type, final int sortType, final NurserTherapistFiltrateData filtrateData, final int tid) {
        if (isFirst) {
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
                            if (isFirst) {
                                mvpView.clearData();
                            }
                            mvpView.getNursingTherapistDatas((List<NurserCommendData>) mvpView.getValidData(result.getData()));
                        } else {
                            showMsg(result);
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();

                //有筛选
                if (filtrateData.isFiltrate()) {
                    /**
                     * 护工筛选 /nv/filterAll.action
                     *
                     days	必选	格式：5,6,7,10  表示5日，6日，7日，10日，中间逗号为英文字符
                     start	必选,int	分页参数，开始位置，以0为起始
                     size	必选,int	分页参数，单页展示数量
                     sortType	必选,int	排序类型，1-按销量,2-按评分
                     type	可选,int	城市字段，1-杭州，尚未开通其它城市，默认为1
                     */
                    if (type == 1) {
                        param.addParam("days", filtrateData.getDayS());

                        param.addParam("start", mvpView.getPageindex() * mvpView.getPageCount());
                        param.addParam("size", mvpView.getPageCount());
                        param.addParam("sortType", sortType);
                        param.addParam("type", 1);//  cityType 城市标记，1-杭州，默认为1
                        if (tid != -1) {
                            param.addParam("tid", tid);
                        }
                        HttpRequest.executePost(httpCallBack, "/nv/filterAll.action", param);

                        /**
                         * 康复师筛选   /nv/filterPeriod.action
                         *
                         hours	必选	格式：1,2,3,4    表示7:00--9:00，9:30--11:30，12:00--14:00，16:30--18:30，无顺序要求，中间逗号为英文字符
                         week	必选,int	周几，周一为1，周三为3，周日为7
                         start	必选,int	分页参数，开始位置，以0为起始
                         size	必选,int	分页参数，单页展示数量
                         sortType	必选,int	排序类型，1-按销量,2-按评分
                         type	可选,int	城市字段，1-杭州，尚未开通其它城市，默认为1
                         */
                    } else {
                        param.addParam("hours", filtrateData.getTime());
                        param.addParam("week", filtrateData.getWeek());
                        param.addParam("start", mvpView.getPageindex() * mvpView.getPageCount());
                        param.addParam("size", mvpView.getPageCount());
                        param.addParam("sortType", sortType);
                        param.addParam("type", 1);
                        if (tid != -1) {
                            param.addParam("tid", tid);
                        }
                        HttpRequest.executePost(httpCallBack, "/nv/filterPeriod.action", param);
                    }
                    /**
                     * 无筛选
                     *
                     start	必选,int	分页参数，开始位置，以0为起始
                     size	必选,int	分页参数，单页展示数量
                     type	必选,int	护理人员类型，1-全天工，2-钟点工
                     sortType	必选,int	排序类型，1-按销量,2-按评分
                     cityType	可选,int	城市标记，1-杭州，默认为1
                     */
                } else {
                    param.addParam("type", type);
                    param.addParam("sortType", sortType);
                    param.addParam("cityType", 1);//  cityType 城市标记，1-杭州，默认为1
                    param.addParam("start", mvpView.getPageindex() * mvpView.getPageCount());
                    param.addParam("size", mvpView.getPageCount());
                    if (tid != -1) {
                        param.addParam("tid", tid);
                    }
                    HttpRequest.executePost(httpCallBack, "/nv/select.action", param);
                }
            }
        };
    }

    /**
     * 获取服务技能标签
     * 接口：/common/labels.action
     * <p>
     * header说明
     * 无
     * 参数说明 (默认字符串类型)
     * 参数 	约束	说明
     * type	可选,int	技能类型，1-护工，2-康复师，默认为1
     *
     * @param type
     */
    public void getService(final int type) {

        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.NULL_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<NurserTypeData>>>(builder) {
            @Override
            public void onSuccess(HttpResult<List<NurserTypeData>> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {

                    mvpView.serviceType(result.getData());
                } else {
                    showMsg(result);
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("type", type);
        HttpRequest.executePost(httpCallBack, "/common/labels.action", param);

    }


}
