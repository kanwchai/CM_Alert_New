package com.saikaew_rus.cm_alert_new;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by NB_A on 7/11/2559.
 */
public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "cm_a_origin_2.db";
    private static final int DATABASE_VERSION = 2;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
