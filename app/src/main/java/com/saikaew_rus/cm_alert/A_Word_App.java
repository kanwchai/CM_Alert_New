package com.saikaew_rus.cm_alert;

/**
 * Created by NB_A on 20/3/2560.
 */

public class A_Word_App {
    //SetTitle Activity
    public static final String[] title_user = {"User", "ผู้ใช้"};
    public static final String[] title_car = {"Car", "รถยนต์"};
    public static final String[] title_add_car = {"Add Car Data", "เพิ่มข้อมูลรถ"};
    public static final String[] title_edit_user = {"Edit User", "แก้ไขข้อมูลผู้ใช้"};
    public static final String[] title_travel = {"Travel", "การเดินทาง"};
    public static final String[] title_list_part = {"List Parts", "รายการอะไหล่"};
    public static final String[] title_maintenance_history = {"Maintenance History", "ประวัติการดูแลรักษา"};
    public static final String[] title_add_part = {"Add Parts", "เพิ่มอะไหล่รถ"};
    public static final String[] title_about = {"About", "เกี่ยวกับ"};
    public static final String[] title_choose_language = {"Choose Language", "เลือกภาษา"};
    public static final String[] title_setting = {"Setting", "ตั้งค่า"};
    public static final String[] title_notification = {"Notification", "การแจ้งเตือน"};

    //Value Config App
    public static int language = 0;
    public static String timealert = " ";

    //Choose Language
    public static final String[] langChooseWord = {"English", "ไทย"};
    public static final int[] langChooseImg = {R.mipmap.ic_flag_usa, R.mipmap.ic_flag_thai};

    //Add User
    public static final String[] add_user_name = {"Name", "ชื่อ"};
    public static final String[] add_user_birthday = {"BirthDay", "วันเกิด"};
    public static final String[] add_user_exp_lincence = {"Expired driver license", "วันหมดอายุใบขับขี่"};
    public static final String[] add_user_ok = {"OK", "ตกลง"};
    public static final String[] add_user_cancel = {"CANCEL", "ยกเลิก"};

    //Menu
    public static final String[] menu_notification = {"Notification", "การแจ้งเตือน"};
    public static final String[] menu_setting = {"Setting", "ตั้งค่า"};
    public static final String[] menu_about = {"About", "จัดทำโดย"};
    public static final String[] menu_alarm = {"Alert Time", "ตั้งเวลาแจ้งเตือน"};
    public static final String[] menu_user = {"Data User", "ข้อมูลผู้ใช้"};

    //Setting
    public static final String[] setting_lang = {"Language", "ภาษา"};
    public static final String[] setting_lang_value = {"English", "ไทย"};
    public static final String[] setting_alert = {"Alert Time", "เวลาแจ้งเตือน"};

    //About
    public static final int[] about_logo = {R.drawable.rmutsb_logo, R.drawable.rmutsb_logo};
    public static final String[] about_title = {"Developed By", "พัฒนาโดย"};
    public static final String[] about_uni_name = {"Rajamangala University of Technology Suvarnabhumi", "มหาวิทยาลัยเทคโนโลยีราชมงคลสุวรรณภูมิ"};
    public static final String[] about_uni_faculty = {"Faculty of Science and Technology", "คณะวิทยาศาสตร์และเทคโนโลยี"};
    public static final String[] about_uni_branch = {"Computer Science", "สาขาวิทยาการคอมพิวเตอร์"};
    public static final String[] about_name_1 = {"Miss Saikaew Ketmanee", "นางสาว ทรายแก้ว เกษมณี"};
    public static final String[] about_email_1 = {"nunuza002@hotmail.com", "nunuza002@hotmail.com"};
    public static final String[] about_name_2 = {"Mr. Kwanchai Trakulsantichai", "นาย ขวัญชัย ตระกูลสันติชัย"};
    public static final String[] about_email_2 = {"kanwchai@hotmail.com", "kanwchai@hotmail.com"};

    //Travel
    public static final String[] travel_regis_car = {"Registration", "ทะเบียนรถ"};
    public static final String[] travel_kilo_car = {"Number Kilo", "เลขกิโล"};
    public static final String[] travel_start = {"Start", "เริ่ม"};
    public static final String[] travel_stop = {"Stop", "หยุด"};
    public static final String[] travel_connecting = {"Connecting", "เชื่อมต่อ"};
    public static final String[] travel_disconnecting = {"Disconnecting", "หยุดเชื่อมต่อ"};

    //Dialog Show Alert
    public static final String[] dialog_list_maintenance = {"List of maintenance", "รายการดูแลรักษา"};
    public static final String[] dialog_driving_licence = {"Driving licence", "ใบขับขี่"};
    public static final String[] dialog_car = {"Car", "รถยนต์"};
    public static final String[] dialog_tax_car = {"Tax car", "ภาษีรถยนต์"};
    public static final String[] dialog_close_alert = {"Do not show 1 day","ปิดการแจ้งเตือน 1 วัน"};

    //List Part
    public static final String[] lp_car_regis = {"Car Registration", "ทะเบียนรถ"};
    public static final String[] lp_no_kilo = {"No. Kilometer", "เลขกิโลรถยนต์"};
    public static final String[] lp_exp_tax_date = {"Expiration Date Car Tax", "วันหมดอายุภาษีรถยนต์"};

    //Add Car
    public static final String[] add_color = {"(Choose Color)", "(เลือกสี)"};
    public static final String[] add_regis_front = {"Regis Front", "เลขทะเบียนหน้า"};
    public static final String[] add_regis_back = {"Regis Back", "เลขทะเบียนหลัง"};
    public static final String[] add_province = {"Province", "จังหวัด"};
    public static final String[] add_no_kilo = {"Number Kilometers", "เลขกิโลรถยนต์"};
    public static final String[] add_tax = {"Expired Tax", "วันต่อภาษี"};
    public static final String[] add_oil_gasso = {"GASOLEAN", "เบนซิน"};
    public static final String[] add_oil_diesel = {"DIESEL", "ดีเซล"};
    public static final String[] add_gas_ngv = {"NGV", "เอ็นจีวี"};
    public static final String[] add_gas_lpg = {"LPG", "แอลพีจี"};
    public static final String[] add_gas_hyb = {"HYBRID", "ไฮบริด"};

}
