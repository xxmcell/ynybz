package com.honganjk.ynybzbizfood.mode;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * **
 * 创建时间:2016/9/24　16:02
 *
 * <p>
 * 功能介绍：基本请求方式的服务器接口
 */

public interface BaseServiceApi {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 要传具体的url路径  如ashx,php,html
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * **
     * 创建时间: 2016/9/24 16:12
     * <p>
     * 方法功能：post请求
     *
     * @param maps : 不一定要传Action
     */
    @FormUrlEncoded
    @POST("{path}")
    Observable<ResponseBody> executePost(
            @Path(value = "path", encoded = true) String path,
            @FieldMap Map<String, String> maps
    );


    /**
     * **
     * 创建时间: 2016/9/24 16:14
     * <p>
     * 方法功能：
     *
     * @param maps : 不一定要传Action
     */


    @GET("{path}")
    Observable<ResponseBody> executeGet(
            @Path(value = "path", encoded = true) String path,
            @QueryMap Map<String, String> maps);

    @DELETE("{path}")
    Observable<ResponseBody> executeDelete(
            @Path(value = "path", encoded = true) String path,
            @QueryMap Map<String, String> maps);

    @PUT("{path}")
    Observable<ResponseBody> executePut(
            @Path(value = "path", encoded = true) String path,
            @QueryMap Map<String, String> maps);


    @Multipart
    @POST("{path}")
    Observable<ResponseBody> uploadFiles(
            @Path(value = "path", encoded = true) String path,
            @PartMap() Map<String, RequestBody> maps);


    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

}
