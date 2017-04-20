package com.saikaew_rus.cm_alert;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Locale;

public class A_Choose_Language extends AppCompatActivity {

    String langNum;
    Configuration config = new Configuration();

    TB_11_Sysconfig tb_11_sysconfig;
    Repo_11_SYSCONFIG repo_11_sysconfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_choose_language);
        this.setTitle(R.string.title_choose_language);

        setValue();
    }

    public void setValue() {
        tb_11_sysconfig = new TB_11_Sysconfig();
        repo_11_sysconfig = new Repo_11_SYSCONFIG(this);
        langNum = "";
    }

    public void chooseLang(View view) {
        switch (view.getId()) {
            case R.id.c_eng:
                langNum = "0";
                config.locale = Locale.ENGLISH;
                mIntent();
                break;
            case R.id.c_thai:
                langNum = "1";
                config.locale = new Locale("th");
                mIntent();
                break;
        }
    }

    public void mIntent() {
        tb_11_sysconfig.sys_Code = TB_11_Sysconfig.Sys_Code_Language;
        tb_11_sysconfig.sys_Desc = "language";
        tb_11_sysconfig.sys_Value = langNum;
        repo_11_sysconfig.insert(tb_11_sysconfig);
        tb_11_sysconfig.sys_Code = TB_11_Sysconfig.Sys_Code_Alarm_Time;
        tb_11_sysconfig.sys_Desc = "alert time";
        tb_11_sysconfig.sys_Value = "08:00";
        repo_11_sysconfig.insert(tb_11_sysconfig);
        A_Word_App.language = Integer.parseInt(langNum);
        getResources().updateConfiguration(config, null);

        Intent intent = new Intent(getApplicationContext(), CM_2_Add_User.class);
        startActivity(intent);
        finish();
    }
}
