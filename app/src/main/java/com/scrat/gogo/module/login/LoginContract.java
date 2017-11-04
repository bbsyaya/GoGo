package com.scrat.gogo.module.login;

import com.scrat.gogo.framework.common.BaseView;

/**
 * Created by scrat on 2017/11/3.
 */

public interface LoginContract {
    interface Presenter {
        void wxLogin(String code);

        void sendSmsCode();

        void telLogin(String tel, String code);
    }

    interface View extends BaseView<Presenter> {
        void showLogin();

        void showLoginSuccess();

        void showLoginFail(String msg);
    }
}
