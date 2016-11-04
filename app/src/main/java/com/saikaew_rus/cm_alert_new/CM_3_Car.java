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

public class CM_3_Car extends AppCompatActivity implements android.view.View.OnClickListener {

    Repo_9_USER repo;
    TB_9_USER user;
    Repo_1_CAR repo_1_car;
    String carRegis;
    int car_Id;
    ArrayList<HashMap<String, String>> carList;
    private static String[] Choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_car);

        Choice = new String[]{"Travel", "Maintenance", "History", "Delete"};//ใส่ตัวเลือก

        TextView showName = (TextView) findViewById(R.id.textView4);
        ListView listView_1 = (ListView) findViewById(R.id.listView);

        repo = new Repo_9_USER(this);
        user = new TB_9_USER();

        repo_1_car = new Repo_1_CAR(this);

        user = repo.getUserById(1);
        if (repo.getUserList().size() > 0) {

            showName.setText(user.user_Name);
        }

        carList = repo_1_car.getCarList();
        ListAdapter adapter = new SimpleAdapter(CM_3_Car.this, carList, R.layout.view_car_list, new String[]{"id", "register"}, new int[]{R.id.car_Id, R.id.car_Register});
        listView_1.setAdapter(adapter);

        listView_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapterView, View view, int position, long id) {
                final TextView carId = (TextView) view.findViewById(R.id.car_Register);
                carRegis = carId.getText().toString();
                car_Id = Integer.parseInt(carList.get(position).get("id"));
//               carRegis = carList.get(position).get("id").toString();

                //***************  Set Android Toast duration to be really long  ***************//
                final Toast toast = Toast.makeText(getApplicationContext(), "Selected Car Registration : " + carRegis, Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 200);
                //***************  End Set Android Toast duration to be really long  ***************//

                AlertDialog.Builder builder = new AlertDialog.Builder(CM_3_Car.this);
                builder.setTitle("Car register : " + carRegis);
                builder.setItems(Choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = Choice[which];
                        Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();

                        if (which == 0) {
                            Intent intent = new Intent(CM_3_Car.this, CM_6_Travel.class);
                            intent.putExtra("car_Id", car_Id);
                            startActivity(intent);
                            CM_3_Car.this.finish();
                        } else if (which == 1) {
                            Intent intent = new Intent(CM_3_Car.this, CM_7_List_Parts.class);
                            intent.putExtra("car_Id", car_Id);
                            startActivity(intent);
                            CM_3_Car.this.finish();
                        } else if (which == 2) {
                            Intent intent = new Intent(CM_3_Car.this, CM_8_History.class);
                            intent.putExtra("car_Id", car_Id);
                            startActivity(intent);
                            CM_3_Car.this.finish();
                        } else if (which == 3) {
                            Intent intent = new Intent(CM_3_Car.this, CM_3_Car.class);
                            startActivity(intent);
                            CM_3_Car.this.finish();
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
                finish();
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
    public void onClick(View view) {

    }
}
