package com.scrat.gogo.module;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.scrat.gogo.R;
import com.scrat.gogo.databinding.ActivityLoginBinding;
import com.scrat.gogo.framework.common.BaseActivity;
import com.scrat.gogo.framework.util.L;
import com.scrat.gogo.receiver.LoginReceiver;
import com.scrat.gogo.wxapi.WXEntryActivity;

/**
 * Created by scrat on 2017/10/31.
 */

public class LoginActivity extends BaseActivity {

    private static final String CODE = "code";

    public static void showWxLoginSuccess(Context context, String code) {
        Intent i = new Intent(context, LoginActivity.class);
        i.putExtra(CODE, code);
        context.startActivity(i);
    }

    public static void show(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private ActivityLoginBinding binding;
    private LoginReceiver receiver;

    @NonNull
    @Override
    protected String getActivityName() {
        return "LoginActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        String code = getIntent().getStringExtra(CODE);
        L.d("code=" + code);
    }

    public void sendSmsCode(View view) {
    }

    public void telLogin(View view) {
    }

    public void wxLogin(View view) {
        WXEntryActivity.login();
    }

    public void navigateToDisclaimer(View view) {

    }
}
