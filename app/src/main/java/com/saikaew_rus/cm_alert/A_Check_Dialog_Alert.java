package com.saikaew_rus.cm_alert;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

/**
 * Created by NB_A on 25/3/2560.
 */

public class A_Check_Dialog_Alert {
    private A_MyDatabase dbHelper;

    public A_Check_Dialog_Alert(Context context) {
        dbHelper = new A_MyDatabase(context);
    }

    public Cursor getDrivingLicense() {
        HashMap<String, String> data = new HashMap<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_9_USER.TABLE + " WHERE " + TB_9_USER.User_Due_Date_Driving + " < DATE('now','+30 day')";

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        db.close();
        return cursor;
    }

    public Cursor getTaxCar() {
        HashMap<String, String> data = new HashMap<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_1_CAR.TABLE + " WHERE " + TB_1_CAR.Car_Tax_Date + " < DATE('now','+30 day')";

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        db.close();
        return cursor;
    }

    public Cursor getDataCar() {
        HashMap<String, String> data = new HashMap<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + TB_1_CAR.TABLE + " WHERE " + TB_1_CAR.Car_Id + " IN " +
                "( SELECT " + TB_4_HISTORYS_OF_CAR.TABLE + "." + TB_4_HISTORYS_OF_CAR.Car_Id + " " +
                "FROM " + TB_4_HISTORYS_OF_CAR.TABLE + "," + TB_2_DUE_OF_PART_FIX.TABLE + " " +
                "ON  " + TB_4_HISTORYS_OF_CAR.TABLE + " ." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " = " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Fix_Due_Id + " " +
                "WHERE " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Fix_Due_Show + " = 1 AND  " + TB_4_HISTORYS_OF_CAR.TABLE + " ." + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + " > DATE('now') " +
                "GROUP BY  " + TB_4_HISTORYS_OF_CAR.TABLE + " ." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " " +
                "UNION " +
                "SELECT  " + TB_4_HISTORYS_OF_CAR.TABLE + " ." + TB_4_HISTORYS_OF_CAR.Car_Id + " " +
                "FROM " + TB_4_HISTORYS_OF_CAR.TABLE + "," + TB_2_DUE_OF_PART_FIX.TABLE + "," + TB_6_RUN_DATA.TABLE + " " +
                "ON  " + TB_4_HISTORYS_OF_CAR.TABLE + " ." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " = " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " AND  " + TB_4_HISTORYS_OF_CAR.TABLE + " ." + TB_4_HISTORYS_OF_CAR.Car_Id + " = " + TB_6_RUN_DATA.TABLE + "." + TB_6_RUN_DATA.Car_Id + " " +
                "WHERE " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Fix_Due_Show + " = 1 AND  " + TB_4_HISTORYS_OF_CAR.TABLE + " ." + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + " < " + TB_6_RUN_DATA.TABLE + "." + TB_6_RUN_DATA.Run_Kilo_End + " " +
                "GROUP BY  " + TB_4_HISTORYS_OF_CAR.TABLE + " ." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + ")";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        db.close();
        return cursor;
    }
}
