package net.lvtushiguang.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.lvtushiguang.widget.R;


/**
 * 项目名称 widget
 * 创建人 梧桐树
 * 创建时间  2018/5/2 09:36
 * 功能描述：仿IOS加载框
 */

public class LoadingDailog extends Dialog {
    private Context mContext;

    private boolean isCancelable = true;
    private boolean isCancelOutside = false;
    private boolean isTimeOut = true;
    private long timeout = 10000;
    private Handler mHandler = new Handler() {
    };

    private TextView mMessage;
    private String message;

    public LoadingDailog(@NonNull Context context) {
        this(context, R.style.LoadingDialogStyle);
    }

    public LoadingDailog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.loading_dialog, null);
        mMessage = view.findViewById(R.id.tipTextView);
        if (TextUtils.isEmpty(message)) {
            mMessage.setVisibility(View.GONE);
        } else {
            mMessage.setVisibility(View.VISIBLE);
            mMessage.setText(message);
        }

        setCancelable(isCancelable);
        setCanceledOnTouchOutside(isCancelOutside);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
        if (isTimeOut)
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, timeout);
    }

    public void setTimeOutState(boolean state) {
        isTimeOut = state;
    }

    public void setTimeOut(long timeout) {
        this.timeout = timeout;
    }

    public void setMessage(String message) {
        if (null == mMessage) {
            this.message = message;
            return;
        }

        if (mMessage.getVisibility() == View.GONE) {
            mMessage.setVisibility(View.VISIBLE);
        }
        mMessage.setText(message);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (isTimeOut)
            mHandler.removeCallbacksAndMessages(null);
    }
}
