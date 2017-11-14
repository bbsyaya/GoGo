package com.scrat.gogo.module.me;

import com.scrat.gogo.framework.common.BaseContract;

/**
 * Created by scrat on 2017/11/14.
 */

public interface MeContract {
    interface Presenter {
        void logout();

        void loadUserInfo();
    }

    interface View extends BaseContract.BaseView<Presenter> {
        void showNotLogin();
    }
}
