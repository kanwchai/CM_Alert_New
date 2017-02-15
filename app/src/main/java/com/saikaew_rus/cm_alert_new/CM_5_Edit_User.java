package com.saikaew_rus.cm_alert_new;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_5_Edit_User extends AppCompatActivity {

    DatePickerDialog mDatePicker;
    DatePickerDialog mDatePicker_2;

    Calendar mCalendar;
    TextView mTextDate, mTextDate_2;
    EditText mTextName;
    ImageView imageView;
    LinearLayout linLayBirth, linLayExpired;
    A_Toast_Time a_toast_time;

    Repo_9_USER repo;
    TB_9_USER user;

    Button bSave;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_user_data);

        setLayout();
        setValue();
        setEvent();
    }

    public void setLayout() {
        mTextDate = (TextView) findViewById(R.id.textView1);
        mTextDate_2 = (TextView) findViewById(R.id.textView2);
        mTextName = (EditText) findViewById(R.id.editText);
        bSave = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView8);
        linLayBirth = (LinearLayout) findViewById(R.id.linBirth);
        linLayExpired = (LinearLayout) findViewById(R.id.linLicense);
        button1 = (Button) findViewById(R.id.button2);
    }

    public void setValue() {
        mCalendar = Calendar.getInstance();
        repo = new Repo_9_USER(this);
        user = new TB_9_USER();
        a_toast_time = new A_Toast_Time();

        user = repo.getFirstUser();
        mTextName.setText(user.user_Name);

        if (user.user_Birth != null) {
            //*****************************  Convert Format Date  *****************************//

            SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMMM-yyyy");

            Date dateBirth = null;
            try {
                dateBirth = curFormater.parse(user.user_Birth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mTextDate.setText(postFormater.format(dateBirth));

            Date dateDue = null;
            try {
                dateDue = curFormater.parse(user.user_Due_Date_Driving);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mTextDate_2.setText(postFormater.format(dateDue));

            //***************************** End Convert Format Date  *****************************//
        }

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
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.user_Name = mTextName.getText().toString();
                if (mTextName.getText().toString().matches("") ||
                        mTextDate.getText().toString().matches("") ||
                        mTextDate_2.getText().toString().matches("")) {
                    a_toast_time.Toast_Time(getApplicationContext(), "Please complete the form below.", 1200);
                } else {
                    repo.update(user);
                    finish();
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        mTextName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    hideSoftKeyboard(CM_5_Edit_User.this);
                }
                return false;
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
                    user.user_Birth = dfm_insert.format(date);
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
                    user.user_Due_Date_Driving = dfm_insert.format(date);
                    mTextDate_2.setText(textDate);

                }
            };

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
