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
    private A_MyDatabase dbHelper;

    public Repo_3_DUE_OF_PART_STANDART(Context context) {
        dbHelper = new A_MyDatabase(context);
    }

    public int insert(TB_3_DUE_OF_PART_STANDART due_st) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_3_DUE_OF_PART_STANDART.Type_Oil_Id, due_st.Type_Oil_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.Type_Gas_Id, due_st.Type_Gas_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.Part_Id, due_st.part_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Kilo, due_st.st_Due_Kilo);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Date, due_st.st_Due_Date);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Status, due_st.st_Due_Status);

        long due_st_id = db.insert(TB_3_DUE_OF_PART_STANDART.TABLE, null, values);

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

        values.put(TB_3_DUE_OF_PART_STANDART.Type_Oil_Id, due_st.Type_Oil_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.Type_Gas_Id, due_st.Type_Gas_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.Part_Id, due_st.part_Id);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Kilo, due_st.st_Due_Kilo);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Date, due_st.st_Due_Date);
        values.put(TB_3_DUE_OF_PART_STANDART.St_Due_Status, due_st.st_Due_Status);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TB_3_DUE_OF_PART_STANDART.TABLE, values, TB_3_DUE_OF_PART_STANDART.St_Due_Id + "=?", new String[]{String.valueOf(due_st.st_Due_Id)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getStandartList() {
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
                due_st.put(TB_3_DUE_OF_PART_STANDART.St_Due_Id, cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Id)));
                due_st.put(TB_3_DUE_OF_PART_STANDART.Type_Oil_Id, cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Type_Oil_Id)));
                due_st.put(TB_3_DUE_OF_PART_STANDART.Type_Gas_Id, cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Type_Gas_Id)));
                due_st.put(TB_3_DUE_OF_PART_STANDART.Part_Id, cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Part_Id)));
                due_st.put(TB_3_DUE_OF_PART_STANDART.St_Due_Kilo, cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Kilo)));
                due_st.put(TB_3_DUE_OF_PART_STANDART.St_Due_Date, cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Date)));
                due_st.put(TB_3_DUE_OF_PART_STANDART.St_Due_Status, cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Status)));

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
                due_st.type_Oil_Id = cursor.getInt(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Type_Oil_Id));
                due_st.type_Gas_Id = cursor.getInt(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Type_Gas_Id));
                due_st.part_Id = cursor.getInt(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.Part_Id));
                due_st.st_Due_Kilo = cursor.getInt(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Kilo));
                due_st.st_Due_Date = cursor.getInt(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Date));
                due_st.st_Due_Status = cursor.getString(cursor.getColumnIndex(TB_3_DUE_OF_PART_STANDART.St_Due_Status));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return due_st;
    }


}
