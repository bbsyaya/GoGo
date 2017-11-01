package com.scrat.gogo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.scrat.gogo.databinding.ActivityMainBinding;
import com.scrat.gogo.framework.common.BaseActivity;
import com.scrat.gogo.module.HomeFragment;
import com.scrat.gogo.module.LoginActivity;
import com.scrat.gogo.module.MeFragment;
import com.scrat.gogo.module.RaceFragment;
import com.scrat.gogo.module.ShopFragment;
import com.scrat.gogo.wxapi.WXEntryActivity;

public class MainActivity extends BaseActivity {

    private HomeFragment homeFragment;
    private RaceFragment raceFragment;
    private ShopFragment shopFragment;
    private MeFragment meFragment;

    private Fragment currFragment;
    private ActivityMainBinding binding;

    @NonNull
    @Override
    protected String getActivityName() {
        return "MainActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initFragment();
        navigateToHome(null);
    }

    private void initFragment() {
        homeFragment = HomeFragment.newInstance();
        raceFragment = RaceFragment.newInstance();
        shopFragment = ShopFragment.newInstance();
        meFragment = MeFragment.newInstance();
    }

    public void navigateToHome(View view) {
        switchFragment(homeFragment);

        binding.home.setTextColor(ContextCompat.getColor(this, R.color.c01_blue));
        binding.race.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
        binding.shop.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
        binding.me.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
    }

    public void navigateToRace(View view) {
        switchFragment(raceFragment);

        binding.home.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
        binding.race.setTextColor(ContextCompat.getColor(this, R.color.c01_blue));
        binding.shop.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
        binding.me.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
    }

    public void navigateToShop(View view) {
        switchFragment(shopFragment);

        binding.home.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
        binding.race.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
        binding.shop.setTextColor(ContextCompat.getColor(this, R.color.c01_blue));
        binding.me.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
    }

    public void navigateToMe(View view) {
        LoginActivity.show(this);
//        switchFragment(meFragment);
//
//        binding.home.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
//        binding.race.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
//        binding.shop.setTextColor(ContextCompat.getColor(this, R.color.c10_icon));
//        binding.me.setTextColor(ContextCompat.getColor(this, R.color.c01_blue));
    }

    private void switchFragment(Fragment target) {
        currFragment = switchFragment(R.id.content, currFragment, target);
    }

    private Fragment switchFragment(int containerViewId, Fragment from, Fragment to) {
        if (from != null && from == to) {
            return to;
        }
        Animation fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        fadeOut.setDuration(150L);
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(150L);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from != null) {
            if (from.getView() != null) {
                from.getView().startAnimation(fadeOut);
            }
            transaction.hide(from);
        }
        if (!to.isAdded()) {
            transaction.add(containerViewId, to);
        } else {
            transaction.show(to);
        }
        if (to.getView() != null) {
            to.getView().startAnimation(fadeIn);
        }
        transaction.commit();
        return to;
    }
}
