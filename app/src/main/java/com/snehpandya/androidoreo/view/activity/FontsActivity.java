package com.snehpandya.androidoreo.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.snehpandya.androidoreo.R;
import com.snehpandya.androidoreo.databinding.ActivityFontsBinding;

/**
 * Created by sneh.pandya on 25/12/17.
 */

public class FontsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFontsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_fonts);
    }
}
