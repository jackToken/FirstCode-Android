package com.firstcode.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.firstcode.util.Utils;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class GameAsyncTask extends AsyncTask <Void, Integer, Boolean>{
    private Activity context;

    public GameAsyncTask(Activity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Utils.showDialogLoading(context, "dialog show");
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        int i = 1;
        while (i <= 100) {
            publishProgress(i);
            SystemClock.sleep(200);
            i++;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Utils.showDialogChangePregress(context, values[0]);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Utils.dismissDialogLoading(context);
    }
}
