package com.honganjk.ynybzbizfood.view.tour.detail.theme;

import com.dsw.calendar.theme.IDayTheme;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.MyApplication;

/**
 * Created by Administrator on 2017-11-29.
 */

public class GridDateTheme implements IDayTheme {
    @Override
    public int colorSelectBG() {
        //选中日期的背景色
        return MyApplication.getContext().getResources().getColor(R.color.red);
    }

    @Override
    public int colorSelectDay() {
        //选中日期的颜色
        return MyApplication.getContext().getResources().getColor(R.color.white);
    }

    @Override
    public int colorToday() {
        //今天日期颜色
        return MyApplication.getContext().getResources().getColor(R.color.green);
    }

    @Override
    public int colorMonthView() {
        //日历的整个背景色
        return 0;
    }

    @Override
    public int colorWeekday() {
        //工作日的颜色
        return MyApplication.getContext().getResources().getColor(R.color.black);
    }

    @Override
    public int colorWeekend() {
        //周末的颜色
        return 0;
    }

    @Override
    public int colorDecor() {
        //事务装饰颜色
        return 0;
    }

    @Override
    public int colorRest() {
        //假日颜色
        return 0;
    }

    @Override
    public int colorWork() {
        //班颜色
        return 0;
    }

    @Override
    public int colorDesc() {
        //描述文字颜色
        return 0;
    }

    @Override
    public int sizeDay() {
        //日期大小
        return 0;
    }

    @Override
    public int sizeDesc() {
        //描述文字大小
        return 0;
    }

    @Override
    public int sizeDecor() {
        //装饰器大小
        return 0;
    }

    @Override
    public int dateHeight() {
        //日期高度
        return 0;
    }

    @Override
    public int colorLine() {
        //线条颜色
        return 0;
    }

    @Override
    public int smoothMode() {
        //滑动模式，0为有滑动模式，1没有滑动效果。
        return 0;
    }
}
