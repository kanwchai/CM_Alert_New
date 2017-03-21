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
public class Repo_9_USER {
    private A_MyDatabase dbHelper;

    public Repo_9_USER(Context context) {
        dbHelper = new A_MyDatabase(context);
    }

    public void insert(TB_9_USER user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_9_USER.User_Name, user.user_Name);
        values.put(TB_9_USER.User_Birth, user.user_Birth);
        values.put(TB_9_USER.User_Due_Date_Driving, user.user_Due_Date_Driving);

        long user_id = db.insert(TB_9_USER.TABLE, null, values);
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

        db.update(TB_9_USER.TABLE, values, TB_9_USER.User_Id + "=?", new String[]{String.valueOf(user.user_Id)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getUserList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_9_USER.TABLE;

        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<>();
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
