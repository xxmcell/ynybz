package com.honganjk.ynybzbizfood.mode.javabean.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class FoodData {


    /**
     * id : 541
     * type : 1
     * name : 菜单一
     * day : 2017-03-09
     * dishs : [{"name":"特色菜","dishs":[{"id":704,"name":"包月套餐一","descs":"含一荤二素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。所选食材含有人体所必需的营养素蛋白质、脂肪、糖、无机盐、矿物质、维生素、水和纤维素7类、还包含许多非必须营养素，适合各种老年人适用。","img":"https://hajk.image.alimmdn.com/dev/1484124828916","total":209,"stock":1000,"price":1299,"type":1},{"id":705,"name":"包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。所选食材含有人体所必需的营养素蛋白质、脂肪、糖、无机盐、矿物质、维生素、水和纤维素7类、还包含许多非必须营养素，适合各种老年人适用。","img":"https://hajk.image.alimmdn.com/dev/1484121769493","total":210,"stock":1000,"price":1499,"type":1}]},{"name":"高血压","dishs":[{"id":620,"name":"高血压包月套餐一","descs":"含一荤两素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。所选用的都是新鲜、有机、无农药残留的食材，其中如茄子、菠菜、荠菜、芹菜等对高血压非常有帮助，它们富含纤维素，促进胃肠蠕动，能够调理三高问题。\n","img":"https://hajk.image.alimmdn.com/dev/1484184084010","total":200,"stock":1000,"price":1299,"type":2},{"id":621,"name":"高血压包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。所选用的都是新鲜、有机、无农药残留的食材，其中如茄子、菠菜、荠菜、芹菜等对高血压非常有帮助，它们富含纤维素，促进胃肠蠕动，能够调理三高问题。\n","img":"https://hajk.image.alimmdn.com/dev/1484124981853","total":199,"stock":1000,"price":1499,"type":2}]},{"name":"高血脂","dishs":[{"id":622,"name":"高血脂包月套餐一","descs":"含一荤二素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制，患有高血脂的老年人群饮食要多注意低脂肪、低胆固醇饮食，要多食用蔬菜和富含胆固醇较低的肉类，比如白肉、鸡肉等肉类食品。","img":"https://hajk.image.alimmdn.com/dev/1484122064385","total":202,"stock":1000,"price":1299,"type":3},{"id":623,"name":"高血脂包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。患有高血脂的老年人群饮食要多注意低脂肪、低胆固醇饮食，要多食用蔬菜和富含胆固醇较低的肉类食，比如白肉、鸡肉等肉类食品。","img":"https://hajk.image.alimmdn.com/dev/1484125068511","total":234,"stock":1000,"price":1499,"type":3}]},{"name":"糖尿病","dishs":[{"id":624,"name":"糖尿病包月套餐一","descs":"含一荤两素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。糖尿病患者要在控制总热量的前提下保证有足够的膳食纤维，还要供给充足的蛋白质例如苦瓜、莴笋、小米、玉米、鱼肉、鸡肉等各种食材。","img":"https://hajk.image.alimmdn.com/dev/1484125051367","total":201,"stock":1000,"price":1299,"type":4},{"id":625,"name":"糖尿病包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。糖尿病患者要在控制总热量的前提下保证有足够的膳食纤维，还要供给充足的蛋白质例如苦瓜、莴笋、小米、玉米、鱼肉、鸡肉等各种食材。","img":"https://hajk.image.alimmdn.com/dev/1484125038942","total":194,"stock":1000,"price":1499,"type":4}]},{"name":"骨质疏松","dishs":[{"id":626,"name":"骨质疏松包月套餐一","descs":"含一荤两素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。患有骨质疏松的老年人不要吃辛辣、油腻、烧烤、油炸的东西。尽量多吃富含钙质的食物。如：牛奶、黄豆、海鱼、瘦肉等食品。","img":"https://hajk.image.alimmdn.com/dev/1484121993224","total":206,"stock":1000,"price":1299,"type":5},{"id":627,"name":"骨质疏松包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。患有骨质疏松的老年人不要吃辛辣、油腻、烧烤、油炸的东西。尽量多吃富含钙质的食物。如：牛奶、黄豆、海鱼、瘦肉等食品。","img":"https://hajk.image.alimmdn.com/dev/1484184104083","total":237,"stock":1000,"price":1499,"type":5}]},{"name":"慢性支气管炎","dishs":[{"id":628,"name":"慢性支气管炎包月套餐一","descs":"含一荤两素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。慢性支气管患者的食物宜清淡：多食用新鲜蔬菜，如白菜、菠菜、油菜、萝卜、冬瓜等，不仅能补充多种维生素和无机盐的供给，且具有清痰、去火、通便等功能。","img":"https://hajk.image.alimmdn.com/dev/1484184117373","total":201,"stock":1000,"price":1299,"type":6},{"id":640,"name":"慢性支气管炎包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。慢性支气管患者的食物宜清淡：多食用新鲜蔬菜，如白菜、菠菜、油菜、萝卜、冬瓜等，不仅能补充多种维生素和无机盐的供给，且具有清痰、去火、通便等功能。","img":"https://hajk.image.alimmdn.com/dev/1484124958886","total":201,"stock":10000,"price":1499,"type":6}]},{"name":"冠心病","dishs":[{"id":630,"name":"冠心病包月套餐一","descs":"含一荤两素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。冠心病患者应少吃动物脂肪和胆固醇含量高的食物，如肥肉、蛋黄、鱼子、动物内脏等 ; 少吃甜食，多吃鱼、豆制品、蔬菜和水果 ; 节制饭量，控制体重。低盐饮食，每日食盐在 10 克 (2 钱 ) 以下为宜。","img":"https://hajk.image.alimmdn.com/dev/1484121963656","total":192,"stock":1000,"price":1299,"type":7},{"id":631,"name":"冠心病包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。冠心病患者应少吃动物脂肪和胆固醇含量高的食物，如肥肉、蛋黄、鱼子、动物内脏等 ; 少吃甜食，多吃鱼、豆制品、蔬菜和水果 ; 节制饭量，控制体重。低盐饮食，每日食盐在 10 克 (2 钱 ) 以下为宜。","img":"https://hajk.image.alimmdn.com/dev/1484184048026","total":205,"stock":1000,"price":1499,"type":7}]},{"name":"中风","dishs":[{"id":632,"name":"中风包月套餐一","descs":"含一荤两素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。脑中风病人饮食注意应有适当的蛋白质。包括动物蛋白质(如蛋清，瘦的猪、牛、羊肉，鱼，鸡肉等)和植物蛋白质(如豆腐、豆浆、豆芽)等各种豆制品可降低胆固醇","img":"https://hajk.image.alimmdn.com/dev/1484124946749","total":236,"stock":1000,"price":1299,"type":8},{"id":633,"name":"中风包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。脑中风病人饮食注意应有适当的蛋白质。包括动物蛋白质(如蛋清，瘦的猪、牛、羊肉，鱼，鸡肉等)和植物蛋白质(如豆腐、豆浆、豆芽)等各种豆制品可降低胆固醇。","img":"https://hajk.image.alimmdn.com/dev/1484124933429","total":209,"stock":1000,"price":1499,"type":8}]},{"name":"消化性溃疡","dishs":[{"id":634,"name":"消化性溃疡包月套餐一","descs":"含一荤两素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。消化道溃疡 饮食要定时定量、少食多餐。以吃易消化富有营养的食物为主，并保证摄入足量的维生素类及蛋白质。比如鱼虾中不仅含有丰富的易于消化的优质蛋白质，而且富含有利于溃疡粘膜修复的微量元素锌。","img":"https://hajk.image.alimmdn.com/dev/1484124924709","total":193,"stock":1000,"price":1299,"type":9},{"id":635,"name":"消化性溃疡包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。消化道溃疡 饮食要定时定量、少食多餐。以吃易消化富有营养的食物为主，并保证摄入足量的维生素类及蛋白质。比如鱼虾中不仅含有丰富的易于消化的优质蛋白质，而且富含有利于溃疡粘膜修复的微量元素锌。","img":"https://hajk.image.alimmdn.com/dev/1484184140420","total":205,"stock":1000,"price":1499,"type":9}]},{"name":"肾炎","dishs":[{"id":636,"name":"肾炎包月套餐一","descs":"含一荤两素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。肾炎患者要供给足够维生素，多用新鲜的绿叶蔬菜及水果。新鲜蔬菜能增进病人的食欲，除非是在少尿期限制钾时，需限制蔬菜;否则应多给时鲜蔬菜。恢复期可多供给山药、红枣、桂圆、莲子、银耳等有滋补作用食物。","img":"https://hajk.image.alimmdn.com/dev/1484121870694","total":238,"stock":1000,"price":1299,"type":10},{"id":637,"name":"肾炎包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。肾炎患者要供给足够维生素，多用新鲜的绿叶蔬菜及水果。新鲜蔬菜能增进病人的食欲，除非是在少尿期限制钾时，需限制蔬菜;否则应多给时鲜蔬菜。恢复期可多供给山药、红枣、桂圆、莲子、银耳等有滋补作用食物。","img":"https://hajk.image.alimmdn.com/dev/1484124913716","total":198,"stock":1000,"price":1499,"type":10}]},{"name":"动脉硬化","dishs":[{"id":638,"name":"动脉硬化包月套餐一","descs":"含一荤两素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。动脉硬化患者要多食用含维生素和纤维素较丰富的食物:多种维生素都具有降低胆固醇、防止动脉硬化的作用，如新鲜水果、豆类、蔬菜等。","img":"https://hajk.image.alimmdn.com/dev/1484184158108","total":203,"stock":1000,"price":1299,"type":11},{"id":639,"name":"动脉硬化包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师上门按您的需求和身体状况量身定制。动脉硬化患者要多食用含维生素和纤维素较丰富的食物:多种维生素都具有降低胆固醇、防止动脉硬化的作用，如新鲜水果、豆类、蔬菜等。","img":"https://hajk.image.alimmdn.com/dev/1484121831117","total":234,"stock":1000,"price":1499,"type":11}]}]
     */

    private int id;
    private int type;
    private String name;
    private String day;
    private List<DishsBeanX> dishs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<DishsBeanX> getDishs() {
        return dishs;
    }

    public void setDishs(List<DishsBeanX> dishs) {
        this.dishs = dishs;
    }

    public static class DishsBeanX implements Parcelable {
        /**
         * name : 特色菜
         * dishs : [{"id":704,"name":"包月套餐一","descs":"含一荤二素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。所选食材含有人体所必需的营养素蛋白质、脂肪、糖、无机盐、矿物质、维生素、水和纤维素7类、还包含许多非必须营养素，适合各种老年人适用。","img":"https://hajk.image.alimmdn.com/dev/1484124828916","total":209,"stock":1000,"price":1299,"type":1},{"id":705,"name":"包月套餐二","descs":"含二荤二素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。所选食材含有人体所必需的营养素蛋白质、脂肪、糖、无机盐、矿物质、维生素、水和纤维素7类、还包含许多非必须营养素，适合各种老年人适用。","img":"https://hajk.image.alimmdn.com/dev/1484121769493","total":210,"stock":1000,"price":1499,"type":1}]
         */
        private boolean seleted;

        private String name;
        private List<DishsBean> dishs;

        protected DishsBeanX(Parcel in) {
            seleted = in.readByte() != 0;
            name = in.readString();
            dishs = in.createTypedArrayList(DishsBean.CREATOR);
        }

        public static final Creator<DishsBeanX> CREATOR = new Creator<DishsBeanX>() {
            @Override
            public DishsBeanX createFromParcel(Parcel in) {
                return new DishsBeanX(in);
            }

            @Override
            public DishsBeanX[] newArray(int size) {
                return new DishsBeanX[size];
            }
        };

        public void setSeleted(boolean secelted) {
            this.seleted = secelted;
        }

        public boolean getSeleted() {
            return seleted;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DishsBean> getDishs() {
            return dishs;
        }

        public void setDishs(List<DishsBean> dishs) {
            this.dishs = dishs;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (seleted ? 1 : 0));
            dest.writeString(name);
            dest.writeTypedList(dishs);
        }

        public static class DishsBean implements Parcelable {
            /**
             * id : 704
             * name : 包月套餐一
             * descs : 含一荤二素一汤一饭，三日不重样。由专业营养师按您的要求和身体状况为您量身定制。所选食材含有人体所必需的营养素蛋白质、脂肪、糖、无机盐、矿物质、维生素、水和纤维素7类、还包含许多非必须营养素，适合各种老年人适用。
             * img : https://hajk.image.alimmdn.com/dev/1484124828916
             * total : 209
             * stock : 1000
             * price : 1299.0
             * type : 1
             */

            private int id;
            private String name;
            private String descs;
            private String img;
            private int total;
            private int stock;
            private double price;
            private double fee;
            private int type;

            private String isFirst;
            private int num;
            private int timeType;

            public double getFee() {
                return fee;
            }

            public int getTimeType() {
                return timeType;
            }

            public void setTimeType(int timeType) {
                this.timeType = timeType;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String isFirst() {
                return isFirst;
            }

            public void setFirst(String first) {
                isFirst = first;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescs() {
                return descs;
            }

            public void setDescs(String descs) {
                this.descs = descs;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public double getPrice() {
                return price;
            }

            public String getPriceStr() {
                return "¥" + price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            @Override
            public boolean equals(Object obj) {
                return ((DishsBean) obj).getId() == this.getId();
            }

            public DishsBean() {
            }

            public DishsBean(int id, String name, String descs, String img, int total, int stock, double price, int type, String isFirst, int num, int timeType) {
                this.id = id;
                this.name = name;
                this.descs = descs;
                this.img = img;
                this.total = total;
                this.stock = stock;
                this.price = price;
                this.type = type;
                this.isFirst = isFirst;
                this.num = num;
                this.timeType = timeType;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeString(this.descs);
                dest.writeString(this.img);
                dest.writeInt(this.total);
                dest.writeInt(this.stock);
                dest.writeDouble(this.price);
                dest.writeDouble(this.fee);
                dest.writeInt(this.type);
                dest.writeString(this.isFirst);
                dest.writeInt(this.num);
                dest.writeInt(this.timeType);
            }

            protected DishsBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.descs = in.readString();
                this.img = in.readString();
                this.total = in.readInt();
                this.stock = in.readInt();
                this.price = in.readDouble();
                this.fee = in.readDouble();
                this.type = in.readInt();
                this.isFirst = in.readString();
                this.num = in.readInt();
                this.timeType = in.readInt();
            }

            public static final Creator<DishsBean> CREATOR = new Creator<DishsBean>() {
                @Override
                public DishsBean createFromParcel(Parcel source) {
                    return new DishsBean(source);
                }

                @Override
                public DishsBean[] newArray(int size) {
                    return new DishsBean[size];
                }
            };
        }

    }
}
