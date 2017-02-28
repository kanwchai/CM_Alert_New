package com.saikaew_rus.cm_alert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_2_Add_User extends AppCompatActivity {

    DatePickerDialog mDatePicker;
    DatePickerDialog mDatePicker_2;
    Calendar mCalendar;
    TextView mTextDate, mTextDate_2;
    EditText mTextName;
    Repo_9_USER repo_9_user;
    TB_9_USER tb_9_user;
    LinearLayout linLayBirth, linLayExpired;
    A_Toast_Time a_toast_time;

    Button bSave;
    Button bSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_main);

        setLayout();
        setValue();
        setEvent();
    }

    public void setLayout() {
        mTextDate = (TextView) findViewById(R.id.textView1);
        mTextDate_2 = (TextView) findViewById(R.id.textView2);
        mTextName = (EditText) findViewById(R.id.editText);
        bSave = (Button) findViewById(R.id.button);
        linLayBirth = (LinearLayout) findViewById(R.id.linBirth);
        linLayExpired = (LinearLayout) findViewById(R.id.linLicense);
        mCalendar = Calendar.getInstance();
        bSkip = (Button) findViewById(R.id.button2);
    }

    public void setValue() {
        repo_9_user = new Repo_9_USER(this);
        tb_9_user = new TB_9_USER();
        a_toast_time = new A_Toast_Time();

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

    public void setEvent() {
        mTextName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    hideSoftKeyboard(CM_2_Add_User.this);
                }

                return false;
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tb_9_user.user_Name = mTextName.getText().toString();
                if (mTextName.getText().toString().matches("") ||
                        mTextDate.getText().toString().matches("") ||
                        mTextDate_2.getText().toString().matches("")) {
                    a_toast_time.Toast_Time(getApplicationContext(), "Please complete the form below.", 1200);
                } else {
                    repo_9_user.insert(tb_9_user);
                    Intent go1 = new Intent(getApplicationContext(), CM_3_Car_Recycle.class);
                    //**********  CLEAR ALL XML  **********//
                    go1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(go1);
                    finish();
                }

            }
        });

        bSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1 = new Intent(getApplicationContext(), CM_3_Car_Recycle.class);
                go1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(go1);
                finish();
            }
        });

        linLayBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.setYearRange(1950, 2030);
                mDatePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        linLayExpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker_2.setYearRange(2000, 2030);
                mDatePicker_2.show(getSupportFragmentManager(), "datePicker");
            }
        });
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
                    tb_9_user.user_Birth = textDate_insert;
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
                    tb_9_user.user_Due_Date_Driving = textDate_insert;
                    mTextDate_2.setText(textDate);

                }
            };

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
