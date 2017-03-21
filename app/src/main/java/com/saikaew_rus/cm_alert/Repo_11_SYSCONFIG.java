package com.saikaew_rus.cm_alert;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by NB_A on 20/3/2560.
 */

public class Repo_11_SYSCONFIG {
    private A_MyDatabase dbHelper;

    public Repo_11_SYSCONFIG(Context context) {
        dbHelper = new A_MyDatabase(context);
    }

    public void chkLang() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TB_11_Sysconfig.TABLE+" WHERE "+TB_11_Sysconfig.Sys_Code + " = ";


//        return
    }
}




