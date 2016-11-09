package com.saikaew_rus.cm_alert_new;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NB_A on 18/10/2559.
 */
public class Repo_6_RUN_DATA {
    private MyDatabase dbHelper;

    public Repo_6_RUN_DATA(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public int insert(TB_6_RUN_DATA run_data) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_6_RUN_DATA.Car_Id, run_data.car_Id);
        values.put(TB_6_RUN_DATA.Run_Date_Start, run_data.run_Date_Start);
        values.put(TB_6_RUN_DATA.Run_Date_End, run_data.run_Date_End);
        values.put(TB_6_RUN_DATA.Run_Kilo_Start, run_data.run_Kilo_Start);
        values.put(TB_6_RUN_DATA.Run_Kilo_End, run_data.run_Kilo_End);

        // Inserting Row
        long run_id = db.insert(TB_6_RUN_DATA.TABLE, null, values);
        // Closing database connection
        db.close();
        return (int) run_id;
    }

    public void delete(int run_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TB_6_RUN_DATA.TABLE, TB_6_RUN_DATA.Run_Id + "=?", new String[]{String.valueOf(run_id)});
        db.close();
    }

    public void update(TB_6_RUN_DATA run_data) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_6_RUN_DATA.Car_Id, run_data.car_Id);
        values.put(TB_6_RUN_DATA.Run_Date_Start, run_data.run_Date_Start);
        values.put(TB_6_RUN_DATA.Run_Date_End, run_data.run_Date_End);
        values.put(TB_6_RUN_DATA.Run_Kilo_Start, run_data.run_Kilo_Start);
        values.put(TB_6_RUN_DATA.Run_Kilo_End, run_data.run_Kilo_End);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TB_6_RUN_DATA.TABLE, values, TB_6_RUN_DATA.Run_Id + "=?", new String[]{String.valueOf(run_data.run_Id)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getCarList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_6_RUN_DATA.TABLE;

        ArrayList<HashMap<String, String>> run_dataList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> run_data = new HashMap<String, String>();
                run_data.put("run_id", cursor.getString(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Id)));
                run_data.put("fix_id", cursor.getString(cursor.getColumnIndex(TB_6_RUN_DATA.Car_Id)));
                run_data.put("car_id", cursor.getString(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Date_Start)));
                run_data.put("change_date", cursor.getString(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Date_End)));
                run_data.put("change_kilo", cursor.getString(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Kilo_Start)));
                run_data.put("next_change_date", cursor.getString(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Kilo_End)));

                run_dataList.add(run_data);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return run_dataList;

    }

    public TB_6_RUN_DATA getRun_DataById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_6_RUN_DATA.TABLE + " WHERE " + TB_6_RUN_DATA.Run_Id + "=?";
        // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        TB_6_RUN_DATA run_data = new TB_6_RUN_DATA();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                run_data.run_Id = cursor.getInt(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Id));
                run_data.car_Id = cursor.getInt(cursor.getColumnIndex(TB_6_RUN_DATA.Car_Id));
                run_data.run_Date_Start = cursor.getString(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Date_Start));
                run_data.run_Date_End = cursor.getString(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Date_End));
                run_data.run_Kilo_Start = cursor.getDouble(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Kilo_Start));
                run_data.run_Kilo_End = cursor.getDouble(cursor.getColumnIndex(TB_6_RUN_DATA.Run_Kilo_End));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return run_data;
    }

}
