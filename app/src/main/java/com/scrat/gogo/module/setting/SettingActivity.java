package com.scrat.gogo.module.setting;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.scrat.gogo.R;
import com.scrat.gogo.databinding.ActivitySettingBinding;
import com.scrat.gogo.framework.common.BaseActivity;
import com.scrat.gogo.module.about.AboutActivity;
import com.scrat.gogo.module.me.feedback.FeedbackActivity;

/**
 * Created by scrat on 2017/11/22.
 */

public class SettingActivity extends BaseActivity {
    private ActivitySettingBinding binding;

    public static void show(Context context) {
        Intent i = new Intent(context, SettingActivity.class);
        context.startActivity(i);
    }

    @NonNull
    @Override
    protected String getActivityName() {
        return "SettingActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.topBar.subject.setText("设置");
    }

    public void feedback(View view) {
        FeedbackActivity.show(this);
    }

    public void about(View view) {
        AboutActivity.show(this);
    }

    public void update(View view) {
    }
}
