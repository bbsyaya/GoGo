package com.scrat.gogo.module.coin;

import com.scrat.gogo.data.model.CoinPlan;
import com.scrat.gogo.framework.common.BaseContract;

import java.util.List;

/**
 * Created by scrat on 2017/11/14.
 */

public interface CoinPlanContract {
    interface Presenter {
        void loadCoinPlan();
    }

    interface View extends BaseContract.BaseView<Presenter> {
        void showLoadingCoinPlan();

        void showLoadCoinPlanError(String e);

        void showCoinPlan(List<CoinPlan> list);
    }
}
