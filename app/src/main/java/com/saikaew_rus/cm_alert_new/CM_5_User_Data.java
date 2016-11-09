package com.saikaew_rus.cm_alert_new;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CM_5_User_Data extends AppCompatActivity {

    private DatePickerDialog mDatePicker;
    private DatePickerDialog mDatePicker_2;

    private Calendar mCalendar;

    private TextView mTextDate;

    private TextView mTextDate_2;

    private EditText mTextName;


    private Repo_9_USER repo;
    private TB_9_USER user;

    Button bSave;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_user_data_1);

        mTextDate = (TextView) findViewById(R.id.textView1);

        mTextDate_2 = (TextView) findViewById(R.id.textView2);

        mCalendar = Calendar.getInstance();

        mTextName = (EditText) findViewById(R.id.editText);

        bSave = (Button) findViewById(R.id.button);

        repo = new Repo_9_USER(this);
        user = new TB_9_USER();

        user = repo.getFirstUser();

        mTextName.setText(user.user_Name);
        if (user.user_Birth != null) {
            //*****************************  Convert Format Date  *****************************//

            SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMMM-yyyy");

            Date dateBirth = null;
            try {
                dateBirth = curFormater.parse(user.user_Birth);
                user.user_Birth = postFormater.format(dateBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mTextDate.setText(user.user_Birth);

            Date dateDue = null;
            try {
                dateDue = curFormater.parse(user.user_Due_Date_Driving);
                user.user_Due_Date_Driving = postFormater.format(dateDue);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mTextDate_2.setText(user.user_Due_Date_Driving);

            //***************************** End Convert Format Date  *****************************//
        }

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
                    }, 1000);
                    //***************  End Set Toast  ***************//
                } else {
                    repo.update(user);
                    finish();
                }
            }
        });

        button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
