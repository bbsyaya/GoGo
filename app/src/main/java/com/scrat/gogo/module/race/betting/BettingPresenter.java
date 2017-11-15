package com.scrat.gogo.module.race.betting;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.model.BettingInfo;

/**
 * Created by scrat on 2017/11/15.
 */

public class BettingPresenter implements BettingContract.Presenter {
    private BettingContract.View view;
    private String raceId;

    public BettingPresenter(BettingContract.View view, String raceId) {
        this.raceId = raceId;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadData() {
        view.showLoadingBetting();
        DataRepository.getInstance().getApi().getBettingDetail(
                raceId,
                new DefaultLoadObjCallback<BettingInfo, Res.BettingInfoRes>() {
                    @Override
                    protected void onSuccess(BettingInfo bettingInfo) {
                        view.showBetting(bettingInfo);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoadBettingError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.BettingInfoRes> getResClass() {
                        return Res.BettingInfoRes.class;
                    }
                });
    }
}
