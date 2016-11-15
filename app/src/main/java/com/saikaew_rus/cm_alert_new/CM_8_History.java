package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CM_8_History extends AppCompatActivity {

    Repo_4_HISTORYS_OF_CAR repo_4_historys_of_car;
    Repo_1_CAR repo_1_car;
    TB_1_CAR tb_1_car;

    String part_name;
    int car_id;
    ListAdapter adapter;
    ListView listView;
    AutoCompleteTextView autoCompleteTextView;
    Button button;
    String[] dataAutocom;
    ArrayList<HashMap<String, String>> getListPart;
    TextView carRegis;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_history);

        carRegis = (TextView) findViewById(R.id.car_regis_his);
        button = (Button) findViewById(R.id.bFind);
        listView = (ListView) findViewById(R.id.listView3);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);

        repo_4_historys_of_car = new Repo_4_HISTORYS_OF_CAR(this);
        repo_1_car = new Repo_1_CAR(this);
        tb_1_car = new TB_1_CAR();
        intent = getIntent();
        car_id = intent.getIntExtra("car_Id", 0);
        part_name = "";
        getLisHis();
        tb_1_car = repo_1_car.getCarById(car_id);
        carRegis.setText(tb_1_car.car_Register);


        dataAutocom = repo_4_historys_of_car.getPartNameByCarId(car_id);
        ArrayAdapter dataAdapter = new ArrayAdapter(CM_8_History.this, android.R.layout.simple_dropdown_item_1line, dataAutocom);
        autoCompleteTextView.setAdapter(dataAdapter);
        autoCompleteTextView.setThreshold(1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLisHis();
            }
        });

    }

    public void getLisHis() {
        part_name = autoCompleteTextView.getText().toString();
        getListPart = repo_4_historys_of_car.getHisList_By_CarId_PartName(car_id, part_name);
        adapter = new SimpleAdapter(CM_8_History.this,
                getListPart,
                R.layout.view_his_car,
                new String[]{TB_5_PARTS.Part_Name, TB_4_HISTORYS_OF_CAR.Changed_Kilo, TB_4_HISTORYS_OF_CAR.Next_Changed_Kilo, TB_4_HISTORYS_OF_CAR.Changed_Date, TB_4_HISTORYS_OF_CAR.Next_Changed_Date},
                new int[]{R.id.part_name, R.id.chg_kilo, R.id.chg_kilo_next, R.id.chg_date, R.id.chg_date_next});
        listView.setAdapter(adapter);
    }
}
