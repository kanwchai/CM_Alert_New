package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class CM_1_Load extends AppCompatActivity {

    DBHelper mHelper;
    SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_load);

        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource
        ImageView img = (ImageView) findViewById(R.id.car_r);
        img.setBackgroundResource(R.drawable.carrun);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

        // Start the animation (looped playback by default).
        frameAnimation.start();

        mHelper = new DBHelper(this);
        mDb = mHelper.getWritableDatabase();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
                Cursor mCursor = mDb.rawQuery("SELECT * FROM " + TB_9_USER.TABLE, null);
                if (mCursor.getCount() == 0) {
                    Intent intent = new Intent(CM_1_Load.this, CM_2_Main.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CM_1_Load.this, CM_3_Car.class);
                    startActivity(intent);
                }
                finish();
            }
        }).start();
    }
}

