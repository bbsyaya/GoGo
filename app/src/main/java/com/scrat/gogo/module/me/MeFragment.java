package com.scrat.gogo.module.me;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.gogo.data.local.Preferences;
import com.scrat.gogo.data.model.UserInfo;
import com.scrat.gogo.databinding.FragmentMeBinding;
import com.scrat.gogo.framework.common.BaseFragment;
import com.scrat.gogo.module.about.AboutActivity;
import com.scrat.gogo.module.coin.CoinPlanActivity;
import com.scrat.gogo.module.login.LoginActivity;

/**
 * Created by scrat on 2017/10/24.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener, MeContract.View {
    private FragmentMeBinding binding;
    private MeContract.Presenter presenter;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected String getFragmentName() {
        return "MeFragment";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMeBinding.inflate(inflater, container, false);

        binding.logoutBtn.setOnClickListener(this);
        binding.userInfo.setOnClickListener(this);
        binding.recharge.setOnClickListener(this);
        binding.about.setOnClickListener(this);

        new MePresenter(this);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadUserInfo();
    }

    @Override
    public void setPresenter(MeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showNotLogin() {
        binding.name.setText("登录");
        binding.coinTip.setVisibility(View.GONE);
        binding.coin.setVisibility(View.GONE);
        binding.logoutBtn.setVisibility(View.GONE);
    }

    @Override
    public void showUserInfo(UserInfo info) {
        binding.name.setText(info.getUsername());
        binding.coinTip.setVisibility(View.VISIBLE);
        binding.coin.setVisibility(View.VISIBLE);
        binding.coin.setText(String.valueOf(info.getCoin()));
        binding.logoutBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLogoutSuccess() {
        showToast("退出成功");
        showNotLogin();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.logoutBtn) {
            presenter.logout();
            return;
        }

        if (view == binding.about) {
            AboutActivity.show(getContext());
            return;
        }

        if (!Preferences.getInstance().isLogin()) {
            LoginActivity.show(view.getContext());
            return;
        }

        if (view == binding.userInfo) {
            return;
        }

        if (view == binding.recharge) {
            CoinPlanActivity.show(getActivity());
            return;
        }

    }
}
