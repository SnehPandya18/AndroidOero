package com.snehpandya.androidoreo.view.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import com.snehpandya.androidoreo.view.service.NormalAlarmReceiver;
import com.snehpandya.androidoreo.view.service.NormalBroadcastReceiver;
import com.snehpandya.androidoreo.view.service.NormalIntentService;

/**
 * Created by sneh.pandya on 26/12/17.
 */

public class BackgroundServicesActivity extends AppCompatActivity {

    private ActivityBackgroundServicesBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_background_services);

        binding.btnIntentService.setOnClickListener(v -> intentService());
        binding.btnBroadcast.setOnClickListener(this::broadcastReceiver);
        binding.btnStartAlarm.setOnClickListener(v -> startAlarm());
        binding.btnStopAlarm.setOnClickListener(v -> stopAlarm());
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

    private void startAlarm() {
        Intent intent = new Intent(getApplicationContext(), NormalAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NormalAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long timeout = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeout, 10000, pendingIntent);
        binding.btnStartAlarm.setEnabled(false);
        binding.btnStopAlarm.setEnabled(true);
    }

    private void stopAlarm() {
        Intent intent = new Intent(getApplicationContext(), NormalAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NormalAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        binding.btnStopAlarm.setEnabled(false);
        binding.btnStartAlarm.setEnabled(true);
    }
}
