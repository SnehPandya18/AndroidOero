package com.snehpandya.androidoreo.view.service;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by sneh.pandya on 27/12/17.
 */

public class NormalJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d("Tag", "FirebaseJobDispatcher: Started");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d("Tag", "FirebaseJobDispatcher: Stopped");
        return false;
    }
}
