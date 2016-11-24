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
    private MyDatabase dbHelper;

    public Repo_1_CAR(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public int insert(TB_1_CAR car) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TB_1_CAR.Type_Oil_Id, car.type_Oil_Id);
        values.put(TB_1_CAR.Type_Gas_Id, car.type_Gas_Id);
        values.put(TB_1_CAR.Car_Register, car.car_Register);
        values.put(TB_1_CAR.Car_Tax_Date, car.car_Tax_Date);
        values.put(TB_1_CAR.Province_Name, car.province_Name);

        // Inserting Row
        int car_Id = (int) db.insert(TB_1_CAR.TABLE, null, values);
        // Closing database connection
        db.close();
        return car_Id;
    }

    public void delete(int car_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TB_1_CAR.TABLE, TB_1_CAR.Car_Id + "=?", new String[]{String.valueOf(car_Id)});
        db.close();
    }

    public void update(TB_1_CAR car) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_1_CAR.Type_Oil_Id, car.type_Oil_Id);
        values.put(TB_1_CAR.Type_Gas_Id, car.type_Gas_Id);
        values.put(TB_1_CAR.Car_Register, car.car_Register);
        values.put(TB_1_CAR.Car_Tax_Date, car.car_Tax_Date);
        values.put(TB_1_CAR.Province_Name, car.province_Name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TB_1_CAR.TABLE, values, TB_1_CAR.Car_Id + "=?", new String[]{String.valueOf(car.car_Id)});
        db.close(); // Closing database connection
    }

    public void updateRegis(int car_Id,String Regis) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_1_CAR.Car_Register, Regis);

        db.update(TB_1_CAR.TABLE, values, TB_1_CAR.Car_Id + "=?", new String[]{String.valueOf(car_Id)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getCarList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_1_CAR.TABLE;

        ArrayList<HashMap<String, String>> carList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> car = new HashMap<String, String>();
                car.put(TB_1_CAR.Car_Id, cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Id)));
                car.put(TB_1_CAR.Type_Oil_Id, cursor.getString(cursor.getColumnIndex(TB_1_CAR.Type_Oil_Id)));
                car.put(TB_1_CAR.Type_Gas_Id, cursor.getString(cursor.getColumnIndex(TB_1_CAR.Type_Gas_Id)));
                car.put(TB_1_CAR.Province_Name, cursor.getString(cursor.getColumnIndex(TB_1_CAR.Province_Name)));
                car.put(TB_1_CAR.Car_Register, cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Register)));
                car.put(TB_1_CAR.Car_Tax_Date, cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Tax_Date)));
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
                car.car_Id = cursor.getInt(cursor.getColumnIndex(TB_1_CAR.Car_Id));
                car.type_Oil_Id = cursor.getInt(cursor.getColumnIndex(TB_1_CAR.Type_Oil_Id));
                car.type_Gas_Id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TB_1_CAR.Type_Gas_Id)));
                car.province_Name = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Province_Name));
                car.car_Register = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Register));
                car.car_Tax_Date = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Tax_Date));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return car;
    }

}
