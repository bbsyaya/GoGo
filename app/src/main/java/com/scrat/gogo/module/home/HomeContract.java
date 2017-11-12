package com.scrat.gogo.module.home;

import com.scrat.gogo.data.model.News;
import com.scrat.gogo.framework.common.BaseContract;

/**
 * Created by scrat on 2017/11/12.
 */

public interface HomeContract {
    interface HomePresenter {
        void loadData(boolean refresh);
    }

    interface HomeView extends BaseContract.BaseListView<HomePresenter, News> {
    }
}
