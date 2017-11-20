package com.honganjk.ynybzbizfood.mode.javabean.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.third.BaiDuLocationUtils;
import com.honganjk.ynybzbizfood.utils.encryption.Md5Utils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.view.common.activity.LoginActivity;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import static com.honganjk.ynybzbizfood.code.MyApplication.myApp;
import static com.honganjk.ynybzbizfood.utils.db.LiteOrmUtil.getLiteOrm;

/**
 * 说明:用户对象类
 * 360621904@qq.com 杨阳 2017/3/4  9:33
 */
@Table("UserData")
public class UserInfo {
    //用户对象
    @Ignore
    public static UserInfo userData;
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    @Column("isLogin")
    private boolean isLogin = false;
    @Column("code")
    private int code;//该用户的唯一码，之后的接口请求header中使用，建议前端解析为数字类型数据存储在本地
    @Column("token")
    private String token = "";//该用户的当前时段的token，之后的接口请求header中使用
    @Ignore
    private String ticket;//短时段内的请求令牌,获取ticket接口返回(游客用户使用的验证)
    @Ignore
    private String secret = "";//动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
    @Ignore
    private String currentCity = "杭州";//当前城市
    @Ignore
    private double latitude;//纬度
    @Ignore
    private double longitude;//经度
    @Ignore
    private String address;//当前地址
    @Column("mac")
    private String mac;//客户端的唯一码， APP可用手机mac，微信企业号可用微信ID，轻应用自己构建唯一码
    @Column("name")
    private String name;
    @Column("mobile")
    private String mobile;//手机号
    @Column("img")
    private String img;//头像地址
    @Column("balance")
    private double balance;//余额
    @Column("addrNum")
    private int imaddrNumg;//收货地址数

    public static UserInfo getInstance() {
        try {
            userData = (getLiteOrm().query(UserInfo.class)).size() > 0 ? (getLiteOrm().query(UserInfo.class)).get(0) : new UserInfo();
            userData.setMac(DeviceUtil.getInstance(myApp).getmDeviceID());
            if ((getLiteOrm().query(UserInfo.class)).size() == 0) {
                save(userData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userData;
    }

    /**
     * 直接判断是否登录
     *
     * @return
     */
    public static boolean isSLogin() {
        return userData.isLogin();
    }

    /**
     * 退出登录
     * <p>
     * <p>
     * int res = LiteOrmUtil.getLiteOrm().update(WhereBuilder.create(UserInfo.class).where("isLogin=?", true), new ColumnsValue(new String[]{"isLogin"}, new Boolean[]{false}), ConflictAlgorithm.None);
     */
    private static boolean exit(Context context) {
        //清除所有用户
        int res = getLiteOrm().deleteAll(UserInfo.class);
        userData.setLogin(false);
        save(userData);
        // 跳转到登录页面
//        ActivityManager.getInstance().exitAllActivity();
        LoginActivity.startUI(context, true);
        return true;
    }

    /**
     * 退出登录,对话框
     */
    public static void exitLogin(final Activity context) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                .title("提示")
                .positiveText("确定")
                .negativeColorRes(R.color.gray)
                .negativeText("取消")
                .content("确定退出登录吗？")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        exit(context);
                    }
                })
                .build();
        materialDialog.show();

    }


    /**
     * 保存数据到数据库（）
     *
     * @param data 被保存的数据，这个数据会被默认成数据库的唯一登录数据
     * @return
     */
    public static boolean save(UserInfo data) {
        if (data != null) {
            try {
                userData = data;
                //清除其他登录的用户
//                LiteOrmUtil.getLiteOrm().update(WhereBuilder.create(UserInfo.class).where("isLogin=?", true), new ColumnsValue(new String[]{"isLogin"}, new Boolean[]{false}), ConflictAlgorithm.None);
                getLiteOrm().deleteAll(UserInfo.class);
                getLiteOrm().save(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 定位
     *
     * @param callBack
     */
    public static void setUserLocation(final LocationListener callBack) {
        BaiDuLocationUtils.getInstance().setOnClickCallback(new BaiDuLocationUtils.locationCallback() {
            @Override
            public void onClick(BaiDuLocationUtils.MyLocation data, BDLocation location) {
                userData.setLatitude(data.getLongitude());
                userData.setLongitude(data.getLatitude());
                userData.setAddress(StringUtils.dataFilter(data.getDistrict()) + StringUtils.dataFilter(data.getSreet(), " "));
                BaiDuLocationUtils.getInstance().stop();
                if (callBack != null) {
                    callBack.callBack(true);
                }
            }
        });
        BaiDuLocationUtils.getInstance().start();
    }

    public interface LocationListener {
        void callBack(boolean isLocation);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 阳_2017/3/4:以下以基本方法
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLogin() {
        return isLogin;
    }


    public void setLogin(boolean login) {
        isLogin = login;
    }


    public static UserInfo getUserData() {
        return userData;
    }

    public String getTicket() {
        return StringUtils.dataFilter(ticket, "000");
    }

    public static void setUserData(UserInfo userData) {
        UserInfo.userData = userData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return StringUtils.dataFilter(token);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getMac() {
        return StringUtils.dataFilter(mac);
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSecret() {
        return secret;
    }

    /**
     * 动态校验码，(mac+"user"+ ticket) MD5-32位小写加密后的值
     */
    public void setSecret() {
        StringBuffer sb = new StringBuffer();
        sb.append(mac).append("user").append(ticket);
        String str = Md5Utils.getMD5(sb.toString());

        this.secret = str;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
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

    public String getAddress() {
        return StringUtils.dataFilter(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return StringUtils.dataFilter(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return StringUtils.dataFilter(mobile, "1");
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getImaddrNumg() {
        return imaddrNumg;
    }

    public String getImaddrNumgStr() {
        return StringUtils.dataFilter(imaddrNumg);
    }

    public void setImaddrNumg(int imaddrNumg) {
        this.imaddrNumg = imaddrNumg;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", isLogin=" + isLogin +
                ", code=" + code +
                ", token='" + token + '\'' +
                ", ticket='" + ticket + '\'' +
                ", secret='" + secret + '\'' +
                ", currentCity='" + currentCity + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", mac='" + mac + '\'' +
                '}';
    }
}
