package com.honganjk.ynybzbizfood.widget.linkedscrollview;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.StickyRecyclerHeadersAdapter;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.utils.ui.UiUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.widget.cartlayout.CartBottomLayout;
import com.honganjk.ynybzbizfood.widget.linkedscrollview.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LinkedScrollView extends RelativeLayout implements CategoryAdapter.OnItemClickListener {

    @BindView(R.id.recyclerview_category)
    RecyclerView recyclerviewCategory;
    @BindView(R.id.recyclerview_teams)
    RecyclerView recyclerviewTeams;

    private List<FoodData.DishsBeanX> categoryList = new ArrayList<>();
    private List<FoodData.DishsBeanX.DishsBean> teams = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private CategoryAdapter categoryAdapter;
    private int oldSelectedPosition = 0;

    private CartBottomLayout.cartBottomLayoutListener listener;

    public void setCartListener(CartBottomLayout.cartBottomLayoutListener listener) {
        this.listener = listener;
    }

    public MenuAdapter getRecycleViewAdapter() {
        return menuAdapter;
    }

    public void clearDatas() {
        for (FoodData.DishsBeanX.DishsBean item : this.teams) {
            item.setNum(0);
        }
        menuAdapter.notifyDataSetChanged();
    }

    public void update(List<FoodData.DishsBeanX.DishsBean> teams) {
        for (FoodData.DishsBeanX.DishsBean item : this.teams) {
            if (teams.contains(item)) {
                item.setNum(teams.get(teams.indexOf(item)).getNum());
            } else {
                item.setNum(0);
            }
        }
        menuAdapter.notifyDataSetChanged();
    }

    public LinkedScrollView(Context context) {
        super(context);
        init();
    }

    public LinkedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinkedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        UiUtils.getInflaterView(getContext(), R.layout.widget_linkscrollview, this, true);
        ButterKnife.bind(this, this);
        initViews();
    }

    private LinearLayoutManager mTeamsLayoutManager;
    private LinearLayoutManager mCategoryLayoutManager;

    private void initViews() {
        mTeamsLayoutManager = new LinearLayoutManager(getContext());
        mCategoryLayoutManager = new LinearLayoutManager(getContext());
        recyclerviewCategory.setLayoutManager(mCategoryLayoutManager);
        recyclerviewTeams.setLayoutManager(mTeamsLayoutManager);
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        categoryAdapter.setOnItemClickListener(this);
        recyclerviewCategory.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.clr_divide).sizeResId(R.dimen.dp_1).build());
        recyclerviewCategory.setAdapter(categoryAdapter);
        menuAdapter = new MenuAdapter(getContext(), teams);
        recyclerviewTeams.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.clr_divide).sizeResId(R.dimen.dp_2).build());
        recyclerviewTeams.setAdapter(menuAdapter);

        // Add the sticky headers decoration,给球队添加标题

//        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(teamsAndHeaderAdapter);
//        recyclerviewTeams.addItemDecoration(headersDecor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerviewTeams.setOnScrollChangeListener(new OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    //第一个完全显示的item和最后一个item。
                    int firstVisibleItem = mTeamsLayoutManager.findFirstCompletelyVisibleItemPosition();
                    int lastVisibleItem = mTeamsLayoutManager.findLastVisibleItemPosition();
                    //此判断，避免左侧点击最后一个item无响应
                    if (lastVisibleItem != mTeamsLayoutManager.getItemCount() - 1) {
                        int sort = menuAdapter.getSortType(firstVisibleItem);
                        changeSelected(sort);
                    } else {
                        changeSelected(categoryAdapter.getItemCount() - 1);
                    }
                    if (needMove) {
                        needMove = false;
                        //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                        int n = movePosition - mTeamsLayoutManager.findFirstVisibleItemPosition();
                        if (0 <= n && n < recyclerviewTeams.getChildCount()) {
                            //获取要置顶的项顶部离RecyclerView顶部的距离
                            int top = recyclerviewTeams.getChildAt(n).getTop() - dip2px(getContext(), 28);
                            //最后的移动
                            recyclerviewTeams.scrollBy(0, top);
                        }
                    }
                }
            });
        } else {
            recyclerviewTeams.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //第一个完全显示的item和最后一个item。
                    int firstVisibleItem = mTeamsLayoutManager.findFirstCompletelyVisibleItemPosition();
                    int lastVisibleItem = mTeamsLayoutManager.findLastVisibleItemPosition();
                    //此判断，避免左侧点击最后一个item无响应
                    if (lastVisibleItem != mTeamsLayoutManager.getItemCount() - 1) {
                        int sort = menuAdapter.getSortType(firstVisibleItem);
                        changeSelected(sort);
                    } else {
                        changeSelected(categoryAdapter.getItemCount() - 1);
                    }
                    if (needMove) {
                        needMove = false;
                        //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                        int n = movePosition - mTeamsLayoutManager.findFirstVisibleItemPosition();
                        if (0 <= n && n < recyclerviewTeams.getChildCount()) {
                            //获取要置顶的项顶部离RecyclerView顶部的距离
                            int top = recyclerviewTeams.getChildAt(n).getTop() - dip2px(getContext(), 28);
                            //最后的移动
                            recyclerviewTeams.scrollBy(0, top);
                        }
                    }
                }
            });
        }
    }

    public ArrayList<FoodData.DishsBeanX.DishsBean> getCategoryList(List<FoodData.DishsBeanX> categoryList, int timeType) {
        ArrayList<FoodData.DishsBeanX.DishsBean> teams = new ArrayList<>();

        this.categoryList = categoryList;
        for (int i = 0; i < categoryList.size(); i++) {
            if (teams != null) {
                teams.addAll(categoryList.get(i).getDishs());
                categoryList.get(i).getDishs().get(0).setFirst(categoryList.get(i).getName());
            }
        }
        for (FoodData.DishsBeanX.DishsBean d : teams) {
            d.setTimeType(timeType);
        }
        return teams;
    }

    public void setDatas(List<FoodData.DishsBeanX> categoryList, int timeType) {

        this.categoryList.addAll(categoryList);
        this.teams.addAll(getCategoryList(categoryList, timeType));
        menuAdapter.notifyDataSetChanged();
    }


    public void changeFoodNum(FoodData.DishsBeanX.DishsBean data) {
        int position = teams.indexOf(data);
        teams.remove(position);
        teams.add(position, data);
        menuAdapter.notifyDataSetChanged();
        menuAdapter.notifyItemChanged(position, data);
    }


    private boolean needMove = false;
    private int movePosition;

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mTeamsLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mTeamsLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerviewTeams.scrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerviewTeams.getChildAt(n - firstItem).getTop();
            recyclerviewTeams.scrollBy(0, top - dip2px(getContext(), 28));
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerviewTeams.scrollToPosition(n);
            movePosition = n;
            needMove = true;
        }
    }

    @Override
    public void onItemClick(int position) {
        changeSelected(position);
        moveTogetthisSortFirstItem(position);

    }

    private void moveTogetthisSortFirstItem(int position) {
        movePosition = 0;
        for (int i = 0; i < position; i++) {
            movePosition += categoryList.get(i).getDishs().size();
        }
        moveToPosition(movePosition);
    }

    private void changeSelected(int position) {
        if (position == -1) {
            return;
        }
        categoryList.get(oldSelectedPosition).setSeleted(false);
        categoryList.get(position).setSeleted(true);
        //增加左侧联动
        recyclerviewCategory.scrollToPosition(position);
        oldSelectedPosition = position;
        categoryAdapter.notifyDataSetChanged();


    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public class MenuAdapter extends CommonAdapter<FoodData.DishsBeanX.DishsBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

        public MenuAdapter(Context context, List<FoodData.DishsBeanX.DishsBean> datas) {
            super(context, R.layout.item_food, datas);
        }

        @Override
        public void convert(final ViewHolder holder, final FoodData.DishsBeanX.DishsBean team) {
            if (!TextUtils.isEmpty(team.isFirst())) {
                holder.setVisible(R.id.tv_title, true);
                holder.setText(R.id.tv_title, team.isFirst());
            } else {
                holder.setVisible(R.id.tv_title, false);
            }
            holder.setText(R.id.tv_name, team.getName());
            holder.setText(R.id.tv_sales, String.format(getContext().getString((int) R.string.tv_sales), team.getTotal()));
            holder.setText(R.id.tv_price, String.format(getContext().getString((int) R.string.tv_price), team.getPrice()));
//            holder.setImageBitmap(R.id.imageview, team.getImg());

            Glide.with(getContext()).load(team.getImg()).
                    asBitmap().centerCrop().into(((ImageView) holder.getView(R.id.imageview)));
            holder.setOnClickListener(R.id.iv_add, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    team.setNum(team.getNum() + 1);
                    setNum(team.getNum(), holder);
                    listener.changeFoodNum(team);

                }
            });
            holder.setOnClickListener(R.id.iv_reduce, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    team.setNum(team.getNum() - 1);
                    setNum(team.getNum(), holder);
                    listener.changeFoodNum(team);
                }
            });

            setNum(team.getNum(), holder);


        }

        private void setNum(int num, ViewHolder holder) {
            if (num == 0) {
                holder.setVisible(R.id.iv_reduce, false);
                holder.setVisible(R.id.tv_num, false);
            } else {
                holder.setVisible(R.id.iv_reduce, true);
                holder.setVisible(R.id.tv_num, true);
                holder.setText(R.id.tv_num, num);
            }

        }


        /**
         * 返回值相同会被默认为同一项
         *
         * @param position
         * @return
         */
        @Override
        public long getHeaderId(int position) {
            return getSortType(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_team_list, parent, false);
            return new HeaderViewHolder(view);

        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        //获取当前球队的类型
        public int getSortType(int position) {
            int sort = -1;
            int sum = 0;
            for (int i = 0; i < categoryList.size(); i++) {
                if (position >= sum) {
                    sort++;
                } else {
                    return sort;
                }
                sum += categoryList.get(i).getDishs().size();
            }
            return sort;
        }

    }

}
