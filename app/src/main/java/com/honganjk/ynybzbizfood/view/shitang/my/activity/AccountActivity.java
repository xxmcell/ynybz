package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.enumeration.ALBCPath;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.mode.third.ALBCUtils;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.ResetNamePresenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;
import com.soundcloud.android.crop.Crop;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


/**
 * 更改个人信息
 */
public class AccountActivity extends BaseMvpActivity<MyParentInterfaces.IRename, ResetNamePresenter> implements MyParentInterfaces.IRename {

    private static final String IMAGEHEAD = "IMAGEHEAD";
    private static final String USERNAME = "USERNAME";
    @BindView(R.id.iv_xiugaihead)
    ImageView ivXiugaihead;
    @BindView(R.id.ll_xiugaihead)
    LinearLayout llXiugaihead;
    @BindView(R.id.tv_resetname)
    TextView tvResetname;
    @BindView(R.id.ll_restename)
    LinearLayout llRestename;
    @BindView(R.id.tv_exit)
    TextView tv_exit;
    private String mName;
    private String mHead;
    ArrayList<String> picturePath = new ArrayList<>();

    public static void startUi(Context context, String imagehead, String name) {
        Intent intent = new Intent(context, AccountActivity.class);
        intent.putExtra(IMAGEHEAD, imagehead);
        intent.putExtra(USERNAME, name);
        context.startActivity(intent);
    }

    @Override
    public ResetNamePresenter initPressenter() {
        return new ResetNamePresenter();

    }

    @Override
    public int getContentView() {
        return R.layout.activity_account;
    }

    @Override
    public void initView() {
        toolbar.setTitle("账户信息");
        toolbar.setBack(this);
//        Glide.with(this).load(mHead).into(ivXiugaihead);
        GlideUtils.showCircle(ivXiugaihead, mHead);
        tvResetname.setText(mName);
    }

    @Override
    public void parseIntent(Intent intent) {
        //解析意图
        mHead = intent.getStringExtra(IMAGEHEAD);
        mName = intent.getStringExtra(USERNAME);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_xiugaihead, R.id.ll_restename, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_xiugaihead:
                // 选择照相
                selectImage();
                break;
            case R.id.ll_restename:
                //跳转界面 名称修改
                ResetNameActivity.startUiForRseult(this, mName, 0);
                break;
            case R.id.tv_exit:
                //退出账号
                UserInfo.exitLogin(this);
                break;
        }
    }

    /**
     * 选择照片
     */
    private void selectImage() {
//        Crop.pickImage(this);
        MultiImageSelector.create()
                .showCamera(true)
                .single()
                .origin(picturePath)
                .start(this, REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 0 && resultCode == ResetNameActivity.RESULT_OK) {
                tvResetname.setText(data.getStringExtra("name"));
            }
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                picturePath.clear();
                picturePath.addAll(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));

                //1把选择的的图片，去裁剪
                try {
                    Crop.of(this, picturePath.get(0)).asSquare().start(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //2接收裁剪好的图片
            if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
                String str = Crop.getOutput(data).getPath();

//                ivXiugaihead.setImageURI( Crop.getOutput(data));//直接加载uri
                //3上传到阿里百川
                new ALBCUtils(this, ALBCPath.USER_ST, str) {
                    @Override
                    public void succeed(String url) {
                        //4上传服务器
                        presenter.saveNewName(url, null);
                        //5展示在UI上
                        GlideUtils.showCircle(ivXiugaihead, url);
                    }
                };
            }


        }
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI,
                new String[]{MediaStore.Images.ImageColumns.DATA},//
                null, null, null);
        if (cursor == null) result = contentURI.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }


    /**
     * 后台返回的数据
     *
     * @param bool
     */
    @Override
    public void saveNewname(boolean bool) {
//        GlideUtils.showCircle(ivXiugaihead, picturePath.get(0));
        showInforSnackbar("上传成功");
    }

    @Override
    public void clearData() {

    }
}
