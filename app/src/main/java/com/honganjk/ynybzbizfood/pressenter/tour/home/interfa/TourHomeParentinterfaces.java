package com.honganjk.ynybzbizfood.pressenter.tour.home.interfa;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.home.HomeTourBean;

import java.util.List;

/**
 *
 * Created by Administrator on 2017-11-07.
 */

public interface TourHomeParentinterfaces {

    interface IHomeInterface extends BaseSwipeView, BaseListView {
        void getHttpData(HomeTourBean.DataBean datas);

        void getSearchHttpData(List<ObjsBean> objsBeanList);
    }

}
