package com.honganjk.ynybzbizfood.mode.javabean.base;


import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpManager;

import java.io.Serializable;

/**
 * 说明：检测版本
 * 作者　　: 杨阳
 * 创建时间: 2017/1/10 17:09
 */

public class CheckVersionData implements Serializable {

    /**
     * VersionName : 1.0
     * AppPath : /APPFiles/lxh_20170110.apk
     * IsUPdate : true
     * VersionIntro : 版本有更新
     */

    private String VersionName;
    private String AppPath;
    private boolean IsUPdate;
    private String VersionIntro;

    public String getVersionName() {
        return VersionName;
    }

    public String getAppPath() {
        return HttpManager.BASE_HOST +AppPath;
    }

    public boolean isUPdate() {
        return IsUPdate;
    }

    public void setUPdate(boolean UPdate) {
        IsUPdate = UPdate;
    }

    public String getVersionIntro() {
        return VersionIntro;
    }
}
