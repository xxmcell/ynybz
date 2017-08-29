package com.honganjk.ynybzbizfood.mode.httpresponse;

import com.google.gson.annotations.SerializedName;
import com.honganjk.ynybzbizfood.mode.enumeration.HttpCode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 说明:请求的base类
 * 360621904@qq.com 杨阳 2017/3/9  18:01
 */
public class BaseHttpResponse {

    @SerializedName("code")
    public String Result;
    @SerializedName("msg")
    public String Msg;
    //返回的json原数据
    public transient String content;


    public String getResult() {
        return Result;
    }

    public String getMsg() {
        return Msg;
    }


    public JSONObject getJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject(content);
        content = null;
        return jsonObject;
    }

    /**
     * **
     * 创建时间: 2016/9/2 11:05
     * <p>
     * 方法功能：返回成功  0
     */

    public boolean isSucceed() {
        return HttpCode.getState(Result) == HttpCode.SUCCEED;
    }


    /**
     * 获取状态码对应的提示语
     *
     * @return
     */
    public String getStatusCodeMsg() {
        return (HttpCode.getState(Result).getValuse());
    }
}
