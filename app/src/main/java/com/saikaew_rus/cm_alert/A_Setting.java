package com.saikaew_rus.cm_alert;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class A_Setting extends AppCompatActivity {

    TextView textView_Time;
    Spinner spinner;

    Repo_11_SYSCONFIG repo_11_sysconfig;
    ArrayAdapter<String> arrayAdapter;

    HashMap<String, String> alarm;

    TimePickerDialog mTimePicker;
    Configuration config = new Configuration();
    Locale[] locale;

    String[] lang;
    String language;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_setting);
        this.setTitle(R.string.title_setting);

        setLayout();
        setValue();
        setEvent();
    }

    public void setLayout() {
        textView_Time = (TextView) findViewById(R.id.textView25);
        spinner = (Spinner) findViewById(R.id.spinnerLang);
    }

    public void setValue() {
        repo_11_sysconfig = new Repo_11_SYSCONFIG(this);
        alarm = repo_11_sysconfig.getConfig(TB_11_Sysconfig.Sys_Code, TB_11_Sysconfig.Sys_Code_Alarm_Time);

        textView_Time.setText(alarm.get(TB_11_Sysconfig.Sys_Value));

        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.setting_lang_value));
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        mTimePicker = TimePickerDialog.newInstance(onTimeSetListener,
                10,     // หน่วยเข็มชั่วโมง
                30,     // เข็มนาที
                true,   // ใช้ระบบนับแบบ 24-Hr หรือไม่? (0 - 23 นาฬิกา)
                false); // ให้สั่นหรือไม่?

        textView_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        lang = new String[]{"en", "th"};
        language = getResources().getConfiguration().locale.getLanguage();
        pos = Arrays.asList(lang).indexOf(language);
        Log.d("testlang2", language + " -- " + pos);
        locale = new Locale[lang.length];
        for (int i = 0; i < lang.length; i++) {
            locale[i] = new Locale(lang[i]);
        }

    }

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
            textView_Time.setText(" " + hourOfDay + " : " + minute);
            repo_11_sysconfig.update(TB_11_Sysconfig.Sys_Code_Alarm_Time, hourOfDay + ":" + minute);
            setValue();
        }
    };

    public void setEvent() {
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                config.locale = locale[i];
                getResources().updateConfiguration(config, null);

                Log.d("testlang3", language + " -- " + pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
