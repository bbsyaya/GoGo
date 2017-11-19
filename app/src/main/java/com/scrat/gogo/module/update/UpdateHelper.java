package com.scrat.gogo.module.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.local.Preferences;
import com.scrat.gogo.data.model.UpdateInfo;
import com.scrat.gogo.framework.util.L;
import com.scrat.gogo.framework.util.Utils;

/**
 * Created by scrat on 2017/11/19.
 */

public class UpdateHelper {
    public interface UpdateListener {
        void update(boolean force, UpdateInfo info);
    }

    public static void checkUpdate(final Context context, final UpdateListener listener) {
        DataRepository.getInstance().getApi()
                .checkUpdate(new DefaultLoadObjCallback<UpdateInfo, Res.UpdateInfoRes>() {
                    @Override
                    protected void onSuccess(UpdateInfo updateInfo) {
                        int lastVerCode = Preferences.getInstance().getLastCheckVerCode();
                        int forceVer = updateInfo.getForceVer();
                        int verCode = Utils.getVersionCode(context);
                        int serverVer = updateInfo.getVer();
                        L.v("last:%s\tcurr:%s\tserver:%s\tfoce:%s", lastVerCode, verCode, serverVer, forceVer);

                        if (verCode < forceVer) {
                            listener.update(true, updateInfo);
                            return;
                        }

                        if (serverVer == lastVerCode) {
                            return;
                        }

                        if (serverVer <= verCode) {
                            return;
                        }

                        listener.update(false, updateInfo);
                    }

                    @Override
                    public void onError(Exception ignore) {
                    }

                    @NonNull
                    @Override
                    protected Class<Res.UpdateInfoRes> getResClass() {
                        return Res.UpdateInfoRes.class;
                    }
                });
    }

    public static void downloadApk(Activity activity, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), "请前往应用市场更新", Toast.LENGTH_LONG).show();
        }
    }
}
