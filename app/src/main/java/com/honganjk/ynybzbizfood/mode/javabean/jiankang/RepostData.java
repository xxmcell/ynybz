package com.honganjk.ynybzbizfood.mode.javabean.jiankang;

import java.util.List;

/**
 * 说明:健康管理-报告
 * 作者： 阳2012; 创建于：  2017/5/10  13:51
 * "id": 1,			//类别id，已从小到大排序
 * "title": "皮肤",		//类别名称
 * "items": [
 */
public class RepostData {
    private int id;
    private String title;
    private List<ItemsBean> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * "id": 1,		//项目id
         * "title": "潮湿",	//项目名称
         * "own": true		//true表示被选择，false，表示未选择
         */

        private int id;
        private String title;
        private boolean own;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isOwn() {
            return own;
        }

        public void setOwn(boolean own) {
            this.own = own;
        }
    }
}
