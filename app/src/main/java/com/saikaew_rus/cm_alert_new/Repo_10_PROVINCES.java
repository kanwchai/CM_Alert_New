package com.saikaew_rus.cm_alert_new;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NB_A on 18/10/2559.
 */
public class Repo_10_PROVINCES {
    private MyDatabase dbHelper;

    public Repo_10_PROVINCES(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public ArrayList<HashMap<String, String>> getProvincesList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_10_PROVINCES.TABLE;

        // TB_10_PROVINCES user = new TB_10_PROVINCES();
        ArrayList<HashMap<String, String>> provinceList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> province = new HashMap<String, String>();
                province.put("province_id", cursor.getString(cursor.getColumnIndex(TB_10_PROVINCES.Province_Id)));
                province.put("province_name", cursor.getString(cursor.getColumnIndex(TB_10_PROVINCES.Province_Name)));

                provinceList.add(province);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return provinceList;
    }

    public String[] getProvincesList_1() {
        List<String> labels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TB_10_PROVINCES.TABLE + " ORDER BY " + TB_10_PROVINCES.Province_Name;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(cursor.getColumnIndex(TB_10_PROVINCES.Province_Name)));
            } while (cursor.moveToNext());
        }

        String[] province = labels.toArray(new String[labels.size()]);

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return province;
    }

}
