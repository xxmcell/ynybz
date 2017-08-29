package com.honganjk.ynybzbizfood.widget.dialog.calender;

import java.util.Calendar;

/**
 * Created by admin on 2017/4/13.
 */

public class TimeSelectData {
    //选择的日期
    private int data;
    //日期的字符串
    private String dataStr;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        calendar.set(Calendar.DAY_OF_MONTH, data);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //如果传过来的时间小于当前日期，就是下月的日期
        if (data < Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
            dataStr = year + "年" + (month + 1) + "月" + day + "日";
            //如果大于就是的本月的日期
        } else {
            dataStr = year + "年" + month + "月" + day + "日";
        }
    }

    public String getDataStr() {
        return dataStr;
    }


}
