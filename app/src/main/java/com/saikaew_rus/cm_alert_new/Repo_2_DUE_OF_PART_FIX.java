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
public class Repo_2_DUE_OF_PART_FIX {
    private MyDatabase dbHelper;

    public Repo_2_DUE_OF_PART_FIX(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public int insert(TB_2_DUE_OF_PART_FIX due_fix) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TB_2_DUE_OF_PART_FIX.Part_Id, due_fix.part_Id);
        values.put(TB_2_DUE_OF_PART_FIX.Car_Id, due_fix.car_Id);
        values.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo, due_fix.fix_Due_Kilo);
        values.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Date, due_fix.fix_Due_Date);

        long due_fix_id = db.insert(TB_2_DUE_OF_PART_FIX.TABLE, null, values);
        db.close();
        return (int) due_fix_id;
    }

    public void insert_part_fix(int car_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String insertQuery = "INSERT INTO " + TB_2_DUE_OF_PART_FIX.TABLE + "(" +
//                TB_2_DUE_OF_PART_FIX.Part_Id + "," + TB_2_DUE_OF_PART_FIX.Car_Id + "," +
//                TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo + "," + TB_2_DUE_OF_PART_FIX.Fix_Due_Date + "," +
//                TB_2_DUE_OF_PART_FIX.Fix_Due_Status + ") " +
//                "SELECT " + TB_3_DUE_OF_PART_STANDART.Part_Id + "," + TB_1_CAR.Car_Id + "," +
//                TB_3_DUE_OF_PART_STANDART.St_Due_Kilo + "," + TB_3_DUE_OF_PART_STANDART.St_Due_Date + "," +
//                TB_3_DUE_OF_PART_STANDART.St_Due_Status +
//                " FROM " + TB_1_CAR.TABLE + " JOIN " + TB_3_DUE_OF_PART_STANDART.TABLE +
//                " ON " + TB_1_CAR.TABLE + "." + TB_1_CAR.Type_Car_Id +
//                " = " + TB_3_DUE_OF_PART_STANDART.TABLE + "." + TB_3_DUE_OF_PART_STANDART.Type_Car_Id +
//                " WHERE CAR_ID = " + car_id;

//        String insertQuery_1 = "INSERT INTO " + TB_2_DUE_OF_PART_FIX.TABLE +
//                " SELECT null, " + TB_3_DUE_OF_PART_STANDART.Part_Id + "," + car_id + "," +
//                TB_3_DUE_OF_PART_STANDART.St_Due_Kilo + "," + TB_3_DUE_OF_PART_STANDART.St_Due_Date + "," +
//                TB_3_DUE_OF_PART_STANDART.St_Due_Status + " FROM " + TB_3_DUE_OF_PART_STANDART.TABLE +
//                " WHERE " + TB_3_DUE_OF_PART_STANDART.Type_Car_Id + " IN (1," + car_type + ")";

        String insertQuery_2 = "INSERT INTO " + TB_2_DUE_OF_PART_FIX.TABLE +
                " SELECT null," + TB_3_DUE_OF_PART_STANDART.Part_Id + "," + TB_1_CAR.Car_Id + "," +
                TB_3_DUE_OF_PART_STANDART.St_Due_Kilo + "," + TB_3_DUE_OF_PART_STANDART.St_Due_Date + "," +
                TB_3_DUE_OF_PART_STANDART.St_Due_Status + " FROM " + TB_3_DUE_OF_PART_STANDART.TABLE + "," +
                "(SELECT " + TB_1_CAR.Car_Id + "," + TB_1_CAR.Type_Car_Id + " FROM " + TB_1_CAR.TABLE +
                " UNION " +
                "SELECT " + TB_1_CAR.Car_Id + "," + " CASE WHEN " + TB_1_CAR.Type_Car_Id + " > 1 THEN 1 END " + TB_1_CAR.Type_Car_Id +
                " FROM " + TB_1_CAR.TABLE + " WHERE " + TB_1_CAR.Type_Car_Id + " > 1)" + TB_1_CAR.TABLE +
                " WHERE " + TB_3_DUE_OF_PART_STANDART.TABLE + "." + TB_3_DUE_OF_PART_STANDART.Type_Car_Id +
                " = " + TB_1_CAR.TABLE + "." + TB_1_CAR.Type_Car_Id +
                " AND " + TB_1_CAR.Car_Id + "=?";

        db.execSQL(insertQuery_2, new String[]{String.valueOf(car_id)});
        db.close();
    }

    public void delete(int due_fix_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TB_2_DUE_OF_PART_FIX.TABLE, TB_2_DUE_OF_PART_FIX.Fix_Due_Id + "=?", new String[]{String.valueOf(due_fix_id)});
        db.close();
    }

    public void update(TB_2_DUE_OF_PART_FIX due_fix) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_2_DUE_OF_PART_FIX.Part_Id, due_fix.part_Id);
        values.put(TB_2_DUE_OF_PART_FIX.Car_Id, due_fix.car_Id);
        values.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo, due_fix.fix_Due_Kilo);
        values.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Date, due_fix.fix_Due_Date);
        values.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Status, due_fix.fix_Due_Status);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TB_2_DUE_OF_PART_FIX.TABLE, values, TB_2_DUE_OF_PART_FIX.Fix_Due_Id + "=?", new String[]{String.valueOf(due_fix.fix_Due_Id)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getFixListByCarId(int carId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TB_2_DUE_OF_PART_FIX.TABLE + "," + TB_5_PARTS.TABLE +
                " WHERE " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Part_Id +
                " = " + TB_5_PARTS.TABLE + "." + TB_5_PARTS.Part_Id + " AND " +
                TB_2_DUE_OF_PART_FIX.Car_Id + "=?";

        ArrayList<HashMap<String, String>> due_fixList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(carId)});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> due_fix = new HashMap<String, String>();
                due_fix.put("due_fix_id", cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Id)));
                due_fix.put("part_id", cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Part_Id)));
                due_fix.put("part_name", cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name)));
                due_fix.put("car_id", cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Car_Id)));
                due_fix.put("due_fix_kilo", cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo)));
                due_fix.put("due_fix_date", cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Date)));
                due_fix.put("due_fix_status", cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Status)));
                due_fixList.add(due_fix);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return due_fixList;

    }

    public TB_2_DUE_OF_PART_FIX getDue_FixById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_2_DUE_OF_PART_FIX.TABLE + " WHERE " + TB_2_DUE_OF_PART_FIX.Fix_Due_Id + "=?";
        // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        TB_2_DUE_OF_PART_FIX due_fix = new TB_2_DUE_OF_PART_FIX();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                due_fix.fix_Due_Id = cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Id));
                due_fix.part_Id = cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Part_Id));
                due_fix.car_Id = cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Car_Id));
                due_fix.fix_Due_Kilo = cursor.getDouble(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo));
                due_fix.fix_Due_Date = cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Date));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return due_fix;
    }


}
