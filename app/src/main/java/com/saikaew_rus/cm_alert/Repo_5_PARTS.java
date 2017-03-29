package com.saikaew_rus.cm_alert;

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
    private A_MyDatabase dbHelper;

    public Repo_5_PARTS(Context context) {
        dbHelper = new A_MyDatabase(context);
    }

    public int insert(TB_5_PARTS part_car) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TB_5_PARTS.Part_Name_en, part_car.part_Name_en);

        int part_id = (int) db.insert(TB_5_PARTS.TABLE, null, values);

        db.close();
        return part_id;
    }

    public void delete(int part_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TB_5_PARTS.TABLE, TB_5_PARTS.Part_Id + "=?", new String[]{String.valueOf(part_id)});
        db.close();
    }

    public void update(TB_5_PARTS part_car) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_5_PARTS.Part_Name_en, part_car.part_Name_en);

        db.update(TB_5_PARTS.TABLE, values, TB_5_PARTS.Part_Id + "=?", new String[]{String.valueOf(part_car.part_Id)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getPartList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_5_PARTS.TABLE;

        ArrayList<HashMap<String, String>> part_carList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> part_car = new HashMap<>();
                part_car.put("part_id", cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Id)));
                part_car.put("part_name", cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name_en)));

                part_carList.add(part_car);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return part_carList;

    }

    public ArrayList<HashMap<String, String>> getPart_Not(int carId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_5_PARTS.TABLE +
                " WHERE " + TB_5_PARTS.TABLE + "." + TB_5_PARTS.Part_Id + " NOT IN " +
                "(SELECT " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Part_Id +
                " FROM " + TB_2_DUE_OF_PART_FIX.TABLE +
                " WHERE " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Car_Id + " = " + carId +
                " AND " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Fix_Due_Show + " = 1)";

        ArrayList<HashMap<String, String>> part_Not = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> part = new HashMap<>();

                part.put(TB_5_PARTS.Part_Id, String.valueOf(cursor.getInt(cursor.getColumnIndex(TB_5_PARTS.Part_Id))));
                part.put(TB_5_PARTS.Part_Name_en, cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name_en)));
                part.put(TB_5_PARTS.Part_Name_th, cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name_th)));

                part_Not.add(part);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return part_Not;
    }

}
