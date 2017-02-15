package com.saikaew_rus.cm_alert_new;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by NB_A on 7/11/2559.
 */
public class A_MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "cm_alert_sqlite.db";
    private static final int DATABASE_VERSION = 1;

    public A_MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
