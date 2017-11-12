package com.scrat.gogo.framework.util;

import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by scrat on 2017/11/12.
 */

public class Utils {
    public static String formatDate(long ts) {

        long zeroTs = getZeroTs();

        if (ts > zeroTs) {
            return DateFormat.format("H:mm", ts).toString();
        }

        long yesterdayZeroTs = zeroTs - 1000 * 60 * 60 * 24;
        if (ts > yesterdayZeroTs) {
            return DateFormat.format("昨天 H:mm", ts).toString();
        }

        return DateFormat.format("M月d日 H:mm", ts).toString();

//        long now = System.currentTimeMillis();
//        long betweenSecond = (now - ts) / 1000L;
//        if (betweenSecond < 60) {
//            return "刚刚";
//        }
//
//        if (betweenSecond < 60 * 60) {
//            return String.format(Locale.getDefault(), "%d分钟前", betweenSecond / 60);
//        }
//
//        if (betweenSecond < 24 * 60 * 60) {
//            return String.format(Locale.getDefault(), "%d小时前", betweenSecond / 60 / 60);
//        }
//
//        long zeroSecond = getZeroTs() / 1000;
//        if (betweenSecond < zeroSecond - 24 * 60 * 60) {
//            return "昨天";
//        }
//
//        if (betweenSecond < zeroSecond - 2 * 24 * 60 * 60) {
//            return "前天";
//        }
//
//        return DateFormat.format("M月d日 H:mm", ts).toString();
    }

    private static long getZeroTs() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
