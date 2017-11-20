package com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemLongClickListener;

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
    protected List<List<T>> mdatas;



    protected T mdata;
    OnItemClickListener onItemClickListener;
    OnItemLongClickListener onItemLongClickListener;
    int headSize = 0;

    private ViewHolder viewHolder;


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
    //多层嵌套使用的
    int num;
    public CommonAdapter(Context context,int layoutId,List<List<T>> datas,int number){
        mContext = context;
        mLayoutId = layoutId;
        mdatas = datas;
        num=number;
        setHasStableIds(true);
    }


    public int positions;

    public int getmLayoutId() {
        return mLayoutId;
    }

    @Override
    public long getItemId(int position) {
        positions=position;
        if(mDatas!=null){
            return mDatas.get(position).hashCode();
        }else if(mdatas!=null){
            return mdatas.get(position).hashCode();
        }
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if(num==1){
            //填充
             viewHolder = ViewHolder.get(mContext, null, null, mLayoutId, -1);
        }else {
            viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        }
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
        if(mDatas!=null){
            convert(holder, mDatas.get(position));
        }else if(mdatas!=null){
            convert(holder, mdatas.get(position));
        }

    }

    public abstract void convert(ViewHolder holder, T t);

    public abstract void convert(ViewHolder holder,List<T> t);

    @Override
    public int getItemCount() {
        return mDatas == null ? mdatas.size() : mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
                    if(mDatas!=null){
                        onItemClickListener.onItemClick(parent, v, mDatas.get(position - headSize), position - headSize);
                    }else if(mdatas!=null){
                        onItemClickListener.onItemClick(parent, v, mdatas.get(position - headSize), position - headSize);
                    }

                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    int position = getPosition(viewHolder);
                    if(mDatas!=null){
                        return onItemLongClickListener.onItemLongClick(parent, v, mDatas.get(position - headSize), position - headSize);
                    }else if(mdatas!=null){
                        return onItemLongClickListener.onItemLongClick(parent, v, mdatas.get(position - headSize), position - headSize);
                    }

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
        if (mDatas != null && mDatas.size() <= position&&mdatas!=null&& mdatas.size()<=position) {
                return null;
        }else if(mDatas!=null){
            return mDatas.get(position);
        }else if(mdatas!=null){
            for (int i = 0; i < mdatas.get(position).size(); i++) {
                return mdatas.get(position).get(i);
            }
        }
        return null;
    }

    public List<T> getDatas() {
        return mDatas;
    }
    public List<List<T>> getmdatas(){
        return mdatas;
    }
}
