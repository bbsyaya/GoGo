package com.scrat.gogo.module.coin;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.scrat.gogo.R;
import com.scrat.gogo.data.model.CoinPlan;
import com.scrat.gogo.data.model.WxPayInfo;
import com.scrat.gogo.databinding.ActivityCoinPlanBinding;
import com.scrat.gogo.databinding.ListItemCoinPlanBinding;
import com.scrat.gogo.framework.common.BaseActivity;
import com.scrat.gogo.wxapi.WXPayEntryActivity;

import java.util.List;

/**
 * Created by scrat on 2017/11/14.
 */

public class CoinPlanActivity extends BaseActivity implements CoinPlanContract.View {

    private ActivityCoinPlanBinding binding;
    private CoinPlanContract.Presenter presenter;
    private TextView selectedPlan;
    private static final int REQUEST_CODE_WXPAY = 1;

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
        selectWeixinPay(binding.weixinPay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_WXPAY:
                if (WXPayEntryActivity.paySuccess(data)) {
                    toast("支付成功");
                    finish();
                }
                break;
        }
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
        for (final CoinPlan plan : list) {
            final ListItemCoinPlanBinding itemBinding = ListItemCoinPlanBinding
                    .inflate(getLayoutInflater(), binding.planList, false);
            itemBinding.item.setText(getCoinItemStr(plan));
            i++;
            if (i == 1) {
                selectPlan(itemBinding.item, plan);
            }
            if (i >= list.size()) {
                itemBinding.line.setVisibility(View.GONE);
            }
            itemBinding.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectPlan(itemBinding.item, plan);
                }
            });
            binding.planList.addView(itemBinding.getRoot());
        }
    }

    @Override
    public void showCreatingOrder() {

    }

    @Override
    public void showCreateOrderFail(String e) {

    }

    @Override
    public void showCreateWeixinOrderSuccess(WxPayInfo info) {
        WXPayEntryActivity.show(this, REQUEST_CODE_WXPAY, info);
    }

    private synchronized void selectPlan(TextView textView, CoinPlan plan) {
        presenter.selectCoinPlan(plan);
        if (selectedPlan == textView) {
            return;
        }
        if (selectedPlan != null) {
            selectedPlan.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        selectedPlan = textView;
        selectedPlan.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_select_16, 0);
    }

    private String getCoinItemStr(CoinPlan plan) {
        return String.format("%s元%s竞猜币", plan.getFee() / 100, plan.getCoinCount());
    }

    public void selectWeixinPay(View view) {
        presenter.selectWeixinPay();
        binding.weixinPay.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_select_16, 0);
        binding.alipay.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    public void selectAlipay(View view) {
        presenter.selectAlipay();
        binding.weixinPay.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        binding.alipay.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_select_16, 0);
    }

    public void pay(View view) {
        presenter.pay();
    }
}
