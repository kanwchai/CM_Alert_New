package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class CM_1_Load extends AppCompatActivity {

    TB_9_USER tb_9_user;
    Repo_9_USER repo_9_user;

    ArrayList userList;

    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_load);

        tb_9_user = new TB_9_USER();
        repo_9_user = new Repo_9_USER(this);

        userList = repo_9_user.getUserList();

        StartAnimations();
    }

    private void StartAnimations() {

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 500) {
                        sleep(100);
                        waited += 100;
                    }

                } catch (InterruptedException e) {
                    // do nothing
                }

                if (userList.size() == 0) {
                    Intent intent = new Intent(CM_1_Load.this, CM_2_Main.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(CM_1_Load.this, CM_3_Car.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashTread.start();
    }
}

