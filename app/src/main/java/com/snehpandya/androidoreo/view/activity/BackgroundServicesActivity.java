package com.snehpandya.androidoreo.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.snehpandya.androidoreo.R;
import com.snehpandya.androidoreo.databinding.ActivityBackgroundServicesBinding;
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
    }

    private void intentService() {
        Intent intent = new Intent(this, NormalIntentService.class);
        intent.putExtra("royal", "enfield");
        startService(intent);
    }
}
