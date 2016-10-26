package com.saikaew_rus.cm_alert_new;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //version number
    private static final int DB_VERSION = 1;
    // Database Name
    private static final String DB_NAME = "CM_A";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TB_1_CAR = "CREATE TABLE " + TB_1_CAR.TABLE + " ("
                + TB_1_CAR.Car_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_1_CAR.Type_Car_Id + " INTEGER,"
                + TB_1_CAR.Car_Register + " TEXT,"
                + TB_1_CAR.Car_Tax_Date + " TEXT)";

        String CREATE_TB_2_DUE_OF_PART_FIX = "CREATE TABLE " + TB_2_DUE_OF_PART_FIX.TABLE + " ("
                + TB_2_DUE_OF_PART_FIX.Fix_Due_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_2_DUE_OF_PART_FIX.Part_Id + " INTEGER ,"
                + TB_2_DUE_OF_PART_FIX.Car_Id + " INTEGER ,"
                + TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo + " INTEGER ,"
                + TB_2_DUE_OF_PART_FIX.Fix_Due_Date + " INTEGER)";

        String CREATE_TB_3_DUE_OF_PART_STANDART = "CREATE TABLE " + TB_3_DUE_OF_PART_STANDART.TABLE + " ("
                + TB_3_DUE_OF_PART_STANDART.St_Due_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_3_DUE_OF_PART_STANDART.Part_Id + " INTEGER ,"
                + TB_3_DUE_OF_PART_STANDART.Type_Car_Id + " INTEGER ,"
                + TB_3_DUE_OF_PART_STANDART.St_Due_Date + " INTEGER ,"
                + TB_3_DUE_OF_PART_STANDART.St_Due_Kilo + " INTEGER)";

        String CREATE_TB_4_HISTORYS_OF_CAR = "CREATE TABLE " + TB_4_HISTORYS_OF_CAR.TABLE + " ("
                + TB_4_HISTORYS_OF_CAR.History_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_4_HISTORYS_OF_CAR.Fix_Due_Id + " INTEGER ,"
                + TB_4_HISTORYS_OF_CAR.Car_Id + " INTEGER ,"
                + TB_4_HISTORYS_OF_CAR.Changed_Date + " TEXT,"
                + TB_4_HISTORYS_OF_CAR.Changed_Kilo + " REAL ,"
                + TB_4_HISTORYS_OF_CAR.Next_Changed_Date + " TEXT,"
                + TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo + " REAL)";

        String CREATE_TB_5_PARTS = "CREATE TABLE " + TB_5_PARTS.TABLE + " ("
                + TB_5_PARTS.Part_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_5_PARTS.Part_Name + " TEXT)";

        String CREATE_TB_6_RUN_DATA = "CREATE TABLE " + TB_6_RUN_DATA.TABLE + " ("
                + TB_6_RUN_DATA.Run_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_6_RUN_DATA.Car_Id + " INTEGER ,"
                + TB_6_RUN_DATA.Run_Date_Start + " TEXT,"
                + TB_6_RUN_DATA.Run_Date_End + " TEXT,"
                + TB_6_RUN_DATA.Run_Kilo_Start + " REAL,"
                + TB_6_RUN_DATA.Run_Kilo_End + " REAL)";

        String CREATE_TB_7_SYSCONFIG = "CREATE TABLE " + TB_7_SYSCONFIG.TABLE + " ("
                + TB_7_SYSCONFIG.SID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_7_SYSCONFIG.Syscode + " TEXT,"
                + TB_7_SYSCONFIG.Sysvalue + " INTEGER)";

        String CREATE_TB_8_TYPE_OF_CAR = "CREATE TABLE " + TB_8_TYPE_OF_CAR.TABLE + " ("
                + TB_8_TYPE_OF_CAR.Type_Car_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_8_TYPE_OF_CAR.Type_Car_Name + " TEXT)";

        String CREATE_TB_9_USER = "CREATE TABLE " + TB_9_USER.TABLE + " ("
                + TB_9_USER.User_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_9_USER.User_Name + " TEXT,"
                + TB_9_USER.User_Birth + " TEXT,"
                + TB_9_USER.User_Due_Date_Driving + " TEXT)";

        db.execSQL(CREATE_TB_1_CAR);
        db.execSQL(CREATE_TB_2_DUE_OF_PART_FIX);
        db.execSQL(CREATE_TB_3_DUE_OF_PART_STANDART);
        db.execSQL(CREATE_TB_4_HISTORYS_OF_CAR);
        db.execSQL(CREATE_TB_5_PARTS);
        db.execSQL(CREATE_TB_6_RUN_DATA);
        db.execSQL(CREATE_TB_7_SYSCONFIG);
        db.execSQL(CREATE_TB_8_TYPE_OF_CAR);
        db.execSQL(CREATE_TB_9_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + TB_1_CAR.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_2_DUE_OF_PART_FIX.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_3_DUE_OF_PART_STANDART.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_4_HISTORYS_OF_CAR.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_5_PARTS.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_6_RUN_DATA.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_7_SYSCONFIG.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_8_TYPE_OF_CAR.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_9_USER.TABLE);

        // Create tables again
        onCreate(db);
    }
}
