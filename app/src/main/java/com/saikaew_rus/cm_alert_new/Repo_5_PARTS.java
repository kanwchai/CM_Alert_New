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
public class Repo_5_PARTS {
    private MyDatabase dbHelper;

    public Repo_5_PARTS(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public int insert(TB_5_PARTS part_car) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_5_PARTS.Part_Name, part_car.part_Name);

        // Inserting Row
        long part_id = db.insert(TB_5_PARTS.TABLE, null, values);
        // Closing database connection
        db.close();
        return (int) part_id;
    }

    public void delete(int part_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TB_5_PARTS.TABLE, TB_5_PARTS.Part_Id + "=?", new String[]{String.valueOf(part_id)});
        db.close();
    }

    public void update(TB_5_PARTS part_car) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_5_PARTS.Part_Name, part_car.part_Name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TB_5_PARTS.TABLE, values, TB_5_PARTS.Part_Id + "=?", new String[]{String.valueOf(part_car.part_Id)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getCarList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_5_PARTS.TABLE;

        // TB_5_PARTS part_car = new TB_5_PARTS();
        ArrayList<HashMap<String, String>> part_carList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> part_car = new HashMap<String, String>();
                part_car.put("part_id", cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Id)));
                part_car.put("part_name", cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name)));

                part_carList.add(part_car);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return part_carList;

    }

    public TB_5_PARTS getPartById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_5_PARTS.TABLE + " WHERE " + TB_5_PARTS.Part_Id + "=?";
        // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        TB_5_PARTS part_car = new TB_5_PARTS();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                part_car.part_Id = cursor.getInt(cursor.getColumnIndex(TB_5_PARTS.Part_Id));
                part_car.part_Name = cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return part_car;
    }

}
