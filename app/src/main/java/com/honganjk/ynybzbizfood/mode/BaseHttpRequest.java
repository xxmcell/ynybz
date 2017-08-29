package com.honganjk.ynybzbizfood.mode;

import com.honganjk.ynybzbizfood.utils.http.httpquest.HostType;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpManager;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpSubscription;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * **
 * 创建时间:2016/9/24　16:24
 *
 * <p>
 * 功能介绍：
 */

public class BaseHttpRequest {

    static Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 订阅
     *
     * @param o 被监听者，相当于网络访问
     * @param s 监听者，  相当于回调监听
     */
    public static <T> Subscription toSubscribe(Observable<T> o, Subscriber<T> s) {
        return o.compose(schedulersTransformer)
                .subscribe(s);//订阅
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     不要传具体的url路径  如ashx,php,html， 一般在生成的时候在baseUrl已经有了（后台只写了一个接口文件）
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription executePost(HttpCallBack<T> subscriber,
                                               Map<String, String> maps) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.executePost(HttpManager.BASE_API, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription executePost(HttpCallBack<T> subscriber,
                                               HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.executePost(HttpManager.BASE_API, param.getStringMap()),
                new HttpSubscription<T>(subscriber));
    }

    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription executeGet(HttpCallBack<T> subscriber,
                                              Map<String, String> maps) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.executeGet(HttpManager.BASE_API, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription executeGet(HttpCallBack<T> subscriber,
                                              HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.executeGet(HttpManager.BASE_API, param.getStringMap()),
                new HttpSubscription<T>(subscriber));
    }

    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription executeDelete(HttpCallBack<T> subscriber,
                                                 Map<String, String> maps) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.executeDelete(HttpManager.BASE_API, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription executeDelete(HttpCallBack<T> subscriber,
                                                 HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.executeDelete(HttpManager.BASE_API, param.getStringMap()),
                new HttpSubscription<T>(subscriber));
    }

    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription executePut(HttpCallBack<T> subscriber,
                                              Map<String, String> maps) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.executePut(HttpManager.BASE_API, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription executePut(HttpCallBack<T> subscriber,
                                              HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.executePut(HttpManager.BASE_API, param.getStringMap()),
                new HttpSubscription<T>(subscriber));
    }

    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription uploadFiles(HttpCallBack<T> subscriber,
                                               Map<String, RequestBody> maps) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.uploadFiles(HttpManager.BASE_API, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription uploadFiles(HttpCallBack<T> subscriber,
                                               HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance().baseServiceApi.uploadFiles(HttpManager.BASE_API, param.getPartMap()),
                new HttpSubscription<T>(subscriber));
    }

    /**
     * 上传大文件,加长上传的时间
     *
     * @param subscriber
     * @param param
     * @param <T>
     * @return
     */
    public static <T> Subscription uploadFilesTimeLong(HttpCallBack<T> subscriber,
                                                       HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance().getLongServiceApi().uploadFiles(HttpManager.BASE_API, param.getPartMap()),
                new HttpSubscription<T>(subscriber));
    }

    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription executePost(HttpCallBack<T> subscriber,
                                               String path,
                                               Map<String, String> maps) {
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executePost(path, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription executePost(HttpCallBack<T> subscriber,
                                               String path,
                                               HttpRequestParam param) {
        LogUtils.e("requestUrl:"+"->"+path+"\n");
        for(String key:param.getStringMap().keySet()){
            LogUtils.e("params:"+key+"->"+param.getStringMap().get(key)+"\n");
        }
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executePost(path, param.getStringMap()),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription executePostStore(HttpCallBack<T> subscriber,
                                               String path,
                                               HttpRequestParam param) {
        LogUtils.e("requestUrl:"+"->"+path+"\n");
        for(String key:param.getStringMap().keySet()){
            LogUtils.e("params:"+key+"->"+param.getStringMap().get(key)+"\n");
        }
        return toSubscribe(HttpManager.getInstance(HostType.TYPE_STORE,subscriber.mHeadType,path).baseServiceApi.executePost(path, param.getStringMap()),
                new HttpSubscription<T>(subscriber));
    }



    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription executeGet(HttpCallBack<T> subscriber,
                                              String path,
                                              Map<String, String> maps) {
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executeGet(path, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription executeGet(HttpCallBack<T> subscriber,
                                              String path,
                                              HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executeGet(path, param.getStringMap()),
                new HttpSubscription<T>(subscriber));
    }

    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription executeDelete(HttpCallBack<T> subscriber,
                                                 String path,
                                                 Map<String, String> maps) {
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executeDelete(path, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription executeDelete(HttpCallBack<T> subscriber,
                                                 String path,
                                                 HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executeDelete(path, param.getStringMap()),
                new HttpSubscription<T>(subscriber));
    }

    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription executePut(HttpCallBack<T> subscriber,
                                              String path,
                                              Map<String, String> maps) {
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executePut(path, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription executePut(HttpCallBack<T> subscriber,
                                              String path,
                                              HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.executePut(path, param.getStringMap()),
                new HttpSubscription<T>(subscriber));
    }

    /**
     * **
     * 创建时间: 2016/9/24 16:09
     * <p>
     * 方法功能：POST请求
     *
     * @param maps : 一定要传Action
     */
    public static <T> Subscription uploadFiles(HttpCallBack<T> subscriber,
                                               String path,
                                               Map<String, RequestBody> maps) {
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.uploadFiles(path, maps),
                new HttpSubscription<T>(subscriber));
    }

    public static <T> Subscription uploadFiles(HttpCallBack<T> subscriber,
                                               String path,
                                               HttpRequestParam param) {
        return toSubscribe(HttpManager.getInstance(subscriber.mHeadType,path).baseServiceApi.uploadFiles(path, param.getPartMap()),
                new HttpSubscription<T>(subscriber));
    }


}
