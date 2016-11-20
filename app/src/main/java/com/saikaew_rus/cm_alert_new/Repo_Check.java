package com.saikaew_rus.cm_alert_new;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by NB_A on 18/10/2559.
 */
public class Repo_Check {
    private MyDatabase dbHelper;

    public Repo_Check(Context context) {
        dbHelper = new MyDatabase(context);
    }

    public void chkDueDate() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " +
                "(SELECT * FROM " + TB_4_HISTORYS_OF_CAR.TABLE + " h," + TB_2_DUE_OF_PART_FIX.TABLE + " df" +
                " ON " + "h." + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " = " + "df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Id +
                " WHERE " + "df." + TB_2_DUE_OF_PART_FIX.Fix_Due_Date + " > 0)" +
                " WHERE julianday(" + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + ") - julianday('now') <= 15";

        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.close();
        db.close();
    }

    public int chkDueKilo(int carid) {
        int dataCar;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " +
                "(SELECT MAX(+" + TB_6_RUN_DATA.Run_Id + "),* FROM " + TB_6_RUN_DATA.TABLE + " GROUP BY " + TB_6_RUN_DATA.Car_Id + ") r ," +
                "(SELECT * FROM " + TB_4_HISTORYS_OF_CAR.TABLE + " h, DUE_OF_PART_FIX df ON h.Fix_due_id = df.fix_due_id WHERE df.Fix_Due_Kilo > 0) hd" +
                "ON r.Car_id = hd.Car_id " +
                "WHERE Next_Changed_Kilo-Run_Kilo_End <= 1500";

        Cursor cursor = db.rawQuery(selectQuery, null);
        dataCar = cursor.getCount();

        cursor.close();
        db.close();

        return dataCar;
    }

    public int che_Car(String car_Reg, String prov_name) {
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

}
