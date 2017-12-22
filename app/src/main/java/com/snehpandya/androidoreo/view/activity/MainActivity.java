package com.snehpandya.androidoreo.view.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.snehpandya.androidoreo.R;
import com.snehpandya.androidoreo.databinding.ActivityMainBinding;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.O;

public class MainActivity extends AppCompatActivity {

    private CharSequence name;
    private int notifyId;
    private int importance;
    private String id;
    private String description;

    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private NotificationChannel mChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btnNotification.setOnClickListener(v -> notification());
    }

    private void notification() {

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifyId = 1;
        description = "Hello World, welcome to Android Oreo!";

        if (SDK_INT >= O) {
            id = "id";
            name = "a";
            importance = NotificationManager.IMPORTANCE_HIGH;

            mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.WHITE);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[] {100, 300, 200, 300});
            mNotificationManager.createNotificationChannel(mChannel);

            mNotification = new Notification.Builder(MainActivity.this, id)
                .setContentTitle(id)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
        } else {
            mNotification = new Notification.Builder(MainActivity.this)
                .setContentTitle(id)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLights(Color.WHITE, Color.RED, Color.GREEN)
                .setVibrate(new long[] {100, 300, 200, 300})
                .build();
        }
            mNotificationManager.notify(notifyId, mNotification);
    }
}