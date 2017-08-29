package com.honganjk.ynybzbizfood.view.health.view;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.jiankang.HealthData;
import com.honganjk.ynybzbizfood.mode.javabean.jiankang.RepostData;

import java.util.List;

/**
 * 说明:健康管理父掊口
 * 作者： 阳2012; 创建于：  2017/5/9  16:17
 */
public interface IHealthManagerParentView {
    /**
     * 说明:健康管理
     * 作者： 阳2012; 创建于：  2017/5/9  16:17
     */
    interface IHealthManager extends BaseListView, BaseSwipeView {
        void ListData(List<HealthData.ObjsBean> datas);
    }

    /**
     * 说明::健康报告
     * 作者： 阳2012; 创建于：  2017/5/10  9:57
     */
    interface IHealthReport extends BaseView {
        void ListData(List<RepostData> datas);
    }
}
