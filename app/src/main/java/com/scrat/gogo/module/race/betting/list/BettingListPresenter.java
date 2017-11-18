package com.scrat.gogo.module.race.betting.list;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.model.UserInfo;

/**
 * Created by scrat on 2017/11/18.
 */

public class BettingListPresenter implements BettingListContract.Presenter {
    private BettingListContract.View view;

    public BettingListPresenter(BettingListContract.View view) {
        this.view = view;
        view.setPresenter(this);
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
