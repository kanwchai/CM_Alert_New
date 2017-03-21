package com.saikaew_rus.cm_alert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NB_A on 18/10/2559.
 */
public class Repo_2_DUE_OF_PART_FIX {
    private A_MyDatabase dbHelper;

    public Repo_2_DUE_OF_PART_FIX(Context context) {
        dbHelper = new A_MyDatabase(context);
    }

    public int insert(TB_2_DUE_OF_PART_FIX due_fix) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_2_DUE_OF_PART_FIX.Part_Id, due_fix.part_Id);
        values.put(TB_2_DUE_OF_PART_FIX.Car_Id, due_fix.car_Id);
        values.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo, due_fix.fix_Due_Kilo);
        values.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Date, due_fix.fix_Due_Date);
        values.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Status, due_fix.fix_Due_Status);

        long due_fix_id = db.insert(TB_2_DUE_OF_PART_FIX.TABLE, null, values);

        db.close();
        return (int) due_fix_id;
    }

    public void insert_part_fix(int car_id, int oil, int gas) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String insertQuery;

        if (gas == 1) {
            insertQuery = "INSERT INTO " + TB_2_DUE_OF_PART_FIX.TABLE +
                    " SELECT null" +
                    "," + TB_3_DUE_OF_PART_STANDART.Part_Id +
                    "," + car_id +
                    "," + TB_3_DUE_OF_PART_STANDART.St_Due_Kilo +
                    "," + TB_3_DUE_OF_PART_STANDART.St_Due_Date +
                    "," + TB_3_DUE_OF_PART_STANDART.St_Due_Status +
                    "," + 1 +
                    " FROM " +
                    "(SELECT * FROM " + TB_3_DUE_OF_PART_STANDART.TABLE + " WHERE " +
                    TB_3_DUE_OF_PART_STANDART.Type_Oil_Id + " = " + oil + ")";
        } else {
            insertQuery = "INSERT INTO " + TB_2_DUE_OF_PART_FIX.TABLE +
                    " SELECT null" +
                    "," + TB_3_DUE_OF_PART_STANDART.Part_Id +
                    "," + car_id +
                    "," + TB_3_DUE_OF_PART_STANDART.St_Due_Kilo +
                    "," + TB_3_DUE_OF_PART_STANDART.St_Due_Date +
                    "," + TB_3_DUE_OF_PART_STANDART.St_Due_Status +
                    "," + 1 +
                    " FROM " +
                    "(SELECT * FROM " + TB_3_DUE_OF_PART_STANDART.TABLE + " WHERE " +
                    TB_3_DUE_OF_PART_STANDART.Type_Oil_Id + " = " + oil +
                    " UNION " +
                    " SELECT * FROM " + TB_3_DUE_OF_PART_STANDART.TABLE + " WHERE "
                    + TB_3_DUE_OF_PART_STANDART.Type_Gas_Id + " = " + gas +
                    " GROUP BY " + TB_3_DUE_OF_PART_STANDART.Part_Id + " ORDER BY " + TB_3_DUE_OF_PART_STANDART.Part_Id + ")";
        }

        db.execSQL(insertQuery);
        db.close();
    }

    public void insert_PartByCar(int car_id, int partId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String insertPart = "INSERT INTO " + TB_2_DUE_OF_PART_FIX.TABLE +
                " SELECT null" +
                "," + TB_5_PARTS.Part_Id +
                "," + car_id +
                "," + TB_3_DUE_OF_PART_STANDART.St_Due_Kilo +
                "," + TB_3_DUE_OF_PART_STANDART.St_Due_Date +
                "," + TB_3_DUE_OF_PART_STANDART.St_Due_Status +
                "," + 1 + " FROM " +
                " (SELECT MAX(ds." + TB_3_DUE_OF_PART_STANDART.St_Due_Kilo + "),* FROM " + TB_5_PARTS.TABLE + " p, " + TB_3_DUE_OF_PART_STANDART.TABLE + " ds " +
                " ON p." + TB_5_PARTS.Part_Id + " = ds." + TB_5_PARTS.Part_Id +
                " WHERE p." + TB_5_PARTS.Part_Id + " = " + partId + ")";

        Log.d("insert_querypart", insertPart);

        db.execSQL(insertPart);
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

    public ArrayList<HashMap<String, String>> getFixListByCarId(int carId, int sortPartNum) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] sortPart = {TB_5_PARTS.Part_Name_en, TB_2_DUE_OF_PART_FIX.CountDate, TB_2_DUE_OF_PART_FIX.CountKilo
                , TB_5_PARTS.Part_Name_en + " DESC", TB_2_DUE_OF_PART_FIX.CountDate + " DESC", TB_2_DUE_OF_PART_FIX.CountKilo + " DESC"};

        String selectQuery = "SELECT *," +
                TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo + "-" + TB_6_RUN_DATA.Run_Kilo_End + " " + TB_2_DUE_OF_PART_FIX.CountKilo + "," +
                "julianday(strftime('%Y-%m-%d'," + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + ")) - julianday(strftime('%Y-%m-%d', 'now')) " + TB_2_DUE_OF_PART_FIX.CountDate + ", " +
                "CASE " +
                "WHEN STRFTIME('%Y',remain_date)+0 > 0 " +
                "THEN (STRFTIME('%Y',remain_date)+0)||'Y '||(STRFTIME('%m',remain_date)+0)||'M '||(STRFTIME('%d',remain_date)+0)||'D' " +
                "WHEN((STRFTIME('%Y', remain_date) + 0 == 0)AND(STRFTIME('%m', remain_date) + 0 >= 0)) " +
                "THEN(STRFTIME('%m', remain_date) + 0) || 'M ' || (STRFTIME('%d', remain_date) + 0) || 'D' " +
                "WHEN remain_date = 'End of life' " +
                "THEN 'End of life' " +
                "ELSE CAST(remain_date AS INT) || 'D' END " + TB_2_DUE_OF_PART_FIX.CountDate +
                " FROM " +
                "(" +
                "SELECT *," +
                "CASE " +
                "WHEN date(" + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + ") > date('now', '+1 month', '+1 day') " +
                "THEN date(" + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + ", '-' || STRFTIME('%Y', 'now') || ' year', '-' || STRFTIME('%m', 'now') || ' month', '-' || STRFTIME('%d', 'now') || ' day') " +
                "WHEN date(" + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + ") < date('now') " +
                "THEN 'End of life' ELSE JULIANDAY(" + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + ") - JULIANDAY('now') " +
                "END remain_date " +
                "FROM " +
                "(SELECT MAX(" + TB_6_RUN_DATA.Run_Id + "), * FROM " + TB_6_RUN_DATA.TABLE + " WHERE " + TB_1_CAR.Car_Id + "=?) r," +
                "(SELECT * FROM " + TB_4_HISTORYS_OF_CAR.TABLE + " h, " + TB_2_DUE_OF_PART_FIX.TABLE + " df " +
                "ON " +
                "h." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + "= df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Id +
                " GROUP BY h." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + ") hd," +
                TB_5_PARTS.TABLE + " p" +
                " ON " +
                "r." + TB_1_CAR.Car_Id + "= hd." + TB_1_CAR.Car_Id +
                " AND " +
                "p." + TB_5_PARTS.Part_Id + "= hd." + TB_5_PARTS.Part_Id +
                ")" +
                " ORDER BY " + sortPart[sortPartNum];

        ArrayList<HashMap<String, String>> due_fixList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(carId)});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> due_fix = new HashMap<String, String>();
                due_fix.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Id, cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Id)));
                due_fix.put(TB_2_DUE_OF_PART_FIX.Part_Id, cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Part_Id)));
                due_fix.put(TB_5_PARTS.Part_Name_en, cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name_en)));
                due_fix.put(TB_2_DUE_OF_PART_FIX.Car_Id, cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Car_Id)));
                due_fix.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo, cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo)));
                due_fix.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Date, cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Date)));
                due_fix.put(TB_2_DUE_OF_PART_FIX.Fix_Due_Status, cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Status)));
                due_fix.put(TB_2_DUE_OF_PART_FIX.CountKilo, cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.CountKilo)));
                due_fix.put(TB_2_DUE_OF_PART_FIX.CountDate, cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.CountDate)));
                due_fix.put(TB_5_PARTS.Maintenance_Guide_En, cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Maintenance_Guide_En)));

                if (cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.CountKilo)) <= 0) {
                    due_fix.put(TB_2_DUE_OF_PART_FIX.SetColor, String.valueOf(R.color.LifeTime_0));
                }

                due_fixList.add(due_fix);

            } while (cursor.moveToNext());
        }

        Log.d("Query", selectQuery);

        cursor.close();
        db.close();
        return due_fixList;

    }

    public TB_2_DUE_OF_PART_FIX getDue_FixByMax() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT *,MAX(" + TB_2_DUE_OF_PART_FIX.Fix_Due_Id + ") FROM " + TB_2_DUE_OF_PART_FIX.TABLE;

        TB_2_DUE_OF_PART_FIX due_fix = new TB_2_DUE_OF_PART_FIX();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                due_fix.fix_Due_Id = cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Id));
                due_fix.part_Id = cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Part_Id));
                due_fix.car_Id = cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Car_Id));
                due_fix.fix_Due_Kilo = cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo));
                due_fix.fix_Due_Date = cursor.getInt(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Date));
                due_fix.fix_Due_Status = cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Status));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return due_fix;
    }

}
