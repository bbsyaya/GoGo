package com.scrat.gogo.module.me;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.local.Preferences;
import com.scrat.gogo.framework.util.L;

/**
 * Created by scrat on 2017/11/14.
 */

public class MePresenter implements MeContract.Presenter {
    private MeContract.View view;

    public MePresenter(MeContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void logout() {
        String refreshToken = Preferences.getInstance().getRefreshToken();
        DataRepository.getInstance().getApi().logout(refreshToken);
        Preferences.getInstance().clearAuth();
        view.showNotLogin();
    }

    @Override
    public void loadUserInfo() {
        L.d(Preferences.getInstance().getUid());
        L.d(Preferences.getInstance().getRefreshToken());
        L.d(Preferences.getInstance().getRefreshToken());
        if (!Preferences.getInstance().isLogin()) {
            view.showNotLogin();
            return;
        }


    }
}
