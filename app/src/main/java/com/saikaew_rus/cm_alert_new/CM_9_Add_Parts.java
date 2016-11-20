package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CM_9_Add_Parts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_add_parts);

        final EditText namepart = (EditText) findViewById(R.id.part_name);

        Intent intent = getIntent();
        final int carid = intent.getIntExtra("car_id", 0);

        Button button;
        button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1 = new Intent(getApplicationContext(), CM_10_Add_Parts_2.class);
                go1.putExtra("car_id", carid);
                go1.putExtra("part_name", namepart.getText().toString());
                startActivity(go1);
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
