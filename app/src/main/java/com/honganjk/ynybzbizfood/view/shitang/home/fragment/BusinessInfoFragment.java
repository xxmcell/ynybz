package com.honganjk.ynybzbizfood.view.shitang.home.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseMvpFragment;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.pressenter.shitang.home.BusinessInfoPresenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideOptions;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.view.home.activity.MainHomeActivity;
import com.honganjk.ynybzbizfood.view.other.activity.PhotoActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.honganjk.ynybzbizfood.code.MyApplication.myApp;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

public class BusinessInfoFragment extends BaseMvpFragment<HomeParentInterface.businessInfo, BusinessInfoPresenter>
        implements HomeParentInterface.businessInfo {


    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.tv_fare)
    TextView tvFare;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_notice_content)
    TextView tvNoticeContent;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_imgs)
    LinearLayout llImgs;
    @BindView(R.id.scrollView)
    HorizontalScrollView scrollView;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_licence)
    TextView tvLicence;
    @BindView(R.id.tv_permit)
    TextView tvPermit;
    @BindView(R.id.iv_tel)
    ImageView ivTel;
    private BusinessDetailsData data;


    public void update(BusinessDetailsData data) {
        this.data = data;
        GlideUtils.show(ivAvatar, data.getImg(), new GlideOptions.Builder().addTransformation(new RoundedCornersTransformation(myApp, DimensUtils.dip2px(getContext(), 8), DimensUtils.dip2px(getContext(), 8))).bulider());

        changeSaved(data.isKeep());

        ImageView imageView = new ImageView(getContext());
        GlideUtils.show(imageView, data.getHead(), new GlideOptions.Builder().setHeight(DimensUtils.dip2px(getContext(), 110)
        ).setWidth(DimensUtils.dip2px(getContext(), 150)).bulider());

        llImgs.addView(imageView);

        tvName.setText(data.getName());
        tvGrade.setText(data.getScore() + "");
        tvRank.setText(String.format(getResources().getString((int) R.string.tv_rank), data.getRank()));
        tvTotal.setText(String.format(getResources().getString((int) R.string.tv_sales), data.getSale()));
        tvOpen.setText(String.format(getResources().getString((int) R.string.tv_open), data.getLowest()));
        tvFare.setText(String.format(getResources().getString((int) R.string.tv_fare), data.getFare()));

        StringBuffer noticeContent = new StringBuffer(data.getBulletin() + "\n\n");
        if (data.getFavors() != null && data.getFavors().size() != 0) {
            noticeContent.append(getResources().getString((int) R.string.tv_favors));
        }
        for (int i = 0; i < data.getFavors().size(); i++) {
            noticeContent.append(i + 1).append("、").append(data.getFavors().get(i).getTitle()).append("\n");
        }

        tvNoticeContent.setText(noticeContent);
        tvContent.setText(data.getDescs());
        tvAddress.setText(String.format(getResources().getString((int) R.string.tv_business_address), data.getAddress()));
        tvTime.setText(String.format(getResources().getString((int) R.string.tv_business_time), data.getHours()));

    }

    private void changeSaved(boolean saved) {
        Drawable drawable = null;
        if (saved) {
            drawable = getResources().getDrawable(R.mipmap.collect_green);
            tvCollect.setText(getResources().getString((int) R.string.tv_collectd));
        } else {
            drawable = getResources().getDrawable(R.mipmap.collect_grey);
            tvCollect.setText(getResources().getString((int) R.string.tv_collect));
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvCollect.setCompoundDrawables(null, drawable, null, null);
    }


    @Override
    public BusinessInfoPresenter initPressenter() {
        return new BusinessInfoPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_businessinfo;
    }

    @Override
    public void initView() {
//        presenter.getBusinessInfo(data.getBid());


    }

    @Override
    public void businessInfo(BusinessDetailsData data) {
        tvName.setText(data.getName());

    }

    @Override
    public void collectBusiness(boolean success) {
        data.setKeep(success);
        changeSaved(success);
    }

    @Override
    public void clearData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void f() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(data.getPermit_img_url());
        PhotoActivity.startUI(activity, urls, 0);
    }

    @OnClick({R.id.tv_licence, R.id.tv_permit, R.id.tv_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_licence:
                ArrayList imgs1 = new ArrayList();
                imgs1.add(data.getLicense_img_url());
                PhotoActivity.startUI(activity, imgs1, 0);
                break;
            case R.id.tv_permit:
                ArrayList imgs2 = new ArrayList();
                imgs2.add(data.getPermit_img_url());
                PhotoActivity.startUI(activity, imgs2, 0);
                break;
            case R.id.tv_collect:
                if (isLogin(true)) {
                    if (data != null) {
                        presenter.getCollect(data.getId(), data.getType(), !data.isKeep());

                    }
                }
                break;
        }
    }

    @OnClick(R.id.iv_tel)
    public void onClick() {
        if (data != null) {
            DeviceUtil.callByPhone(getContext(), data.getMobile()
            );
        }
    }
}
