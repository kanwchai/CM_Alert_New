package com.saikaew_rus.cm_alert_new;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CM_3_Car extends AppCompatActivity {

    Repo_9_USER repo;
    TB_9_USER user;
    Repo_1_CAR repo_1_car;
    String carRegis;
    int car_Id;
    ArrayList<HashMap<String, String>> getCarList;
    String[] Choice;
    Toast toast;
    ListAdapter adapter;
    ListView listView_1;
    TextView showName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_car);

        Choice = new String[]{"Travel", "Maintenance", "History", "Delete"};//ใส่ตัวเลือก

        showName = (TextView) findViewById(R.id.textView4);
        listView_1 = (ListView) findViewById(R.id.listView);

        repo = new Repo_9_USER(this);
        user = new TB_9_USER();

        repo_1_car = new Repo_1_CAR(this);

        getDatacar();

        listView_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapterView, View view, int position, long id) {
                car_Id = Integer.parseInt(getCarList.get(position).get(TB_1_CAR.Car_Id));
                carRegis = getCarList.get(position).get(TB_1_CAR.Car_Register).toString();

                //***************  Set Toast duration  ***************//
                toast = Toast.makeText(getApplicationContext(), "Selected Car Registration : " + carRegis, Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 1000);
                //***************  End Set Toast  ***************//

                AlertDialog.Builder builder = new AlertDialog.Builder(CM_3_Car.this);
                builder.setTitle("Car Registration : " + carRegis);
                builder.setItems(Choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(getApplicationContext(), CM_6_Travel.class);
                            intent.putExtra("car_Id", car_Id);
                            startActivity(intent);
                        } else if (which == 1) {
                            Intent intent = new Intent(getApplicationContext(), CM_7_List_Parts.class);
                            intent.putExtra("car_Id", car_Id);
                            startActivity(intent);
                            finish();
                        } else if (which == 2) {
                            Intent intent = new Intent(getApplicationContext(), CM_8_History.class);
                            intent.putExtra("car_Id", car_Id);
                            startActivity(intent);
                        } else if (which == 3) {
                            AlertDialog.Builder builder_1 = new AlertDialog.Builder(CM_3_Car.this);
                            builder_1.setMessage("Are sure Delete " + car_Id + " ?");
                            builder_1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    repo_1_car.delete(car_Id);
                                    Toast.makeText(getApplicationContext(), "Car No. " + car_Id + " is Deleted Success", Toast.LENGTH_SHORT).show();
                                    getDatacar();
                                }
                            });
                            builder_1.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getDatacar();
                                }
                            });

                            builder_1.show();
                        }
                    }
                });//คลิกเพื่อเปลี่ยนหน้า
                // สุดท้ายอย่าลืม show() ด้วย
                builder.show();
            }
        });

        ImageView fixUser = (ImageView) findViewById(R.id.imageView);
        fixUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1 = new Intent(getApplicationContext(), CM_5_User_Data.class);
                startActivity(go1);
            }
        });

        ImageButton imBT;
        imBT = (ImageButton) findViewById(R.id.imageButton);
        imBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CM_4_Plus_Car.class);
                startActivity(intent);
            }
        });
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
        adapter = new SimpleAdapter(CM_3_Car.this, getCarList, R.layout.view_car_list, new String[]{TB_1_CAR.Car_Id, TB_1_CAR.Car_Register, TB_1_CAR.Province_Name}, new int[]{R.id.car_Id, R.id.car_Register, R.id.car_Province});
        listView_1.setAdapter(adapter);
    }
}
