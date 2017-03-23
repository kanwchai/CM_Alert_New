package com.saikaew_rus.cm_alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class A_Choose_Language extends AppCompatActivity {
    Spinner spinLang;
    ImageView imageLang;
    ArrayAdapter<String> adapter;
    Button button;

    int langNum;

    TB_11_Sysconfig tb_11_sysconfig;
    Repo_11_SYSCONFIG repo_11_sysconfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_choose_language);
        this.setTitle(A_Word_App.title_choose_language[0]);

        setLayout();
        setValue();
        setEvent();

    }

    public void setLayout() {
        spinLang = (Spinner) findViewById(R.id.spinLang);
        imageLang = (ImageView) findViewById(R.id.imgLang);
        button = (Button) findViewById(R.id.button9);
    }

    public void setValue() {
        tb_11_sysconfig = new TB_11_Sysconfig();
        repo_11_sysconfig = new Repo_11_SYSCONFIG(this);
        langNum = 0;
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, A_Word_App.langChooseWord);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinLang.setAdapter(adapter);

        button.setBackgroundResource(R.mipmap.ic_check);
    }

    public void setEvent() {
        spinLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                imageLang.setBackgroundResource(A_Word_App.langChooseImg[i]);
                langNum = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tb_11_sysconfig.sys_Code = TB_11_Sysconfig.Sys_Code_Language;
                tb_11_sysconfig.sys_Desc = "language";
                tb_11_sysconfig.sys_Value = langNum;
                A_Word_App.language = langNum;

                repo_11_sysconfig.insert(tb_11_sysconfig);

                Intent intent = new Intent(getApplicationContext(), CM_2_Add_User.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
