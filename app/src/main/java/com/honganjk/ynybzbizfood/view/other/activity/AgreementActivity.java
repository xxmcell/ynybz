package com.honganjk.ynybzbizfood.view.other.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.pressenter.othre.AgreementPresenter;
import com.honganjk.ynybzbizfood.utils.ui.WebViewUtils;
import com.honganjk.ynybzbizfood.view.other.view.IOtherView;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import butterknife.BindView;


/**
 * 作者　　: 杨阳
 * 创建时间: 2016/10/22 16:05
 * 邮箱　　：360621904@qq.com
 * <p>
 * 功能介绍：协议
 */

public class AgreementActivity extends BaseMvpActivity<IOtherView.IAgreement, AgreementPresenter> implements IOtherView.IAgreement {
    String url;
    @BindView(R.id.switchRoot)
    WebView webView;
    public static void startUI(Context context, String url) {
        Intent intent = new Intent(context, AgreementActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @Override
    public int getStatusBarResource() {
        return R.color.main_color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public AgreementPresenter initPressenter() {
        return new AgreementPresenter();
    }


    public void parseIntent(Intent intent) {
        url = intent.getStringExtra("url");

    }

    @Override
    public int getContentView() {
        return R.layout.activity_agreement;
    }

    public void initView() {
        toolbar.setBack(this);
        toolbar.setTitle(getResources().getString(R.string.app_name_simple));
        WebViewUtils.configWebview(webView);
        getHttpData(url);
    }


    @Override
    public void getHttpData(final String url) {
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                loadingAndRetryManager.showContent();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingAndRetryManager.showLoading(new ContextData("页面获取中"));
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.reload();
    }

    @Override
    public void clearData() {

    }


}
