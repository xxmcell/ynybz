package com.honganjk.ynybzbizfood.utils.ui;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpManager;
import com.honganjk.ynybzbizfood.utils.other.EncodingUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2016/1/20.
 */
public class WebViewUtils {

    /*
    * 获取到服务器数据后开始跟新UI
    */
    public static void loadDataWithBaseURL(WebView webView, String content) {
        if (content == null) {
            content = "";
        }
        content = EncodingUtils.decodeUnicode(content);
        // 配置webview数据源
        StringBuilder data = new StringBuilder();
        data.append("<style type=\"text/css\">img{WIDTH:100% !important;HEIGHT:auto !important;}");
        data.append(getFont());
        data.append("</style><base href=\"");
        data.append(HttpManager.BASE_HOST);
        data.append("\"/><body class=\"Likun\" style=\"padding-left:0%;padding-right:0%;\">");
        data.append(content);
        data.append("</body>");
        // 设置webview 加载数据
        webView.loadDataWithBaseURL(null, data.toString(), "text/html",
                "utf-8", null);
    }

    public static String getFont() {
        StringBuilder builder = new StringBuilder();
        // <body class="MsoNormal"> </body>
        builder.append(".Likun { font-family: fzlt;}");
        builder.append("@font-face {font-family:fzlt;");
        builder.append("src:url(file:///android_asset/fzlt.ttf);}");
        return builder.toString();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void configWebview(WebView webView) {
        // 设置文本编码
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        // 可以缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启 Application Caches 功能
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);// 默认缩放模式
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
    }

    public static void configWebview2(WebView web) {
        //支持javascript
        web.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        web.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        web.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        web.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.getSettings().setLoadWithOverviewMode(true);
    }

    /*
   * html 获取纯文本
   */
    public static String Html2Text(String inputString) {
        if (inputString == null || inputString.trim().equals("")) {
            return "";
        }

        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return textStr;// 返回文本字符串
    }

    /*
    * 获得超链接
    */
    public static String getHrefInnerHtml(String href) {
        if (StringUtils.isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern
                .compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }
}
