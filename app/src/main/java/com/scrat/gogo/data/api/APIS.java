package com.scrat.gogo.data.api;

/**
 * Created by scrat on 2017/11/3.
 */

public class APIS {
    private static final String HOST = "https://gogo.scrats.cn/api";
    static final String WX_LOGIN = HOST + "/account/wx_login";
    static final String COIN_PLAN_ORDER = HOST + "/pay/alipay/order/coin_plan/%s";
    static final String NEWS_LIST_URL = HOST + "/core/news";
    static final String NEWS_DETAIL_URL = HOST + "/core/news/%s";
    static final String COMMENT_LIST_URL = HOST + "/core/comments";

    static final String PT = "pt";
    static final String APP_KEY = "app_key";
    static final String UID = "uid";
    static final String ACCESS_TOKEN = "access_token";
    static final String INDEX = "index";
    static final String TP = "tp";
    static final String TARGET_ID = "target_id";
    static final String NEWS = "news";

    static final String DEFAULT_PT = "app";
    static final String DEFAULT_APP_KEY = "test_key";

    public static final String CODE = "code";
}
