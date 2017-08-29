package com.honganjk.ynybzbizfood.code;

import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.media.MediaService;
import com.baidu.mapapi.SDKInitializer;
import com.honganjk.ynybzbizfood.BuildConfig;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.broadcast.NetWorkBroadcastReceiver;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.db.LiteOrmUtil;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;
import com.taobao.tae.sdk.callback.InitResultCallback;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.ButterKnife;
import me.drakeet.library.CrashWoodpecker;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.getInstance;
import static com.honganjk.ynybzbizfood.wxapi.WXEntryActivity.WX_API;
import static com.honganjk.ynybzbizfood.wxapi.WXEntryActivity.appId;

public class MyApplication extends MultiDexApplication {
    public static Application myApp;
    public NetWorkType netWorkType = NetWorkType.NULL;
    public static String appCachePath;
    public static String appFilePath;
    public static String appSDCachePath;
    public static String appSDFilePath;

    public enum NetWorkType {
        WIFI, ETHERNET, MOBILE, NetWorkType, NULL
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LiteOrmUtil.init(this);
        ButterKnife.setDebug(BuildConfig.DEBUG);
        myApp = this;
        GlideUtils.init(myApp);
        CrashWoodpecker.init(this);
        new NetWorkBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //typeface = Typeface.createFromAsset(getAssets(), "fzlt.ttf");
        appCachePath = getCacheDir().getPath();
        appFilePath = getFilesDir().getPath();
        appSDCachePath = Environment.getExternalStorageDirectory().getPath() + "/" + getResources().getString(R.string.app_name_letter) + "/cache";
        appSDFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + getResources().getString(R.string.app_name_letter) + "/file";

        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_load_empty;
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_load_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_load_loading;

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        getInstance();


        //注册微信
        WX_API = WXAPIFactory.createWXAPI(this, appId, true);
        WX_API.registerApp(appId);
        //阿里百川
        SDKInitializer.initialize(this);
        AlibabaSDK.asyncInit(this, new InitResultCallback() {
            @Override
            public void onSuccess() {
                LogUtils.e("-----阿里百川  initTaeSDK----onSuccess()-------");
                MediaService mediaService = AlibabaSDK.getService(MediaService.class);
                mediaService.enableHttpDNS(); //果用户为了避免域名劫持，可以启用HttpDNS
                if (BuildConfig.DEBUG)
                    mediaService.enableLog(); //在调试时，可以打印日志。正式上线前可以关闭
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtils.e("-------阿里百川 onFailure----msg:" + msg + "  code:" + code);
            }
        });

        //信鸽推送
        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, !BuildConfig.DEBUG);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
        XGPushManager.registerPush(getApplicationContext(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                LogUtils.e("-------信鸽推送 onSuccess");
            }

            @Override
            public void onFail(Object o, int i, String s) {
                LogUtils.e("-------信鸽推送 onFail");
            }
        });

        // 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
        // 具体可参考详细的开发指南
        // 传递的参数为ApplicationContext
        XGPushManager.registerPush(this);


    }

    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            ActivityManager.getInstance().pushActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityManager.getInstance().exitActivity(activity);
        }
    };


}
