package com.honganjk.ynybzbizfood.pressenter.store.classify;

import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.HttpResult;
import com.honganjk.ynybzbizfood.mode.javabean.store.classify.ClassifyRequestBean;
import com.honganjk.ynybzbizfood.mode.javabean.store.classify.FiltrateBean;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.pressenter.common.CommonPresent;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.ui.SnackbarUtil;
import com.honganjk.ynybzbizfood.view.store.classify.interfaces.IClassifyParentInterfaces;
import com.honganjk.ynybzbizfood.widget.PopupPulldown;

import java.util.ArrayList;
import java.util.List;


/**
 * 陪护-首页-p
 */
public class ClassifyPresenter extends CommonPresent<IClassifyParentInterfaces.IClassifyInterface> {
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
    public void getData(final ClassifyRequestBean requestBean) {
        if (requestBean.isFirstRequest()) {
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
                            if (requestBean.isFirstRequest()) {
                                mvpView.clearData();
                            }
                            mvpView.getHttpData((List<StoreHomeData.ObjsBean>) mvpView.getValidData(result.getData().getObjs()));
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                param.addParam("start", mvpView.getPageCount() * mvpView.getPageindex());
                param.addParam("size", mvpView.getPageCount());

                if (requestBean.getType() != 0) {
                    param.addParam("type", requestBean.getType());
                }
                if (requestBean.getBrand() != 0) {
                    param.addParam("brand", requestBean.getBrand());
                }
                if (requestBean.getSort() != 0) {
                    param.addParam("sort", requestBean.getSort());
                }
                HttpRequest.executePostStore(httpCallBack, "/ticket/bjps.json", param);
            }
        };
    }

    /**
     * 获取保健品种类
     * 接口：/ticket/bjpTypes.json
     * 使用方: APP
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/ticket/bjpTypes.json
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
     * 返回说明
     */
    public void filtrateClassify() {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingStatus(mvpView)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<FiltrateBean>>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<List<FiltrateBean>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.filtrateClassify(getPullDownListData(result.getData(), "所有类别"));
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                HttpRequest.executePostStore(httpCallBack, "/ticket/bjpTypes.json", param);
            }
        };
    }

    /**
     * 获取保健品品牌
     * 接口：/ticket/bjpBrands.json
     * 使用方: APP
     * 请求方式: http—get/post
     * 请求地址:
     * 环境	地址
     * 开发	http://bjpsc.honganjk.com/ticket/bjpBrands.json
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
     * 返回说明
     */
    public void filtrateBrand() {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingStatus(mvpView)
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<List<FiltrateBean>>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<List<FiltrateBean>> result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            mvpView.filtrateBrand(getPullDownListData(result.getData(), "所有品牌"));
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                HttpRequest.executePostStore(httpCallBack, "/ticket/bjpBrands.json", param);
            }
        };
    }



    /**
     * 数据的转换
     *
     * @param data
     * @return
     */
    private List<PopupPulldown.PullDownData> getPullDownListData(List<FiltrateBean> data, String all) {
        ArrayList<PopupPulldown.PullDownData> filtrareDatas = new ArrayList<>();
        filtrareDatas.add(new PopupPulldown.PullDownData(0, all));
        for (int i = 0; i < data.size(); i++) {
            filtrareDatas.add(new PopupPulldown.PullDownData(data.get(i).getId(), data.get(i).getLabel()));
        }
        return filtrareDatas;
    }


    /**
     * 获取统合排序的数据
     * 排序方案，1-价格升序；2-价格降序；3-销量降序（首页推荐用此）
     *
     * @param data
     */
    public void getIntegetionPullDownListData(ArrayList<PopupPulldown.PullDownData> data) {
        data.clear();
        data.add(new PopupPulldown.PullDownData(0, "综合排序"));
        data.add(new PopupPulldown.PullDownData(1, "价格升序"));
        data.add(new PopupPulldown.PullDownData(2, "价格降序"));
        data.add(new PopupPulldown.PullDownData(3, "销量降序"));
    }

    /**
     * 、加入购物车（增加数量）
     * 接口：/token/addCart.json
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
     */
    public void addShopingCar(int did, int type, int num) {
        HttpCallBack.Builder builder = new HttpCallBack.Builder()
                .setShowLoadding(true)
                .setLoadingStatus(mvpView)
                .setHttpHead(HeadType.LOGIN_HEAD);

        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<Boolean>>(builder) {
            @Override
            public void onSuccess(HttpResult<Boolean> result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.addShoppingCar(true);
                    mvpView.showSnackbar("添加购物车成功", SnackbarUtil.Info, false);
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("bid", did);
        param.addParam("type", type);
        param.addParam("num", num);
        HttpRequest.executePostStore(httpCallBack, "/token/addCart.json", param);
    }



    /**
     *
     * 搜索数据接口：
     */
    public void getSearchData(final String  s) {

        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder builder = new HttpCallBack.Builder()
                        .setShowLoadding(true)
                        .setLoadingAndRetryManager(mvpView.getLoadingAndRetryManager())
                        .setHttpHead(HeadType.UNREGISTERED_HEAD);

                HttpCallBack httpCallBack = new HttpCallBack<HttpResult<StoreHomeData>>(builder) {
                    @Override
                    public void onSuccess(HttpResult<StoreHomeData> result) {//{"msg":"ok","code":"A00000","data":{"total":0,"objs":[]}}
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            //返回搜索结果
//                            mvpView.setSaerchData((ArrayList<StoreHomeData.ObjsBean>) result.getData().getObjs());
                            mvpView.getHttpData((List<StoreHomeData.ObjsBean>) mvpView.getValidData(result.getData().getObjs()));
                        }
                    }
                };

                HttpRequestParam param = new HttpRequestParam();// TODO: 2017-09-01  .....
                param.addParam("start","0");
                param.addParam("size", "20");
                param.addParam("keyword", s);
                HttpRequest.executePostStore(httpCallBack, "/ticket/bjps.json", param); //搜索接口
            }
        };
    }


    /**
     *
     * 搜索数据  刷新结果：/token/addCart.json
     */
    public void getSearch_RefreshData(final String  s,final ClassifyRequestBean requestBean) {

        if (requestBean.isFirstRequest()) {
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
                    public void onSuccess(HttpResult<StoreHomeData> result) {//{"msg":"ok","code":"A00000","data":{"total":0,"objs":[]}}
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            //返回搜索结果
//                            mvpView.setSaerchData((ArrayList<StoreHomeData.ObjsBean>) result.getData().getObjs());


                            if (result.isSucceed()) {
                                if (requestBean.isFirstRequest()) {
                                    mvpView.clearData();
                                }
                                mvpView.getHttpData((List<StoreHomeData.ObjsBean>) mvpView.getValidData(result.getData().getObjs()));
                            }
                        }
                    }
                };

                HttpRequestParam param = new HttpRequestParam();// TODO: 2017-09-01  .....
                param.addParam("start","0");
                param.addParam("size", "20");
                param.addParam("keyword", s);
                HttpRequest.executePostStore(httpCallBack, "/ticket/bjps.json", param); //搜索接口
            }
        };
    }

}
