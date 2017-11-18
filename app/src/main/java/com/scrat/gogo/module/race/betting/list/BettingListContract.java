package com.scrat.gogo.module.race.betting.list;

import com.scrat.gogo.framework.common.BaseContract;

/**
 * Created by scrat on 2017/11/18.
 */

public interface BettingListContract {
    interface Presenter {
        void betting(String bettingItemId, int coin);

        void loadCoinInfo();
    }

    interface View extends BaseContract.BaseView<Presenter> {
        void showBettingExecuting();

        void showBettingExecuteSuccess();

        void showBettingExecuteError(String e);

        void showLoadingCoin();

        void showLoadingCoinError(String e);

        void showUserCoin(long coin);
    }
}
