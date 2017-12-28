package com.snehpandya.androidoreo.view.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by sneh.pandya on 27/12/17.
 */

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public class NormalJobSchedulerService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        HandlerThread handlerThread = new HandlerThread("nextThread");
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("Tag", "JobScheduler: Started");
                jobFinished(params, true);
            }
        });
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("Tag", "JobScheduler: Stopped");
        return false;
    }
}
