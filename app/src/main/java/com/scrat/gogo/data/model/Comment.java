package com.scrat.gogo.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by scrat on 2017/11/12.
 */

public class Comment implements Serializable {
    @SerializedName("comment_id")
    private String commentId;
    private String content;
    @SerializedName("create_ts")
    private long createTs;

    public String getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public long getCreateTs() {
        return createTs;
    }
}
