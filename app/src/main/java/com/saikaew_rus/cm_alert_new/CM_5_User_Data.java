package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_5_User_Data extends AppCompatActivity {

    private DatePickerDialog mDatePicker;
    private DatePickerDialog mDatePicker_2;

    private Calendar mCalendar;

    private Button mDateButton;
    private TextView mTextDate;

    private Button mDateButton_2;
    private TextView mTextDate_2;

    private EditText mTextName;

    private int setUser_Id;

    Button bSave;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_user_data_1);

        mDateButton = (Button) findViewById(R.id.button9);
        mTextDate = (TextView) findViewById(R.id.textView1);

        mDateButton_2 = (Button) findViewById(R.id.button10);
        mTextDate_2 = (TextView) findViewById(R.id.textView2);

        mCalendar = Calendar.getInstance();

        mTextName = (EditText) findViewById(R.id.editText);

        bSave = (Button) findViewById(R.id.button);

        final Repo_9_USER repo = new Repo_9_USER(this);
        TB_9_USER user = new TB_9_USER();
        final TB_9_USER finalUser = user;

        Intent intent = getIntent();
        setUser_Id = intent.getIntExtra("student_Id", 1);
        user = repo.getUserById(setUser_Id);

        mTextName.setText(user.user_Name);
        mTextDate.setText(user.user_Birth);
        mTextDate_2.setText(user.user_Due_Date_Driving);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalUser.user_Id = 1;
                finalUser.user_Name = mTextName.getText().toString();
                finalUser.user_Birth = mTextDate.getText().toString();
                finalUser.user_Due_Date_Driving = mTextDate_2.getText().toString();
                repo.update(finalUser);

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
