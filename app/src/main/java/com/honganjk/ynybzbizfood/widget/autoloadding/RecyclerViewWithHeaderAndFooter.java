package com.honganjk.ynybzbizfood.widget.autoloadding;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/22.
 */
public class RecyclerViewWithHeaderAndFooter extends RecyclerView {

    private static final int TYPE_HEADER = -4;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_REFRESH_FOOTER = -3;
    private static final int TYPE_FOOTER = -2;
    protected ArrayList<View> mHeaderViews = new ArrayList<>();
    //最后一个为加载的布局
    protected ArrayList<View> mFootViews = new ArrayList<>();
    private Adapter mAdapter;
    private Adapter mWrapAdapter;


    public RecyclerViewWithHeaderAndFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewWithHeaderAndFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public RecyclerViewWithHeaderAndFooter(Context context) {
        this(context, null);
    }


    public int getDataSize() {
        return mAdapter.getItemCount();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mWrapAdapter = new WrapAdapter(mHeaderViews, mFootViews, adapter);
        super.setAdapter(mWrapAdapter);
        mAdapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
        if (adapter instanceof CommonAdapter) {
            ((CommonAdapter) adapter).setHeadSize(getHeaderViewSize());
        }
    }

    public void addHeaderView(View view) {
        mHeaderViews.add(view);
        if (mAdapter instanceof CommonAdapter) {
            ((CommonAdapter) mAdapter).setHeadSize(getHeaderViewSize());
        }
    }

    public void removeHeaderView(View view) {
        mHeaderViews.remove(view);
        if (mAdapter instanceof CommonAdapter) {
            ((CommonAdapter) mAdapter).setHeadSize(getHeaderViewSize());
        }
    }

    public void scrollToPositionWithOffset(int postion) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(postion, 0);
        } else if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) getLayoutManager()).scrollToPositionWithOffset(postion, 0);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) getLayoutManager()).scrollToPositionWithOffset(postion, 0);
        }
    }

    public void addFootView(final View view) {
        mFootViews.add(view);
    }

    public void addFootView(int index, final View view) {
        mFootViews.add(index, view);
    }

    public int getHeaderViewSize() {
        return mHeaderViews.size();
    }

    public int getFootViewSize() {
        return mFootViews.size();
    }

    public int getFootViewHeight() {
        int height = 0;
        for (View v : mFootViews) {
            height += v.getHeight();
        }
        return height;
    }

    public int getHeaderViewHeight() {
        int height = 0;
        for (View v : mHeaderViews) {
            height += v.getHeight();
        }
        return height;
    }


    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private final AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };


    private class WrapAdapter extends Adapter<ViewHolder> {

        private Adapter adapter;

        private ArrayList<View> mHeaderViews;

        private ArrayList<View> mFootViews;

        private int headerPosition = 0;
        private int footerPosition = 0;

        public WrapAdapter(ArrayList<View> headerViews, ArrayList<View> footViews, Adapter adapter) {
            this.adapter = adapter;
            this.mHeaderViews = headerViews;
            this.mFootViews = footViews;
        }


        public boolean isHeader(int position) {
            return position >= 0 && position < mHeaderViews.size();
        }

        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - mFootViews.size();
        }

        public boolean isFooterLoad(int position) {
            return mFootViews.size() > 0 && position == getItemCount() - 1 && mFootViews.get(mFootViews.size() - 1) instanceof FooterView;
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFootViews.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            adapter.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {

                        return (isHeader(position) || isFooter(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
                gridManager.setSpanCount(gridManager.getSpanCount());
            }
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            adapter.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_REFRESH_FOOTER) {
                return new SimpleViewHolder(mFootViews.get(mFootViews.size() - 1));
            } else if (viewType == TYPE_HEADER) {
                if (headerPosition >= mHeaderViews.size()) {
                    headerPosition = 0;
                }

                return new SimpleViewHolder(mHeaderViews.get(headerPosition++));
            } else if (viewType == TYPE_FOOTER) {
                if (footerPosition >= mFootViews.size()) {
                    footerPosition = 0;
                }
                return new SimpleViewHolder(mFootViews.get(footerPosition++));
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isFooterLoad(position)) {
                return;
            }
            if (isHeader(position)) {
                return;
            }
            if (isFooter(position)) {
                return;
            }
            int adjPosition = position - getHeadersCount();
            if (adapter != null) {
                if (adjPosition < adapter.getItemCount()) {
                    adapter.onBindViewHolder(holder, adjPosition);
                    return;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                return getHeadersCount() + getFootersCount() + adapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isFooterLoad(position)) {
                return TYPE_REFRESH_FOOTER;
            }
            if (isHeader(position)) {
                return TYPE_HEADER;
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int adjPosition = position - getHeadersCount();
            if (adapter != null) {
                int adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemViewType(adjPosition);
                }
            }
            return TYPE_NORMAL;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeadersCount()) {
                int adjPosition = position - getHeadersCount();
                int adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.registerAdapterDataObserver(observer);
            }
        }

        private class SimpleViewHolder extends ViewHolder {
            View view;

            public SimpleViewHolder(View itemView) {
                super(itemView);
                this.view = itemView;
            }
        }
    }
}
