package net.lvtushiguang.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.lvtushiguang.widget.R;


/**
 * 项目名称 widget
 * 创建人 梧桐树
 * 创建时间  2018/5/2 09:36
 * 功能描述：仿IOS加载框
 */

public class LoadingDailog extends Dialog {
    private boolean isTimeOut = false;
    private long timeout = 0;
    private Handler mHandler = new Handler() {
    };

    public LoadingDailog(@NonNull Context context) {
        super(context);
    }

    public LoadingDailog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public LoadingDailog setTimeOut(long timeout) {
        this.isTimeOut = true;
        this.timeout = timeout;
        return this;
    }

    @Override
    public void show() {
        super.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, timeout);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mHandler.removeCallbacksAndMessages(null);
    }

    public static class Builder {
        private Context context;
        private String message;
        private boolean isShowMessage = true;
        private boolean isCancelable = false;
        private boolean isCancelOutside = false;
        private boolean isTimeOut = false;
        private long timeout = 0;

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setShowMessage(boolean isShowMessage) {
            this.isShowMessage = isShowMessage;
            return this;
        }

        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public Builder setCancelOutside(boolean isCancelOutside) {
            this.isCancelOutside = isCancelOutside;
            return this;
        }

        public Builder setTimeOut(long timeout) {
            this.isTimeOut = true;
            this.timeout = timeout;
            return this;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public LoadingDailog create() {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            View view = inflater.inflate(R.layout.loading_dialog, (ViewGroup) null);
            LoadingDailog loadingDailog = new LoadingDailog(this.context, R.style.LoadingDialogStyle);
            TextView msgText = view.findViewById(R.id.tipTextView);
            if (this.isShowMessage) {
                msgText.setText(this.message);
            } else {
                msgText.setVisibility(View.GONE);
            }

            loadingDailog.setContentView(view);
            loadingDailog.setCancelable(this.isCancelable);
            loadingDailog.setCanceledOnTouchOutside(this.isCancelOutside);
            if (isTimeOut)
                loadingDailog.setTimeOut(timeout);
            return loadingDailog;
        }
    }
}
