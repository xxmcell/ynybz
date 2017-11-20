package com.honganjk.ynybzbizfood.utils.http.httpquest;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 说明:主机的类型
 * 作者： 杨阳; 创建于：  2017-06-08  13:08
 */
public class HostType {
    /**
     * 多少种Host类型
     */
    public static final int TYPE_COUNT = 4;

    /**
     * 默认地址host，食堂陪护的访问地址
     */
    public static final int TYPE_DEFAULT = 1;

    /**
     * 第二种host,商城的访问地址
     */
    public static final int TYPE_STORE = 2;

    /**
     * 第三种host
     */
    public static final int TYPE_THREE = 3;
    /**
     * 第四种种host，旅游的访问地址
     */
    public static final int TYPE_FOUR=4;
    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({TYPE_DEFAULT, TYPE_STORE, TYPE_THREE,TYPE_FOUR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }
}
