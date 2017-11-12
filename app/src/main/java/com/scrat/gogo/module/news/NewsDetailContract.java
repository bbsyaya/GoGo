package com.scrat.gogo.module.news;

import com.scrat.gogo.data.model.NewsDetail;
import com.scrat.gogo.framework.common.BaseContract;

/**
 * Created by scrat on 2017/11/12.
 */

public interface NewsDetailContract {
    interface Presenter {
        void loadNewsDetail();
    }

    interface View extends BaseContract.BaseView<Presenter> {
        void showLoadingNewsDetail();

        void showLoadNewsDetailError(String msg);

        void showNewsDetail(NewsDetail detail);
    }
}
