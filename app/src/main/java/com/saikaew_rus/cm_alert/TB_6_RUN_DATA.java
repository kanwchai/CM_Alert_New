package com.saikaew_rus.cm_alert;

/**
 * Created by NB_A on 18/10/2559.
 */
public class TB_6_RUN_DATA {
    // Labels table name
    public static final String TABLE = "RUN_DATA";

    // Labels Table Columns names
    public static final String Run_Id = "Run_Id";
    public static final String Car_Id = "Car_Id";
    public static final String Run_Date_Start = "Run_Date_Start";
    public static final String Run_Date_End = "Run_Date_End";
    public static final String Run_Kilo_Start = "Run_Kilo_Start";
    public static final String Run_Kilo_End = "Run_Kilo_End";

    //property help us to keep data
    public int run_Id;
    public int car_Id;
    public String run_Date_Start;
    public String run_Date_End;
    public double run_Kilo_Start;
    public double run_Kilo_End;
}
