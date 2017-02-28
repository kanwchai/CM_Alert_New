package com.saikaew_rus.cm_alert;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_4_Add_Car extends AppCompatActivity {

    DatePickerDialog mDatePicker;
    Calendar mCalendar;
    TextView mTextDate;

    EditText regis, kilo;

    TB_1_CAR tb_1_car;
    Repo_1_CAR repo_1_car;

    TB_6_RUN_DATA tb_6_run_data;
    Repo_6_RUN_DATA repo_6_run_data;

    Repo_2_DUE_OF_PART_FIX repo_2_due_of_part_fix;
    Repo_4_HISTORYS_OF_CAR repo_4_historys_of_car;
    Repo_10_PROVINCES repo_10_provinces;
    A_Repo_Check repo_check;

    SimpleDateFormat sdf;
    Button b_enter, b_cancel;
    RadioGroup radioGroup;
    String[] dataAdap;
    AutoCompleteTextView autoProvince;
    A_Toast_Time a_toast_time;
    ArrayAdapter dataAdapter;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_plus_car);

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
        regis = (EditText) findViewById(R.id.editText4);
        kilo = (EditText) findViewById(R.id.editText5);
        mTextDate = (TextView) findViewById(R.id.editText6);
        b_enter = (Button) findViewById(R.id.button3);
        b_cancel = (Button) findViewById(R.id.button4);
        autoProvince = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        radioGroup = (RadioGroup) findViewById(R.id.radioOil);
        linearLayout = (LinearLayout) findViewById(R.id.linNumKilo);
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
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        dataAdap = repo_10_provinces.getProvincesList_1();

        dataAdapter = new ArrayAdapter(CM_4_Add_Car.this, android.R.layout.simple_dropdown_item_1line, dataAdap);
        autoProvince.setAdapter(dataAdapter);
        autoProvince.setThreshold(1);
    }

    public void setEvent() {
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
            a_toast_time.Toast_Time(this, "Please complete the form below.", 1200);
        } else {
            tb_1_car.car_Register = regis.getText().toString();
            tb_1_car.province_Name = autoProvince.getText().toString();

            if (repo_check.chk_Car(regis.getText().toString(), autoProvince.getText().toString()) >= 1) {
                a_toast_time.Toast_Time(this, "Car Registration " + regis.getText().toString() + " not empty", 1200);
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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}

