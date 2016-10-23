package com.erickmtz.tipcalc.fragments;

import com.erickmtz.tipcalc.models.TipRecord;

/**
 * Created by erick on 10/10/16.
 */
public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);
    void clearList();
}
