package com.honganjk.ynybzbizfood.widget.city_select;


import java.io.Serializable;

/**
 * 说明：获取数据的类型
 * 作者　　: 杨阳
 * 创建时间: 2016/11/2 14:29
 */

public class VisitHttpType implements Serializable {
    private ActionType type;
    private String provinceName;
    private String cityName;
    private String countyName;

    /**
     * 获取省
     *
     * @param type
     */
    public VisitHttpType(ActionType type) {
        this.type = type;
    }

    /**
     * 根据省获取市
     *
     * @param type
     */
    public VisitHttpType(ActionType type, String provinceName) {
        this.type = type;
        this.provinceName = provinceName;
    }

    /**
     * 根据市获取县
     *
     * @param type
     */
    public VisitHttpType(ActionType type, String provinceName, String cityName) {
        this.type = type;
        this.cityName = cityName;
        this.provinceName = provinceName;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }


    /**
     * 说明：
     * 作者　　: 杨阳
     * 创建时间: 2016/11/2 14:36
     * <p>
     * 获得所有省份:http://lixinghang.hbung.com/tools/app_api.ashx?action=GetProvince
     * <p>
     * 根据省获得城市:http://lixinghang.hbung.com/tools/app_api.ashx?action=GetCity
     * 请求参数:     province	省
     * <p>
     * 根据省市获得区县 : http://lixinghang.hbung.com/tools/app_api.ashx?action=GetArea
     * 请求参数: city	市, province	省
     */

    public static enum ActionType {

        PROVINCE(1, "GetProvince"),
        CITY(2, "GetCity"),
        COUNTY(3, "GetArea");

        private int key;
        private String value;

        ActionType(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
