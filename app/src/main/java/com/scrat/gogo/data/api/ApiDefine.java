package com.scrat.gogo.data.api;

/**
 * Created by scrat on 2017/11/3.
 */

public class ApiDefine {
    private static final String HOST = "https://gogo.scrats.cn/api";
    static final String WX_LOGIN = HOST + "/account/wx_login";
    static final String COIN_PLAN_ORDER = HOST + "/pay/alipay/order/coin_plan/%s";
    static final String NEWS = HOST + "/core/news";
    static final String NEWS_DETAIL = HOST + "/core/news/%s";

    static final String PT = "pt";
    static final String APP_KEY = "app_key";
    static final String UID = "uid";
    static final String ACCESS_TOKEN = "access_token";
    static final String INDEX = "index";

    static final String DEFAULT_PT = "app";
    static final String DEFAULT_APP_KEY = "test_key";

    public static final String CODE = "code";
}
