package com.saikaew_rus.cm_alert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;

import java.util.ArrayList;

public class CM_1_Load extends AppCompatActivity {

    TB_9_USER tb_9_user;
    Repo_9_USER repo_9_user;

    ArrayList userList;

    Thread splashTread;

    A_Repo_Check a_repo_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_load);

        a_repo_check = new A_Repo_Check(this);
        chkTotal();
        tb_9_user = new TB_9_USER();
        repo_9_user = new Repo_9_USER(this);
        userList = repo_9_user.getUserList();
        startAnimations();
    }

    public void chkTotal() {
        if ((a_repo_check.chkDrivingLicense() >= 1) || (a_repo_check.chkTaxCar() >= 1) || (a_repo_check.chkDueDate() >= 1)) {
            notifiChkkilo();
        }
    }

    private static final String TITLE = "Maintenance Your Car";
    private static final String MESSAGE = "Fix Your Car";

    public void notifiChkkilo() {
        Intent intent = new Intent(this, A_ListAlert.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(CM_7_List_Parts_Recycle.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.iconnew_4_02_60);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.iconnew_4_02_60)
                .setLargeIcon(icon)
                .setContentTitle(TITLE)
                .setContentText(MESSAGE)
                .setAutoCancel(true)
                .setVibrate(new long[]{500, 1000, 500})
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(500, notification);
    }

    private void startAnimations() {

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2000) {
                        sleep(100);
                        waited += 100;
                    }

                } catch (InterruptedException e) {
                    // do nothing
                }

                if (userList.size() == 0) {
                    Intent intent = new Intent(CM_1_Load.this, CM_2_Add_User.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(CM_1_Load.this, CM_3_Car_Recycle.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashTread.start();
    }
}

