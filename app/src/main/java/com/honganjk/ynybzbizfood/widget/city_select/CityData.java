package com.honganjk.ynybzbizfood.widget.city_select;

import com.honganjk.ynybzbizfood.utils.db.LiteOrmUtil;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/8/27.
 */
@Table("CityData")
public class CityData implements Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("CityID")
    private int CityID;
    /**
     * 城市名字
     */
    @Column("CityName")
    private String CityName;
    /**
     * 省id
     */
    @Column("ProvinceID")
    private int ProvinceID;
    /**
     * 增加首字母
     */
    @Column("allInitial")
    private String allInitial;

    public CityData(String cityName) {
        CityName = cityName;
    }

    public void setAllInitial() {
        this.allInitial = HanziToPinyin.getPinYinInitial(CityName);
    }

    public void setCityName(String cityName) {
        CityName = cityName;
        this.allInitial = HanziToPinyin.getPinYinInitial(CityName);
    }

    public void setCityID(int cityID) {
        CityID = cityID;
    }

    public void setProvinceID(int provinceID) {
        ProvinceID = provinceID;
    }


    //获取首字母
    public String getInitial() {
        return (allInitial == null || allInitial.length() == 0) ? "#" : allInitial.substring(0, 1).toUpperCase();
    }


    public String getAllInitial() {
        return allInitial;
    }

    /**
     * 列表排序
     *
     * @param list
     */
    public static void sortList(List<CityData> list) {
        Collections.sort(list, new CharComparator());
    }

    /**
     * 保存到数据库
     *
     * @param list
     */
    public static void saveDb(List<CityData> list) {

        LiteOrmUtil.getLiteOrm().save(list);
    }

    public int getCityID() {
        return CityID;
    }

    public String getCityName() {
        return CityName;
    }

    public int getProvinceID() {
        return ProvinceID;
    }

    public static ArrayList<CityData> getCtiList() {
        ArrayList<CityData> list = LiteOrmUtil.getLiteOrm().query(CityData.class);
        Collections.sort(list, new CharComparator());
        return list;
    }

}
