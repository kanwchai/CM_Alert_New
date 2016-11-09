package com.saikaew_rus.cm_alert_new;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CM_7_List_Parts extends AppCompatActivity {

    TextView tv_regis;
    TextView tv_kilo;
    TextView tv_ex_date;
    ListView partList;
    ArrayList<HashMap<String, String>> getPartList;
    ListAdapter adapter;

    TB_1_CAR tb_1_car;
    TB_2_DUE_OF_PART_FIX tb_2_due_of_part_fix;

    Repo_1_CAR repo_1_car;
    Repo_2_DUE_OF_PART_FIX repo_2_due_of_part_fix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_list_parts);

        tv_regis = (TextView) findViewById(R.id.textView6);
        tv_kilo = (TextView) findViewById(R.id.textView7);
        tv_ex_date = (TextView) findViewById(R.id.textView8);
        partList = (ListView) findViewById(R.id.listView2);

        tb_1_car = new TB_1_CAR();
        tb_2_due_of_part_fix = new TB_2_DUE_OF_PART_FIX();

        repo_1_car = new Repo_1_CAR(this);
        repo_2_due_of_part_fix = new Repo_2_DUE_OF_PART_FIX(this);
    }

    public void getPart(){
        getPartList = repo_2_due_of_part_fix.getFixList();
        adapter = new SimpleAdapter(CM_7_List_Parts.this, getPartList, R.layout.view_part_list, new String[]{"id", "register"}, new int[]{R.id.car_Id, R.id.car_Register});
    }
}
