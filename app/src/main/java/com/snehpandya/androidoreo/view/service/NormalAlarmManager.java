package com.snehpandya.androidoreo.view.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sneh.pandya on 27/12/17.
 */

public class NormalAlarmManager extends IntentService {

    public NormalAlarmManager() {
        super("normalAlarmManager");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("Tag", "AlarmManager: running");
    }
}
