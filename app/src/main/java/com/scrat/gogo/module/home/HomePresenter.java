package com.scrat.gogo.module.home;

import android.support.annotation.NonNull;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.model.News;
import com.scrat.gogo.framework.util.L;

import java.util.List;

import okhttp3.Call;

/**
 * Created by scrat on 2017/11/12.
 */

public class HomePresenter implements HomeContract.HomePresenter {
    private HomeContract.HomeView view;
    private String index;
    private Call call;

    public HomePresenter(HomeContract.HomeView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadBanner() {
        DataRepository.getInstance().getApi().getBanner(
                new DefaultLoadObjCallback<List<News>, Res.BannerRes>() {
                    @Override
                    protected void onSuccess(List<News> list) {
                        view.showBanner(list);
                    }

                    @Override
                    public void onError(Exception e) {
                        L.e(e);
                    }

                    @NonNull
                    @Override
                    protected Class<Res.BannerRes> getResClass() {
                        return Res.BannerRes.class;
                    }
                });
    }

    @Override
    public void loadData(final boolean refresh) {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }

        if (refresh) {
            index = "0";
        }

        if (!Res.ListRes.hasMoreData(index)) {
            view.showNoMoreListData();
            return;
        }

        view.showLoadingList();
        call = DataRepository.getInstance().getApi().getNews(
                index, new DefaultLoadObjCallback<Res.ListRes<News>, Res.NewsListRes>() {
                    @Override
                    protected void onSuccess(Res.ListRes<News> newsListRes) {
                        index = newsListRes.getIndex();
                        if (newsListRes.isEmpty()) {
                            if (refresh) {
                                view.showEmptyList();
                                return;
                            }
                            view.showNoMoreListData();
                            return;
                        }

                        view.showListData(newsListRes.getItems(), refresh);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoadingListError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.NewsListRes> getResClass() {
                        return Res.NewsListRes.class;
                    }
                });
    }
}
