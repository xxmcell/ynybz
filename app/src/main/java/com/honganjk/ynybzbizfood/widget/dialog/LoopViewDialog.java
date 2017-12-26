package com.honganjk.ynybzbizfood.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.base.LoopViewData;
import com.honganjk.ynybzbizfood.widget.wheelview3d.LoopListener;
import com.honganjk.ynybzbizfood.widget.wheelview3d.LoopView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${Roman} on 2017/3/20 0020.
 * 我不要加班
 */

public class LoopViewDialog extends Dialog {

    @BindView(R.id.loopview)
    LoopView loopview;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.selectName)
    TextView selectName;
    @BindView(R.id.affirm)
    TextView affirm;
    @BindView(R.id.dialog_root)
    LinearLayout dialogRoot;

    private CallBack callBack;

    private ArrayList<LoopViewData> loopViewDatas = new ArrayList<>();
    private LoopListener listener;

    public LoopViewDialog(Context context, ArrayList<LoopViewData> datas, CallBack callBack) {
        super(context);
        this.loopViewDatas = datas;
        this.callBack = callBack;
        init();
    }


    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loopview);
        ButterKnife.bind(this, getWindow().getDecorView().findViewById(R.id.dialog_root));

        loopview.setNotLoop();
        loopview.setArrayList(loopViewDatas);
        loopview.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item, LoopViewData data) {
                selectName.setText(data.getName());
                loopview.setTag(data);
            }
        });
        if(loopViewDatas!=null&&loopViewDatas.size()>0){
            selectName.setText(loopViewDatas.get(0).getName());
            loopview.setTag(loopViewDatas.get(0));
        }
    }

    @OnClick({R.id.cancel, R.id.affirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.affirm:
                if(loopview.getTag()!=null){
                    callBack.setData((LoopViewData) loopview.getTag());
                    dismiss();
                }
                break;
        }
    }

    public interface CallBack {
        void setData(LoopViewData data);
    }

}
