package com.scrat.gogo.module.race.betting.list;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.model.Betting;
import com.scrat.gogo.data.model.UserInfo;

import java.util.List;

/**
 * Created by scrat on 2017/11/18.
 */

public class BettingListPresenter implements BettingListContract.Presenter {
    private BettingListContract.View view;
    private String raceId;
    private String tp;

    public BettingListPresenter(BettingListContract.View view, String raceId, String tp) {
        this.tp = tp;
        this.raceId = raceId;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadBetting() {
        view.showBettingLoading();
        DataRepository.getInstance().getApi().getBettingItem(
                raceId, tp, new DefaultLoadObjCallback<List<Betting>, Res.BettingListRes>() {
                    @Override
                    protected void onSuccess(List<Betting> list) {
                        view.showBetting(list);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showBettingLoadError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.BettingListRes> getResClass() {
                        return Res.BettingListRes.class;
                    }
                });
    }

    @Override
    public void betting(String bettingItemId, int coin) {
        view.showBettingExecuting();
        DataRepository.getInstance().getApi().betting(
                coin, bettingItemId, new DefaultLoadObjCallback<String, Res.DefaultStrRes>() {
                    @Override
                    protected void onSuccess(String s) {
                        view.showBettingExecuteSuccess();
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showBettingExecuteError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.DefaultStrRes> getResClass() {
                        return Res.DefaultStrRes.class;
                    }
                });
    }

    @Override
    public void loadCoinInfo() {
        view.showLoadingCoin();
        DataRepository.getInstance().getApi().getUserInfo(
                new DefaultLoadObjCallback<UserInfo, Res.UserInfoRes>() {
                    @Override
                    protected void onSuccess(UserInfo info) {
                        view.showUserCoin(info.getCoin());
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoadingCoinError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.UserInfoRes> getResClass() {
                        return Res.UserInfoRes.class;
                    }
                });
    }
}
