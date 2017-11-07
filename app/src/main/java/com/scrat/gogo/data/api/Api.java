package com.scrat.gogo.data.api;

import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.local.Preferences;
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
        header.put(ApiDefine.PT, ApiDefine.DEFAULT_PT);
        header.put(ApiDefine.APP_KEY, ApiDefine.DEFAULT_APP_KEY);
        header.put(ApiDefine.UID, Preferences.getInstance().getUid());
        header.put(ApiDefine.ACCESS_TOKEN, Preferences.getInstance().getAccessToken());
        return header;
    }

    public Call wxLogin(String code, DefaultLoadObjCallback<TokenInfo, Res.TokenRes> cb) {
        JSONObject obj = new JSONObject();
        try {
            obj.put(ApiDefine.CODE, code);
            return OkHttpHelper.getInstance()
                    .post(ApiDefine.WX_LOGIN, getHeader(), obj.toString(), cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }

    public Call getCoinPlanOrder(long coinPlanId, DefaultLoadObjCallback<String, Res.DefaultStrRes> cb) {
        String url = String.format(ApiDefine.COIN_PLAN_ORDER, coinPlanId);
        try {
            return OkHttpHelper.getInstance().post(url, getHeader(), null, cb);
        } catch (Exception e) {
            cb.onError(e);
            return null;
        }
    }
}
