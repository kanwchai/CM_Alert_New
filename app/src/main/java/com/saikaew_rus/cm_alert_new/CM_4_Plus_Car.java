package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_4_Plus_Car extends AppCompatActivity {

    private DatePickerDialog mDatePicker;
    private Calendar mCalendar;
    private TextView mDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_plus_car);

        mDateEditText = (TextView) findViewById(R.id.editText6);
        mCalendar = Calendar.getInstance();

        Button button;
        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(getApplicationContext(), CM_3_Car.class);
                startActivity(go);
                finish();
            }
        });

        Button button1;
        button1 = (Button) findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1 = new Intent(getApplicationContext(), CM_3_Car.class);
                startActivity(go1);
                finish();
            }
        });

        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePicker.setYearRange(2000, 2020);
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
                    mCalendar.set(year, month, day);
                    Date date = mCalendar.getTime();
                    String textDate = dfm.format(date);
                    mDateEditText.setText(" "+textDate);
                }
            };

}

