package com.scrat.gogo.module.me.profile;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.local.Preferences;
import com.scrat.gogo.data.model.UserInfo;

/**
 * Created by scrat on 2017/11/19.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View view;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadUserInfo() {
        view.showLoadingUserInfo();
        DataRepository.getInstance().getApi().getUserInfo(
                new DefaultLoadObjCallback<UserInfo, Res.UserInfoRes>() {
                    @Override
                    protected void onSuccess(UserInfo info) {
                        view.showUserInfo(info);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoadUserInfoError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.UserInfoRes> getResClass() {
                        return Res.UserInfoRes.class;
                    }
                });
    }

    @Override
    public void logout() {
        String refreshToken = Preferences.getInstance().getRefreshToken();
        DataRepository.getInstance().getApi().logout(refreshToken);
        Preferences.getInstance().clearAuth();
        view.showLogoutSuccess();
    }

    @Override
    public void updateGenderToMale() {
        updateUserInfo("male");
    }

    @Override
    public void updateGenderToFemale() {
        updateUserInfo("female");
    }

    private void updateUserInfo(final String gender) {
        view.showProfileUpdating();
        DataRepository.getInstance().getApi().getUserInfo(
                new DefaultLoadObjCallback<UserInfo, Res.UserInfoRes>() {
                    @Override
                    protected void onSuccess(final UserInfo info) {
                        DataRepository.getInstance().getApi()
                                .updateUserInfo(info.getUsername(),
                                        info.getAvatar(),
                                        gender,
                                        new DefaultLoadObjCallback<String, Res.DefaultStrRes>() {
                                            @Override
                                            protected void onSuccess(String s) {
                                                info.setGender(gender);
                                                view.showProfileUpdateSuccess(info);
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                view.showProfileUpdateError(e.getMessage());
                                            }

                                            @NonNull
                                            @Override
                                            protected Class<Res.DefaultStrRes> getResClass() {
                                                return Res.DefaultStrRes.class;
                                            }
                                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showProfileUpdateError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.UserInfoRes> getResClass() {
                        return Res.UserInfoRes.class;
                    }
                });
    }
}
