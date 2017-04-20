package com.saikaew_rus.cm_alert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CM_3_Car_Recycle extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Repo_9_USER repo_9_user;
    Repo_11_SYSCONFIG repo_11_sysconfig;
    Repo_1_CAR repo_1_car;

    TB_9_USER tb_9_user;
    TB_11_Sysconfig tb_11_sysconfig;

    HashMap<String, String> chkSys;

    int car_Id;
    ArrayList<HashMap<String, String>> getCarList;
    TextView showName;
    A_Toast_Time a_toast_time;
    LinearLayout linearLayout;
    A_Check_Dialog_Alert a_check_dialog_alert;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    ImageView icon;
    FloatingActionButton actionButton;
    SimpleDateFormat sdf;

    public static final String alert_id = "id";
    public static final String alert_name = "name";
    public static final String alert_total = "total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        this.setTitle(R.string.title_car);

        setMenu();
        setLayout();
    }

    public void setLayout() {
//        showName = (TextView) findViewById(R.id.textView4);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        linearLayout = (LinearLayout) findViewById(R.id.linUser);
    }

    public void setValue() {
        repo_9_user = new Repo_9_USER(this);
        repo_1_car = new Repo_1_CAR(this);
        repo_11_sysconfig = new Repo_11_SYSCONFIG(this);

        a_check_dialog_alert = new A_Check_Dialog_Alert(this);
        a_toast_time = new A_Toast_Time();

        tb_9_user = new TB_9_USER();
        tb_11_sysconfig = new TB_11_Sysconfig();

        sdf = new SimpleDateFormat("yyyy-MM-dd");

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
    }

    public void setEvent() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentPage(CM_4_Add_Car.class);
            }
        });
    }

    public void chkDialog() {
        Cursor drivingLicence, taxCar, dataCar;
        ArrayList<HashMap<String, String>> data = new ArrayList<>();

        drivingLicence = a_check_dialog_alert.getDrivingLicense();
        taxCar = a_check_dialog_alert.getTaxCar();
        dataCar = a_check_dialog_alert.getDataCar();

        if (drivingLicence.getCount() > 0 || taxCar.getCount() > 0 || dataCar.getCount() > 0) {
            Log.d("Total_Alert", drivingLicence.getCount() + "  ++++  " + taxCar.getCount() + "  ++++  " + dataCar.getCount());
            if (drivingLicence.getCount() > 0) {
                HashMap<String, String> getdata = new HashMap<>();
                getdata.put(alert_id, "0");
                getdata.put(alert_name, A_Word_App.dialog_driving_licence[A_Word_App.language]);
                getdata.put(alert_total, String.valueOf(drivingLicence.getCount()));
                data.add(getdata);
            }
            if (taxCar.getCount() > 0) {
                HashMap<String, String> getdata = new HashMap<>();
                getdata.put(alert_id, "1");
                getdata.put(alert_name, A_Word_App.dialog_tax_car[A_Word_App.language]);
                getdata.put(alert_total, String.valueOf(taxCar.getCount()));
                data.add(getdata);
            }
            if (dataCar.getCount() > 0) {
                HashMap<String, String> getdata = new HashMap<>();
                getdata.put(alert_id, "2");
                getdata.put(alert_name, A_Word_App.dialog_car[A_Word_App.language]);
                getdata.put(alert_total, String.valueOf(dataCar.getCount()));
                data.add(getdata);
            }

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();

            View view = inflater.inflate(R.layout.dialog_custom_listcar, null);
            builder.setView(view);

            TextView title = (TextView) view.findViewById(R.id.titleAlert);
            ListView listAlert = (ListView) view.findViewById(R.id.listAlert);
            final CheckBox donAlert = (CheckBox) view.findViewById(R.id.donAlert);
            ImageButton cancelAlert = (ImageButton) view.findViewById(R.id.cancelAlert);

            title.setText(A_Word_App.dialog_list_maintenance[A_Word_App.language]);
            donAlert.setText(A_Word_App.dialog_close_alert[A_Word_App.language]);

            A_CustomListDialog a_customListDialog = new A_CustomListDialog(this, data);
            listAlert.setAdapter(a_customListDialog);

            donAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tb_11_sysconfig.sys_Code = TB_11_Sysconfig.Sys_Code_Don_Alert;
                    tb_11_sysconfig.sys_Desc = "Don't Alert";
                    tb_11_sysconfig.sys_Value = sdf.format(Calendar.getInstance().getTime()).toString();

                    if (donAlert.isChecked()) {
                        if (repo_11_sysconfig.getConfig(TB_11_Sysconfig.Sys_Code, TB_11_Sysconfig.Sys_Code_Don_Alert).size() > 0) {
                            repo_11_sysconfig.update(TB_11_Sysconfig.Sys_Code_Don_Alert, tb_11_sysconfig.sys_Value);
                        } else {
                            repo_11_sysconfig.insert(tb_11_sysconfig);
                        }
                    } else {
                        repo_11_sysconfig.getConfig(TB_11_Sysconfig.Sys_Code, TB_11_Sysconfig.Sys_Code_Don_Alert).size();
                        tb_11_sysconfig.sys_Value = "";
                        repo_11_sysconfig.update(TB_11_Sysconfig.Sys_Code_Don_Alert, tb_11_sysconfig.sys_Value);
                    }
                }
            });

            final AlertDialog alertDialog = builder.show();
            cancelAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.sitting_menu, menu);
//
//        menu.findItem(R.id.noti).setTitle(A_Word_App.menu_notification[A_Word_App.language]);
//        menu.findItem(R.id.setting).setTitle(A_Word_App.menu_setting[A_Word_App.language]);
//        menu.findItem(R.id.about).setTitle(A_Word_App.menu_about[A_Word_App.language]);
//
//        return true;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_activity_test, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.noti:
//                intentPage(A_ListAlert.class);
//                return true;
//            case R.id.setting:
//                intentPage(A_Setting.class);
//                return true;
//            case R.id.about:
//                intentPage(A_AboutRus.class);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void intentPage(Class aClass) {
        Intent intent = new Intent(getApplication(), aClass);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setValue();
        setEvent();
        getDatacar();

        String date = sdf.format(Calendar.getInstance().getTime()).toString();
        int chk = repo_11_sysconfig.getConfig((TB_11_Sysconfig.Sys_Value), "'" + date + "'").size();
        Log.d("showCheck", String.valueOf(chk));

        if (chk > 0) {

        } else {
            chkDialog();
        }
    }

    public void getDatacar() {
        tb_9_user = repo_9_user.getFirstUser();
        if (repo_9_user.getUserList().size() > 0) {
            showName.setText(tb_9_user.user_Name);
        }

        getCarList = repo_1_car.getCarList();
        mAdapter = new A_CM_3_Car_Base_Adapter_Recycle_Sub(this, getCarList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    public class A_CM_3_Car_Base_Adapter_Recycle_Sub extends RecyclerView.Adapter<A_CM_3_Car_Base_Adapter_Recycle_Sub.ViewHolder> {
        private Context mContext;
        private ArrayList<HashMap<String, String>> getCarList;
        A_Toast_Time a_toast_time;
        int car_Id;
        String[] Choice =  getResources().getStringArray(R.array.choice_en);
        Repo_1_CAR repo_1_car;
        String regCar, provCar;


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView car_Regis;
            public TextView car_Prov;
            public ConstraintLayout linearLayout;
            public ImageView imageCar;
            public ArcProgress arcProgress;

            public ViewHolder(View view) {
                super(view);
                car_Regis = (TextView) view.findViewById(R.id.car_Register);
                car_Prov = (TextView) view.findViewById(R.id.car_Province);
                imageCar = (ImageView) view.findViewById(R.id.imageCar);
                linearLayout = (ConstraintLayout) view.findViewById(R.id.linRecycle);
                arcProgress = (ArcProgress) view.findViewById(R.id.arc_progress);

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

            int i = Integer.parseInt(getCarList.get(position).get(TB_1_CAR.SetTitle));
            holder.arcProgress.setProgress(i);
            if (i >= 100) {
                holder.arcProgress.setFinishedStrokeColor(Color.GREEN);
            } else if (i >= 50) {
                holder.arcProgress.setFinishedStrokeColor(Color.YELLOW);
            } else if (i >= 25) {
                holder.arcProgress.setFinishedStrokeColor(Color.rgb(255, 118, 0));
            } else {
                holder.arcProgress.setFinishedStrokeColor(Color.RED);
            }

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    regCar = getCarList.get(position).get(TB_1_CAR.Car_Register);
                    provCar = getCarList.get(position).get(TB_1_CAR.Province_Name);
                    car_Id = Integer.parseInt(getCarList.get(position).get(TB_1_CAR.Car_Id));
                    a_toast_time.Toast_Time(mContext, mContext.getResources().getString(R.string.lp_car_regis) + " : " + regCar, 700);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(mContext.getResources().getString(R.string.lp_car_regis) + " : " + regCar);
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
                                builder_1.setMessage(mContext.getResources().getString(R.string.are_you_delete) + " " + regCar + " ?");
                                builder_1.setNegativeButton(R.string.add_user_ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        repo_1_car.delete(car_Id);
                                        a_toast_time.Toast_Time(mContext, regCar + " " + mContext.getResources().getString(R.string.delete_success), 700);
                                        getDatacar();
                                    }
                                });
                                builder_1.setPositiveButton(R.string.add_user_cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        builder.show();
                                    }
                                });

                                builder_1.show();
                            }
                        }
                    });
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

    public void setMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        MenuItem nav_time = menu.findItem(R.id.nav_time);
        nav_time.setTitle(A_Word_App.menu_alarm[A_Word_App.language]);

        MenuItem nav_setting = menu.findItem(R.id.nav_setting);
        nav_setting.setTitle(A_Word_App.menu_setting[A_Word_App.language]);

        MenuItem nav_user = menu.findItem(R.id.nav_user);
        nav_user.setTitle(A_Word_App.menu_user[A_Word_App.language]);

        MenuItem nav_about = menu.findItem(R.id.nav_about);
        nav_about.setTitle(A_Word_App.menu_about[A_Word_App.language]);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_time) {
            intentPage(A_Setting.class);
        } else if (id == R.id.nav_setting) {
            intentPage(A_Setting.class);
        } else if (id == R.id.nav_user) {
            intentPage(CM_5_Edit_User.class);
        } else if (id == R.id.nav_about) {
            intentPage(A_AboutRus.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

