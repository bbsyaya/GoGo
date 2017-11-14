package com.scrat.gogo.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by scrat on 2017/11/14.
 */

public class CoinPlan implements Serializable {
    @SerializedName("coin_plan_id")
    private String coinPlanId;
    private long fee;
    @SerializedName("coin_count")
    private int coinCount;

    public String getCoinPlanId() {
        return coinPlanId;
    }

    public long getFee() {
        return fee;
    }

    public int getCoinCount() {
        return coinCount;
    }
}
