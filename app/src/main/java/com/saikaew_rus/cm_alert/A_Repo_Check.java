package com.saikaew_rus.cm_alert;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by NB_A on 18/10/2559.
 */
public class A_Repo_Check {
    private A_MyDatabase dbHelper;

    public A_Repo_Check(Context context) {
        dbHelper = new A_MyDatabase(context);
    }

    public int chkDrivingLicense() {
        int size;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_9_USER.TABLE + " WHERE " + TB_9_USER.User_Due_Date_Driving + " < DATE('now','+30 day')";

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        size = cursor.getCount();

        cursor.close();
        db.close();

        return size;
    }

    public int chkTaxCar() {
        int size;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_1_CAR.TABLE + " WHERE " + TB_1_CAR.Car_Tax_Date + " < DATE('now','+30 day')";

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        size = cursor.getCount();

        cursor.close();
        db.close();

        return size;
    }

    public int chkDueDate() {
        int size;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " +
                "(SELECT * FROM " + TB_4_HISTORYS_OF_CAR.TABLE + " h," + TB_2_DUE_OF_PART_FIX.TABLE + " df" +
                " ON " + "h." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " = " + "df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Id +
                " WHERE " + "df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Date + " > 0)" +
                " WHERE julianday(" + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + ") - julianday('now') <= 15";

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        size = cursor.getCount();

        cursor.close();
        db.close();

        return size;
    }

    public int chkDueKilo(int carid) {
        int dataCar;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " +
                "(SELECT MAX(" + TB_6_RUN_DATA.Run_Id + "),* FROM " + TB_6_RUN_DATA.TABLE + " WHERE " + TB_1_CAR.Car_Id + " = " + carid + ") r ," +
                "(SELECT MAX(" + TB_4_HISTORYS_OF_CAR.History_Id + "),* FROM " + TB_4_HISTORYS_OF_CAR.TABLE + " h, " +
                TB_2_DUE_OF_PART_FIX.TABLE + " df ON h." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " = df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Id +
                " WHERE df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo + " > 0 GROUP BY df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Id + ") hd" +
                " ON r.Car_id = hd.Car_id " +
                " WHERE " + TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo + " - " + TB_6_RUN_DATA.Run_Kilo_End + " <= 1500";

        Cursor cursor = db.rawQuery(selectQuery, null);
        dataCar = cursor.getCount();

        cursor.close();
        db.close();

        return dataCar;
    }

    public int chk_Car(String car_Reg, String prov_name) {
        int dataCar;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_1_CAR.TABLE +
                " WHERE " + TB_1_CAR.Car_Register + " = " + "'" + car_Reg + "'" +
                " AND " + TB_1_CAR.Province_Name + " = " + "'" + prov_name + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        dataCar = cursor.getCount();

        cursor.close();
        db.close();

        return dataCar;
    }

    public int chk_Part(String part_name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectPartAll = "SELECT * FROM " + TB_5_PARTS.TABLE +
                " WHERE " + TB_5_PARTS.Part_Name_en + " = " + "'" + part_name + "'";

        Cursor cursor = db.rawQuery(selectPartAll, null);

        int countPart = cursor.getCount();

        cursor.close();
        db.close();

        return countPart;
    }

    public List<String> getDataUser() {
        String name, drivLicense;
        List<String> dataUser = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_9_USER.TABLE + " WHERE " + TB_9_USER.User_Due_Date_Driving + " < DATE('now','+30 day')";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Name));
                drivLicense = cursor.getString(cursor.getColumnIndex(TB_9_USER.User_Due_Date_Driving));

                //*****************************  Convert Format Date  *****************************//
                SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat postFormater = new SimpleDateFormat("dd / MM / yyyy");

                Date exDate = null;
                try {
                    exDate = curFormater.parse(drivLicense);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //***************************** End Convert Format Date  *****************************//
                drivLicense = postFormater.format(exDate);

                dataUser.add("ชื่อ " + name + "    วันหมดอายุใบขับขี่  " + drivLicense);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataUser;
    }

    public List<String> getDataCar() {
        String carRegis, proVince, taxDate, carData = new String();
        List<String> dataCar = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_1_CAR.TABLE + " WHERE " + TB_1_CAR.Car_Tax_Date + " < DATE('now','+30 day')";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                carRegis = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Register));
                proVince = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Province_Name));
                taxDate = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Tax_Date));

                //*****************************  Convert Format Date  *****************************//
                SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat postFormater = new SimpleDateFormat("dd / MM / yyyy");

                Date exDate = null;
                try {
                    exDate = curFormater.parse(taxDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //***************************** End Convert Format Date  *****************************//
                taxDate = postFormater.format(exDate);

                dataCar.add("เลขทะเบียน " + carRegis + "  " + proVince + "  วันหมดอายุภาษีรถ " + taxDate);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataCar;
    }

    public List<String> getDataTotalPart() {
        String carRegis, proVince, totalPart, carData = new String();
        List<String> dataTotalPart = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT COUNT(" + TB_1_CAR.Car_Id + ") '" + TB_1_CAR.TotalPart + "',*" +
                " FROM (SELECT MAX(" + TB_4_HISTORYS_OF_CAR.TABLE + "." + TB_4_HISTORYS_OF_CAR.History_Id + "),*" +
                " FROM " + TB_4_HISTORYS_OF_CAR.TABLE + "," + TB_2_DUE_OF_PART_FIX.TABLE + "," + TB_1_CAR.TABLE +
                " ON " + TB_4_HISTORYS_OF_CAR.TABLE + "." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id +
                " = " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Fix_Due_Id +
                " AND" +
                " " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Car_Id +
                " = " + TB_1_CAR.TABLE + "." + TB_1_CAR.Car_Id +
                " WHERE " + TB_2_DUE_OF_PART_FIX.TABLE + "." + TB_2_DUE_OF_PART_FIX.Fix_Due_Date + " > 0" +
                " GROUP BY " + TB_4_HISTORYS_OF_CAR.TABLE + "." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + ")" +
                " WHERE " + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + " <= DATE('now','+15 day')" +
                " GROUP BY " + TB_1_CAR.Car_Id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                carRegis = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Car_Register));
                proVince = cursor.getString(cursor.getColumnIndex(TB_1_CAR.Province_Name));
                totalPart = cursor.getString(cursor.getColumnIndex(TB_1_CAR.TotalPart));

                dataTotalPart.add(carRegis + "  " + proVince + "  อะไหล่ชำรุดทั้งหมด " + totalPart);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataTotalPart;
    }

}
