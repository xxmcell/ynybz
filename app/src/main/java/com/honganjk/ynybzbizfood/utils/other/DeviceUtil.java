/* 
 * @(#)DeviceHelper.java    Created on 2013-3-14
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.honganjk.ynybzbizfood.utils.other;


import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import static com.honganjk.ynybzbizfood.code.MyApplication.myApp;

/**
 * **
 * 创建时间: 13:45 Administrator
 * <p>
 * <p>
 * 功能介绍：获取设备信息的工具类
 * 权限：android.permission.TELEPHONY_SERVICE   ，  READ_PHONE_STATE
 * <p>
 * <p>
 * <p>
 * android.os.Build类中。包括了这样的一些信息。我们可以直接调用 而不需要添加任何的权限和方法。
 * android.os.Build.BOARD：获取设备基板名称
 * android.os.Build.BOOTLOADER:获取设备引导程序版本号
 * android.os.Build.BRAND：获取设备品牌
 * android.os.Build.CPU_ABI：获取设备指令集名称（CPU的类型）
 * android.os.Build.CPU_ABI2：获取第二个指令集名称
 * android.os.Build.DEVICE：获取设备驱动名称
 * android.os.Build.DISPLAY：获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
 * android.os.Build.FINGERPRINT：设备的唯一标识。由设备的多个信息拼接合成。
 * android.os.Build.HARDWARE：设备硬件名称,一般和基板名称一样（BOARD）
 * android.os.Build.HOST：设备主机地址
 * android.os.Build.ID:设备版本号。
 * android.os.Build.MODEL ：获取手机的型号 设备名称。
 * android.os.Build.MANUFACTURER:获取设备制造商
 * android:os.Build.PRODUCT：整个产品的名称
 * android:os.Build.RADIO：无线电固件版本号，通常是不可用的 显示unknown
 * android.os.Build.TAGS：设备标签。如release-keys 或测试的 test-keys
 * android.os.Build.TIME：时间
 * android.os.Build.TYPE:设备版本类型  主要为"user" 或"eng".
 * android.os.Build.USER:设备用户名 基本上都为android-build
 * android.os.Build.VERSION.RELEASE：获取系统版本字符串。如4.1.2 或2.2 或2.3等
 * android.os.Build.VERSION.CODENAME：设备当前的系统开发代号，一般使用REL代替
 * android.os.Build.VERSION.INCREMENTAL：系统源代码控制值，一个数字或者git hash值
 * android.os.Build.VERSION.SDK：系统的API级别 一般使用下面大的SDK_INT 来查看
 * android.os.Build.VERSION.SDK_INT：系统的API级别 数字表示
 * android.os.Build.VERSION_CODES类 中有所有的已公布的Android版本号。全部是Int常亮。可用于与SDK_INT进行比较来判断当前的系统版本
 */

public class DeviceUtil {
    public String UA = Build.MODEL;
    private String mIMEI;// 唯一的设备ID，GSM手机的 IMEI 和 CDMA手机的 MEID
    private String mSIM;// SIM卡的序列号：需要权限：READ_PHONE_STATE
    private String mMobileVersion;// 设置软件的版本号：需要权限：READ_PHONE_STATE
    private String mNetwrokIso;// 当前注册的国家环境代码
    private String mNetType;// 当前的连网类型
    private String mDeviceID;// 唯一设备号
    Context context;


    /**
     * 获取实例
     *
     * @param context
     * @return
     */
    public static synchronized DeviceUtil getInstance(Context context) {
        if (instance == null) {
            instance = new DeviceUtil(context);
        }
        return instance;
    }

    /**
     * 根据手机好拨打电话
     *
     * @param context
     * @param phone
     */
    public static void callByPhone(final Context context, final String phone) {
        if (StringUtils.isEmpty(phone)) {
            return;
        }
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .content("确定拨打 " + phone)
                .title("拨打电话")
                .positiveText("拨打")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
                        context.startActivity(intent);
                    }
                })
                .build();
        dialog.show();
    }


    private TelephonyManager telephonyManager = null;// 很多关于手机的信息可以用此类得到

    private static DeviceUtil instance = null;// 单例模式


    private DeviceUtil(Context context) {
        this.context = context;
        findData();
    }

    /**
     * 设置手机立刻震动
     *
     * @param context
     * @param milliseconds milliseconds/1000(S)
     */
    public void Vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 刷新基本信息
     */
    public void onRefresh() {
        findData();
    }

    /**
     * 获得android设备-唯一标识，android2.2 之前无法稳定运行
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        return Secure
                .getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    /**
     * 打印基本信息串
     *
     * @return
     */
    public String getDeviceInfo() {
        StringBuffer info = new StringBuffer();
        info.append("IMEI:").append(getImei());
        info.append("\n");
        info.append("SIM:").append(getSIM());
        info.append("\n");
        info.append("UA:").append(getUA());
        info.append("\n");
        info.append("MobileVersion:").append(mMobileVersion);
        info.append("\n");
        info.append(getCallState());
        info.append("\n");
        info.append("SIM_STATE: ").append(getSimState());
        info.append("\n");
        info.append("SIM: ").append(getSIM());
        info.append("\n");
        info.append(getSimOpertorName());
        info.append("\n");
        info.append(getPhoneType());
        info.append("\n");
        info.append(getPhoneSettings());
        info.append("\n");
        return info.toString();
    }

    /**
     * 获取sim卡的状态
     *
     * @return
     */
    public String getSimState() {
        switch (telephonyManager.getSimState()) {
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return "未知SIM状态_"
                        + TelephonyManager.SIM_STATE_UNKNOWN;
            case TelephonyManager.SIM_STATE_ABSENT:
                return "没插SIM卡_"
                        + TelephonyManager.SIM_STATE_ABSENT;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return "锁定SIM状态_需要用户的PIN码解锁_"
                        + TelephonyManager.SIM_STATE_PIN_REQUIRED;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                return "锁定SIM状态_需要用户的PUK码解锁_"
                        + TelephonyManager.SIM_STATE_PUK_REQUIRED;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                return "锁定SIM状态_需要网络的PIN码解锁_"
                        + TelephonyManager.SIM_STATE_NETWORK_LOCKED;
            case TelephonyManager.SIM_STATE_READY:
                return "就绪SIM状态_"
                        + TelephonyManager.SIM_STATE_READY;
            default:
                return "未知SIM状态_"
                        + TelephonyManager.SIM_STATE_UNKNOWN;
        }
    }

    /**
     * 获取手机信号类型
     *
     * @return
     */
    public String getPhoneType() {
        switch (telephonyManager.getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_NONE:
                return "PhoneType: 无信号_"
                        + TelephonyManager.PHONE_TYPE_NONE;
            case TelephonyManager.PHONE_TYPE_GSM:
                return "PhoneType: GSM信号_"
                        + TelephonyManager.PHONE_TYPE_GSM;
            case TelephonyManager.PHONE_TYPE_CDMA:
                return "PhoneType: CDMA信号_"
                        + TelephonyManager.PHONE_TYPE_CDMA;
            default:
                return "PhoneType: 无信号_"
                        + TelephonyManager.PHONE_TYPE_NONE;
        }
    }

    /**
     * 服务商名称：例如：中国移动、联通 　　 SIM卡的状态必须是 SIM_STATE_READY就绪状态(使用getSimState()判断).
     */
    public String getSimOpertorName() {
        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            StringBuffer sb = new StringBuffer();
            sb.append("SimOperatorName: ").append(
                    telephonyManager.getSimOperatorName());
            sb.append("\n");
            sb.append("SimOperator: ")
                    .append(telephonyManager.getSimOperator());
            sb.append("\n");
            sb.append("Phone:").append(telephonyManager.getLine1Number());
            return sb.toString();
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("SimOperatorName: ").append("未知");
            sb.append("\n");
            sb.append("SimOperator: ").append("未知");
            return sb.toString();
        }
    }

    /**
     * 获取手机的基本设置
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public String getPhoneSettings() {
        StringBuffer buf = new StringBuffer();
        String str = Secure.getString(context.getContentResolver(),
                Secure.BLUETOOTH_ON);
        buf.append("蓝牙:");
        if (str.equals("0")) {
            buf.append("禁用");
        } else {
            buf.append("开启");
        }

        str = Secure.getString(context.getContentResolver(),
                Secure.BLUETOOTH_ON);
        buf.append("WIFI:");
        buf.append(str);

        str = Secure.getString(context.getContentResolver(),
                Secure.INSTALL_NON_MARKET_APPS);
        buf.append("APP位置来源:");
        buf.append(str);

        return buf.toString();
    }

    /**
     * 电话活动的状态
     *
     * @return
     */
    public String getCallState() {
        switch (telephonyManager.getCallState()) {
            case TelephonyManager.CALL_STATE_IDLE:
                return "电话状态[CallState]: 挂断";
            case TelephonyManager.CALL_STATE_OFFHOOK:
                return "电话状态[CallState]: 接听";
            case TelephonyManager.CALL_STATE_RINGING:
                return "电话状态[CallState]: 来电";
            default:
                return "电话状态[CallState]: 未知";
        }
    }

    // 设置基本信息
    private void findData() {
        telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        mIMEI = telephonyManager.getDeviceId();
        mMobileVersion = telephonyManager.getDeviceSoftwareVersion();
        mNetwrokIso = telephonyManager.getNetworkCountryIso();
        mSIM = telephonyManager.getSimSerialNumber();
        mDeviceID = getDeviceId();

        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            // WIFI/MOBILE
            mNetType = info.getTypeName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 获取设备号
    private String getDeviceId() {
        return Secure
                .getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    public String getNetwrokIso() {
        return mNetwrokIso;
    }

    /**
     * 设备维一id
     *
     * @return
     */
    public String getmDeviceID() {
        return mDeviceID;
    }

    /**
     * 连接网络类型
     *
     * @return
     */
    public String getNetType() {
        return mNetType;
    }

    /**
     * 唯一的设备ID，GSM手机的 IMEI 和 CDMA手机的 MEID
     *
     * @return
     */
    public String getImei() {
        return mIMEI;
    }

    public String getSIM() {
        return mSIM;
    }

    public String getUA() {
        return UA;
    }

    /**
     * 根据手机号发送短信
     *
     * @param context
     * @param phone
     */
    public static void sendSmsByPhone(Context context, String phone) {
        if (StringUtils.isEmpty(phone)) {
            return;
        }
        Uri uri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        // intent.putExtra("sms_body", "");
        context.startActivity(intent);
    }

    /**
     * 根据内容调用手机通讯录
     *
     * @param context
     * @param content
     */
    public static void sendSmsByContent(Context context, String content) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        context.startActivity(intent);
    }


    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }


    /**
     * 获取版本名
     *
     * @return
     */
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }


    /**
     * 获取包信息对象
     *
     * @return
     */
    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm = myApp.getPackageManager();
            pi = pm.getPackageInfo(myApp.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }


    private static final int ERROR = -1;

    //======================================================================获取cup 与 memory 信息==================================

    // 获取CPU名字
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "未知";
        } catch (IOException e) {
            e.printStackTrace();
            return "未知";
        }
    }

    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部剩余存储空间
     *
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * 获取系统总内存
     *
     * @return 总内存大单位为B。
     */
    public static long getTotalMemorySize() {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存单位为B。
     */
    public static long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");

    /**
     * 单位换算
     *
     * @param size      单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0M";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }
}
