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
public class Repo_4_HISTORYS_OF_CAR {
    private MyDatabase dbHelper;

    public Repo_4_HISTORYS_OF_CAR(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public int insert(TB_4_HISTORYS_OF_CAR his_car) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_4_HISTORYS_OF_CAR.Fix_Due_Id, his_car.fix_Due_Id);
        values.put(TB_4_HISTORYS_OF_CAR.Car_Id, his_car.car_Id);
        values.put(TB_4_HISTORYS_OF_CAR.Changed_Date, his_car.changed_Date);
        values.put(TB_4_HISTORYS_OF_CAR.Changed_Kilo, his_car.changed_Kilo);
        values.put(TB_4_HISTORYS_OF_CAR.Next_Changed_Date, his_car.next_Changed_Date);
        values.put(TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo, his_car.next_Changed_Kilo);

        // Inserting Row
        long his_id = db.insert(TB_4_HISTORYS_OF_CAR.TABLE, null, values);
        // Closing database connection
        db.close();
        return (int) his_id;
    }

    public void delete(int his_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TB_4_HISTORYS_OF_CAR.TABLE, TB_4_HISTORYS_OF_CAR.History_Id + "=?", new String[]{String.valueOf(his_id)});
        db.close();
    }

    public void update(TB_4_HISTORYS_OF_CAR his_car) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_4_HISTORYS_OF_CAR.Fix_Due_Id, his_car.fix_Due_Id);
        values.put(TB_4_HISTORYS_OF_CAR.Car_Id, his_car.car_Id);
        values.put(TB_4_HISTORYS_OF_CAR.Changed_Date, his_car.changed_Date);
        values.put(TB_4_HISTORYS_OF_CAR.Changed_Kilo, his_car.changed_Kilo);
        values.put(TB_4_HISTORYS_OF_CAR.Next_Changed_Date, his_car.next_Changed_Date);
        values.put(TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo, his_car.next_Changed_Kilo);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TB_4_HISTORYS_OF_CAR.TABLE, values, TB_4_HISTORYS_OF_CAR.History_Id + "=?", new String[]{String.valueOf(his_car.history_Id)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getCarList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_4_HISTORYS_OF_CAR.TABLE;

        // TB_4_HISTORYS_OF_CAR his_car = new TB_4_HISTORYS_OF_CAR();
        ArrayList<HashMap<String, String>> his_carList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> his_car = new HashMap<String, String>();
                his_car.put("his_id", cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.History_Id)));
                his_car.put("fix_id", cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Fix_Due_Id)));
                his_car.put("car_id", cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Car_Id)));
                his_car.put("change_date", cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Changed_Date)));
                his_car.put("change_kilo", cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Changed_Kilo)));
                his_car.put("next_change_date", cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Next_Changed_Date)));
                his_car.put("next_change_kilo", cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo)));

                his_carList.add(his_car);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return his_carList;

    }

    public TB_4_HISTORYS_OF_CAR getHis_CarById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_4_HISTORYS_OF_CAR.TABLE + " WHERE " + TB_4_HISTORYS_OF_CAR.History_Id + "=?";
        // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        TB_4_HISTORYS_OF_CAR his_car = new TB_4_HISTORYS_OF_CAR();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                his_car.history_Id = cursor.getInt(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.History_Id));
                his_car.fix_Due_Id = cursor.getInt(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Fix_Due_Id));
                his_car.car_Id = cursor.getInt(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Car_Id));
                his_car.changed_Date = cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Changed_Date));
                his_car.changed_Kilo = cursor.getDouble(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Changed_Kilo));
                his_car.next_Changed_Date = cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Next_Changed_Date));
                his_car.next_Changed_Kilo = cursor.getDouble(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return his_car;
    }

}
