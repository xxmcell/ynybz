package com.honganjk.ynybzbizfood.utils.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;


public class CommonDialog extends Dialog {

    private View mDialogView;

    public CommonDialog(Context context) {
        this(context, 0);
    }

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 设置点击事件
     */
    public void setOnClickListener(int viewId,View.OnClickListener listener){
        findViewById(viewId).setOnClickListener(listener);
    }

    /**
     * 设置TextView 文本
     */
    public void setText(int viewId,String text){
        ((TextView)findViewById(viewId)).setText(text);
    }

    /**
     * 绑定和设置参数
     */
    private void apply(Builder.DialogParams P) {
        if(mDialogView == null && P.mViewLayoutResId == 0){
            throw new NullPointerException("大哥请设置布局!!!");
        }

        mDialogView = P.mView;// 资源  View

        if(mDialogView == null){
            mDialogView = View.inflate(P.context,P.mViewLayoutResId,null);
        }

        // 设置布局
        setContentView(mDialogView);


        // 设置基本参数
        Window window = getWindow();

        window.setLayout(P.mWidth, P.mHeight);

        window.setGravity(P.mGravity);

        if(P.mAnimation != 0) {
            window.setWindowAnimations(P.mAnimation);
        }

    }


    public static class Builder {

        private DialogParams P;

        public Builder(Context context){
            // 如果没有传默认的就是自定义的主题
            this(context, R.style.dialog);
        }

        public Builder(Context context,int themeResId){
            P = new DialogParams(context,themeResId);
        }


        /**
         * Set a custom view resource to be the contents of the Dialog. The
         * resource will be inflated, adding all top-level views to the screen.
         *
         * @param layoutResId Resource ID to be inflated.
         * @return this Builder object to allow for chaining of calls to set
         *         methods
         */
        public Builder setView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * Sets a custom view to be the contents of the alert dialog.
         * <p>
         * When using a pre-Holo theme, if the supplied view is an instance of
         * a {@link ListView} then the light background will be used.
         * <p>
         * <strong>Note:</strong> To ensure consistent styling, the custom view
         * should be inflated or constructed using the alert dialog's themed
         * context obtained via {@link #getContext()}.
         *
         * @param view the view to use as the contents of the alert dialog
         * @return this Builder object to allow for chaining of calls to set
         *         methods
         */
        public Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置宽度
         */
        public Builder setWidth(int width){
            P.mWidth = width;
            return this;
        }

        /**
         * 设置高度
         */
        public Builder setHeight(int height){
            P.mHeight = height;
            return this;
        }


        /**
         * 显示动画
         */
        public Builder showAnimationFromBottom(){
            P.mAnimation = R.style.main_menu_animstyle;
            P.mGravity = Gravity.BOTTOM;
            return this;
        }


        public Builder setGravity(int gravity){
            P.mGravity = gravity;
            return this;
        }

        /**
         * 显示动画  可以自定动画
         */
        public Builder showAnimation(int styleResId){
            P.mAnimation = styleResId;
            return this;
        }

        public CommonDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final CommonDialog dialog = new CommonDialog(P.context, P.themeResId);
            // 绑定一些参数
            dialog.apply(P);
            return dialog;
        }

        public static class DialogParams{
            public Context context;
            public int themeResId;
            public View mView;
            public int mViewLayoutResId;
            public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            public int mAnimation = 0;
            public int mGravity = Gravity.CENTER;

            public DialogParams(Context context, int themeResId) {
                this.context = context;
                this.themeResId = themeResId;
            }
        }
    }
}
