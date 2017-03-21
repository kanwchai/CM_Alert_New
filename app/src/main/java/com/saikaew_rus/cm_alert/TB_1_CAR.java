package com.saikaew_rus.cm_alert;

/**
 * Created by NB_A on 18/10/2559.
 */
public class TB_1_CAR {
    // Labels table name
    public static final String TABLE = "CAR";

    // Labels Table Columns names
    public static final String Car_Id = "Car_Id";
    public static final String Type_Oil_Id = "Type_Oil_Id";
    public static final String Type_Gas_Id = "Type_Gas_Id";
    public static final String Province_Name = "Province_Name";
    public static final String Car_Register = "Car_Register";
    public static final String Car_Tax_Date = "Car_Tax_Date";
    public static final String Car_Pic = "Car_Pic";

    public static final String SetTitle = "SetTitle";
    public static final String SetColor = "SetColor";
    public static final String PartBroke = "partBroke";
    public static final String PartTotal = "partTotal";
    public static final String TotalPart = "TotalPart";

    //property help us to keep data
    public int car_Id;
    public int type_Oil_Id;
    public int type_Gas_Id;
    public String car_Register;
    public String car_Tax_Date;
    public String province_Name;
    public int car_Pic;

    public static final int[] carPic = {R.drawable.car_select_14_03_2560_pink
            , R.drawable.car_select_14_03_2560_red
            , R.drawable.car_select_14_03_2560_orange
            , R.drawable.car_select_14_03_2560_yellow
            , R.drawable.car_select_14_03_2560_green
            , R.drawable.car_select_14_03_2560_blue
            , R.drawable.car_select_14_03_2560_gray};
}
