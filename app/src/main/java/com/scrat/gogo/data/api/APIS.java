package com.scrat.gogo.data.api;

/**
 * Created by scrat on 2017/11/3.
 */

public class APIS {
    private static final String HOST = "https://gogo.scrats.cn/api";
    static final String WX_LOGIN = HOST + "/account/wx_login";
    static final String ALIPAY_COIN_PLAN_ORDER = HOST + "/pay/alipay/order/coin_plan/%s";
    static final String NEWS_LIST_URL = HOST + "/core/news";
    static final String NEWS_DETAIL_URL = HOST + "/core/news/%s";
    static final String COMMENT_LIST_URL = HOST + "/core/comments";
    static final String GOODS_LIST_URL = HOST + "/mall/goods";
    static final String QINUIU_UPTOKEN_URL = HOST + "/file/qiniu_token";
    static final String COIN_PLAN_WEIXIN_ORDER_URL = HOST + "/pay/weixin/order/coin_plan/%s";
    static final String SEND_SMS_URL = HOST + "/account/sms";
    static final String SMS_LOGIN_URL = HOST + "/account/sms_login";
    static final String LOGOUT_URL = HOST + "/account/logout";
    static final String USER_INFO_URL = HOST + "/core/user";

    static final String PT = "pt";
    static final String APP_KEY = "app_key";
    static final String UID = "uid";
    static final String ACCESS_TOKEN = "access_token";
    static final String INDEX = "index";
    static final String TP = "tp";
    static final String TARGET_ID = "target_id";
    static final String NEWS = "news";
    static final String TEL = "tel";
    static final String CODE = "code";
    static final String REFRESH_TOKEN = "refresh_token";

    static final String DEFAULT_PT = "app";
    static final String DEFAULT_APP_KEY = "test_key";
}
