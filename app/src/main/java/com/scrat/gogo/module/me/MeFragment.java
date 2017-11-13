package com.scrat.gogo.module.me;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.gogo.databinding.FragmentMeBinding;
import com.scrat.gogo.framework.common.BaseFragment;
import com.scrat.gogo.module.login.LoginActivity;

/**
 * Created by scrat on 2017/10/24.
 */

public class MeFragment extends BaseFragment {
    private FragmentMeBinding binding;

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

        binding.userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.show(view.getContext());
            }
        });

        return binding.getRoot();
    }
}
