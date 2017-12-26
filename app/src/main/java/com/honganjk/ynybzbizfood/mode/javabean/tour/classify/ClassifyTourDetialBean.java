package com.honganjk.ynybzbizfood.mode.javabean.tour.classify;

import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;

import java.util.List;

/**
 * Created by Administrator on 2017-11-17.
 */

public class ClassifyTourDetialBean {
    /*  {
          "msg": "ok",
              "code": "A00000",
              "data": {
          "total": 2,	//总数
                  "objs": [
          {
              "img": "",	//图片
                  "price": 0,	//参考价格
                  "days":[1502770393000, 1502770395000],	//出发日期时间戳
              "id": 5,	//数据id
                  "sales": 0,	//销量
                  "title": "Schiff维骨力氨基3倍强化片170粒/瓶 ",//旅行项目标题
                  "tags": "避暑圣地-无购物-历史古迹"	//标签
          },
          {
              "img": "",
                  "price": 0,
                  "days":[1502770393000, 1502770395000],
              "id": 3,
                  "sales": 0,
                  "tags": "避暑圣地-无购物-历史古迹"
          }
          ]
      }
      }*/
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
        private List<ObjsBean> objs;

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

        public void setObjs(List<ObjsBean> objs) {
            this.objs = objs;
        }

        public List<ObjsBean> getObjs() {
            return objs;
        }

    }
}