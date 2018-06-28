package com.android.hcbd.overruntransportation.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by guocheng on 2017/3/16.
 */

public class DialogUtils {
    private ProgressDialog dialog;
    /**
     * 显示等待框
     *
     * @param context
     */
    public void showLoading(Context context) {
        if(dialog != null && dialog.isShowing()) {
            dismissLoading ();
        }
        dialog = new ProgressDialog (context);
        dialog.setMessage ("请稍后...");
        dialog.setCanceledOnTouchOutside (false);
        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * 显示等待框
     *
     * @param context
     */
    public void showLoading(Context context, String title, String mes) {
        if(dialog != null && dialog.isShowing()) {
            dismissLoading ();
        }
        dialog = new ProgressDialog (context);
        dialog.setTitle (title);
        dialog.setMessage (mes);
        dialog.setCanceledOnTouchOutside (false);
        dialog.setCancelable(true);
        dialog.show ();
    }

    /**
     * 等待框消失
     */
    public void dismissLoading() {
        if (dialog != null && dialog.isShowing ()) {
            dialog.dismiss ();
        }
    }

}
