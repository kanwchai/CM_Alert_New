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
public class Repo_4_HISTORYS_OF_CAR {
    private MyDatabase dbHelper;

    public Repo_4_HISTORYS_OF_CAR(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public void insert(TB_4_HISTORYS_OF_CAR his_car) {

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
//        return (int) his_id;
    }

    public void insertBy_CarId(int car_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TB_4_HISTORYS_OF_CAR.TABLE +
                " SELECT " +
                "NULL," +
                TB_2_DUE_OF_PART_FIX.Fix_Due_Id + "," +
                "c." + TB_1_CAR.Car_Id + "," +
                TB_6_RUN_DATA.Run_Kilo_End + "," +
                TB_6_RUN_DATA.Run_Kilo_End + "+" + TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo + "," +
                "NULL," +
                "DATE('now')," +
                "DATE('now','+'||" + TB_2_DUE_OF_PART_FIX.Fix_Due_Date + "||' MONTH')" +","+
                "NULL" +
                " FROM " +
                TB_1_CAR.TABLE + " c," +
                TB_6_RUN_DATA.TABLE + " rd," +
                TB_2_DUE_OF_PART_FIX.TABLE + " df" +
                " ON " +
                "c." + TB_1_CAR.Car_Id + " = " + "rd." + TB_6_RUN_DATA.Car_Id +
                " AND " +
                "c." + TB_1_CAR.Car_Id + " = " + "df." + TB_2_DUE_OF_PART_FIX.Car_Id +
                " WHERE " +
                "c." + TB_1_CAR.Car_Id + " = " + car_id;

        db.execSQL(insertQuery);
        db.close();
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

    public ArrayList<HashMap<String, String>> getHisList_By_CarId_PartName(int car_id, String part_name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "";

        if (part_name.matches("")) {
            selectQuery = "SELECT strftime('%d/%m/%Y',"+TB_4_HISTORYS_OF_CAR.Changed_Date+") chg_date_format,* FROM " +
                    TB_4_HISTORYS_OF_CAR.TABLE + " h," +
                    TB_2_DUE_OF_PART_FIX.TABLE + " df," +
                    TB_5_PARTS.TABLE + " p" +
                    " ON " +
                    "h." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " = " + "df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Id +
                    " AND " +
                    "df." + TB_2_DUE_OF_PART_FIX.Part_Id + " = " + "p." + TB_5_PARTS.Part_Id +
                    " WHERE " +
                    "h." + TB_4_HISTORYS_OF_CAR.Car_Id + " = " + car_id;
        } else {
            selectQuery = "SELECT strftime('%d/%m/%Y',"+TB_4_HISTORYS_OF_CAR.Changed_Date+") chg_date_format,* FROM " +
                    TB_4_HISTORYS_OF_CAR.TABLE + " h," +
                    TB_2_DUE_OF_PART_FIX.TABLE + " df," +
                    TB_5_PARTS.TABLE + " p" +
                    " ON " +
                    "h." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " = " + "df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Id +
                    " AND " +
                    "df." + TB_2_DUE_OF_PART_FIX.Part_Id + " = " + "p." + TB_5_PARTS.Part_Id +
                    " WHERE " +
                    "h." + TB_4_HISTORYS_OF_CAR.Car_Id + " = " + car_id +
                    " AND " +
                    "p." + TB_5_PARTS.Part_Name + " LIKE " + "'%" + part_name + "%'";
        }

        ArrayList<HashMap<String, String>> his_carList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> his_car = new HashMap<String, String>();
                his_car.put(TB_5_PARTS.Part_Name, cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name)));
                his_car.put("chg_date_format", cursor.getString(cursor.getColumnIndex("chg_date_format")));
                his_car.put(TB_4_HISTORYS_OF_CAR.Changed_Date, cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Changed_Date)));
                his_car.put(TB_4_HISTORYS_OF_CAR.Changed_Kilo, cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Changed_Kilo)));
                his_car.put(TB_4_HISTORYS_OF_CAR.Next_Changed_Date, cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Next_Changed_Date)));
                his_car.put(TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo, cursor.getString(cursor.getColumnIndex(TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo)));

                his_carList.add(his_car);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return his_carList;

    }

    public String[] getPartNameByCarId(int carId) {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " +
                TB_2_DUE_OF_PART_FIX.TABLE + " df," +
                TB_5_PARTS.TABLE + " p" +
                " ON " +
                "df." + TB_2_DUE_OF_PART_FIX.Part_Id + " = " + "p." + TB_5_PARTS.Part_Id +
                " WHERE " +
                "df." + TB_2_DUE_OF_PART_FIX.Car_Id + " = " + carId;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(cursor.getColumnIndex(TB_5_PARTS.Part_Name)));
            } while (cursor.moveToNext());
        }

        String[] province = labels.toArray(new String[labels.size()]);

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return province;
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
