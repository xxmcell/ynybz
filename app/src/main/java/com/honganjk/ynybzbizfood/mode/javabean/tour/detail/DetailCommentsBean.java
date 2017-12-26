package com.honganjk.ynybzbizfood.mode.javabean.tour.detail;

import java.util.List;

/**
 * Created by Administrator on 2017-11-24.
 */

public class DetailCommentsBean {
    /**
     * {
     "msg": "ok",
     "code": "A00000",
     "data": {
     "total": 2,	//总数目
     "objs": [
     {
     "avatar": "https://hajk.image.alimmdn.com/bz/head.jpg",//头像
     "content": "测试6",//评论内容
     "imgs": null,	//附图，可能为空，可能为多图，以英文分号间隔
     "name": "qaz",	//评论人昵称
     "score": 3,		//评分
     "time": 1466504329000	//评论时间
     },
     {
     "avatar": "https://hajk.image.alimmdn.com/bz/head.jpg",
     "content": "测试6",
     "imgs": null,
     "name": "qaz",
     "score": 3,
     "time": 1466504323000
     }
     ]
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
    public static class Data{
        private int total;//总数目
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
        public static class ObjsBean{
            private String avatar;//头像
            private String content;//评论内容
            private String imgs;//附图，可能为空，可能为多图，以英文分号间隔
            private String name;//评论人昵称
            private int score;//评分
            private String time;//评论时间
            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
            public String getAvatar() {
                return avatar;
            }

            public void setContent(String content) {
                this.content = content;
            }
            public String getContent() {
                return content;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }
            public String getImgs() {
                return imgs;
            }

            public void setName(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }

            public void setScore(int score) {
                this.score = score;
            }
            public int getScore() {
                return score;
            }

            public void setTime(String time) {
                this.time = time;
            }
            public String getTime() {
                return time;
            }
        }
    }
}
