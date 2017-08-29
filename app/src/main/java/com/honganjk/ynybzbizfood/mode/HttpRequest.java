package com.honganjk.ynybzbizfood.mode;

import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpManager;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpSubscription;

import java.util.Map;

import rx.Subscription;

/**
 * Created by Administrator on 2016/8/3.
 * http的一般请求
 */

public class HttpRequest extends BaseHttpRequest {


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 要传具体的url路径  如ashx,php,html
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static <T> Subscription getVerification(HttpCallBack<T> subscriber,
                                                   String path,
                                                   Map<String, String> maps ) {

        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executePost(path, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription getWXToken(HttpCallBack<T> subscriber,
                                                   String path,
                                                   Map<String, String> maps ) {

        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executeGet(path, maps),
                new HttpSubscription<T>(subscriber));
    }



}
