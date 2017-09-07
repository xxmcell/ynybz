package com.honganjk.ynybzbizfood.utils.http.httpquest;

/**
 * 说明:
 * 作者： 杨阳; 创建于：  2017-06-08  13:21
 */
public class ApiConstants {
    //是不正式环境
    public static boolean IS_OFFICIAL = true;

    //食堂陪护-正式地址/测试地址
    public static final String BASE_HOST = IS_OFFICIAL ? "https://urapi.honganjk.com" : "http://ur.honganjk.com";

    //商城-正式地址/测试地址
    public static final String STORE_HOST = IS_OFFICIAL ? "https://urapi.honganjk.com" : "http://bjpsc.honganjk.com";


    public static final String THREE_HOST = "http://c.m.163.com/";


    /**
     * 获取对应的host
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.TYPE_DEFAULT:
                host = BASE_HOST;
                break;
            case HostType.TYPE_STORE:
                host = STORE_HOST;
                break;
            case HostType.TYPE_THREE:
                host = THREE_HOST;
                break;
            default:
                host = "";
                break;
        }
        return host;
    }


}
