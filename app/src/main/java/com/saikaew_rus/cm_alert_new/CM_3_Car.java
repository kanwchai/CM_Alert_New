package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CM_3_Car extends AppCompatActivity {

    DBHelper mHelper;
    SQLiteDatabase mDB;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_3_car);

        mHelper = new DBHelper(this);
        mDB = mHelper.getWritableDatabase();
        mCursor = mDB.rawQuery("SELECT * FROM " + DBHelper.getTB_User(), null);

        mCursor.moveToFirst();
        if (mCursor.getCount() > 0) {
            TextView showName = (TextView) findViewById(R.id.textView4);
            showName.setText(mCursor.getString(mCursor.getColumnIndex(DBHelper.getUser_Name())));
        }

        ImageButton imBT;
        imBT = (ImageButton) findViewById(R.id.imageButton);
        imBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CM_3_Car.this, CM_4_Plus_Car.class);
                startActivity(intent);
            }
        });

    }
}
