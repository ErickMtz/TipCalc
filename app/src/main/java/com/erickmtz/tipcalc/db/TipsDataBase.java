package com.erickmtz.tipcalc.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by erick on 3/11/16.
 */
@Database(name=TipsDataBase.NAME,version=TipsDataBase.VERSION)
public class TipsDataBase {
    public static final String NAME = "Tips";
    public static final int VERSION = 1;

}
