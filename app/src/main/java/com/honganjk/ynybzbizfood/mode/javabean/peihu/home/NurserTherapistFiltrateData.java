package com.honganjk.ynybzbizfood.mode.javabean.peihu.home;


import com.honganjk.ynybzbizfood.widget.dialog.calender.TimeSelectData;

import java.util.ArrayList;

/**
 * 陪护-首页-推荐实体
 */
public class NurserTherapistFiltrateData {
    //是否被筛选
    private boolean isFiltrate;
    //康复师周日期
    private int week;
    //康复师时间段日期
    private String time;
    //护工日期
    private String dayS;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String setTime(ArrayList<Integer> selectData) {
        if (selectData == null || selectData.size() == 0) {
            return "1,2,3,4,5";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selectData.size(); i++) {
            if (i == selectData.size() - 1) {
                sb.append(selectData.get(i));
            } else {
                sb.append(selectData.get(i)).append(",");
            }
        }
        time = sb.toString();
        return time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFiltrate() {
        return isFiltrate;
    }

    public void setFiltrate(boolean filtrate) {
        isFiltrate = filtrate;
    }

    public String getDayS() {
        return dayS;
    }

    public void setDayS(ArrayList<TimeSelectData> selectData) {
        if (selectData==null||selectData.size() == 0) {
            this.dayS = null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < selectData.size(); i++) {
            if (i == selectData.size() - 1) {
                sb.append(selectData.get(i).getData());
            } else {
                sb.append(selectData.get(i).getData()).append(",");
            }
        }
        this.dayS = sb.toString();
    }
}
