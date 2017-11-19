package com.scrat.gogo.module.me.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.scrat.gogo.R;
import com.scrat.gogo.data.model.UserInfo;
import com.scrat.gogo.databinding.ActivityProfileBinding;
import com.scrat.gogo.framework.common.BaseActivity;

/**
 * Created by scrat on 2017/11/19.
 */

public class ProfileActivity extends BaseActivity implements ProfileContract.View {
    private ActivityProfileBinding binding;
    private ProfileContract.Presenter presenter;

    public static void show(Context context) {
        Intent i = new Intent(context, ProfileActivity.class);
        context.startActivity(i);
    }

    @NonNull
    @Override
    protected String getActivityName() {
        return "ProfileActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.topBar.subject.setText("个人信息");

        new ProfilePresenter(this);
        presenter.loadUserInfo();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingUserInfo() {

    }

    @Override
    public void showLoadUserInfoError(String e) {
        showMessage(e);
    }

    @Override
    public void showUserInfo(UserInfo info) {
        binding.nickname.setText(info.getUsername());
        binding.gender.setText(getGenderStr(info.getGender()));
    }

    @Override
    public void showLogoutSuccess() {
        toast("退出成功");
        finish();
    }

    private String getGenderStr(String gender) {
        if ("male".equals(gender)) {
            return "男";
        }

        if ("female".equals(gender)) {
            return "女";
        }

        return "未知";
    }

    public void logout(View view) {
        presenter.logout();
    }
}
