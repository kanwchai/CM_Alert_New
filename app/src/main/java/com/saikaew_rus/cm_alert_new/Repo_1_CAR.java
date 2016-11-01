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
public class Repo_1_CAR {
    private DBHelper dbHelper;

    public Repo_1_CAR(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(TB_1_CAR car) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_1_CAR.Type_Car_Id, car.type_car_id);
        values.put(TB_1_CAR.Car_Register, car.car_register);
        values.put(TB_1_CAR.Car_Tax_Date, car.car_tax_date);

        // Inserting Row
        long car_id = db.insert(TB_1_CAR.TABLE, null, values);
        // Closing database connection
        db.close();
        return (int) car_id;
    }

    public void delete(int car_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TB_1_CAR.TABLE, TB_1_CAR.Car_Id + "=?", new String[]{String.valueOf(car_id)});
        db.close();
    }

    public void update(TB_1_CAR car) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_1_CAR.Type_Car_Id, car.type_car_id);
        values.put(TB_1_CAR.Car_Register, car.car_register);
        values.put(TB_1_CAR.Car_Tax_Date, car.car_tax_date);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TB_1_CAR.TABLE, values, TB_1_CAR.Car_Id + "=?", new String[]{String.valueOf(car.car_id)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getCarList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_1_CAR.TABLE;

        // TB_1_CAR car = new TB_1_CAR();
        ArrayList<HashMap<String, String>> carList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> car = new HashMap<String, String>();
                car.put("id", cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Id)));
                car.put("type", cursor.getString(cursor.getColumnIndex(TB_1_CAR.Type_Car_Id)));
                car.put("register", cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Register)));
                car.put("date", cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Tax_Date)));
                carList.add(car);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return carList;

    }

    public TB_1_CAR getCarById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_1_CAR.TABLE + " WHERE " + TB_1_CAR.Car_Id + "=?";
        // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        TB_1_CAR car = new TB_1_CAR();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                car.car_id = cursor.getInt(cursor.getColumnIndex(TB_1_CAR.Car_Id));
                car.type_car_id = cursor.getInt(cursor.getColumnIndex(TB_1_CAR.Type_Car_Id));
                car.car_register = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Register));
                car.car_tax_date = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Tax_Date));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return car;
    }

}
