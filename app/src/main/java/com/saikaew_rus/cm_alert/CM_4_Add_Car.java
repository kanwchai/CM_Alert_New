package com.saikaew_rus.cm_alert;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_4_Add_Car extends AppCompatActivity {

    DatePickerDialog mDatePicker;
    Calendar mCalendar;
    TextView mTextDate, chooseColor;
    Button b_enter, b_cancel;
    EditText regisFront, regisBack, kilo;
    String[] dataAdap;
    RadioGroup radioGroup;
    AutoCompleteTextView autoProvince;
    LinearLayout linearLayout;
    ImageView imageCar;
    RadioButton oil_1,oil_2;
    CheckBox gas_1,gas_2, gas_3;

    TB_1_CAR tb_1_car;
    Repo_1_CAR repo_1_car;

    TB_6_RUN_DATA tb_6_run_data;
    Repo_6_RUN_DATA repo_6_run_data;

    Repo_2_DUE_OF_PART_FIX repo_2_due_of_part_fix;
    Repo_4_HISTORYS_OF_CAR repo_4_historys_of_car;
    Repo_10_PROVINCES repo_10_provinces;
    A_Repo_Check repo_check;

    SimpleDateFormat sdf;
    A_Toast_Time a_toast_time;
    ArrayAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_add_car);
        this.setTitle(R.string.title_add_car);

        setLayout();
        setValue();
        setEvent();

    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
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

    public void setLayout() {
        mCalendar = Calendar.getInstance();
        chooseColor = (TextView) findViewById(R.id.ac_1);
        regisFront = (EditText) findViewById(R.id.ac_2);
        regisBack = (EditText) findViewById(R.id.ac_3);
        autoProvince = (AutoCompleteTextView) findViewById(R.id.ac_4);
        kilo = (EditText) findViewById(R.id.ac_5);
        mTextDate = (TextView) findViewById(R.id.ac_6);
        oil_1 = (RadioButton) findViewById(R.id.benzine);
        oil_2 = (RadioButton) findViewById(R.id.diesel);
        gas_1 = (CheckBox) findViewById(R.id.ngv);
        gas_2 = (CheckBox) findViewById(R.id.lpg);
        gas_3 = (CheckBox) findViewById(R.id.hyb);
        b_enter = (Button) findViewById(R.id.button3);
        b_cancel = (Button) findViewById(R.id.button4);
        radioGroup = (RadioGroup) findViewById(R.id.radioOil);
        linearLayout = (LinearLayout) findViewById(R.id.linNumKilo);
        imageCar = (ImageView) findViewById(R.id.imageView2);

    }

    public void setValue() {
        repo_1_car = new Repo_1_CAR(this);
        repo_6_run_data = new Repo_6_RUN_DATA(this);
        repo_10_provinces = new Repo_10_PROVINCES(this);
        repo_2_due_of_part_fix = new Repo_2_DUE_OF_PART_FIX(this);
        repo_4_historys_of_car = new Repo_4_HISTORYS_OF_CAR(this);
        repo_check = new A_Repo_Check(this);
        a_toast_time = new A_Toast_Time();

        tb_1_car = new TB_1_CAR();
        tb_6_run_data = new TB_6_RUN_DATA();

        tb_1_car.type_Gas_Id = 1;
        tb_1_car.type_Oil_Id = 1;
        tb_1_car.car_Pic = 0;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        dataAdap = repo_10_provinces.getProvincesList_1();

        dataAdapter = new ArrayAdapter(CM_4_Add_Car.this, android.R.layout.simple_dropdown_item_1line, dataAdap);
        autoProvince.setAdapter(dataAdapter);
        autoProvince.setThreshold(1);
    }

    public void setEvent() {
        kilo.setFilters(new InputFilter[]{new A_SetMinMaxNumber("1", "500000")});

        regisFront.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    regisBack.requestFocus();
                }
                return false;
            }
        });

        autoProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kilo.requestFocus();
            }
        });

        kilo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    hideSoftKeyboard(CM_4_Add_Car.this);
                }
                return false;
            }
        });

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

        linearLayout.setOnClickListener(new View.OnClickListener() {
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

    public void chooseColor(View view) {
        switch (view.getId()) {
            case R.id.color_pink:
                imageCar.setBackgroundResource(TB_1_CAR.carPic[0]);
                tb_1_car.car_Pic = 0;
                break;
            case R.id.color_red:
                imageCar.setBackgroundResource(TB_1_CAR.carPic[1]);
                tb_1_car.car_Pic = 1;
                break;
            case R.id.color_orange:
                imageCar.setBackgroundResource(TB_1_CAR.carPic[2]);
                tb_1_car.car_Pic = 2;
                break;
            case R.id.color_yellow:
                imageCar.setBackgroundResource(TB_1_CAR.carPic[3]);
                tb_1_car.car_Pic = 3;
                break;
            case R.id.color_green:
                imageCar.setBackgroundResource(TB_1_CAR.carPic[4]);
                tb_1_car.car_Pic = 4;
                break;
            case R.id.color_blue:
                imageCar.setBackgroundResource(TB_1_CAR.carPic[5]);
                tb_1_car.car_Pic = 5;
                break;

        }

    }

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
        if (regisFront.getText().toString().matches("")
                || regisBack.getText().toString().matches("")
                || kilo.getText().toString().matches("")
                || mTextDate.getText().toString().matches("")
                || autoProvince.getText().toString().matches("")) {
            a_toast_time.Toast_Time(this, "Please complete the form below.", 1200);
        } else {
            if (regisFront.getText().toString().isEmpty()) {
                tb_1_car.car_Register = regisBack.getText().toString();
            } else {
                tb_1_car.car_Register = regisFront.getText().toString() + " - " + regisBack.getText().toString();

            }
            tb_1_car.province_Name = autoProvince.getText().toString();

            if (repo_check.chk_Car(tb_1_car.car_Register, tb_1_car.province_Name) >= 1) {
                a_toast_time.Toast_Time(this, "Car registration " + regisBack.getText().toString() + " is duplicate", 1200);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void hideSoftKeyboard(AppCompatActivity appCompatActivity) {
        InputMethodManager inputMethodManager = (InputMethodManager) appCompatActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(appCompatActivity.getCurrentFocus().getWindowToken(), 0);
    }

}

