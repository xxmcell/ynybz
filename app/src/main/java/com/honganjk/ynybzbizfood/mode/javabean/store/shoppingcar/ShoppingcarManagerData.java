package com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.List;

/**
 * 购物车的管理
 */
public class ShoppingcarManagerData implements SelectListenerView {
    List<ShoppingcarData.ObjsBean> mDatas;
    int mSumCount = 0;
    int mSelectCount = 0;
    double mPrice = 0;
    boolean mIsAllSelect = false;
    SelectAllListenerView mSelectAllListenerView;

    public ShoppingcarManagerData(List<ShoppingcarData.ObjsBean> datas, SelectAllListenerView selectAllListenerView) {
        this.mDatas = datas;
        mSelectAllListenerView = selectAllListenerView;
    }

    public int getCount() {
        mSumCount = mDatas.size();

        return mSumCount;
    }

    public boolean getmIsAllSelect() {
        return mIsAllSelect = (mSumCount == mSelectCount);
    }

    @Override
    public void isSelect(boolean isSelect) {
        mSelectCount = isSelect ? ++mSelectCount : --mSelectCount;
        if (mSelectAllListenerView != null) {
            mSelectAllListenerView.isAllSelect(getmIsAllSelect(), mSelectCount, getSumPrice());
        }
    }

    @Override
    public void setNumber(int count) {
        if (mSelectAllListenerView != null) {
            mSelectAllListenerView.isAllSelect(getmIsAllSelect(), mSelectCount, getSumPrice());
        }
    }


    /**
     * 全选或者都不选
     *
     * @param isAllSelect
     */
    public void setmIsAllSelect(boolean isAllSelect) {
        for (int i = 0; i < mDatas.size(); i++) {
            mDatas.get(i).setIsSelect(isAllSelect);
        }
        if (isAllSelect) {
            mIsAllSelect = true;
            mSelectCount = getCount();
            mSelectAllListenerView.isAllSelect(mIsAllSelect, getCount(), getSumPrice());
        } else {
            mPrice = 0;
            mSelectCount = 0;
            mSumCount = 0;
            mIsAllSelect = false;
            mSelectAllListenerView.isAllSelect(mIsAllSelect, 0, getSumPrice());
        }
    }

    public double getSumPrice() {
        mPrice=0;
        for (int i = 0; i < mDatas.size(); i++) {
           if(mDatas.get(i).getIsSelect()==true){

               mPrice+=(mDatas.get(i).getMoney()*mDatas.get(i).getNum());
           }
        }
        return StringUtils.roundDouble(mPrice, 2);
    }
}
