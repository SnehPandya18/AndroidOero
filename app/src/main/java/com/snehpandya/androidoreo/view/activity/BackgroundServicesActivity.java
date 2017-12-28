package com.snehpandya.androidoreo.view.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.snehpandya.androidoreo.R;
import com.snehpandya.androidoreo.databinding.ActivityBackgroundServicesBinding;
import com.snehpandya.androidoreo.view.service.NormalAlarmReceiver;
import com.snehpandya.androidoreo.view.service.NormalBroadcastReceiver;
import com.snehpandya.androidoreo.view.service.NormalIntentService;
import com.snehpandya.androidoreo.view.service.NormalJobSchedulerService;
import com.snehpandya.androidoreo.view.service.NormalJobService;

import java.util.concurrent.TimeUnit;

/**
 * Created by sneh.pandya on 26/12/17.
 */

public class BackgroundServicesActivity extends AppCompatActivity {

    private ActivityBackgroundServicesBinding binding;

    private FirebaseJobDispatcher mFirebaseJobDispatcher;
    private JobScheduler mJobScheduler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_background_services);

        binding.btnIntentService.setOnClickListener(v -> intentService());
        binding.btnBroadcast.setOnClickListener(this::broadcastReceiver);
        binding.btnStartAlarm.setOnClickListener(v -> startAlarm());
        binding.btnStopAlarm.setOnClickListener(v -> stopAlarm());
        binding.btnStartFjd.setOnClickListener(v -> dispatchFJobD());
        binding.btnStopFjd.setOnClickListener(v -> cancelFJobD());
        binding.btnStartJsd.setOnClickListener(v -> startJobScheduler());
        binding.btnStopJsd.setOnClickListener(v -> cancelJobScheduler());
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

    private void dispatchFJobD() {
        mFirebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        Job job = mFirebaseJobDispatcher.newJobBuilder()
            .setService(NormalJobService.class)
            .setTag("FirebaseJobDispatcher")
            .build();

        mFirebaseJobDispatcher.mustSchedule(job);
        binding.btnStartFjd.setEnabled(false);
        binding.btnStopFjd.setEnabled(true);
    }

    private void cancelFJobD() {
        mFirebaseJobDispatcher.cancel("FirebaseJobDispatcher");
        mFirebaseJobDispatcher.cancelAll();
        binding.btnStopFjd.setEnabled(false);
        binding.btnStartFjd.setEnabled(true);
    }

    private void startJobScheduler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            mJobScheduler.cancelAll();
            ComponentName componentName = new ComponentName(this, NormalJobSchedulerService.class);
            int result = mJobScheduler.schedule(getJobInfo(1, 20, componentName));
            if (result == JobScheduler.RESULT_SUCCESS) {
                Log.d("Tag", "JobScheduler: Job scheduled successfully");
            } else {
                Log.d("Tag", "JobScheduler: JobScheduler requires API 21");
            }
            binding.btnStopJsd.setEnabled(true);
            binding.btnStartJsd.setEnabled(false);
        }
    }

    private JobInfo getJobInfo(int id, int minutes, ComponentName componentName) {
        JobInfo jobInfo = null;
        long interval = TimeUnit.MINUTES.toMillis(minutes);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo = new JobInfo.Builder(id, componentName)
                .setMinimumLatency(interval)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobInfo = new JobInfo.Builder(id, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(interval)
                .build();
        } else {
            Log.d("Tag", "JobInfo: JobScheduler requires API 21");
        }

        return jobInfo;
    }

    private void cancelJobScheduler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler.cancel(1);
            mJobScheduler.cancelAll();
        }
        binding.btnStartJsd.setEnabled(true);
        binding.btnStopJsd.setEnabled(false);
    }
}
