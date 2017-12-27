package com.snehpandya.androidoreo.view.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by sneh.pandya on 26/12/17.
 */

public class NormalBroadcastReceiver extends IntentService {

    public static final String ACTION = "com.snehpandya.androidoreo.view.service.NormalBroadcastReceiver";

    public NormalBroadcastReceiver() {
        super("normalBroadcastReceiver");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent in = new Intent(ACTION);
        if (intent != null) {
            in.putExtra("resultCode", Activity.RESULT_OK);
            in.putExtra("resultValue", "My result value: " + intent.getStringExtra("royal"));
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(in);
    }
}
