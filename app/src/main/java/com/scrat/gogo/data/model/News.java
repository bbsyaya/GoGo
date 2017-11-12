package com.scrat.gogo.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by scrat on 2017/11/12.
 */

public class News implements Serializable {
    @SerializedName("news_id")
    private String newsId;
    private String title;
    private String tp;
//    @SerializedName("news_ts")
//    private long newsTs;
    @SerializedName("comment_count")
    private int totalComment;
    private String cover;

    public String getNewsId() {
        return newsId;
    }

    public String getTitle() {
        return title;
    }

    public String getTp() {
        return tp;
    }

//    public long getNewsTs() {
//        return newsTs;
//    }

    public int getTotalComment() {
        return totalComment;
    }

    public String getCover() {
        return cover;
    }
}
