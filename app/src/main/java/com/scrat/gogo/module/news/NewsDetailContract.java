package com.scrat.gogo.module.news;

import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.model.NewsDetail;
import com.scrat.gogo.framework.common.BaseContract;

/**
 * Created by scrat on 2017/11/12.
 */

public interface NewsDetailContract {
    interface Presenter {
        void loadNewsDetail();

        void loadComment(boolean refresh);
    }

    interface View extends BaseContract.BaseListView<Presenter, Res.CommentItem> {
        void showLoadingNewsDetail();

        void showLoadNewsDetailError(String msg);

        void showNewsDetail(NewsDetail detail);
    }
}
