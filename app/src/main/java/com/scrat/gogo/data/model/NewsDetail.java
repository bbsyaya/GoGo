package com.scrat.gogo.data.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by scrat on 2017/11/12.
 */

public class NewsDetail extends News implements Serializable {
    private String body;
    private String url;
    private String video;

    public boolean isWebViewNews() {
        return !TextUtils.isEmpty(url);
    }

    public boolean isVideoNews() {
        return !TextUtils.isEmpty(video);
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    public String getVideo() {
        return video;
    }
}
