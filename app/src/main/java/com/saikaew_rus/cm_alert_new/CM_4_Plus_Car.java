package com.saikaew_rus.cm_alert_new;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_4_Plus_Car extends AppCompatActivity {

    DatePickerDialog mDatePicker;
    Calendar mCalendar;
    TextView mTextDate;

    EditText regis;
    EditText kilo;

    TB_1_CAR tb_1_car;
    Repo_1_CAR repo_1_car;

    TB_6_RUN_DATA tb_6_run_data;
    Repo_6_RUN_DATA repo_6_run_data;

    Repo_2_DUE_OF_PART_FIX repo_2_due_of_part_fix;
    Repo_4_HISTORYS_OF_CAR repo_4_historys_of_car;
    Repo_10_PROVINCES repo_10_provinces;
    Repo_Check repo_check;

    SimpleDateFormat sdf;
    Button b_enter;
    Button b_cancel;
    RadioGroup radioGroup;
    String[] dataAdap;
    AutoCompleteTextView autoProvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_plus_car);

        mCalendar = Calendar.getInstance();
        regis = (EditText) findViewById(R.id.editText4);
        kilo = (EditText) findViewById(R.id.editText5);
        mTextDate = (TextView) findViewById(R.id.editText6);
        b_enter = (Button) findViewById(R.id.button3);
        b_cancel = (Button) findViewById(R.id.button4);
        autoProvince = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        radioGroup = (RadioGroup) findViewById(R.id.radioOil);

        repo_1_car = new Repo_1_CAR(this);
        repo_6_run_data = new Repo_6_RUN_DATA(this);
        repo_10_provinces = new Repo_10_PROVINCES(this);
        repo_2_due_of_part_fix = new Repo_2_DUE_OF_PART_FIX(this);
        repo_4_historys_of_car = new Repo_4_HISTORYS_OF_CAR(this);
        repo_check = new Repo_Check(this);

        tb_1_car = new TB_1_CAR();
        tb_6_run_data = new TB_6_RUN_DATA();

        tb_1_car.type_Gas_Id = 1;
        tb_1_car.type_Oil_Id = 1;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        dataAdap = repo_10_provinces.getProvincesList_1();

        ArrayAdapter dataAdapter = new ArrayAdapter(CM_4_Plus_Car.this, android.R.layout.simple_dropdown_item_1line, dataAdap);
        autoProvince.setAdapter(dataAdapter);
        autoProvince.setThreshold(1);

        b_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chk_input();
            }
        });

        b_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.benzine:
                        tb_1_car.type_Oil_Id = 1;
                        break;
                    case R.id.diesel:
                        tb_1_car.type_Oil_Id = 2;
                        break;
                }
            }
        });

        mTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePicker.setYearRange(2000, 2030);
                mDatePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        mDatePicker = DatePickerDialog.newInstance(onDateSetListener,
                mCalendar.get(Calendar.YEAR),       // ปี
                mCalendar.get(Calendar.MONTH),      // เดือน
                mCalendar.get(Calendar.DAY_OF_MONTH),// วัน (1-31)
                false);
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

                    SimpleDateFormat dfm = new SimpleDateFormat("dd-MMMM-yyyy");
                    SimpleDateFormat dfm_1 = new SimpleDateFormat("yyyy-MM-dd");

                    mCalendar.set(year, month, day);
                    Date date = mCalendar.getTime();

                    String textDate = dfm.format(date);
                    String textDate_1 = dfm_1.format(date);
                    mTextDate.setText(textDate);
                    tb_1_car.car_Tax_Date = textDate_1;
                }
            };

    public void onCheckboxClicked(View view) {
        // Is the button now checked?
        boolean checked = ((Checkable) view).isChecked();
        CheckBox chk_ngv = (CheckBox) findViewById(R.id.ngv);
        CheckBox chk_lpg = (CheckBox) findViewById(R.id.lpg);
        CheckBox chk_hyb = (CheckBox) findViewById(R.id.hyb);

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.ngv:
                if (checked) {
                    tb_1_car.type_Gas_Id = 2;
                    chk_lpg.setChecked(false);
                    chk_hyb.setChecked(false);
                } else {
                    tb_1_car.type_Gas_Id = 1;
                }
                break;
            case R.id.lpg:
                if (checked) {
                    tb_1_car.type_Gas_Id = 3;
                    chk_ngv.setChecked(false);
                    chk_hyb.setChecked(false);
                } else {
                    tb_1_car.type_Gas_Id = 1;
                }
                break;
            case R.id.hyb:
                if (checked) {
                    tb_1_car.type_Gas_Id = 4;
                    chk_ngv.setChecked(false);
                    chk_lpg.setChecked(false);
                } else {
                    tb_1_car.type_Gas_Id = 1;
                }
                break;
        }
    }

    public void chk_input() {
        if (regis.getText().toString().matches("") ||
                kilo.getText().toString().matches("") ||
                mTextDate.getText().toString().matches("") ||
                autoProvince.getText().toString().matches("")) {
            //***************  Set Toast duration  ***************//
            final Toast toast = Toast.makeText(getApplicationContext(), "Please complete the form below.", Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 1000);
            //***************  End Set Toast  ***************//
        } else {
            tb_1_car.car_Register = regis.getText().toString();
            tb_1_car.province_Name = autoProvince.getText().toString();

            if (repo_check.che_Car(regis.getText().toString(), autoProvince.getText().toString()) >= 1) {
                //***************  Set Toast duration  ***************//
                final Toast toast = Toast.makeText(getApplicationContext(),
                        "Car Registration " + regis.getText().toString() +
                                " not empty", Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 1000);
                //***************  End Set Toast  ***************//
            } else {
                tb_6_run_data.car_Id = repo_1_car.insert(tb_1_car);
                tb_6_run_data.run_Date_Start = sdf.format(Calendar.getInstance().getTime());
                tb_6_run_data.run_Date_End = sdf.format(Calendar.getInstance().getTime());
                tb_6_run_data.run_Kilo_Start = Double.parseDouble(kilo.getText().toString());
                tb_6_run_data.run_Kilo_End = Double.parseDouble(kilo.getText().toString());
                repo_6_run_data.insert(tb_6_run_data);
                repo_2_due_of_part_fix.insert_part_fix(tb_6_run_data.car_Id, tb_1_car.type_Oil_Id, tb_1_car.type_Gas_Id);
                repo_4_historys_of_car.insertBy_CarId(tb_6_run_data.car_Id);

                finish();
            }
        }
    }
}

