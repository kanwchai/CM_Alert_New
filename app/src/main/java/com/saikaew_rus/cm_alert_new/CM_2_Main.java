package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_2_Main extends AppCompatActivity {

    private DatePickerDialog mDatePicker;
    private DatePickerDialog mDatePicker_2;

    private Calendar mCalendar;

    private TextView mTextDate;

    private TextView mTextDate_2;

    private EditText mTextName;

    private Repo_9_USER repo;
    private TB_9_USER user;

    Button bSave;
    Button bSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_main);

        mTextDate = (TextView) findViewById(R.id.textView1);

        mTextDate_2 = (TextView) findViewById(R.id.textView2);

        mCalendar = Calendar.getInstance();

        mTextName = (EditText) findViewById(R.id.editText);

        bSave = (Button) findViewById(R.id.button);

        repo = new Repo_9_USER(this);
        user = new TB_9_USER();

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.user_Name = mTextName.getText().toString();
                if (mTextName.getText().toString().matches("") ||
                        mTextDate.getText().toString().matches("") ||
                        mTextDate_2.getText().toString().matches("")) {
                    //***************  Set Toast duration  ***************//
                    final Toast toast = Toast.makeText(getApplicationContext(), "Please complete the form below.", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 1500);
                    //*************  End Set Toast duration  *************//
                } else {
                    repo.insert(user);
                    Intent go1 = new Intent(getApplicationContext(), CM_3_Car.class);
                    //**********  CLEAR ALL XML  **********//
                    go1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(go1);
                }

            }
        });

        bSkip = (Button) findViewById(R.id.button2);

        bSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1 = new Intent(getApplicationContext(), CM_3_Car.class);
                go1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(go1);
            }
        });

        mTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.setYearRange(1950, 2030);
                mDatePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        mTextDate_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker_2.setYearRange(2000, 2030);
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
                    SimpleDateFormat dfm_insert = new SimpleDateFormat("yyyy-MM-dd");

                    mCalendar.set(year, month, day);
                    Date date = mCalendar.getTime();

                    String textDate = dfm.format(date);
                    String textDate_insert = dfm_insert.format(date);
                    user.user_Birth = textDate_insert;
                    mTextDate.setText(textDate);

                }
            };

    private DatePickerDialog.OnDateSetListener onDateSetListener_2 =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

                    SimpleDateFormat dfm = new SimpleDateFormat("dd-MMMM-yyyy");
                    SimpleDateFormat dfm_insert = new SimpleDateFormat("yyyy-MM-dd");

                    mCalendar.set(year, month, day);
                    Date date = mCalendar.getTime();

                    String textDate = dfm.format(date);
                    String textDate_insert = dfm_insert.format(date);
                    user.user_Due_Date_Driving = textDate_insert;
                    mTextDate_2.setText(textDate);

                }
            };

}
