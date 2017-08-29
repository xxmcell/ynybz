package com.honganjk.ynybzbizfood.view.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.third.BaiDuPOIUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 说明:搜索的页面
 * 360621904@qq.com 杨阳 2017/3/9  9:49
 * 使用方法 在onActivityResult()方法中获取，字段为：
 * address//地址
 * latitude//经度
 * longitude//纬度
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<SuggestionResult.SuggestionInfo> mDatas = new ArrayList<>();

    /**
     * @param context
     * @param requestCode 请求的code
     */
    public static void startUI(Activity context, int requestCode) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getContentView() {
        return R.layout.base_activity_search;
    }

    @Override
    public void parseIntent(Intent intent) {

    }

    @Override
    public void initView() {
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                new BaiDuPOIUtils(newText) {
                    @Override
                    public void succeed(List<SuggestionResult.SuggestionInfo> datas) {
                        mDatas.clear();
                        mDatas.addAll(datas);
                        adapter.notifyDataSetChanged();
                    }
                };
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener<SuggestionResult.SuggestionInfo>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, SuggestionResult.SuggestionInfo data, int position) {
                Intent intent = new Intent();
                intent.putExtra("address", data.city + data.district + data.key);
                intent.putExtra("latitude",data.pt.latitude);
                intent.putExtra("longitude",data.pt.longitude);
                SearchActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

    CommonAdapter adapter = new CommonAdapter<SuggestionResult.SuggestionInfo>(this, R.layout.base_item_search, mDatas) {
        @Override
        public void convert(ViewHolder holder, SuggestionResult.SuggestionInfo datas) {
            holder.setText(R.id.title, datas.key);
            holder.setText(R.id.details, datas.city + "-" + datas.district);
        }
    };

    @OnClick(R.id.cancel)
    public void onClick() {
        finish();
    }
}
