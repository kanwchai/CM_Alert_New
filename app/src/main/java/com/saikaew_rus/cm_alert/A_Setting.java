package com.saikaew_rus.cm_alert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class A_Setting extends AppCompatActivity {

    TextView textView_Lang, textView_Alert, textView_Time;
    Spinner spinner;
    ImageView imageView;

    Repo_11_SYSCONFIG repo_11_sysconfig;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_setting);
        this.setTitle(A_Word_App.title_setting[A_Word_App.language]);

        setLayout();
        setValue();
        setEvent();

    }

    public void setLayout() {
        textView_Lang = (TextView) findViewById(R.id.textView23);
        textView_Alert = (TextView) findViewById(R.id.textView24);
        textView_Time = (TextView) findViewById(R.id.textView25);
        spinner = (Spinner) findViewById(R.id.spinnerLang);
        imageView = (ImageView) findViewById(R.id.imageView12);
    }

    public void setValue() {
        repo_11_sysconfig = new Repo_11_SYSCONFIG(this);
        textView_Lang.setText(A_Word_App.setting_lang[A_Word_App.language]);
        textView_Alert.setText(A_Word_App.setting_alert[A_Word_App.language]);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, A_Word_App.setting_lang_value);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    }

    public void setEvent() {
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                A_Word_App.language = i;
                imageView.setBackgroundResource(A_Word_App.langChooseImg[A_Word_App.language]);
                repo_11_sysconfig.update(i);
                setValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner.setSelection(A_Word_App.language);
        imageView.setBackgroundResource(A_Word_App.langChooseImg[A_Word_App.language]);

    }
}
