package com.honganjk.ynybzbizfood.widget.dialog;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.base.LoopViewData;
import com.honganjk.ynybzbizfood.utils.http.GsonHelper;
import com.honganjk.ynybzbizfood.utils.ui.ScreenInfoUtils;
import com.honganjk.ynybzbizfood.widget.wheelview3d.LoopListener;
import com.honganjk.ynybzbizfood.widget.wheelview3d.LoopView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.utils.http.GsonHelper.getGson;

/**
 * 说明：城市选择的对话框
 * 作者　　: 杨阳
 * 创建时间: 2016/11/21 10:41
 */

public class DialogCitySelect extends Dialog {
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.selectName)
    TextView selectName;
    @BindView(R.id.affirm)
    TextView affirm;
    @BindView(R.id.province)
    LoopView province;
    @BindView(R.id.city)
    LoopView city;
    @BindView(R.id.county)
    LoopView county;
    //记录选择的名字与id
    PCCData pccData = new PCCData();
    Context context;
    CallBack callBack;
    //保存从资源的解析出来的json对象
    JSONArray jsonArrayParent;
    ArrayList<LoopViewData> provinces = new ArrayList<>();
    ArrayList<LoopViewData> citys = new ArrayList<>();
    ArrayList<LoopViewData> countys = new ArrayList<>();

    public DialogCitySelect(Context context) {
        this(context, R.style.Dialog_select);
        this.context = context;
    }

    public DialogCitySelect(Context context, int themeResId) {
        super(context, themeResId);

    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_dialog_city_select);
        ButterKnife.bind(this, getWindow().getDecorView().findViewById(R.id.dialog_root));

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        ScreenInfoUtils screen = new ScreenInfoUtils();
        lp.width = (screen.getWidth()); //设置宽度
        lp.height = (ActionBar.LayoutParams.WRAP_CONTENT); //设置宽度
        getWindow().setAttributes(lp);
        getWindow().getAttributes().gravity = Gravity.BOTTOM;

        init();
        addListener();
        initJsonData();

    }

    private void init() {
        province.setNotLoop();
        city.setNotLoop();
        county.setNotLoop();
        province.setTextSize(18f);
        city.setTextSize(18f);
        county.setTextSize(18f);
    }

    private void addListener() {
        province.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item, LoopViewData data) {
                addData(data, 1);
                addCity(data.getId());
            }
        });

        city.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item, LoopViewData data) {
                addData(data, 2);
                addCity(pccData.provinceId, pccData.cityId);
            }
        });

        county.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item, LoopViewData data) {
                addData(data, 3);
            }
        });
    }

    @OnClick({R.id.cancel, R.id.affirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.affirm:
                if (callBack != null) {
                    callBack.setData(pccData);
                }
                dismiss();
                break;
        }
    }

    /**
     * @param data
     * @param type 1省，2市，3县
     */
    private void addData(LoopViewData data, int type) {
        if (type == 1) {
            pccData.province = data.getName();
            pccData.provinceId = data.getId();

        } else if (type == 2) {
            pccData.city = data.getName();
            pccData.cityId = data.getId();
        } else if (type == 3) {
            pccData.county = data.getName();
            pccData.countyId = data.getId();
        }
        selectName.setText(pccData.province + "-" + pccData.city + "-" + pccData.county);
    }

    /**
     * 从文件中读取地址数据
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/" + "address.json");
            int len = -1;
            byte[] buf = new byte[is.available()];

            is.read(buf);
            sb.append(new String(buf, "utf-8"));
//            while ((len = is.read(buf)) != -1) {
//                sb.append(new String(buf, 0, len, "utf-8"));
//            }
            is.close();
            JSONObject mJsonObj = new JSONObject(sb.toString());
            jsonArrayParent = mJsonObj.getJSONArray("Province");

            //解析省
            provinces = getGson().fromJson(jsonArrayParent.toString(), new TypeToken<List<LoopViewData>>() {
            }.getType());

            province.setArrayList(provinces);

            addCity(provinces.get(0).getId());

            addData(provinces.get(0), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解析城市
    private void addCity(int id) {
        try {
            for (int i = 0; i < jsonArrayParent.length(); i++) {
                JSONObject jsonObj = jsonArrayParent.getJSONObject(i);
                if (id == jsonObj.getInt("id") && jsonObj.has("City")) {
                    if (jsonObj.get("City") instanceof JSONArray) {
                        JSONArray ja = jsonObj.getJSONArray("City");

                        citys = GsonHelper.getGson().fromJson(ja.toString(), new TypeToken<List<LoopViewData>>() {
                        }.getType());

                        //解析区
                        for (int j = 0; j < ja.length(); j++) {
                            JSONObject jb = ja.getJSONObject(j);
                            if (jb.has("District") && jb.get("District") instanceof JSONArray) {
                                countys = GsonHelper.getGson().fromJson(jb.getJSONArray("District").toString(), new TypeToken<List<LoopViewData>>() {
                                }.getType());
                            }
                        }

                    } else if (jsonObj.get("City") instanceof JSONObject) {
                        citys.clear();
                        citys.add(GsonHelper.getGson().fromJson(jsonObj.getJSONObject("City").toString(), LoopViewData.class));
                        JSONObject cityJs = jsonObj.getJSONObject("City");
                        if (cityJs.has("District") && cityJs.get("District") instanceof JSONArray) {
                            JSONArray ja = cityJs.getJSONArray("District");
                            //解析区
                            for (int j = 0; j < ja.length(); j++) {
                                countys = GsonHelper.getGson().fromJson(ja.toString(), new TypeToken<List<LoopViewData>>() {
                                }.getType());
                            }
                        } else {
                            countys.clear();
                            countys.add(GsonHelper.getGson().fromJson(jsonObj.getJSONObject("City").toString(), LoopViewData.class));
                        }
                    }

                    city.setArrayList(citys);
                    county.setArrayList(countys);
                    city.setInitPosition(0);
                    county.setInitPosition(0);

                    addData(citys.get(0), 2);
                    addData(countys.get(0), 3);
                    addCity(pccData.provinceId, pccData.cityId);
                    return;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //解析县
    private void addCity(int provinceId, int cityId) {
        try {
            for (int i = 0; i < jsonArrayParent.length(); i++) {
                JSONObject jsonObj = jsonArrayParent.getJSONObject(i);
                if (provinceId == jsonObj.getInt("id") && jsonObj.has("City")) {
                    if (jsonObj.get("City") instanceof JSONArray) {
                        JSONArray ja = jsonObj.getJSONArray("City");


                        citys = GsonHelper.getGson().fromJson(ja.toString(), new TypeToken<List<LoopViewData>>() {
                        }.getType());

                        //解析区
                        for (int j = 0; j < ja.length(); j++) {

                            JSONObject countyJS = ja.getJSONObject(j);

                            if (cityId == countyJS.getInt("id") && countyJS.has("District") && countyJS.get("District") instanceof JSONArray) {
                                countys = GsonHelper.getGson().fromJson(countyJS.getJSONArray("District").toString(), new TypeToken<List<LoopViewData>>() {
                                }.getType());
                            }


                        }
                    } else if (jsonObj.get("City") instanceof JSONObject) {

                        citys.clear();
                        citys.add(GsonHelper.getGson().fromJson(jsonObj.getJSONObject("City").toString(), LoopViewData.class));
                        JSONObject cityJs = jsonObj.getJSONObject("City");
                        if (cityJs.has("District") && cityJs.get("District") instanceof JSONArray) {
                            JSONArray ja = cityJs.getJSONArray("District");
                            //解析区
                            for (int j = 0; j < ja.length(); j++) {
                                countys = GsonHelper.getGson().fromJson(ja.toString(), new TypeToken<List<LoopViewData>>() {
                                }.getType());
                            }
                        } else {
                            countys.clear();
                            countys.add(GsonHelper.getGson().fromJson(jsonObj.getJSONObject("City").toString(), LoopViewData.class));
                        }


                    }

                    county.setArrayList(countys);
                    county.setInitPosition(0);
                    addData(countys.get(0), 3);
                    return;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择的内容
     */
    public class PCCData {
        public String province;
        public String city;
        public String county;
        public int provinceId;
        public int cityId;
        public int countyId;
    }

    public interface CallBack {
        void setData(PCCData data);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;

    }
}
