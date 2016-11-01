package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CM_3_Car extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_3_car);

        Repo_9_USER repo = new Repo_9_USER(this);
        TB_9_USER user = new TB_9_USER();

        user = repo.getUserById(1);

        if (repo.getUserList().size() > 0) {
            TextView showName = (TextView) findViewById(R.id.textView4);
            showName.setText(user.user_Name);
        }

        ImageButton imBT;
        imBT = (ImageButton) findViewById(R.id.imageButton);
        imBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CM_3_Car.this, CM_4_Plus_Car.class);
                intent.putExtra("setUser_Id",1);
                startActivity(intent);
            }
        });

        ImageView fixUser = (ImageView) findViewById(R.id.imageView);
        fixUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go1 = new Intent(getApplicationContext(), CM_5_User_Data.class);
                startActivity(go1);
                finish();
            }
        });

    }

}
