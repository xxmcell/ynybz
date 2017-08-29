package com.honganjk.ynybzbizfood.mode.javabean.peihu.home;


import android.content.Context;
import android.text.SpannableStringBuilder;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.List;

/**
 * 陪护-首页-推荐实体
 * <p>
 * "head": 	"https://hajk.image.alimmdn.com/YNYBZ/tempUpload/file_83cbc6f1f26f437aa5ecaf2269d70302",	//护工头像
 * "introduction": "introduction",//护工介绍
 * "name": "郑涛",		//名字
 * "mobile": "18267193629",//手机号
 * "type": 1,			//护理类型，1-全天工；2-钟点工
 * "point": 0,			//评分
 * "age": 26,			//年龄
 * "years": "2年",		//经验
 * "birthplace": "天津",	//籍贯
 * "sex": 1,			//性别，1-女；2-男
 * "collect": 0			//被收藏数
 * "area": 0			//服务商圈
 * "img": "https://hajk.image.alimmdn.com/bz/item.png",//项目图片
 * "title": "老人全天护理",	//护理项目标题
 * "tid": 1,			//护理项目id
 * "content": "全天项目",	//护理项目详细介绍
 * "price": 100.12,		//护理项目单价
 * "total": 4,			//评论总数
 * "count": 3,			//过往服务单数
 * "collected": true,		//当前用户是否已关注该护工
 * "skill": "护士职业资格证,鼻试管喂药",	//个人技能
 */
public class NurserCommendDetailsData {
    private String area;
    private String img;
    private int sex;
    private String mobile;
    private int count;
    private boolean collected;
    private int type;
    private String title;
    private String years;
    private double point;
    private int tid;
    private String content;
    private String head;
    private int total;
    private String birthplace;
    private double price;
    private String name;
    private int collect;
    private String introduction;
    private String skill;
    private int age;
    private List<ObjsBean> objs;    //评论用户名
    private List<ItemsBean> items;////服务内容

    public String getSkill() {
        return skill;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public String getArea() {
        return StringUtils.dataFilter(area);
    }

    public String getImg() {
        return StringUtils.dataFilter(img);
    }

    public int getSex() {
        return sex;
    }


    public void setCollect(int collect) {
        this.collect = collect;
    }

    /**
     * * "sex": 1,			//性别，1-女；2-男
     *
     * @return
     */
    public String getSexStr() {
        return sex == 1 ? "女" : "男";
    }

    /**
     * 获取基本信息
     *
     * @return
     */
    public String getInfo() {
        return StringUtils.dataFilter(getSexStr() + " | " + age + " | " + birthplace);
    }

    public String getMobile() {
        return StringUtils.dataFilter(mobile);
    }

    public int getCount() {
        return count;
    }

    public SpannableStringBuilder getCountStr(Context context) {
        String str = count + "份\n服务单数";
        return StringUtils.convertTextColor(str, count + "份", context.getResources().getColor(R.color.main_color));
    }


    public boolean isCollected() {
        return collected;
    }
    public boolean setCollected(boolean collected) {
        return this.collected=collected;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return StringUtils.dataFilter(title);
    }

    public String getYears() {
        return StringUtils.dataFilter(years);
    }

    public SpannableStringBuilder getYearsStr(Context context) {
        String str = years + "\n工作经验";
        return StringUtils.convertTextColor(str, years, context.getResources().getColor(R.color.main_color));
    }


    public double getPoint() {
        return point;
    }


    public SpannableStringBuilder getPointStr(Context context) {
        String str = point + "分\n评分";
        return StringUtils.convertTextColor(str, (point + "分"), context.getResources().getColor(R.color.main_color));
    }


    public int getTid() {
        return tid;
    }

    public String getContent() {
        return StringUtils.dataFilter(content);
    }

    public String getHead() {
        return StringUtils.dataFilter(head);
    }

    public int getTotal() {
        return total;
    }

    public String getBirthplace() {
        return StringUtils.dataFilter(birthplace);
    }

    public double getPrice() {
        return price;
    }

    public String getPriceStr() {
        return "参考价¥" + price + "/天";
    }


    public String getName() {
        return StringUtils.dataFilter(name);
    }


    public int getCollect() {
        return collect;
    }


    public String getCollectStr() {
        return collect + "人收藏";
    }

    public String getIntroduction() {
        return StringUtils.dataFilter(introduction);
    }

    public int getAge() {
        return age;
    }

    public List<ObjsBean> getObjs() {
        return objs;
    }

    /**
     * //评论用户名
     * <p>
     * "name": "pippo2",
     * "img": "https://hajk.image.alimmdn.com/bz/head.jpg",//头像
     * "time": 1477043938000,	//评论时间,单位ms
     * "score": 5,			//评分
     * "content": "dsfdsfdsfdsfsdf"	//评论内容
     */
    public static class ObjsBean {
        private String name;
        private String img;
        private long time;
        private int score;
        private String content;

        public String getName() {
            return StringUtils.dataFilter(name);
        }

        public String getImg() {
            return StringUtils.dataFilter(img);
        }

        public long getTime() {
            return time;
        }

        public String getTimeStr() {
            return DateUtils.getTime(time);
        }

        public int getScore() {
            return score;
        }

        public String getContent() {
            return StringUtils.dataFilter(content);
        }
    }

    /**
     * //服务内容
     * <p>
     * "content": "护士职业证是护士执业的法律凭证，也是实施护士准入制度的具体表现",
     * "title": "护士职业资格证"
     */
    public static class ItemsBean {
        private String content;
        private String title;

        public String getContent() {
            return StringUtils.dataFilter(content);
        }

        public String getTitle() {
            return StringUtils.dataFilter(title);
        }
    }


}
