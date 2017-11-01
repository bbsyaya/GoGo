package com.scrat.gogo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.scrat.gogo.GoGoApp;
import com.scrat.gogo.framework.util.L;
import com.scrat.gogo.module.LoginActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by scrat on 2017/11/1.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public static boolean login() {
        if (!GoGoApp.WX_API.isWXAppInstalled()) {
            return false;
        }

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        GoGoApp.WX_API.sendReq(req);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHandleIntent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        setHandleIntent();
    }

    private void setHandleIntent() {
        try {
            GoGoApp.WX_API.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        L.d(baseReq.transaction);
        L.d(baseReq.openId);
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp resp = ((SendAuth.Resp) baseResp);
                LoginActivity.showWxLoginSuccess(this, resp.code);
                break;
            default:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
        }
//        L.d(baseResp.errCode + "");
//        L.d(baseResp.errStr);
//        L.d(baseResp.transaction);
//        L.d(baseResp.openId);
//        // 获取code
//        SendAuth.Resp resp = ((SendAuth.Resp) baseResp);
//        L.e(resp.code);
//        L.e(resp.state);
        finish();
    }
}
