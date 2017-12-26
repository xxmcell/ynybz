package com.honganjk.ynybzbizfood.mode.javabean.tour.home;

import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;

import java.util.List;

/**
 * Created by Administrator on 2017-11-07.
 */

public class HomeTourBean {

    /**
     * data : {"banners":["https: //hajk.image.alimmdn.com/bz/banner1.jpg?t=1499067204099","https: //hajk.image.alimmdn.com/bz/banner2.jpg?t=1499067308439"],"objs":[{"img":"","price":0,"days":["1502770393000","1502770395000"],"id":5,"sales":0,"title":"Schiff维骨力氨基3倍强化片170粒/瓶","tags":"避暑圣地-无购物-历史古迹"},{"img":"","price":0,"days":[1502770393000,1502770395000],"id":3,"sales":0,"tags":"避暑圣地-无购物-历史古迹"}]}
     */

    public DataBean data;

    public DataBean getObjs() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public List<String> banners;
        public List<ObjsBean> objs;

        public List<String> getBanners() {
            return banners;
        }

        public void setBanners(List<String> banners) {
            this.banners = banners;
        }

        public List<ObjsBean> getObjs() {
            return objs;
        }

        public void setObjs(List<ObjsBean> objs) {
            this.objs = objs;
        }


    }
}
