package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CM_1_Load extends AppCompatActivity {

    DBHelper mHelper;
    SQLiteDatabase mDb;

    Thread splashTread;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_load);

        StartAnimations();
    }

    private void StartAnimations() {

        mHelper = new DBHelper(this);
        mDb = mHelper.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT * FROM " + TB_9_USER.TABLE, null);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        RelativeLayout l = (RelativeLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();

        ImageView iv = (ImageView) findViewById(R.id.logo_car);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }
                    if (mCursor.getCount() == 0) {
                        Intent intent = new Intent(CM_1_Load.this, CM_2_Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(CM_1_Load.this, CM_3_Car.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                    CM_1_Load.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    CM_1_Load.this.finish();
                }
            }
        };
        splashTread.start();
    }
}

