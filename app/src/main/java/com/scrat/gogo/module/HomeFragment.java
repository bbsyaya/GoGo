package com.scrat.gogo.module;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.gogo.databinding.FragmentHomeBinding;
import com.scrat.gogo.framework.common.BaseFragment;

/**
 * Created by scrat on 2017/10/24.
 */

public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @NonNull
    @Override
    protected String getFragmentName() {
        return "HomeFragment";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
