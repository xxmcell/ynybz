package com.honganjk.ynybzbizfood.mode.javabean.peihu.home;


/**
 * 护工与康复师服务类型
 * <p>
 * "id": 1,	//数据id，注册接口使用
 * "label": "护士职业资格证"	//技能标题
 */
public class NurserTypeData {
    private int id;
    private String label;
    private boolean isSelect;

    public NurserTypeData(int id, String label, boolean isSelect) {
        this.id = id;
        this.label = label;
        this.isSelect = isSelect;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
