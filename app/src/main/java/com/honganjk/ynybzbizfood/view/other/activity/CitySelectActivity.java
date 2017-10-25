package com.honganjk.ynybzbizfood.view.other.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.widget.appbarlayout.SupperToolBar;
import com.honganjk.ynybzbizfood.widget.city_select.CharBar;
import com.honganjk.ynybzbizfood.widget.city_select.CharComparator;
import com.honganjk.ynybzbizfood.widget.city_select.CityData;
import com.honganjk.ynybzbizfood.widget.city_select.VisitHttpType;
import com.honganjk.ynybzbizfood.widget.sticky_recycler.StickyHeadersAdapter;
import com.honganjk.ynybzbizfood.widget.sticky_recycler.StickyHeadersBuilder;
import com.honganjk.ynybzbizfood.widget.sticky_recycler.StickyHeadersItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;


/**
 * 城市选择
 * Created by Administrator on 2016/8/27.
 * <p>
 * 返回的数据
 * intent.putExtra("cityId", String.valueOf(listDatas.get(position).getCityID()));
 * intent.putExtra("cityName", listDatas.get(position).getCityName());
 * setResult(RESULT_OK, intent);
 * <p>
 * 使用方法：
 * 选择省：  CitySelectActivity.startUI(this, REQUEST_CODE, new VisitHttpType(VisitHttpType.ActionType.PROVINCE));
 * <p>
 * 选择市：  CitySelectActivity.startUI(this, REQUEST_CODE, new VisitHttpType(VisitHttpType.ActionType.CITY, "省名");
 * <p>
 * 选择县：CitySelectActivity.startUI(this, REQUEST_CODE, new VisitHttpType(VisitHttpType.ActionType.CITY, "省名",  "市名"));
 *
 *
 */

public class CitySelectActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.charBar)
    CharBar charBar;
    @BindView(R.id.hint)
    TextView hintTextView;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.toolbar)
    SupperToolBar toolbar;

    private LinearLayoutManager linearLayoutManager;
    private StickyHeadersItemDecoration stickyHeaderItem;
    private CarBrandHeaderAdapter headerAdapter;
    private List<CityData> listDatas = new ArrayList<>();
    private List<CityData> listDataTotal = new ArrayList<>();

    @BindView(R.id.switchRoot)
    RelativeLayout switchRoot;

    VisitHttpType data;

    @Override
    public int getContentView() {
        return R.layout.activity_city_select;
    }


    /**
     * 获取省,市，县
     *
     * @param activity
     * @param requestCode 请求与返回的code
     * @param data        获取数据的类型
     */
    public static void startUI(Activity activity, int requestCode, VisitHttpType data) {
        Intent intent = new Intent(activity, CitySelectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startUI(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), CitySelectActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {
        addData();
        setAdapter();
        recyclerView.setFilterTouchesWhenObscured(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    listDatas.clear();
                    for (int i = 0; i < listDataTotal.size(); i++) {
                        if (listDataTotal.get(i).getCityName().contains(newText)) {
                            listDatas.add(listDataTotal.get(i));
                        }
                    }
                } else {
                    listDatas.clear();
                    listDatas.addAll(listDataTotal);
                }
                charBar.addAllChar(listDatas);
                commonAdapter.notifyDataSetChanged();
                return true;
            }
        });

        toolbar.setBack(this);
    }


    /**
     * 添加城市数据
     */
    private void addData() {
        data = (VisitHttpType) getIntent().getSerializableExtra("data");
        getHttpProvinceData();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 获取 省 数据
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getHttpProvinceData() {
        HttpCallBack.Builder buider = new HttpCallBack.Builder(this).setShowLoadding(true);

        final HttpCallBack httpCallBack = new HttpCallBack<ResponseBody>(buider) {

            @Override
            public void onSuccess(ResponseBody result) {
                super.onSuccess(result);
                List<CityData> list = new ArrayList<>();
                try {
                    String str = result.string();
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        CityData d = null;
                        //省
                        if (data.getType().getValue().equals(VisitHttpType.ActionType.PROVINCE.getValue())) {
                            d = new CityData(jsonArray.getJSONObject(i).getString("provinceName"));
                            //市
                        } else if (data.getType().getValue().equals(VisitHttpType.ActionType.CITY.getValue())) {
                            d = new CityData(jsonArray.getJSONObject(i).getString("cityName"));
                            //县
                        } else if (data.getType().getValue().equals(VisitHttpType.ActionType.COUNTY.getValue())) {
                            d = new CityData(jsonArray.getJSONObject(i).getString("areaName"));
                        }

                        d.setAllInitial();
                        list.add(d);
                    }

                    Collections.sort(list, new CharComparator());
                    listDatas.addAll(list);
                    listDataTotal.addAll(listDatas);
                    commonAdapter.notifyDataSetChanged();
                    charBar.addAllChar(listDatas);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        HttpRequestParam param = new HttpRequestParam();
        param.addAction(data.getType().getValue());

        //根据省获得城市
        if (data.getType().getValue().equals(VisitHttpType.ActionType.CITY.getValue())) {
            param.addParam("province", data.getProvinceName());
        }
        //根据省市获得区县
        if (data.getType().getValue().equals(VisitHttpType.ActionType.COUNTY.getValue())) {
            param.addParam("province", data.getProvinceName());
            param.addParam("city", data.getCityName());
        }
        HttpRequest.uploadFiles(httpCallBack, param);
    }


    private void setAdapter() {
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        stickyHeaderItem = new StickyHeadersBuilder().setAdapter(commonAdapter)
                .setStickyHeadersAdapter(headerAdapter = new CarBrandHeaderAdapter(listDatas), true)
                .setRecyclerView(recyclerView).build();

        recyclerView.addItemDecoration(stickyHeaderItem);

        recyclerView.setAdapter(commonAdapter);
        commonAdapter.notifyDataSetChanged();
        charBar.setHintView(hintTextView);
        charBar.SetOnTouchLetterChangedListener(new CharBar.OnTouchLetterChangedListener() {
            @Override
            public void onTouchLetter(String s) {
                stickyHeaderItem.setSelectPostion(s, linearLayoutManager);
            }
        });
        charBar.addAllChar(listDatas);


        commonAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object data, int position) {
                Intent intent = new Intent();
                intent.putExtra("cityId", String.valueOf(listDatas.get(position).getCityID()));
                intent.putExtra("cityName", listDatas.get(position).getCityName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

    @Override
    public void parseIntent(Intent intent) {

    }


    /**
     * 返回选择数据
     *
     * @param cityName
     */
    private void returnData(String cityName) {

        for (int i = 0; i < listDatas.size(); i++) {
            if (listDatas.get(i).getCityName().contains(cityName)) {
                Intent intent = new Intent();
                intent.putExtra("cityId", String.valueOf(listDatas.get(i).getCityID()));
                intent.putExtra("cityName", listDatas.get(i).getCityName());
                setResult(RESULT_OK, intent);
                finish();
            }
        }


    }

    /**
     * 内容的adapter
     */
    private CommonAdapter<CityData> commonAdapter = new CommonAdapter<CityData>(this, R.layout.item_city_select_content, listDatas) {
        @Override
        public void convert(ViewHolder holder, CityData cityData) {
            holder.setText(R.id.brand, cityData.getCityName());
        }

        @Override
        public void convert(ViewHolder holder, List<CityData> t) {

        }

        @Override
        public long getItemId(int position) {
            return mDatas.get(position).hashCode();
        }
    };

    @OnClick({R.id.activity_city_selectBJ, R.id.activity_city_selectSH, R.id.activity_city_selectGZ, R.id.activity_city_selectShenZ, R.id.activity_city_selectTJ, R.id.activity_city_selectZQ, R.id.activity_city_selectHZ, R.id.activity_city_selectSZ, R.id.activity_city_selectZH, R.id.activity_city_selectXA, R.id.activity_city_selectHEB, R.id.activity_city_selectGM})
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                returnData(((TextView) findViewById(view.getId())).getText().toString());
        }
    }


    /**
     * 左边adapter
     */
    public static class CarBrandHeaderAdapter implements StickyHeadersAdapter<CarBrandHeaderAdapter.ViewHolder> {

        private List<CityData> items;

        public CarBrandHeaderAdapter(List<CityData> items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_select_header, parent, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder headerViewHolder, int position) {
            headerViewHolder.title.setText(items.get(position).getInitial());
        }

        @Override
        public long getHeaderId(int position) {
            return items.get(position).getInitial().hashCode();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.zhimu);
            }
        }
    }
}
