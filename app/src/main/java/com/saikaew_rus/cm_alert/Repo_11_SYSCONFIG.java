package com.saikaew_rus.cm_alert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by NB_A on 20/3/2560.
 */

public class Repo_11_SYSCONFIG {
    private A_MyDatabase dbHelper;

    public Repo_11_SYSCONFIG(Context context) {
        dbHelper = new A_MyDatabase(context);
    }

    public int chkLang() {
        int i;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_11_Sysconfig.TABLE +
                " WHERE " + TB_11_Sysconfig.Sys_Code + " = " + TB_11_Sysconfig.Sys_Code_Language;

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        i = cursor.getCount();

        cursor.close();
        db.close();

        return i;
    }

    public void insert(TB_11_Sysconfig tb_11_sysconfig) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_11_Sysconfig.Sys_Code, tb_11_sysconfig.sys_Code);
        values.put(TB_11_Sysconfig.Sys_Desc, tb_11_sysconfig.sys_Desc);
        values.put(TB_11_Sysconfig.Sys_Value, tb_11_sysconfig.sys_Value);

        db.insert(TB_11_Sysconfig.TABLE, null, values);
        db.close();
    }

    public void update(String code,String value) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_11_Sysconfig.Sys_Value, value);

        db.update(TB_11_Sysconfig.TABLE, values, TB_11_Sysconfig.Sys_Code + "=" + code, null);
        db.close();
    }

    public HashMap<String, String> getConfig(String find, String sysCode) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_11_Sysconfig.TABLE + " WHERE " + find + " = " + sysCode;

        Log.d("showQuery", selectQuery);

        HashMap<String, String> dataConfig = new HashMap<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                dataConfig.put(TB_11_Sysconfig.Sys_Id, cursor.getString(cursor.getColumnIndex(TB_11_Sysconfig.Sys_Id)));
                dataConfig.put(TB_11_Sysconfig.Sys_Code, cursor.getString(cursor.getColumnIndex(TB_11_Sysconfig.Sys_Code)));
                dataConfig.put(TB_11_Sysconfig.Sys_Desc, cursor.getString(cursor.getColumnIndex(TB_11_Sysconfig.Sys_Desc)));
                dataConfig.put(TB_11_Sysconfig.Sys_Value, cursor.getString(cursor.getColumnIndex(TB_11_Sysconfig.Sys_Value)));

                Log.d("showDataChk", cursor.getString(cursor.getColumnIndex(TB_11_Sysconfig.Sys_Id))
                        + " ++ " + cursor.getString(cursor.getColumnIndex(TB_11_Sysconfig.Sys_Code))
                        + " ++ " + cursor.getString(cursor.getColumnIndex(TB_11_Sysconfig.Sys_Desc))
                        + " ++ " + cursor.getString(cursor.getColumnIndex(TB_11_Sysconfig.Sys_Value)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dataConfig;
    }
}




