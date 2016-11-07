package com.saikaew_rus.cm_alert_new;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
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

    TB_1_CAR car;
    Repo_1_CAR repo_1_car;

    TB_6_RUN_DATA run_data;
    Repo_6_RUN_DATA repo_6_run_data;

    SimpleDateFormat sdf;
    Button b_enter;
    Button b_cancel;

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

        repo_1_car = new Repo_1_CAR(this);
        repo_6_run_data = new Repo_6_RUN_DATA(this);
        car = new TB_1_CAR();
        run_data = new TB_6_RUN_DATA();
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        b_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regis.getText().toString().matches("") ||
                        kilo.getText().toString().matches("") ||
                        mTextDate.getText().toString().matches("")) {
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
                    car.car_Register = regis.getText().toString();

                    run_data.car_Id = repo_1_car.insert(car);
                    run_data.run_Date_Start = sdf.format(Calendar.getInstance().getTime());
                    run_data.run_Date_End = sdf.format(Calendar.getInstance().getTime());
                    run_data.run_Kilo_Start = Double.parseDouble(kilo.getText().toString());
                    run_data.run_Kilo_End = Double.parseDouble(kilo.getText().toString());
                    repo_6_run_data.insert(run_data);
                    finish();
                }

            }
        });

        b_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    car.car_Tax_Date = textDate_1;
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
                    car.type_Car_Id = 2;
                    chk_lpg.setChecked(false);
                    chk_hyb.setChecked(false);
                } else {
                    car.type_Car_Id = 1;
                }
                break;
            case R.id.lpg:
                if (checked) {
                    car.type_Car_Id = 3;
                    chk_ngv.setChecked(false);
                    chk_hyb.setChecked(false);
                } else {
                    car.type_Car_Id = 1;
                }
                break;
            case R.id.hyb:
                if (checked) {
                    car.type_Car_Id = 4;
                    chk_ngv.setChecked(false);
                    chk_lpg.setChecked(false);
                } else {
                    car.type_Car_Id = 1;
                }
                break;
        }
    }
}

