package com.snehpandya.androidoreo.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.snehpandya.androidoreo.R;
import com.snehpandya.androidoreo.databinding.ActivityLocaleBinding;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sneh.pandya on 25/12/17.
 */

public class LocaleActivity extends AppCompatActivity {

    private Number mNumber = 123456;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLocaleBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.textDate.setText(DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault(Locale.Category.DISPLAY)).format(new Date().getTime()));
            binding.textNumber.setText(NumberFormat.getInstance(Locale.getDefault(Locale.Category.DISPLAY)).format(mNumber));
        } else {
                binding.textDate.setText(DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault()).format(new Date().getTime()));
                binding.textNumber.setText(NumberFormat.getInstance(Locale.getDefault()).format(mNumber));
            }
        }
}
