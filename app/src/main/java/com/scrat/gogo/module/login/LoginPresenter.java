package com.scrat.gogo.module.login;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.local.Preferences;
import com.scrat.gogo.data.model.TokenInfo;

/**
 * Created by scrat on 2017/11/3.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void wxLogin(String code) {
        view.showLogin();
        DataRepository.getInstance().getApi().wxLogin(
                code, new DefaultLoadObjCallback<TokenInfo, Res.TokenRes>() {
                    @Override
                    protected void onSuccess(TokenInfo tokenInfo) {
                        Preferences.getInstance().setUid(tokenInfo.getUid());
                        Preferences.getInstance().setAccessToken(tokenInfo.getAccessToken());
                        Preferences.getInstance().setRefreshToken(tokenInfo.getRefreshToken());
                        view.showLoginSuccess();
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoginFail(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.TokenRes> getResClass() {
                        return Res.TokenRes.class;
                    }
                });
    }

    @Override
    public void sendSmsCode() {

    }

    @Override
    public void telLogin(String tel, String code) {

    }
}
