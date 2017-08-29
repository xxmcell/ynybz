package com.honganjk.ynybzbizfood.mode.javabean.jiankang;

import android.content.Context;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.List;

/**
 * 说明:健康管理-实体
 * 作者： 阳2012; 创建于：  2017/5/10  10:10
 * <p>
 * id": 10,		//订单id
 * "name": "武",	//护理人员名字
 * "type": 1,		//项目类型，1-全天项目，2-钟点项目
 * "serviceTime":“2017-05-14至2017-05-17共4天”,//服务时间
 * "title": "老人全天护理"	//服务项目名字
 */
public class HealthData {


    /**
     * total : 11
     */

    private int total;
    private List<ObjsBean> objs;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ObjsBean> getObjs() {
        return objs;
    }

    public void setObjs(List<ObjsBean> objs) {
        this.objs = objs;
    }

    public static class ObjsBean {


        private int id;
        private int nid;
        private String name;
        private String img;
        private int type;
        private String serviceTime;
        private int num;
        private String title;
        private double price;
        private int state;
        private String mobile;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getName() {
            String str = getTypeStr();
            return StringUtils.dataFilter(str + ":" + name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return StringUtils.dataFilter(img);
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getType() {
            return type;
        }

        /**
         * 说明:根据类型设置背景色
         * 作者： 阳2012; 创建于：  2017/5/10  11:08
         */
        public int getTypeColor(Context context) {
            if (type == 1) {
                return context.getResources().getColor(R.color.pro_red_e8b8cc);
            } else {
                return context.getResources().getColor(R.color.pro_blue_52a4d1);
            }

        }


        /**
         * //项目类型，1-全天项目，2-钟点项目
         *
         * @return
         */
        public String getTypeStr() {
            return type == 1 ? "护工" : "康复师";
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getServiceTime() {
            return StringUtils.dataFilter(serviceTime);
        }

        public void setServiceTime(String serviceTime) {
            this.serviceTime = serviceTime;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTitle() {
            return StringUtils.dataFilter(title);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getMobile() {
            return StringUtils.dataFilter(mobile);
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }


    }
}
