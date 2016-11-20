package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class CM_10_Add_Parts_2 extends AppCompatActivity {

    Repo_1_CAR repo_1_car;
    TB_1_CAR tb_1_car;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10_add_partstwo);

        final EditText kilo = (EditText) findViewById(R.id.kilo_due);
        final EditText date = (EditText) findViewById(R.id.date_due);
        Button save = (Button) findViewById(R.id.button7);
        Button cancel = (Button) findViewById(R.id.button8);
        RadioGroup radioManType = (RadioGroup) findViewById(R.id.radioManType);

        repo_1_car = new Repo_1_CAR(this);
        tb_1_car = new TB_1_CAR();
        repo_3_due_of_part_standart = new Repo_3_DUE_OF_PART_STANDART(this);
        tb_3_due_of_part_standart = new TB_3_DUE_OF_PART_STANDART();
        repo_2_due_of_part_fix = new Repo_2_DUE_OF_PART_FIX(this);
        tb_2_due_of_part_fix = new TB_2_DUE_OF_PART_FIX();

        Intent getIntent = getIntent();
        carid = getIntent.getIntExtra("car_id", 0);
        partname = getIntent.getStringExtra("part_name");

        tb_1_car = repo_1_car.getCarById(carid);
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

                tb_3_due_of_part_standart.type_Oil_Id = tb_1_car.type_Oil_Id;
                tb_3_due_of_part_standart.type_Gas_Id = tb_1_car.type_Gas_Id;
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
                repo_2_due_of_part_fix.insert(tb_2_due_of_part_fix);

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
