package com.snehpandya.androidoreo.view.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sneh.pandya on 26/12/17.
 */

public class NormalIntentService extends IntentService {

    public NormalIntentService() {
        super("normalIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!= null)
            Log.d("Tag", "onHandleIntent: " + intent.getStringExtra("royal"));
    }
}
