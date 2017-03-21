package com.saikaew_rus.cm_alert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

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
    LinearLayout linearLayout;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    ImageView icon;
    FloatingActionButton actionButton;

    SubActionButton.Builder itemBuilder;
    ImageView itemIcon1, itemIcon2, itemIcon3, itemIcon4;
    SubActionButton button1, button2, button3, button4;

    FloatingActionMenu floatingActionMenu;

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
    }

    public void setValue() {
        a_toast_time = new A_Toast_Time();
        repo = new Repo_9_USER(this);
        user = new TB_9_USER();
        repo_1_car = new Repo_1_CAR(this);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //Create a button to attach the menu:
        icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
        Drawable drawable = getResources().getDrawable(R.drawable.btn_add_car);
        actionButton = new FloatingActionButton.Builder(this)
                .setPosition(4)
                .setContentView(icon).build();
        actionButton.setBackground(getResources().getDrawable(R.drawable.btn_add_car));


//        //Create menu items:
//        itemBuilder = new SubActionButton.Builder(this);
//        // repeat many times:
//        itemIcon1 = new ImageView(this);
//        itemIcon2 = new ImageView(this);
//        itemIcon3 = new ImageView(this);
//        itemIcon4 = new ImageView(this);
//
//        itemIcon1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_about));
//        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.plus_car_10_10_59));
//        itemIcon3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_language));
//        itemIcon4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_alarm));
//
//        //Create the menu with the items:
//        floatingActionMenu = new FloatingActionMenu.Builder(this)
//                .addSubActionView(itemBuilder.setContentView(itemIcon1).build())
//                .addSubActionView(itemBuilder.setContentView(itemIcon2).build())
//                .addSubActionView(itemBuilder.setContentView(itemIcon3).build())
//                .addSubActionView(itemBuilder.setContentView(itemIcon4).build())
//                .attachTo(actionButton).build();
    }

    public void setEvent() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CM_4_Add_Car.class);
                startActivity(intent);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1 = new Intent(getApplicationContext(), CM_5_Edit_User.class);
                startActivity(go1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sitting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.noti:
                return true;
            case R.id.setting:
                return true;
            case R.id.about:
                intentPage(A_AboutRus.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void intentPage(Class aClass) {
        Intent intent = new Intent(getApplication(), aClass);
        startActivity(intent);
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
        mAdapter = new A_CM_3_Car_Base_Adapter_Recycle_Sub(this, getCarList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class A_CM_3_Car_Base_Adapter_Recycle_Sub extends RecyclerView.Adapter<A_CM_3_Car_Base_Adapter_Recycle_Sub.ViewHolder> {
        private Context mContext;
        private ArrayList<HashMap<String, String>> getCarList;
        A_Toast_Time a_toast_time;
        int car_Id;
        String[] Choice = new String[]{"Travel", "Maintenance", "History", "Delete"};//ใส่ตัวเลือก
        Repo_1_CAR repo_1_car;
        String regCar, provCar;


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView car_Regis;
            public TextView car_Prov;
            public LinearLayout linearLayout;
            public A_CircularBar c2;
            public ImageView imageCar;

            public ViewHolder(View view) {
                super(view);
                car_Regis = (TextView) view.findViewById(R.id.car_Register);
                car_Prov = (TextView) view.findViewById(R.id.car_Province);
                imageCar = (ImageView) view.findViewById(R.id.imageCar);
                linearLayout = (LinearLayout) view.findViewById(R.id.linRecycle);
                c2 = (A_CircularBar) view.findViewById(R.id.circularprogressbar2);

                repo_1_car = new Repo_1_CAR(mContext);
                a_toast_time = new A_Toast_Time();
            }
        }

        public A_CM_3_Car_Base_Adapter_Recycle_Sub(Context mContext, ArrayList<HashMap<String, String>> getCarList) {
            this.mContext = mContext;
            this.getCarList = getCarList;
        }

        @Override
        public A_CM_3_Car_Base_Adapter_Recycle_Sub.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_car_list, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final A_CM_3_Car_Base_Adapter_Recycle_Sub.ViewHolder holder, final int position) {
            regCar = getCarList.get(position).get(TB_1_CAR.Car_Register);
            provCar = getCarList.get(position).get(TB_1_CAR.Province_Name);
            Log.d("dataPartCar", regCar + "  " + getCarList.get(position).get(TB_1_CAR.SetTitle));
            holder.car_Regis.setText(regCar);
            holder.car_Prov.setText(provCar);
            holder.imageCar.setBackgroundResource(TB_1_CAR.carPic[Integer.parseInt(getCarList.get(position).get(TB_1_CAR.Car_Pic))]);

            holder.c2.animateProgressTo(0, Integer.parseInt(getCarList.get(position).get(TB_1_CAR.SetTitle)), new A_CircularBar.ProgressAnimationListener() {
                @Override
                public void onAnimationStart() {
                }

                @Override
                public void onAnimationProgress(int progress) {
                    holder.c2.setTitle(progress + "%");
                }

                @Override
                public void onAnimationFinish() {
//                    holder.c2.setTitle(getCarList.get(position).get(TB_1_CAR.SetTitle) + "%");
                    holder.c2.setSubTitle("Status");
                    if (getCarList.get(position).get(TB_1_CAR.SetColor) != null) {
                        holder.c2.setTitleColor(mContext.getResources().getColor(Integer.valueOf(getCarList.get(position).get(TB_1_CAR.SetColor))));
                        holder.c2.setSubTitleColor(mContext.getResources().getColor(Integer.valueOf(getCarList.get(position).get(TB_1_CAR.SetColor))));
                        holder.c2.setProgressColorPaint(mContext.getResources().getColor(Integer.valueOf(getCarList.get(position).get(TB_1_CAR.SetColor))));
                    }
                }
            });

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    car_Id = Integer.parseInt(getCarList.get(position).get(TB_1_CAR.Car_Id));

                    a_toast_time.Toast_Time(mContext, "Selected Car Registration : " + regCar, 700);

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Car Registration : " + regCar);
                    builder.setItems(Choice, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                controlPage(CM_6_Travel_Map.class);
                            } else if (which == 1) {
                                controlPage(CM_7_List_Parts_Recycle.class);
                            } else if (which == 2) {
                                controlPage(CM_8_History.class);
                            } else if (which == 3) {
                                AlertDialog.Builder builder_1 = new AlertDialog.Builder(mContext);
                                builder_1.setMessage("Are sure Delete " + regCar + " ?");
                                builder_1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        repo_1_car.delete(car_Id);
                                        a_toast_time.Toast_Time(mContext, "Car No. " + regCar + " is Deleted Success", 800);
                                        getDatacar();
                                    }
                                });
                                builder_1.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
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
        }

        @Override
        public int getItemCount() {
            return getCarList.size();
        }

        public void controlPage(Class aClass) {
            Intent intent = new Intent(mContext, aClass);
            intent.putExtra(TB_1_CAR.Car_Id, car_Id);
            mContext.startActivity(intent);
        }
    }

}

