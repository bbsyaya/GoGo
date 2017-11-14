package com.scrat.gogo.module.coin;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.model.CoinPlan;

import java.util.List;

/**
 * Created by scrat on 2017/11/14.
 */

public class CoinPlanPresenter implements CoinPlanContract.Presenter {
    private CoinPlanContract.View view;

    public CoinPlanPresenter(CoinPlanContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadCoinPlan() {
        view.showLoadingCoinPlan();
        DataRepository.getInstance().getApi().getCoinPlan(
                new DefaultLoadObjCallback<List<CoinPlan>, Res.CoinPlanListRes>() {
                    @Override
                    protected void onSuccess(List<CoinPlan> coinPlans) {
                        view.showCoinPlan(coinPlans);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoadCoinPlanError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.CoinPlanListRes> getResClass() {
                        return Res.CoinPlanListRes.class;
                    }
                });
    }
}
