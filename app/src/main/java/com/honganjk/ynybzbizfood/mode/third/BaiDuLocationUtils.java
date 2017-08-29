package com.honganjk.ynybzbizfood.mode.third;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import static com.honganjk.ynybzbizfood.code.MyApplication.myApp;

/**
 * 说明:获取定位
 * 360621904@qq.com 杨阳 2017/3/8  19:21
 */
public class BaiDuLocationUtils {
    public locationCallback lCallback = null;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    public static BaiDuLocationUtils myLocationUtils = null;

    private BaiDuLocationUtils() {
        mLocationClient = new LocationClient(myApp); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener); // 注册监听函数
        initLocation();
    }

    public static BaiDuLocationUtils getInstance() {
        if (myLocationUtils == null) {
            return myLocationUtils = new BaiDuLocationUtils();
        } else {
            return myLocationUtils;
        }
    }

    /**
     * 设置定位参数包括：定位模式（高精度定位模式，低功耗定位模式和仅用设备定位模式），返回坐标类型，是否打开GPS，是否返回地址信息、位置语义化信息、
     * POI信息等等。
     */
    public void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 2000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    /**
     * 取消定位
     */
    public void stop() {
        mLocationClient.stop();
    }

    /**
     * 开始定位
     */
    public void start() {
        mLocationClient.start();
    }

    /**
     * 定位的监听
     *
     * @author yang
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // 设置回调内容
            if (lCallback != null) {
                MyLocation data = new MyLocation(
                        location.getLongitude(),
                        location.getLatitude(),
                        location.getProvince(),
                        location.getCity(),
                        location.getDistrict(),
                        location.getStreet()
                );
                lCallback.onClick(data, location);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 接口
     *
     * @author yang
     */
    public interface locationCallback {
        void onClick(MyLocation data, BDLocation location);
    }

    /**
     * 回调方法
     *
     * @param location
     */
    public void setOnClickCallback(locationCallback location) {
        lCallback = location;
    }




    /**
     * 返回的实体对象
     */
    public static class MyLocation {
        private double latitude;//纬度
        private double longitude;//经度
        private String province;//省名
        private String city;//市
        private String district;//区名
        private String sreet;//街道

        public MyLocation(double latitude, double longitude, String province, String city, String district, String sreet) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.province = province;
            this.city = city;
            this.district = district;
            this.sreet = sreet;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getSreet() {
            return sreet;
        }

        public void setSreet(String sreet) {
            this.sreet = sreet;
        }

        @Override
        public String toString() {
            return "MyLocation{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", sreet='" + sreet + '\'' +
                    '}';
        }
    }

}