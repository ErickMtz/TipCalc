package com.erickmtz.tipcalc.utils;

import com.erickmtz.tipcalc.entity.TipRecord;

import java.text.SimpleDateFormat;

/**
 * Created by erick on 3/11/16.
 */
public class TipUtils {
    public static double getTip(TipRecord tipRecord) {
        return tipRecord.getBill()*(tipRecord.getTipPercentage()/100d);
    }

    public static String getDateFormated(TipRecord tipRecord) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd, yyyy HH:mm");
        return simpleDateFormat.format(tipRecord.getTimestamp());
    }
}
