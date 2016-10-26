package com.saikaew_rus.cm_alert_new;

/**
 * Created by NB_A on 18/10/2559.
 */
public class TB_4_HISTORYS_OF_CAR {
    // Labels table name
    public static final String TABLE = "HISTORYS_OF_CAR";

    // Labels Table Columns names
    public static final String History_Id = "History_Id ";
    public static final String Fix_Due_Id = "Fix_Due_Id";
    public static final String Car_Id = "Car_Id ";
    public static final String Changed_Date = "Changed_Date";
    public static final String Changed_Kilo = "Changed_Kilo";
    public static final String Next_Changed_Date = "Next_Changed_Date";
    public static final String Next_Changed_Kilo = "Next_Changed_Kilo";

    //property help us to keep data
    public int history_Id;
    public int fix_Due_Id;
    public int car_Id;
    public String changed_Date;
    public double changed_Kilo;
    public String next_Changed_Date;
    public double next_Changed_Kilo;

}
