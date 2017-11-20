package com.honganjk.ynybzbizfood.pressenter.tour.classify.interfa;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.mode.javabean.tour.classify.ClassifyTourBean;

/**
 * Created by Administrator on 2017-11-14.
 */

public interface TourClassifyParentinterfaces {
    interface IClassifyInterface extends BaseSwipeView, BaseListView {
        void getHttpData(ClassifyTourBean.Data datas);
    }
}
