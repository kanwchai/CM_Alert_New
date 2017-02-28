package com.saikaew_rus.cm_alert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
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
    ImageButton button;
    String[] dataAutocom;
    ArrayList<HashMap<String, String>> getListPart;
    TextView carRegis;
    Intent intent;
    ImageButton bClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_history);

        carRegis = (TextView) findViewById(R.id.car_regis_his);
        button = (ImageButton) findViewById(R.id.bFind);
        listView = (ListView) findViewById(R.id.listView3);
        bClear = (ImageButton) findViewById(R.id.imageButton2);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);

        lockButtonClear();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        repo_4_historys_of_car = new Repo_4_HISTORYS_OF_CAR(this);
        repo_1_car = new Repo_1_CAR(this);
        tb_1_car = new TB_1_CAR();
        intent = getIntent();
        car_id = intent.getIntExtra(TB_1_CAR.Car_Id, 0);
        part_name = "";
        getLisHis();
        tb_1_car = repo_1_car.getCarById(car_id);
        carRegis.setText(tb_1_car.car_Register);

        dataAutocom = repo_4_historys_of_car.getPartNameByCarId(car_id);
        ArrayAdapter dataAdapter = new ArrayAdapter(CM_8_History.this, android.R.layout.simple_dropdown_item_1line, dataAutocom);
        autoCompleteTextView.setAdapter(dataAdapter);
        autoCompleteTextView.setThreshold(1);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getLisHis();
                hideSoftKeyboard(CM_8_History.this);
                showButtonClear();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLisHis();
                if (!part_name.matches("")) {
                    showButtonClear();
                    hideSoftKeyboard(CM_8_History.this);

                } else {
                    lockButtonClear();
                    hideSoftKeyboard(CM_8_History.this);
                }

            }
        });

        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTextView.setText("");
                getLisHis();
                lockButtonClear();
                hideSoftKeyboard(CM_8_History.this);
            }
        });

    }

    public void lockButtonClear() {
        bClear.setClickable(false);
        bClear.setBackgroundResource(0);
    }

    public void showButtonClear() {
        bClear.setClickable(true);
        bClear.setBackgroundResource(android.R.drawable.ic_delete);
    }

    public void getLisHis() {
        part_name = autoCompleteTextView.getText().toString();
        getListPart = repo_4_historys_of_car.getHisList_By_CarId_PartName(car_id, part_name);
        adapter = new SimpleAdapter(CM_8_History.this,
                getListPart,
                R.layout.view_his_car,
                new String[]{TB_5_PARTS.Part_Name_en, TB_4_HISTORYS_OF_CAR.Changed_Kilo, "chg_date_format"},
                new int[]{R.id.partName, R.id.chg_kilo, R.id.chg_date});
        listView.setAdapter(adapter);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
