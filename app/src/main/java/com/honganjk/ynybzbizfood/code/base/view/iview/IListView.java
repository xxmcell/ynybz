package com.honganjk.ynybzbizfood.code.base.view.iview;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */

public interface IListView<T> extends BaseListView, BaseSwipeView {
    /**
     * **
     * 创建时间: 2016/9/22 11:01
     * <p>
     * 方法功能：更新界面
     */

    void upDataUI(List<T> datas);
}
