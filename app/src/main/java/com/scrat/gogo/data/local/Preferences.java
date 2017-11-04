package com.scrat.gogo.data.local;

import android.content.Context;

import com.scrat.gogo.framework.common.BaseSharedPreferences;

/**
 * Created by scrat on 2017/11/3.
 */

public class Preferences extends BaseSharedPreferences {
    private static final String FILE_NAME = "conf";

    private static class SingletonHolder {
        private static Preferences instance = new Preferences();
    }

    public static Preferences getInstance() {
        return SingletonHolder.instance;
    }

    public void init(Context applicationContext) {
        init(applicationContext, FILE_NAME);
    }

    private static final String UID = "uid";

    public String getUid() {
        return getString(UID, "");
    }

    public void setUid(String uid) {
        setString(UID, uid);
    }

    private static final String ACCESS_TOKEN = "access_token";

    public String getAccessToken() {
        return getString(ACCESS_TOKEN, "");
    }

    public void setAccessToken(String accessToken) {
        setString(ACCESS_TOKEN, accessToken);
    }

    private static final String REFRESH_TOKEN = "refresh_token";

    public String getRefreshToken() {
        return getString(REFRESH_TOKEN, "");
    }

    public void setRefreshToken(String refreshToken) {
        setString(REFRESH_TOKEN, refreshToken);
    }

    public void clearAuth() {
        removeItem(UID);
        removeItem(ACCESS_TOKEN);
        removeItem(REFRESH_TOKEN);
    }
}
