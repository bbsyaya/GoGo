package com.scrat.gogo.module.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.scrat.gogo.MainActivity;
import com.scrat.gogo.R;
import com.scrat.gogo.databinding.ActivityLoginBinding;
import com.scrat.gogo.framework.common.BaseActivity;
import com.scrat.gogo.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by scrat on 2017/10/31.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    public static void show(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private ActivityLoginBinding binding;
    private LoginContract.Presenter presenter;

    @NonNull
    @Override
    protected String getActivityName() {
        return "LoginActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        WXEntryActivity.initHandler(new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) {

            }

            @Override
            public void onResp(BaseResp baseResp) {
                switch (baseResp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        SendAuth.Resp resp = ((SendAuth.Resp) baseResp);
                        presenter.wxLogin(resp.code);
                        break;
                    default:
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        break;
                }
            }
        });

        new LoginPresenter(this);
    }

    @Override
    protected void onDestroy() {
        WXEntryActivity.releaseHandler();
        super.onDestroy();
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

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLogin() {

    }

    @Override
    public void showLoginSuccess() {
        MainActivity.redirect(this);
        finish();
    }

    @Override
    public void showLoginFail(String msg) {
        showMessage(msg);
    }
}
