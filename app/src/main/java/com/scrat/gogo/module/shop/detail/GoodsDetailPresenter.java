package com.scrat.gogo.module.shop.detail;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.model.GoodsDetail;

/**
 * Created by scrat on 2017/11/17.
 */

public class GoodsDetailPresenter implements GoodsDetailContract.Presenter {
    private GoodsDetailContract.View view;
    private String goodsId;

    public GoodsDetailPresenter(GoodsDetailContract.View view, String goodsId) {
        this.view = view;
        this.goodsId = goodsId;
        view.setPresenter(this);
    }

    @Override
    public void loadData() {
        view.showLoadingGoodsDetail();
        DataRepository.getInstance().getApi().getGoodsDetail(
                goodsId, new DefaultLoadObjCallback<GoodsDetail, Res.GoodsDetailRes>() {
                    @Override
                    protected void onSuccess(GoodsDetail goodsDetail) {
                        view.showGoodsDetail(goodsDetail);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoadGoodsDetailError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.GoodsDetailRes> getResClass() {
                        return Res.GoodsDetailRes.class;
                    }
                });
    }
}
