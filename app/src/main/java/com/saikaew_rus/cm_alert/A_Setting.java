package com.saikaew_rus.cm_alert;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.HashMap;
import java.util.Locale;

public class A_Setting extends AppCompatActivity {

    TextView textView_Lang, textView_Alert, textView_Time;
    Spinner spinner;

    Repo_11_SYSCONFIG repo_11_sysconfig;
    ArrayAdapter<String> arrayAdapter;

    HashMap<String, String> alarm;

    TimePickerDialog mTimePicker;
    Configuration config = new Configuration();
    Locale[] locale;

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
        textView_Lang = (TextView) findViewById(R.id.textView23);
        textView_Alert = (TextView) findViewById(R.id.textView24);
        textView_Time = (TextView) findViewById(R.id.textView25);
        spinner = (Spinner) findViewById(R.id.spinnerLang);
    }

    public void setValue() {
        repo_11_sysconfig = new Repo_11_SYSCONFIG(this);
        alarm = repo_11_sysconfig.getConfig(TB_11_Sysconfig.Sys_Code, TB_11_Sysconfig.Sys_Code_Alarm_Time);

        textView_Lang.setText(A_Word_App.setting_lang[A_Word_App.language]);
        textView_Alert.setText(A_Word_App.setting_alert[A_Word_App.language]);
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

        locale = new Locale[]{Locale.ENGLISH, new Locale("th")};
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                config.locale = locale[i];
                getResources().updateConfiguration(config, null);

                A_Word_App.language = i;
                repo_11_sysconfig.update(TB_11_Sysconfig.Sys_Code_Language, String.valueOf(i));
                setValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner.setSelection(A_Word_App.language);
    }
}
