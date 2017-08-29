package com.honganjk.ynybzbizfood.widget.dialog.calender;

/**
 * Created by admin on 2017/4/12.
 */

public class CalendarData {
    //日期
    private int calendar;
    //是否可选择
    private boolean isSelect;
    //是否改变颜色
    private boolean changeColor;

    public int getCalendar() {
        return calendar;
    }

    public String getCalendarStr() {
        return calendar == -1 ? "" : calendar + "";
    }

    public void setCalendar(int calendar) {
        this.calendar = calendar;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isChangeColor() {
        return changeColor;
    }

    public void setChangeColor(boolean changeColor) {
        this.changeColor = changeColor;
    }
}
