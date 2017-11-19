package com.scrat.gogo.module.me.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.scrat.gogo.R;
import com.scrat.gogo.data.model.UserInfo;
import com.scrat.gogo.databinding.ActivityProfileBinding;
import com.scrat.gogo.framework.common.BaseActivity;
import com.scrat.gogo.framework.glide.GlideApp;
import com.scrat.gogo.framework.glide.GlideCircleTransform;
import com.scrat.gogo.framework.glide.GlideRequests;
import com.scrat.gogo.framework.view.IosDialog;

/**
 * Created by scrat on 2017/11/19.
 */

public class ProfileActivity extends BaseActivity implements ProfileContract.View {
    private ActivityProfileBinding binding;
    private ProfileContract.Presenter presenter;
    private GlideRequests glideRequests;
    private RequestOptions options;
    private IosDialog logoutDialog;

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
        glideRequests = GlideApp.with(this);
        options = new RequestOptions()
                .centerCrop()
                .transform(new GlideCircleTransform());

        new ProfilePresenter(this);
        presenter.loadUserInfo();

        logoutDialog = new IosDialog(this)
                .setTitle("退出提示")
                .setContent("是否现在退出账号？")
                .setNegative("取消")
                .setPositive("退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.logout();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (logoutDialog.isShowing()) {
            logoutDialog.dismiss();
        }
        super.onDestroy();
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
        glideRequests.load(info.getAvatar()).apply(options).into(binding.avatar);
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
        logoutDialog.show(view);
    }
}
