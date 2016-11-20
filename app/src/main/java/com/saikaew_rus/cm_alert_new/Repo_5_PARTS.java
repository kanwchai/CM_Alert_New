package com.saikaew_rus.cm_alert_new;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NB_A on 18/10/2559.
 */
public class Repo_5_PARTS {
    private MyDatabase dbHelper;

    public Repo_5_PARTS(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public int insert(TB_5_PARTS part_car) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TB_5_PARTS.Part_Name, part_car.part_Name);

        long part_id = db.insert(TB_5_PARTS.TABLE, null, values);

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
                part_car.put("part_name", cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name)));

                part_carList.add(part_car);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return part_carList;

    }

    public String[] getPart_Not(int carId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + TB_5_PARTS.Part_Name +
                " FROM " + TB_5_PARTS.TABLE + " p " +
                " LEFT OUTER JOIN " +
                "(SELECT * FROM " + TB_2_DUE_OF_PART_FIX.TABLE + " WHERE " + TB_2_DUE_OF_PART_FIX.Car_Id + " = " + carId + ") df" +
                " ON " +
                " p." + TB_5_PARTS.Part_Id + " = " + " df." + TB_2_DUE_OF_PART_FIX.Part_Id +
                " WHERE " +
                TB_2_DUE_OF_PART_FIX.Car_Id + " IS NULL GROUP BY p." + TB_5_PARTS.Part_Id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        List<String> getPartName = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                getPartName.add(cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name)));
            } while (cursor.moveToNext());
        }

        String[] partname = getPartName.toArray(new String[getPartName.size()]);

        cursor.close();
        db.close();
        return partname;
    }

}
