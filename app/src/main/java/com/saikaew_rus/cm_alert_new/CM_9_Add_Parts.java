package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CM_9_Add_Parts extends AppCompatActivity {

    Repo_Check repo_check;
    EditText namepart;
    int carid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_add_parts);

        namepart = (EditText) findViewById(R.id.part_name);

        repo_check = new Repo_Check(this);

        Intent intent = getIntent();
        carid = intent.getIntExtra("car_id", 0);

        Button button;
        button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (repo_check.chk_Part(namepart.getText().toString()) >= 1) {
                    //***************  Set Toast duration  ***************//
                    final Toast toast = Toast.makeText(getApplicationContext(), "Part Name : " +
                            namepart.getText().toString() + " is duplicate!!!", Toast.LENGTH_SHORT);
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
                    Intent go1 = new Intent(getApplicationContext(), CM_10_Add_Parts_2.class);
                    go1.putExtra("car_id", carid);
                    go1.putExtra("part_name", namepart.getText().toString());
                    startActivity(go1);
                }
            }
        });

        Button button1;
        button1 = (Button) findViewById(R.id.button6);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
