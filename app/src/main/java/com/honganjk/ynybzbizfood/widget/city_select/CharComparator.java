package com.honganjk.ynybzbizfood.widget.city_select;

import java.util.Comparator;

/**
 * .CharComparator接口用来对ListView中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在后面
 */
public class CharComparator implements Comparator<CityData> {

    public int compare(CityData o1, CityData o2) {

        if (o1.getAllInitial() == null || o2.getAllInitial() == null) return 1;
        if (!o1.getAllInitial().matches("[A-Z]+") || !o2.getAllInitial().matches("[A-Z]+")) {
            if (o1.getAllInitial().equals("#")) {
                return 1;
            } else if (o2.getAllInitial().equals("#")) {
                return -1;
            }
        }


            /*
             * 每个都比较
			 */
        return o1.getAllInitial().compareTo(o2.getAllInitial());

    }
}
