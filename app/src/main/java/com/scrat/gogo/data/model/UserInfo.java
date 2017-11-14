package com.scrat.gogo.data.model;

import java.io.Serializable;

/**
 * Created by scrat on 2017/11/14.
 */

public class UserInfo extends User implements Serializable {
    private long coin;

    public long getCoin() {
        return coin;
    }
}
