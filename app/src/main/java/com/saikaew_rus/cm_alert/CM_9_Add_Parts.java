package com.saikaew_rus.cm_alert;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CM_9_Add_Parts extends AppCompatActivity {

    A_Repo_Check repo_check;
    EditText namepart;
    int carid;
    String partAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_add_parts);
        this.setTitle(R.string.title_add_part);

        namepart = (EditText) findViewById(R.id.partName);

        repo_check = new A_Repo_Check(this);

        Intent intent = getIntent();
        carid = intent.getIntExtra("car_id", 0);

        Button button;
        button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partAdd = namepart.getText().toString();
                if (partAdd.matches("")) {
                    //***************  Set Toast duration  ***************//
                    final Toast toast = Toast.makeText(getApplicationContext(),
                            "Part Name is empty!!!", Toast.LENGTH_SHORT);
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
                    int count = repo_check.chk_Part(partAdd);
                    if (count >= 1) {
                        //***************  Set Toast duration  ***************//
                        final Toast toast = Toast.makeText(getApplicationContext(),
                                "Part Name : " + partAdd + " is duplicate!!!", Toast.LENGTH_SHORT);
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
                        go1.putExtra("part_name", partAdd);
                        startActivity(go1);
                    }
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
