package com.firstcode.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.firstcode.util.UIUtils;
import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class DialogTestAsyncTack extends AsyncTask<Void, Integer, Void> {
    private Activity context;
    public DialogTestAsyncTack(Activity context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        UIUtils.showDialogLoading(context);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int i = 1;
        while (i <= 100) {
            SystemClock.sleep(300);
            i++;
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        UIUtils.dismissDialogLoading(context);
        Utils.showToastShort(context, "progress success");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        UIUtils.showDialogChangePregress(context, values[0]);
    }
}
