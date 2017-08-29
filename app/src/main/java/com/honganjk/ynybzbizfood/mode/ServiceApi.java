package com.honganjk.ynybzbizfood.mode;

import com.honganjk.ynybzbizfood.mode.httpresponse.BaseHttpResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.honganjk.ynybzbizfood.utils.http.httpquest.HttpManager.BASE_API;

/**
 * Created by liukun on 16/3/9.
 */
public interface ServiceApi {


    /**
     * **
     * 创建时间: 2016/9/14 16:58
     * <p>
     * 方法功能：评论	        PinglunAdd	keyid：主键
     * type:1、秘籍 详情 ,2、学习资料 详情, 3、餐饮标准化详情 ,4、热点资讯详情 ,5、学习课堂 ,6、动态, 7、动态评论
     * text:评论内容
     * 如果type=7,还需传参数touserid:对某人的评论
     */
    @FormUrlEncoded
    @POST(BASE_API + "?action=PinglunAdd")
    Observable<BaseHttpResponse> PinglunAddXiuChangDetail(@Field("keyid") int keyid, @Field("type") int type, @Field("touserid") int touserid, @Field("text") String text);


}

