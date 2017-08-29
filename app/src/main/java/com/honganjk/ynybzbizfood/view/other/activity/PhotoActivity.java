package com.honganjk.ynybzbizfood.view.other.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.ui.SnackbarUtil;
import com.honganjk.ynybzbizfood.widget.photoBrowse.PhotoViewPager;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * 照片浏览
 * Created by Administrator on 2016/8/29.
 */


public class PhotoActivity extends BaseActivity implements AdapterView.OnItemLongClickListener, View.OnClickListener {
    @BindView(R.id.photoViewPager)
    PhotoViewPager photoViewPager;
    ArrayList<String> files;
    int position = 0;
    @BindView(R.id.numberTv)
    TextView numberTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_photo;
    }

    public static void startUI(Activity context, ArrayList<String> files, int position) {
        if (files == null || files.size() == 0) {
//            context.showWarningSnackbar("图片信息错误");
            SnackbarUtil.showLong(context,"图片信息错误",SnackbarUtil.Warning);
            return;
        }
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putStringArrayListExtra("data", files);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }


    @Override
    public int getStatusBarPaddingTop() {
        return 0;
    }

    @Override
    public int getStatusBarResource() {
        return R.color.translucent;
    }

    @Override
    public void initView() {
        photoViewPager.setAdapter(photoViewPager.new ViewPagerAdapter(files));
        photoViewPager.setCurrentItem(position);
        photoViewPager.setOnItemLongClickListener(this);
        photoViewPager.setOnItemClickListener(this);
        photoViewPager.setNumberTv(numberTv);
    }


    @Override
    public void parseIntent(Intent intent) {
        files = intent.getStringArrayListExtra("data");
        position = intent.getIntExtra("position", 0);

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        GlideUtils.downloadOnly(files.get(i), new GlideUtils.OnDownloadCallbacl() {
            @Override
            public void onCall(File file) {
                if (file == null) {
                    showErrorSnackbar("文件保存失败");
                    return;
                }
                MaterialDialog dialog = new MaterialDialog.Builder(PhotoActivity.this)
                        .title("图片保存成功")
                        .positiveText("确定")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .content("保存在 : " + file.getPath())
                        .build();
                dialog.show();
            }
        });
        return false;
    }

    @Override
    public void onClick(View view) {
        finish();
    }


}
