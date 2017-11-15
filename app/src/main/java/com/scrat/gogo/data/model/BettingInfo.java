package com.scrat.gogo.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by scrat on 2017/11/15.
 */

public class BettingInfo implements Serializable {
    private RaceDetail race;
    private List<Betting> betting;

    public RaceDetail getRace() {
        return race;
    }

    public List<Betting> getBetting() {
        return betting;
    }
}
