package com.honganjk.ynybzbizfood.mode.javabean.common;

import java.util.List;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

public class EvaluateData {


    /**
     * score : 5.17
     * rank : 1
     * list : [{"name":"qaz","img":"qaz","time":1466504938000,"score":5,"content":"测试6"},{"name":"qaz","img":"qaz","time":1466504938000,"score":5,"content":"测试6"},{"name":"qaz","img":"qaz","time":1466504938000,"score":5,"content":"测试6"}]
     */

    private double score;
    private int rank;
    private List<ListBean> list;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : qaz
         * img : qaz
         * time : 1466504938000
         * score : 5
         * content : 测试6
         */

        private String name;
        private String img;
        private long time;
        private int score;
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
