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
public class Repo_3_DUE_OF_PART_STANDART {
    private DBHelper dbHelper;

    public Repo_3_DUE_OF_PART_STANDART(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(TB_3_DUE_OF_PART_STANDART due_st) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_3_DUE_OF_PART_STANDART.Part_Id, due_st.part_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.Type_Car_Id, due_st.type_Car_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Kilo, due_st.st_Due_Kilo);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Date, due_st.st_Due_Date);

        // Inserting Row
        long due_st_id = db.insert(TB_3_DUE_OF_PART_STANDART.TABLE, null, values);
        // Closing database connection
        db.close();
        return (int) due_st_id;
    }

    public void delete(int due_st_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TB_3_DUE_OF_PART_STANDART.TABLE, TB_3_DUE_OF_PART_STANDART.St_Due_Id + "=?", new String[]{String.valueOf(due_st_id)});
        db.close();
    }

    public void update(TB_3_DUE_OF_PART_STANDART due_st) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_3_DUE_OF_PART_STANDART.Part_Id, due_st.part_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.Type_Car_Id, due_st.type_Car_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Kilo, due_st.st_Due_Kilo);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Date, due_st.st_Due_Date);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TB_3_DUE_OF_PART_STANDART.TABLE, values, TB_3_DUE_OF_PART_STANDART.St_Due_Id + "=?", new String[]{String.valueOf(due_st.st_Due_Id)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getCarList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_3_DUE_OF_PART_STANDART.TABLE;

        // TB_3_DUE_OF_PART_STANDART due_st = new TB_3_DUE_OF_PART_STANDART();
        ArrayList<HashMap<String, String>> due_stList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> due_st = new HashMap<String, String>();
                due_st.put("due_st_id", cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Id)));
                due_st.put("part_id", cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Part_Id)));
                due_st.put("car_id", cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Type_Car_Id)));
                due_st.put("due_st_kilo", cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Kilo)));
                due_st.put("due_st_date", cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Date)));

                due_stList.add(due_st);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return due_stList;

    }

    public TB_3_DUE_OF_PART_STANDART getDue_StById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_3_DUE_OF_PART_STANDART.TABLE + " WHERE " + TB_3_DUE_OF_PART_STANDART.St_Due_Id + "=?";
        // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        TB_3_DUE_OF_PART_STANDART due_st = new TB_3_DUE_OF_PART_STANDART();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                due_st.st_Due_Id = cursor.getInt(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Id));
                due_st.part_Id = cursor.getInt(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Part_Id));
                due_st.type_Car_Id = cursor.getInt(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Type_Car_Id));
                due_st.st_Due_Kilo = cursor.getDouble(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Kilo));
                due_st.st_Due_Date = cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Date));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return due_st;
    }


}