package com.scrat.gogo.data.api;

import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.local.Preferences;
import com.scrat.gogo.data.model.Goods;
import com.scrat.gogo.data.model.News;
import com.scrat.gogo.data.model.NewsDetail;
import com.scrat.gogo.data.model.TokenInfo;
import com.scrat.gogo.data.model.Uptoken;
import com.scrat.gogo.data.model.WxPayInfo;
import com.scrat.gogo.framework.util.OkHttpHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by scrat on 2017/11/2.
 */

public class Api {

    private Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        header.put(APIS.PT, APIS.DEFAULT_PT);
        header.put(APIS.APP_KEY, APIS.DEFAULT_APP_KEY);
        header.put(APIS.UID, Preferences.getInstance().getUid());
        header.put(APIS.ACCESS_TOKEN, Preferences.getInstance().getAccessToken());
        return header;
    }

    public Call wxLogin(String code, DefaultLoadObjCallback<TokenInfo, Res.TokenRes> cb) {
        JSONObject obj = new JSONObject();
        try {
            obj.put(APIS.CODE, code);
            return OkHttpHelper.getInstance()
                    .post(APIS.WX_LOGIN, getHeader(), obj.toString(), cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public Call getAlipayCoinPlanOrder(
            String coinPlanId, DefaultLoadObjCallback<String, Res.DefaultStrRes> cb) {
        String url = String.format(APIS.ALIPAY_COIN_PLAN_ORDER, coinPlanId);
        try {
            return OkHttpHelper.getInstance().post(url, getHeader(), null, cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public Call getWeixinCoinPlanOrder(
            String coinPlanId, DefaultLoadObjCallback<WxPayInfo, Res.WxPayInfoRes> cb) {
        String url = String.format(APIS.COIN_PLAN_WEIXIN_ORDER_URL, coinPlanId);
        try {
            return OkHttpHelper.getInstance().post(url, getHeader(), null, cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public Call getNews(
            String index, DefaultLoadObjCallback<Res.ListRes<News>, Res.NewsListRes> cb) {
        Map<String, String> param = new HashMap<>();
        param.put(APIS.INDEX, index);
        try {
            return OkHttpHelper.getInstance().get(APIS.NEWS_LIST_URL, getHeader(), param, cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public Call getNewsDetail(
            String newsId, DefaultLoadObjCallback<NewsDetail, Res.NewsDetailRes> cb) {
        String url = String.format(APIS.NEWS_DETAIL_URL, newsId);
        try {
            return OkHttpHelper.getInstance().get(url, getHeader(), null, cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    private Call getComments(String tp,
                             String tpId,
                             String index,
                             DefaultLoadObjCallback<Res.ListRes<Res.CommentItem>, Res.CommentItemListRes> cb) {
        Map<String, String> param = new HashMap<>();
        param.put(APIS.INDEX, index);
        param.put(APIS.TP, tp);
        param.put(APIS.TARGET_ID, tpId);
        try {
            return OkHttpHelper.getInstance().get(APIS.COMMENT_LIST_URL, getHeader(), param, cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public Call getNewsComments(
            String newsId,
            String index,
            DefaultLoadObjCallback<Res.ListRes<Res.CommentItem>, Res.CommentItemListRes> cb) {
        return getComments(APIS.NEWS, newsId, index, cb);
    }

    public Call getGoodsList(String tp,
                             String index,
                             DefaultLoadObjCallback<Res.ListRes<Goods>, Res.GoodsListRes> cb) {

        Map<String, String> param = new HashMap<>();
        param.put(APIS.INDEX, index);
        param.put(APIS.TP, tp);
        try {
            return OkHttpHelper.getInstance().get(APIS.GOODS_LIST_URL, getHeader(), param, cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public Call getQiniuUptoken(DefaultLoadObjCallback<Uptoken, Res.UptokenRes> cb) {
        try {
            return OkHttpHelper.getInstance()
                    .get(APIS.QINUIU_UPTOKEN_URL, getHeader(), null, cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public Call sendSms(String tel, DefaultLoadObjCallback<String, Res.DefaultStrRes> cb) {
        JSONObject obj = new JSONObject();
        try {
            obj.put(APIS.TEL, tel);
            return OkHttpHelper.getInstance()
                    .post(APIS.SEND_SMS_URL, getHeader(), obj.toString(), cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public Call smsLogin(
            String tel, String code, DefaultLoadObjCallback<TokenInfo, Res.TokenRes> cb) {
        JSONObject obj = new JSONObject();
        try {
            obj.put(APIS.TEL, tel);
            obj.put(APIS.CODE, code);
            return OkHttpHelper.getInstance()
                    .post(APIS.SMS_LOGIN_URL, getHeader(), obj.toString(), cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public void logout(String refreshToken) {
        JSONObject obj = new JSONObject();
        try {
            obj.put(APIS.REFRESH_TOKEN, refreshToken);
            OkHttpHelper.getInstance()
                    .post(APIS.LOGOUT_URL, getHeader(), obj.toString(), null);
        } catch (Exception ignore) {
        }
    }
}
