package com.saikaew_rus.cm_alert_new;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

    DBHelper mHelper;
    SQLiteDatabase mDb;

    private DatePickerDialog mDatePicker;
    private DatePickerDialog mDatePicker_2;

    private Calendar mCalendar;

    private Button mDateButton;
    private TextView mTextDate;

    private Button mDateButton_2;
    private TextView mTextDate_2;

    private EditText mTextName;

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

        mHelper = new DBHelper(this);
        mDb = mHelper.getWritableDatabase();

        Button button;
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUser = mTextName.getText().toString();
                String birthUser = mTextDate.getText().toString();
                String diverUser = mTextDate_2.getText().toString();

                ContentValues val_1 = new ContentValues();
                val_1.put(TB_9_USER.User_Name, nameUser);
                val_1.put(TB_9_USER.User_Birth, birthUser);
                val_1.put(TB_9_USER.User_Due_Date_Driving, diverUser);
                mDb.insert(TB_9_USER.TABLE, null, val_1);

                Intent go = new Intent(getApplicationContext(), CM_3_Car.class);
                startActivity(go);
                finish();
            }
        });

        Button button1;
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

//                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
                    SimpleDateFormat dfm = new SimpleDateFormat("dd-MMMM-yyyy");
                    mCalendar.set(year, month, day);
                    Date date = mCalendar.getTime();
//                    String textDate = dateFormat.format(date);
                    String textDate = dfm.format(date);
                    mTextDate_2.setText(textDate);

                }
            };
}
