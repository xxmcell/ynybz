package com.honganjk.ynybzbizfood.pressenter.store;

import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2017-09-26.
 */

public class ShoppingManager {
    private static ShoppingManager instance;

    private ShoppingManager() {
    }

    public static ShoppingManager getInstance() {
        if (instance == null) {
            instance = new ShoppingManager();
        }
        return instance;
    }
    public CopyOnWriteArrayList<StoreOrderData2.ObjsBean> StoreOrderDataList = new CopyOnWriteArrayList<>();

    public List<StoreOrderData2.ObjsBean> addDataStoreOrderData(StoreOrderData2.ObjsBean data){
            if(!StoreOrderDataList.contains(data)){
                StoreOrderDataList.add(data);
            }
            return StoreOrderDataList;
    }

    public CopyOnWriteArrayList<StoreOrderData2.ObjsBean.DetailsBean> StoreOrderDetailsDataList = new CopyOnWriteArrayList<>();

    public void addDataStoreOrderDetailsData(StoreOrderData2.ObjsBean.DetailsBean data){
        if(!StoreOrderDetailsDataList.contains(data)){
            StoreOrderDetailsDataList.add(data);
        }

    }

    public CopyOnWriteArrayList<StoreOrderData2.ObjsBean.DetailsBean.ListBean> StoreOrderDetailsListBeanDataList = new CopyOnWriteArrayList<>();

    public List<StoreOrderData2.ObjsBean.DetailsBean.ListBean> addDataStoreOrderDetailsListBeanData(StoreOrderData2.ObjsBean.DetailsBean.ListBean data){
        if(!StoreOrderDetailsListBeanDataList.contains(data)){
            StoreOrderDetailsListBeanDataList.add(data);
        }
        return StoreOrderDetailsListBeanDataList;
    }
}
