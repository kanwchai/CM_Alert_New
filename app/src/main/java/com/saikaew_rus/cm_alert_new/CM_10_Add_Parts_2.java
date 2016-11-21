package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CM_10_Add_Parts_2 extends AppCompatActivity {

    int carid;
    String partname;
    TB_5_PARTS tb_5_parts;
    Repo_5_PARTS repo_5_parts;
    TB_3_DUE_OF_PART_STANDART tb_3_due_of_part_standart;
    Repo_3_DUE_OF_PART_STANDART repo_3_due_of_part_standart;
    TB_2_DUE_OF_PART_FIX tb_2_due_of_part_fix;
    Repo_2_DUE_OF_PART_FIX repo_2_due_of_part_fix;
    String status;
    int partid;
    TB_4_HISTORYS_OF_CAR tb_4_historys_of_car;
    Repo_4_HISTORYS_OF_CAR repo_4_historys_of_car;
    TB_6_RUN_DATA tb_6_run_data;
    Repo_6_RUN_DATA repo_6_run_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10_add_partstwo);

        final EditText kilo = (EditText) findViewById(R.id.kilo_due);
        final EditText date = (EditText) findViewById(R.id.date_due);
        Button save = (Button) findViewById(R.id.button7);
        final Button cancel = (Button) findViewById(R.id.button8);
        RadioGroup radioManType = (RadioGroup) findViewById(R.id.radioManType);

        repo_3_due_of_part_standart = new Repo_3_DUE_OF_PART_STANDART(this);
        tb_3_due_of_part_standart = new TB_3_DUE_OF_PART_STANDART();
        repo_2_due_of_part_fix = new Repo_2_DUE_OF_PART_FIX(this);
        tb_2_due_of_part_fix = new TB_2_DUE_OF_PART_FIX();
        repo_4_historys_of_car = new Repo_4_HISTORYS_OF_CAR(this);
        tb_4_historys_of_car = new TB_4_HISTORYS_OF_CAR();
        repo_6_run_data = new Repo_6_RUN_DATA(this);
        tb_6_run_data = new TB_6_RUN_DATA();

        Intent getIntent = getIntent();
        carid = getIntent.getIntExtra("car_id", 0);
        partname = getIntent.getStringExtra("part_name");
        tb_6_run_data = repo_6_run_data.getLastRunByCar_Id(carid);

        status = "m";

        radioManType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.modify:
                        status = "m";
                        break;

                    case R.id.repair:
                        status = "r";
                        break;

                    case R.id.clean:
                        status = "c";
                        break;
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tb_5_parts.part_Name = partname;
                partid = repo_5_parts.insert(tb_5_parts);

                tb_3_due_of_part_standart.type_Oil_Id = 99;
                tb_3_due_of_part_standart.type_Gas_Id = 99;
                tb_3_due_of_part_standart.part_Id = partid;
                tb_3_due_of_part_standart.st_Due_Date = Integer.parseInt(date.getText().toString());
                tb_3_due_of_part_standart.st_Due_Kilo = Integer.parseInt(kilo.getText().toString());
                tb_3_due_of_part_standart.st_Due_Status = status;
                repo_3_due_of_part_standart.insert(tb_3_due_of_part_standart);

                tb_2_due_of_part_fix.part_Id = partid;
                tb_2_due_of_part_fix.car_Id = carid;
                tb_2_due_of_part_fix.fix_Due_Date = Integer.parseInt(date.getText().toString());
                tb_2_due_of_part_fix.fix_Due_Kilo = Integer.parseInt(kilo.getText().toString());
                tb_2_due_of_part_fix.fix_Due_Status = status;

                tb_4_historys_of_car.fix_Due_Id = repo_2_due_of_part_fix.insert(tb_2_due_of_part_fix);
                tb_4_historys_of_car.car_Id = carid;
                tb_4_historys_of_car.changed_Kilo = tb_6_run_data.run_Kilo_End;
                tb_4_historys_of_car.next_Changed_Kilo = Integer.parseInt(kilo.getText().toString());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                tb_4_historys_of_car.changed_Date = df.format(c.getTime());
                c.add(Calendar.MONTH, Integer.parseInt(date.getText().toString()));
                tb_4_historys_of_car.next_Changed_Date = df.format(c.getTime());

                repo_4_historys_of_car.insert(tb_4_historys_of_car);

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
