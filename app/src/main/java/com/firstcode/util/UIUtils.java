package com.firstcode.util;

import android.app.Activity;
import android.app.Dialog;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class UIUtils {
    private static Dialog mDialog = null;
    private static ProgressBar pb = null;

    public static void showDialogLoading(Activity mActivity) {
        showDialogLoading(mActivity, false);
    }

    public static void showDialogLoading(final Activity mActivity, final boolean cancelable) {
        if(mDialog != null){
            mDialog.dismiss();
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialog = new Dialog(mActivity, R.style.EG_ThemeDialog);
                mDialog.setContentView(R.layout.eg_game_progress_dialog_layout);
                pb = (ProgressBar) mDialog.findViewById(R.id.eg_game_dialog_pb);
                mDialog.setCancelable(cancelable);
                mDialog.show();
            }
        });
    }

    public static void dismissDialogLoading(final Activity mActivity) {
        if (mDialog != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                    pb = null;
                    mDialog = null;
                }
            });
        }
    }

    public static void showDialogChangePregress(Activity context, Integer values) {
        if(mDialog != null && pb != null) {
            pb.setProgress(values);
        }
    }
}
