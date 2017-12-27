package com.snehpandya.androidoreo.view.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by sneh.pandya on 27/12/17.
 */

public class NormalAlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 9999;
    public static final String ACTION = "com.snehpandya.androidoreo.view.service.NormalAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent in = new Intent(context, NormalAlarmManager.class);
        context.startService(in);
    }
}
