package com.honganjk.ynybzbizfood.view.other.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.HttpRequest;
import com.honganjk.ynybzbizfood.mode.enumeration.HeadType;
import com.honganjk.ynybzbizfood.mode.httpresponse.BaseHttpResponse;
import com.honganjk.ynybzbizfood.mode.third.BaiDuLocationUtils;
import com.honganjk.ynybzbizfood.mode.third.PingPPUtils;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.http.httphead.HttpAddTicketHead;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpCallBack;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpRequestParam;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.common.activity.SearchActivity;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.honganjk.ynybzbizfood.R.id.recyclerView;

/**
 * 说明:测试类
 * 360621904@qq.com 杨阳 2017/3/4  15:01
 */
public class TestActivity extends BaseActivity {
    ArrayList<String> picturePath = new ArrayList<>();
    ArrayList<TestData> names = new ArrayList<>();
    @BindView(recyclerView)
    RecyclerView rv;

    public static void startUI(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_test;
    }

    @Override
    public void parseIntent(Intent intent) {
    }

    @Override
    public void initView() {
        names.add(new TestData(0, "添加登录头部"));
        names.add(new TestData(1, "添加未登录头部"));
        names.add(new TestData(2, "选择与上传图片"));
        names.add(new TestData(3, "支付的测试"));
        names.add(new TestData(4, "定位的测试"));
        names.add(new TestData(5, "跳转到搜索页"));

//        names.add(new TestData(5, "wwww"));
        DeviceUtil.getInstance(this).getCallState();
        DeviceUtil.getInstance(this).getDeviceInfo();
        DeviceUtil.getInstance(this).getImei();
        DeviceUtil.getInstance(this).getmDeviceID();
        DeviceUtil.getInstance(this).getNetType();
        DeviceUtil.getInstance(this).getNetwrokIso();
        DeviceUtil.getInstance(this).getPhoneSettings();
        DeviceUtil.getInstance(this).getPhoneType();
        DeviceUtil.getInstance(this).getSIM();
        DeviceUtil.getInstance(this).getSimOpertorName();
        DeviceUtil.getInstance(this).getSimState();
        DeviceUtil.getInstance(this).getUA();
        DeviceUtil.getInstance(this).getNetType();
        DeviceUtil.getInstance(this).getImei();




        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).sizeResId(R.dimen.dp_1).colorResId(R.color.white).build());
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object data, int position) {
                switch (position) {
                    //添加登录头部
                    case 0:
                        textHttp0();
                        break;
                    //添加未登录头部
                    case 1:
                        textHttp1();
                        break;
                    //选择与上传图片
                    case 2:
                        textHttp2();
                        break;
                    //支付的测试
                    case 3:
                        textHttp3();
                        break;
                    //定位的测试
                    case 4:
                        textHttp4();
                        break;
                    //跳转到搜索页
                    case 5:
                        SearchActivity.startUI(TestActivity.this, REQUEST_CODE + 1);
                        break;
                }
            }
        });
    }

    CommonAdapter adapter = new CommonAdapter<TestData>(TestActivity.this, R.layout.item_test, names) {
        @Override
        public void convert(ViewHolder holder, TestData o) {
            holder.setText(R.id.number, o.index);
            holder.setText(R.id.describe, o.name);
        }
    };

    /**
     * 定位的测试
     */
    private void textHttp4() {
        BaiDuLocationUtils.getInstance().start();
        BaiDuLocationUtils.getInstance().setOnClickCallback(new BaiDuLocationUtils.locationCallback() {
            @Override
            public void onClick(BaiDuLocationUtils.MyLocation data, BDLocation location) {
                LogUtils.e(data.getLatitude() + "--" + data.getLongitude());
            }
        });
    }

    /**
     * 支付测试
     * <p>
     * pay	必选,int	支付渠道：1-支付宝；2-微信；3-银联
     * real	必选，double	充值金额，真正的支付金额
     * obtain	可选，double	充值总共可以获得的金额，为优惠活动预留，可以不填，表示无活动，充值多少即多少
     */
    private void textHttp3() {
        HttpCallBack.Builder buider = new HttpCallBack.Builder()
                .setHttpHead(HeadType.LOGIN_HEAD)//添加头部类型，如没有头部则不用写
                .setShowLoadding(true);
        HttpCallBack httpCallBack = new HttpCallBack<BaseHttpResponse>(buider) {
            @Override
            public void onSuccess(BaseHttpResponse result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    try {
                        PingPPUtils.createPayment(TestActivity.this, result.getJSONObject().getJSONObject("data").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        param.addParam("pay", 3);
        param.addParam("real", 0.01);
        HttpRequest.executePost(httpCallBack, "/pay/getRecharge.action", param);
    }

    /**
     * 图片上传测试
     */
    private void textHttp2() {
        if (picturePath.size()>0) {
            PhotoActivity.startUI(mActivity, picturePath, 0);
            return;
        }

        MultiImageSelector.create()
                .showCamera(true)
                .multi()
                .count(12)
                .origin(picturePath)
                .start(this, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 获取搜索的地址与位置信息
         * address//地址
         * latitude//经度
         * longitude//纬度
         */
        if (requestCode == REQUEST_CODE + 1 && resultCode == RESULT_OK) {
            String address = data.getStringExtra("address");
            String latitude = data.getStringExtra("latitude");
            String longitude = data.getStringExtra("longitude");
        }


        //支付成功的判断
        PingPPUtils.judgePaymentStatus(this, requestCode, resultCode, data);

        //过程图片路径
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            picturePath.clear();
            picturePath.addAll(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));
            GlideUtils.show((ImageView) findViewById(R.id.image1), picturePath.get(0));
//            new ALBCUtils(this, ALBCPath.USER_ST, picturePath.get(0)) {
//                @Override
//                public void succeed(String url) {
//                    showInforSnackbar("上传成功");
//                }
//            };
        }
    }

    /**
     * 添加未登录头部
     * 未登录获取商户信息
     */
    private void textHttp1() {
        new HttpAddTicketHead() {
            @Override
            public void succeed(boolean isSucceed) {
                HttpCallBack.Builder buider = new HttpCallBack.Builder()
                        .setHttpHead(HeadType.UNREGISTERED_HEAD)//添加头部类型，如没有头部则不用写
                        .setShowLoadding(true);
                HttpCallBack httpCallBack = new HttpCallBack<BaseHttpResponse>(buider) {
                    @Override
                    public void onSuccess(BaseHttpResponse result) {
                        super.onSuccess(result);
                        if (result.isSucceed()) {
                            return;
                        }
                    }
                };
                HttpRequestParam param = new HttpRequestParam();
                HttpRequest.executePost(httpCallBack, "/bz/promotions.action", param);
            }
        };
    }

    /**
     * 添加登录头部
     * 登录获取帐户余额
     */
    public void textHttp0() {
        HttpCallBack.Builder buider = new HttpCallBack.Builder()
                .setHttpHead(HeadType.LOGIN_HEAD)//添加头部类型，如没有头部则不用写
                .setShowLoadding(true);
        HttpCallBack httpCallBack = new HttpCallBack<BaseHttpResponse>(buider) {
            @Override
            public void onSuccess(BaseHttpResponse result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    return;
                }
            }
        };
        HttpRequestParam param = new HttpRequestParam();
        HttpRequest.executePost(httpCallBack, "/user/getOverage.action", param);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaiDuLocationUtils.getInstance().stop();
    }

    class TestData {
        public int index;
        public String name;

        public TestData(int index, String name) {
            this.index = index;
            this.name = name;
        }
    }
}
