package com.honganjk.ynybzbizfood.widget.empty_layout;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.mode.enumeration.HttpCode;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.view.common.activity.LoginActivity;
import com.honganjk.ynybzbizfood.widget.button.FlatButton;
import com.honganjk.ynybzbizfood.R;

/**
 * 说明: 布局切换管理
 * 2017/2/10-11:40
 */
public class MyOnLoadingAndRetryListener extends OnLoadingAndRetryListener {
    private Context context;
    private OnRetryClickListion clickListion;


    public MyOnLoadingAndRetryListener(Context context, OnRetryClickListion clickListion) {
        this.context = context;
        this.clickListion = clickListion;
    }

    @Override
    public void setRetryEvent(View retryView, final ContextData data) {
        TextView title = (TextView) retryView.findViewById(R.id.title);
        if (title != null) {
            if (data == null || TextUtils.isEmpty(data.getTitle())) {
                title.setText(context.getResources().getString(R.string.http_request_failure));
            } else if (data != null && !TextUtils.isEmpty(data.getTitle())) {
                title.setText(data.getTitle() + (!data.getErrCode().equals("0") ? "" : ("\n(错误码:" + data.getErrCode() + ")")));
            }
        }
        ImageView img = (ImageView) retryView.findViewById(R.id.image);
        if (img != null) {
            if (data == null || data.getResId() <= 0) {
                img.setImageResource(R.mipmap.fail);
            } else if (data != null && data.getResId() > 0) {
                img.setImageResource(data.getResId());
            }
        }

        View butt = retryView.findViewById(R.id.reSet);
        if (butt != null) {
            if (!TextUtils.isEmpty(data.getButtonText())) {
                ((FlatButton) butt).setText(data.getButtonText());
            }
            butt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListion != null) {
                        //如果是需要登录接口，跳转到登录页面，这种情况一般出现在帐号在另一个设备中登录，token失效所致
                        if (data.getErrCode().equals(HttpCode.FAIL_C.getKey()) && !UserInfo.userData.isLogin()) {
                            LoginActivity.startUI(context);
                            //重新加载
                        } else {
                            clickListion.onRetryClick(data);
                        }
                    }
                }
            });
        }


    }

    @Override
    public void setLoadingEvent(View loadingView, ContextData data) {
        TextView title = (TextView) loadingView.findViewById(R.id.content);
        if (title != null) {
            if (data == null || TextUtils.isEmpty(data.getTitle())) {
                title.setText(context.getResources().getString(R.string.loadding));
            } else if (data != null && !TextUtils.isEmpty(data.getTitle())) {
                title.setText(data.getTitle());
            }
        }
    }

    @Override
    public void setEmptyEvent(View emptyView, final ContextData data) {
        TextView title = (TextView) emptyView.findViewById(R.id.title);
        if (title != null) {
            if (data == null || TextUtils.isEmpty(data.getTitle())) {
                title.setText(context.getResources().getString(R.string.no_data));
            } else if (data != null && !TextUtils.isEmpty(data.getTitle())) {
                title.setText(data.getTitle());
            }
        }
        ImageView img = (ImageView) emptyView.findViewById(R.id.image);
        if (img != null) {
            if (data == null || data.getResId() <= 0) {
                img.setImageResource(R.mipmap.zhan_wei);
            } else if (data != null && data.getResId() > 0) {
                img.setImageResource(data.getResId());
            }
        }
        View butt = emptyView.findViewById(R.id.reSet);
        if (butt != null) {
            if (!TextUtils.isEmpty(data.getButtonText())) {
                ((FlatButton) butt).setText(data.getButtonText());
            }
            butt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListion != null) {
                        //如果是需要登录接口，跳转到登录页面，这种情况一般出现在帐号在另一个设备中登录，token失效所致
                        if (data.getErrCode().equals(HttpCode.FAIL_C.getKey()) && !UserInfo.userData.isLogin()) {
                            LoginActivity.startUI(context);
                            //重新加载
                        } else {
                            clickListion.onEmptyClick(data);
                        }
                    }
                }
            });
        }

    }
}
