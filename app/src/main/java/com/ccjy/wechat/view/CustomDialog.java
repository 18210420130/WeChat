package com.ccjy.wechat.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.ccjy.wechat.R;

/**
 * Created by dell on 2017/3/27.
 * dialog进度提示框
 */

public class CustomDialog extends ProgressDialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIndeterminate(false); //进度条有没有准确的数值
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.load_dialog);

        WindowManager.LayoutParams arr =getWindow().getAttributes();
        arr.width=WindowManager.LayoutParams.WRAP_CONTENT; //宽
        arr.height=WindowManager.LayoutParams.WRAP_CONTENT; //高
        arr.alpha=0.8f; //透明度
        getWindow().setAttributes(arr);
    }
}
