package com.saikaew_rus.cm_alert_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CM_3_Car_Recycle extends AppCompatActivity {

    Repo_9_USER repo;
    TB_9_USER user;
    Repo_1_CAR repo_1_car;
    int car_Id;
    ArrayList<HashMap<String, String>> getCarList;
    TextView showName;
    A_Toast_Time a_toast_time;
    ImageButton imBT;
    LinearLayout linearLayout;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_car_recycle);

        setLayout();
        setValue();
        setEvent();

        getDatacar();
    }

    public void setLayout() {
        showName = (TextView) findViewById(R.id.textView4);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayout = (LinearLayout) findViewById(R.id.linUser);
        imBT = (ImageButton) findViewById(R.id.imageButton);
    }

    public void setValue() {
        a_toast_time = new A_Toast_Time();
        repo = new Repo_9_USER(this);
        user = new TB_9_USER();
        repo_1_car = new Repo_1_CAR(this);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setEvent() {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1 = new Intent(getApplicationContext(), CM_5_Edit_User.class);
                startActivity(go1);
            }
        });

        imBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CM_4_Add_Car.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDatacar();
    }

    public void getDatacar() {
        user = repo.getFirstUser();
        if (repo.getUserList().size() > 0) {
            showName.setText(user.user_Name);
        }

        getCarList = repo_1_car.getCarList();
        mAdapter = new A_CM_3_Car_Base_Adapter_Recycle(this, getCarList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
