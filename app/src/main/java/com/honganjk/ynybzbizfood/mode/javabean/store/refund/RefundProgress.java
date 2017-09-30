package com.honganjk.ynybzbizfood.mode.javabean.store.refund;

/**
 * Created by Administrator on 2017-09-29.
 */

public class RefundProgress {
    private double money;
    private int success;
    private int start;
    private  int label;
    private String title;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
