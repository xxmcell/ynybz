package com.honganjk.ynybzbizfood.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.widget.AnimCheckBox;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 说明:联系平台选择原因
 * 作者： 阳2012; 创建于：  2017-05-19  14:08
 */
public class DialogSelectCause extends Dialog {

    @BindView(R.id.select1)
    AnimCheckBox select1;
    @BindView(R.id.select2)
    AnimCheckBox select2;
    @BindView(R.id.select3)
    AnimCheckBox select3;
    @BindView(R.id.select4)
    AnimCheckBox select4;
    ArrayList<AnimCheckBox> views = new ArrayList<>();
    private OnClickCallback clickCallback;
    private Context mContext;
    private int position = 0;

    String[] mHint = new String[]{"不方便服务", "不在家", "重新下单", "其他原因"};

    public DialogSelectCause(Context context) {
        this(context, R.style.Dialog);
    }

    public DialogSelectCause(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private void init() {
        setContentView(R.layout.base_dialog_select_cause);
        ButterKnife.bind(this, getWindow().getDecorView().findViewById(R.id.dialog_select_root));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        ScreenInfoUtils screen = new ScreenInfoUtils();
//        lp.width = (screen.getWidth()); //设置宽度
//        lp.height = (ActionBar.LayoutParams.WRAP_CONTENT); //设置宽度
//        getWindow().setAttributes(lp);
//        getWindow().getAttributes().gravity = Gravity.BOTTOM;

        views.add(select1);
        views.add(select2);
        views.add(select3);
        views.add(select4);

    }


    @OnClick({R.id.select1, R.id.select2, R.id.select3, R.id.select4, R.id.cancel, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select1:
                position = 0;
                setSelctStatus(position);
                break;
            case R.id.select2:
                position = 1;
                setSelctStatus(position);
                break;
            case R.id.select3:
                position = 2;
                setSelctStatus(position);
                break;
            case R.id.select4:
                position = 3;
                setSelctStatus(position);
                break;
            case R.id.cancel:
                dismiss();
                break;
            case R.id.commit:
                if (clickCallback != null) {
                    clickCallback.onClick(mHint[position]);
                }
                dismiss();
                break;
        }
    }

    /**
     * 设置选择的状态
     *
     * @param position
     */
    private void setSelctStatus(int position) {
//        if (views.get(position).isChecked()) {
//            return;
//        }
        for (int i = 0; i < views.size(); i++) {
            if (i == position) {
                views.get(i).setChecked(true);
            } else {
                views.get(i).setChecked(false);
            }
        }
    }


    public void setClickCallback(OnClickCallback clickCallback) {

        this.clickCallback = clickCallback;
    }

    public interface OnClickCallback {

        void onClick(String type);
    }

}
