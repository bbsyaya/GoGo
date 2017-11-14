package com.scrat.gogo.module.coin;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.scrat.gogo.R;
import com.scrat.gogo.data.model.CoinPlan;
import com.scrat.gogo.databinding.ActivityCoinPlanBinding;
import com.scrat.gogo.databinding.ListItemCoinPlanBinding;
import com.scrat.gogo.framework.common.BaseActivity;

import java.util.List;

/**
 * Created by scrat on 2017/11/14.
 */

public class CoinPlanActivity extends BaseActivity implements CoinPlanContract.View {

    private ActivityCoinPlanBinding binding;
    private CoinPlanContract.Presenter presenter;

    public static void show(Activity activity) {
        Intent i = new Intent(activity, CoinPlanActivity.class);
        activity.startActivity(i);
    }

    @NonNull
    @Override
    protected String getActivityName() {
        return "CoinPlanActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coin_plan);
        binding.topBar.subject.setText("充值");
        new CoinPlanPresenter(this);
        presenter.loadCoinPlan();
    }

    @Override
    public void setPresenter(CoinPlanContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingCoinPlan() {

    }

    @Override
    public void showLoadCoinPlanError(String e) {

    }

    @Override
    public void showCoinPlan(List<CoinPlan> list) {
        binding.planList.removeAllViews();
        int i = 0;
        for (CoinPlan plan : list) {
            ListItemCoinPlanBinding itemBinding = ListItemCoinPlanBinding
                    .inflate(getLayoutInflater(), binding.planList, false);
            itemBinding.item.setText(getCoinItemStr(plan));
            i++;
            if (i >= list.size()) {
                itemBinding.line.setVisibility(View.GONE);
            }
            binding.planList.addView(itemBinding.getRoot());
        }
    }

    private String getCoinItemStr(CoinPlan plan) {
        return String.format("%s元%s竞猜币", plan.getFee() / 100, plan.getCoinCount());
    }
}
