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
public class Repo_9_USER {
    private MyDatabase dbHelper;

    public Repo_9_USER(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public void insert(TB_9_USER user) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_9_USER.User_Name, user.user_Name);
        values.put(TB_9_USER.User_Birth, user.user_Birth);
        values.put(TB_9_USER.User_Due_Date_Driving, user.user_Due_Date_Driving);

        // Inserting Row
        long user_id = db.insert(TB_9_USER.TABLE, null, values);
        // Closing database connection
        db.close();
//        return (int) user_id;
    }

    public void delete(int user_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TB_9_USER.TABLE, TB_9_USER.User_Id + "=?", new String[]{String.valueOf(user_id)});
        db.close();
    }

    public void update(TB_9_USER user) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_9_USER.User_Name, user.user_Name);
        values.put(TB_9_USER.User_Birth, user.user_Birth);
        values.put(TB_9_USER.User_Due_Date_Driving, user.user_Due_Date_Driving);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TB_9_USER.TABLE, values, TB_9_USER.User_Id + "=?", new String[]{String.valueOf(user.user_Id)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getUserList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_9_USER.TABLE;

        // TB_9_USER user = new TB_9_USER();
        ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<String, String>();
                user.put("user_id", cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Id)));
                user.put("fix_id", cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Name)));
                user.put("car_id", cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Birth)));
                user.put("change_date", cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Due_Date_Driving)));

                userList.add(user);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    public TB_9_USER getUserById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_9_USER.TABLE + " WHERE " + TB_9_USER.User_Id + "=?";
        // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        TB_9_USER user = new TB_9_USER();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                user.user_Id = cursor.getInt(cursor.getColumnIndex(TB_9_USER.User_Id));
                user.user_Name = cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Name));
                user.user_Birth = cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Birth));
                user.user_Due_Date_Driving = cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Due_Date_Driving));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return user;
    }

    public TB_9_USER getFirstUser() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT min(" + TB_9_USER.User_Id + "),* FROM " + TB_9_USER.TABLE;
        // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        TB_9_USER user = new TB_9_USER();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                user.user_Id = cursor.getInt(cursor.getColumnIndex(TB_9_USER.User_Id));
                user.user_Name = cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Name));
                user.user_Birth = cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Birth));
                user.user_Due_Date_Driving = cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Due_Date_Driving));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return user;
    }

}
