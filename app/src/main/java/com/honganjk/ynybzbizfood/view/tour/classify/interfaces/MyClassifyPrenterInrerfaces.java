package com.honganjk.ynybzbizfood.view.tour.classify.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourDetialBean;

/**
 * Created by Administrator on 2017-11-20.
 */

public interface MyClassifyPrenterInrerfaces extends BaseView {
    /**
     * 旅游-分类
     */
    interface MyClassifyInrerfaces extends BaseSwipeView, BaseListView, BaseView{
        /**
         * 获取数据
         *
         * @param data
         */
        void getHttpData(ClassifyTourDetialBean.Data data);
    }


}
