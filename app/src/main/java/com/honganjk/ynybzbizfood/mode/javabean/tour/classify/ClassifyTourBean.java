package com.honganjk.ynybzbizfood.mode.javabean.tour.classify;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-11-14.
 */

public class ClassifyTourBean {
    /*  {
          "msg": "ok",
          "code": "A00000",
          "data": {"total": 2,	//总数
                    "objs": [{"feature": "西湖",	//介绍
                              "id": 6,		//数据id
                               "img": "https://hajk.image.alimmdn.com/dev/1502694843224",//图片
                               "name": "杭州"	//目的地名},

                              {"feature": "山水甲天下2",
                               "id": 5,
                               "img": "https://hajk.image.alimmdn.com/dev/1502777943682",
                               "name": "桂林"}]
                  }
          }
      */
    private String msg;
    private String code;
    private Data data;
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public static class Data {

        private int total;
        private List<Objs> objs;
        public void setTotal(int total) {
            this.total = total;
        }
        public int getTotal() {
            return total;
        }

        public void setObjs(List<Objs> objs) {
            this.objs = objs;
        }
        public List<Objs> getObjs() {
            return objs;
        }

        public static class Objs implements Serializable{

            private String feature;
            private int id;
            private String img;
            private String name;
            public void setFeature(String feature) {
                this.feature = feature;
            }
            public String getFeature() {
                return feature;
            }

            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setImg(String img) {
                this.img = img;
            }
            public String getImg() {
                return img;
            }

            public void setName(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }
        }
    }
}
