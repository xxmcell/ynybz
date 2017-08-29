package com.honganjk.ynybzbizfood.utils.http.httpquest;

import android.content.Context;
import android.util.Log;

import com.honganjk.ynybzbizfood.utils.http.NetWorkHelper;

import java.io.UnsupportedEncodingException;

public class HttpLocalUtils {

    // 网络连接判断

    /**
     * 判断是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        return NetWorkHelper.isNetworkAvailable(context);
    }

    /**
     * 判断mobile网络是否可用
     */
    public static boolean isMobileDataEnable(Context context) {
        String TAG = "httpUtils.isMobileDataEnable()";
        try {
            return NetWorkHelper.isMobileDataEnable(context);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断wifi网络是否可用
     */
    public static boolean isWifiDataEnable(Context context) {
        String TAG = "httpUtils.isWifiDataEnable()";
        try {
            return NetWorkHelper.isWifiDataEnable(context);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否为漫�?
     */
    public static boolean isNetworkRoaming(Context context) {
        return NetWorkHelper.isNetworkRoaming(context);
    }

    /**
     * 编码测试
     */
    public static void testCharset(String datastr) {
        try {
            String temp = new String(datastr.getBytes(), "GBK");
            Log.v("TestCharset", "****** getBytes() -> GBK ******/n" + temp);
            temp = new String(datastr.getBytes("GBK"), "UTF-8");
            Log.v("TestCharset", "****** GBK -> UTF-8 *******/n" + temp);
            temp = new String(datastr.getBytes("GBK"), "ISO-8859-1");
            Log.v("TestCharset", "****** GBK -> ISO-8859-1 *******/n" + temp);
            temp = new String(datastr.getBytes("ISO-8859-1"), "UTF-8");
            Log.v("TestCharset", "****** ISO-8859-1 -> UTF-8 *******/n" + temp);
            temp = new String(datastr.getBytes("ISO-8859-1"), "GBK");
            Log.v("TestCharset", "****** ISO-8859-1 -> GBK *******/n" + temp);
            temp = new String(datastr.getBytes("UTF-8"), "GBK");
            Log.v("TestCharset", "****** UTF-8 -> GBK *******/n" + temp);
            temp = new String(datastr.getBytes("UTF-8"), "ISO-8859-1");
            Log.v("TestCharset", "****** UTF-8 -> ISO-8859-1 *******/n" + temp);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /*
     * 对网络资源文件判断路径 如果是已http开头的就返回这个值 否则在前面加上ORG
     */
    public static String getHttpFileUrl(String url) {
        String res = "";
        if (url != null) {
            if (url.startsWith("http://")||url.startsWith("https://")) {
                res = url;
            } else if (url.startsWith("/storage")) {
                res = url;
            } else if (url.startsWith("storage")) {
                res = url;
            } else if (url.startsWith("/")) {
                res = HttpManager.BASE_HOST + url;
            } else {
                res = HttpManager.BASE_HOST + "/" + url;
            }
        }
        return res;
    }
}
