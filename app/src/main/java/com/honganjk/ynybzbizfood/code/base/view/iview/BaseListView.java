package com.honganjk.ynybzbizfood.code.base.view.iview;

import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;

import java.util.Collection;

/**
 * Created by Administrator on 2016/8/8.
 * 上拉加载更多、分页功能
 */

public interface BaseListView extends BaseView {


    /**
     * **
     * 创建时间: 2016/9/22 11:04
     * <p>
     * 方法功能：获取改变加载状态
     */

    StatusChangListener getStatusChangListener();


    /**
     * **
     * 创建时间: 2016/9/22 11:04
     * <p>
     * 方法功能：获取分页的有效数据
     */

    public <T> Collection<T> getValidData(Collection<T> c);


    /**
     * **
     * 创建时间: 2016/9/22 11:03
     * <p>
     * 方法功能：清空分页数据
     */

    void clearPagingData();

    /**
     * **
     * 创建时间: 2016/9/22 11:03
     * <p>
     * 方法功能：获取分页的页数
     */


    int getPageindex();

    /**
     * **
     * 创建时间: 2016/9/22 11:04
     * <p>
     * 方法功能：获取分页的数量
     */

    int getPageCount();
}
