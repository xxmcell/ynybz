package com.honganjk.ynybzbizfood.utils.http.httpquest;

import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.type;
import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * Created by likun on 2016/8/3.
 * 添加一些公共的参数
 */

public class MarvelSigningInterceptor implements Interceptor {

    public String[] noAction = new String[]{};

    public MarvelSigningInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取请求
        Request oldRequest = chain.request();
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        String ac = oldRequest.url().encodedQuery();


//        boolean isAddPublic = false;
//        for (String action : noAction) {
//            if (ac.contains(action)) {
//                isAddPublic = true;
//            }
//        }
        RequestBody requestBody = oldRequest.body();
//        if (isAddPublic) {
//
//
//
//        }


        // 新的请求
        Request newRequest ;

        if (type == HeadType.LOGIN_HEAD.getKey()) {
            newRequest = chain.request().newBuilder()
                    .addHeader("code", String.valueOf(userData.getCode()))
                    .addHeader("token", userData.getToken())
                    .method(oldRequest.method(), requestBody)
                    .url(authorizedUrlBuilder.build())
                    .build();
        } else if (type == HeadType.UNREGISTERED_HEAD.getKey()) {
            newRequest = chain.request().newBuilder()
                    .addHeader("mac", userData.getMac())
                    .addHeader("ticket", userData.getTicket())
                    .addHeader("secret", userData.getSecret())
                    .method(oldRequest.method(), requestBody)
                    .url(authorizedUrlBuilder.build())
                    .build();
        } else {
            newRequest = chain.request().newBuilder()
                    .method(oldRequest.method(), requestBody)
                    .url(authorizedUrlBuilder.build())
                    .build();
        }











        return chain.proceed(newRequest);
    }
}
