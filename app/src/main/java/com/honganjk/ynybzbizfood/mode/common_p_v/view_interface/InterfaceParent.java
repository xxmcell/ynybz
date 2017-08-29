package com.honganjk.ynybzbizfood.mode.common_p_v.view_interface;

/**
 * Created by yang on 2016/12/19.
 */

public class InterfaceParent {

   public interface GetTimeScope {

        /**
         * 获取时间的范围
         *
         * @param minTime 最低的时间
         * @param maxTime 最大的时间
         */
        void getTimeScopet(int minTime, int maxTime);
    }
}
