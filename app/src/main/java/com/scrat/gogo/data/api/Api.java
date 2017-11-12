package com.scrat.gogo.data.api;

import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.local.Preferences;
import com.scrat.gogo.data.model.News;
import com.scrat.gogo.data.model.NewsDetail;
import com.scrat.gogo.data.model.TokenInfo;
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

    public Call getCoinPlanOrder(
            long coinPlanId, DefaultLoadObjCallback<String, Res.DefaultStrRes> cb) {
        String url = String.format(APIS.COIN_PLAN_ORDER, coinPlanId);
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
}
