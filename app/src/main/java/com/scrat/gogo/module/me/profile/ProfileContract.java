package com.scrat.gogo.module.me.profile;

import com.scrat.gogo.data.model.UserInfo;
import com.scrat.gogo.framework.common.BaseContract;

/**
 * Created by scrat on 2017/11/19.
 */

public interface ProfileContract {
    interface Presenter {
        void loadUserInfo();

        void logout();
    }

    interface View extends BaseContract.BaseView<Presenter> {
        void showLoadingUserInfo();

        void showLoadUserInfoError(String e);

        void showUserInfo(UserInfo info);

        void showLogoutSuccess();
    }
}
