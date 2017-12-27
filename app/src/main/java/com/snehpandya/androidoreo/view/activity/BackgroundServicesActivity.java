package com.snehpandya.androidoreo.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.snehpandya.androidoreo.R;
import com.snehpandya.androidoreo.databinding.ActivityBackgroundServicesBinding;
import com.snehpandya.androidoreo.view.service.NormalBroadcastReceiver;
import com.snehpandya.androidoreo.view.service.NormalIntentService;

/**
 * Created by sneh.pandya on 26/12/17.
 */

public class BackgroundServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBackgroundServicesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_background_services);

        binding.btnIntentService.setOnClickListener(v -> intentService());
        binding.btnBroadcast.setOnClickListener(this::broadcastReceiver);
    }

    private void intentService() {
        Intent intent = new Intent(this, NormalIntentService.class);
        intent.putExtra("royal", "enfield");
        startService(intent);
    }

    private void broadcastReceiver(View v) {
        Intent intent = new Intent(this, NormalBroadcastReceiver.class);
        intent.putExtra("royal", "enfield");
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(NormalBroadcastReceiver.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String resultValue = intent.getStringExtra("resultValue");
                Toast.makeText(context, resultValue, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
