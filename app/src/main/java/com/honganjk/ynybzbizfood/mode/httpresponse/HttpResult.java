package com.honganjk.ynybzbizfood.mode.httpresponse;

import com.google.gson.annotations.SerializedName;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;

/**
 * Created by likun
 * http返回的基本数据， 用泛型解耦，可以适用于大部分接口
 */
public class HttpResult<T> extends BaseHttpResponse {


    //用来模仿Data
//    @SerializedName( value = "data",alternate = {"objs","advert"})
    @SerializedName(value = "data")
    public T list;

    public T getData() {
        content=null;
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("code=" + Result + "msg=" + Msg);
        if (null != list) {
            sb.append(" subjects:" + list.toString());
        }
        LogUtils.e("result:"+sb.toString());
        return sb.toString();
    }
}
