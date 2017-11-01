package com.scrat.gogo;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by scrat on 2017/11/1.
 */

public class GoGoApp extends Application {

    private static final String WX_APP_ID = "wx3349545f4d083130";
    public static IWXAPI WX_API;

    @Override
    public void onCreate() {
        super.onCreate();
        WX_API = WXAPIFactory.createWXAPI(this, WX_APP_ID);
        WX_API.registerApp(WX_APP_ID);
    }
}
