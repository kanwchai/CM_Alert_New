package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_2_Main extends AppCompatActivity {

    private DatePickerDialog mDatePicker;
    private DatePickerDialog mDatePicker_2;

    private Calendar mCalendar;

    private Button mDateButton;
    private TextView mTextDate;

    private Button mDateButton_2;
    private TextView mTextDate_2;

    private EditText mTextName;

    Button bSave;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_main);

        mDateButton = (Button) findViewById(R.id.button9);
        mTextDate = (TextView) findViewById(R.id.textView1);

        mDateButton_2 = (Button) findViewById(R.id.button10);
        mTextDate_2 = (TextView) findViewById(R.id.textView2);

        mCalendar = Calendar.getInstance();

        mTextName = (EditText) findViewById(R.id.editText);

        bSave = (Button) findViewById(R.id.button);

        final Repo_9_USER repo = new Repo_9_USER(this);
        final TB_9_USER user = new TB_9_USER();

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.user_Name = mTextName.getText().toString();
                user.user_Birth = mTextDate.getText().toString();
                user.user_Due_Date_Driving = mTextDate_2.getText().toString();
                repo.insert(user);

//                Toast.makeText(this, "เพิ่มข้อมูลผู้ใช้แล้ว", Toast.LENGTH_SHORT).show();

                Intent go1 = new Intent(getApplicationContext(), CM_3_Car.class);
                startActivity(go1);
                finish();
            }
        });


        button1 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1 = new Intent(getApplicationContext(), CM_3_Car.class);
                startActivity(go1);
                finish();
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.setYearRange(2000, 2020);
                mDatePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        mDateButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker_2.setYearRange(2000, 2020);
                mDatePicker_2.show(getSupportFragmentManager(), "datePicker");
            }
        });

        mDatePicker = DatePickerDialog.newInstance(onDateSetListener,
                mCalendar.get(Calendar.YEAR),       // ปี
                mCalendar.get(Calendar.MONTH),      // เดือน
                mCalendar.get(Calendar.DAY_OF_MONTH),// วัน (1-31)
                false);

        mDatePicker_2 = DatePickerDialog.newInstance(onDateSetListener_2,
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
                    mTextDate.setText(textDate);

                }
            };

    private DatePickerDialog.OnDateSetListener onDateSetListener_2 =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

                    SimpleDateFormat dfm = new SimpleDateFormat("dd-MMMM-yyyy");
                    mCalendar.set(year, month, day);
                    Date date = mCalendar.getTime();
                    String textDate = dfm.format(date);
                    mTextDate_2.setText(textDate);

                }
            };

}
