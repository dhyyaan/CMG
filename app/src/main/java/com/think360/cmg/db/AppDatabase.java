package com.think360.cmg.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by think360 on 01/05/17.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "CMG_DB"; // we will add the .db extension

    public static final int VERSION = 1;
}