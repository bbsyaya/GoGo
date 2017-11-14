package com.scrat.gogo.module.coin;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.model.CoinPlan;
import com.scrat.gogo.data.model.WxPayInfo;

import java.util.List;

/**
 * Created by scrat on 2017/11/14.
 */

public class CoinPlanPresenter implements CoinPlanContract.Presenter {
    private CoinPlanContract.View view;
    private CoinPlan plan;
    private int state;
    private static final int STATE_WEIXIN = 0;
    private static final int STATE_ALIPAY = 1;

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

    @Override
    public void selectCoinPlan(CoinPlan plan) {
        this.plan = plan;
    }

    @Override
    public void selectWeixinPay() {
        state = STATE_WEIXIN;
    }

    @Override
    public void selectAlipay() {
        state = STATE_ALIPAY;
    }

    @Override
    public void pay() {
        if (plan == null) {
            loadCoinPlan();
            return;
        }

        view.showCreatingOrder();
        if (state == STATE_WEIXIN) {
            DataRepository.getInstance().getApi().getWeixinCoinPlanOrder(
                    plan.getCoinPlanId(), new DefaultLoadObjCallback<WxPayInfo, Res.WxPayInfoRes>() {
                        @Override
                        protected void onSuccess(WxPayInfo wxPayInfo) {
                            view.showCreateWeixinOrderSuccess(wxPayInfo);
                        }

                        @Override
                        public void onError(Exception e) {
                            view.showCreateOrderFail(e.getMessage());
                        }

                        @NonNull
                        @Override
                        protected Class<Res.WxPayInfoRes> getResClass() {
                            return Res.WxPayInfoRes.class;
                        }
                    });
            return;
        }
    }
}
