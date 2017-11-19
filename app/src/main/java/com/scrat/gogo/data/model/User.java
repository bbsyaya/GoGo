package com.scrat.gogo.data.model;

import java.io.Serializable;

/**
 * Created by scrat on 2017/11/12.
 */

public class User implements Serializable {
    private String uid;
    private String username;
    private String avatar;
    private String gender;

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getGender() {
        return gender;
    }
}
