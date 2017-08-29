package com.honganjk.ynybzbizfood.code.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.honganjk.ynybzbizfood.code.ActivityManager;
import com.honganjk.ynybzbizfood.code.MyApplication;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.utils.other.VersionUpdata;
import com.honganjk.ynybzbizfood.view.common.activity.WelcomeActivity;

/**
 * Created by Administrator on 2016/6/21.
 */
public class NetWorkBroadcastReceiver extends BroadcastReceiver {


    public NetWorkBroadcastReceiver(Context activity) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        // 判断 这个动作是否是网络连通
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            // 手机连接网络管理类
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 从手机连通�?�里面获取网络状态信息?
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            // 判断网路状态类型?
            if (netInfo != null && netInfo.isAvailable()) {
                Activity activity = ActivityManager.getInstance().currentActivity();
                if (activity != null && activity instanceof BaseActivity && !(activity instanceof WelcomeActivity)) {
                    new VersionUpdata((BaseActivity) activity);
//                    GetPersonalInformation.getUserDataTwo(activity, userData);
                }

                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // WiFi网络
                    ((MyApplication) context.getApplicationContext()).netWorkType = MyApplication.NetWorkType.WIFI;
                } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                    // 有线网络
                    ((MyApplication) context.getApplicationContext()).netWorkType = MyApplication.NetWorkType.ETHERNET;
                } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // 3g网络
                    ((MyApplication) context.getApplicationContext()).netWorkType = MyApplication.NetWorkType.MOBILE;
                }
            } else {
                // 无网络?
                ((MyApplication) context.getApplicationContext()).netWorkType = MyApplication.NetWorkType.NULL;
            }
        }
    }
}
