package com.honganjk.ynybzbizfood.code.base.presenter;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.httpresponse.BaseHttpResponse;

import java.util.Map;

/**
 * 注释说明: Presenter的基类
 * 阳：2017/3/9-18:02
 */
public abstract class BasePresenter<T extends BaseView> {
    public T mvpView;

    /**
     * 方法功能：当P创建的时候 把view也创建
     */
    public void onCreate(T mvpView) {
        this.mvpView = mvpView;
    }

    /**
     * 方法功能：销毁
     */
    public void onDestroy() {
        mvpView = null;
    }

    /**
     * 方法功能：销毁
     */
    public void onLowMemory() {
        mvpView = null;
    }

    /**
     * 注释说明: 返回值提示信息
     * 阳：2017/3/9-18:01
     */
    public void showMsg(BaseHttpResponse result) {
        if (result.isSucceed()) {
            mvpView.showErrorSnackbar(result.getMsg());
        } else {
            mvpView.showWarningSnackbar(result.getMsg());
        }
    }

    public void addPageData(Map<String, String> map) {
        if (mvpView instanceof BaseListView) {
            map.put("Pageindex", String.valueOf(((BaseListView) mvpView).getPageindex()));
            map.put("PageCount", String.valueOf(((BaseListView) mvpView).getPageCount()));
        }
    }
}
