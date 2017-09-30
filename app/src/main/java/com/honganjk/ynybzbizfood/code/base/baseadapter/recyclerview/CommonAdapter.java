package com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemLongClickListener;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemsClickListener;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    //布局
    protected int mLayoutId;
    //加载数据
    protected List<T> mDatas;
    protected List<T> data;


    protected T mdata;
    OnItemClickListener onItemClickListener;
    OnItemLongClickListener onItemLongClickListener;
    int headSize = 0;
     OnItemsClickListener onItemsClickListener;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
        setHasStableIds(true);
    }
    public CommonAdapter (Context context, int layoutId, T datas){
        mContext = context;
        mLayoutId = layoutId;
        mdata = datas;
        setHasStableIds(true);
    }

    public int getmLayoutId() {
        return mLayoutId;
    }

    @Override
    public long getItemId(int position) {
        return mDatas.get(position).hashCode();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemsClickListener(OnItemsClickListener<StoreOrderData2.ObjsBean, StoreOrderData2.ObjsBean.DetailsBean.ListBean, StoreOrderData2.ObjsBean.DetailsBean> onItemClickListener) {
        this.onItemsClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    onItemClickListener.onItemClick(parent, v, mDatas.get(position - headSize), position - headSize);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    int position = getPosition(viewHolder);
                    return onItemLongClickListener.onItemLongClick(parent, v, mDatas.get(position - headSize), position - headSize);
                }
                return false;
            }
        });


    }

    public void setHeadSize(int headSize) {
        this.headSize = headSize;
    }

    public int getHeadSize() {
        return headSize;
    }

    public T getItemData(int position) {
        if (mDatas != null && mDatas.size() <= position) {
            return null;
        }
        return mDatas.get(position);
    }

    public List<T> getDatas() {
        return mDatas;
    }
}
