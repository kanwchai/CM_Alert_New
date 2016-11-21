package com.saikaew_rus.cm_alert_new;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CM_7_List_Parts extends AppCompatActivity {

    TextView tv_regis;
    TextView tv_kilo;
    TextView tv_ex_date;
    ListView partList;
    ArrayList<HashMap<String, String>> getPartList;
    ListAdapter adapter;
    Intent intent;
    int car_id;
    int part_id;
    int due_fix_id;
    String part_name;
    Toast toast;
    Button add_part;
    String[] new_part;
    String[] Choice;
    Calendar mCalendar;
    DatePickerDialog mDatePicker;
    TextView due_date;
    EditText due_kilo;
    int due_fix_kilo;
    int due_fix_date;
    String due_fix_status;
    String partName;

    TB_1_CAR tb_1_car;
    TB_2_DUE_OF_PART_FIX tb_2_due_of_part_fix;
    TB_6_RUN_DATA tb_6_run_data;
    TB_4_HISTORYS_OF_CAR tb_4_historys_of_car;

    Repo_1_CAR repo_1_car;
    Repo_2_DUE_OF_PART_FIX repo_2_due_of_part_fix;
    Repo_5_PARTS repo_5_parts;
    Repo_4_HISTORYS_OF_CAR repo_4_historys_of_car;
    Repo_6_RUN_DATA repo_6_run_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_list_parts);

        tv_regis = (TextView) findViewById(R.id.textView6);
        tv_kilo = (TextView) findViewById(R.id.textView7);
        tv_ex_date = (TextView) findViewById(R.id.textView8);
        partList = (ListView) findViewById(R.id.listView2);
        add_part = (Button) findViewById(R.id.button10);
        mCalendar = Calendar.getInstance();

        tb_1_car = new TB_1_CAR();
        tb_2_due_of_part_fix = new TB_2_DUE_OF_PART_FIX();
        tb_6_run_data = new TB_6_RUN_DATA();
        tb_4_historys_of_car = new TB_4_HISTORYS_OF_CAR();

        repo_1_car = new Repo_1_CAR(this);
        repo_2_due_of_part_fix = new Repo_2_DUE_OF_PART_FIX(this);
        repo_5_parts = new Repo_5_PARTS(this);
        repo_4_historys_of_car = new Repo_4_HISTORYS_OF_CAR(this);
        repo_6_run_data = new Repo_6_RUN_DATA(this);

        Choice = new String[]{"Specify Last Maintenance", "Set Usage", "Delete"};

        intent = getIntent();
        car_id = intent.getIntExtra("car_Id", 0);
        tb_1_car = repo_1_car.getCarById(car_id);
        tb_6_run_data = repo_6_run_data.getLastRunByCar_Id(car_id);
        tv_kilo.setText(String.valueOf(tb_6_run_data.run_Kilo_End));

        getDatacar();
        getPartList(car_id);

        partList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> AdapterView, final View view, final int position, long id) {

                due_fix_id = Integer.parseInt(getPartList.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Id));
                part_id = Integer.parseInt(getPartList.get(position).get(TB_5_PARTS.Part_Id));
                part_name = getPartList.get(position).get(TB_5_PARTS.Part_Name).toString();
                due_fix_kilo = Integer.parseInt(getPartList.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo));
                due_fix_date = Integer.parseInt(getPartList.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Date));
                due_fix_status = getPartList.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Status);

                //***************  Set Toast duration  ***************//
                toast = Toast.makeText(getApplicationContext(), "Selected Part : " + part_name, Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 1000);
                //***************  End Set Toast  ***************//

                AlertDialog.Builder builder = new AlertDialog.Builder(CM_7_List_Parts.this);
                builder.setTitle("Part : " + part_name);
                builder.setItems(Choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            final Dialog dialog_2 = new Dialog(CM_7_List_Parts.this);
                            dialog_2.setContentView(R.layout.dialog_custom_specify);

                            due_date = (TextView) dialog_2.findViewById(R.id.due_date_spec);
                            due_kilo = (EditText) dialog_2.findViewById(R.id.due_kilo_spec);
                            Button bSave = (Button) dialog_2.findViewById(R.id.button_save);
                            Button bCancel = (Button) dialog_2.findViewById(R.id.button_cancel);

                            dialog_2.setTitle("Last Maintenance " + part_name);

                            due_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDatePicker.setYearRange(2000, 2030);
                                    mDatePicker.show(getSupportFragmentManager(), "datePicker");
                                }
                            });

                            bSave.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (due_date.getText().toString().matches("") ||
                                            due_kilo.getText().toString().matches("")) {
                                        //***************  Set Toast duration  ***************//
                                        final Toast toast = Toast.makeText(getApplicationContext(), "Please complete the form below.", Toast.LENGTH_SHORT);
                                        toast.show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                toast.cancel();
                                            }
                                        }, 1500);
                                        //*************  End Set Toast duration  *************//
                                    } else {
                                        tb_4_historys_of_car.fix_Due_Id = due_fix_id;
                                        tb_4_historys_of_car.car_Id = car_id;
                                        tb_4_historys_of_car.changed_Kilo = Double.parseDouble(due_kilo.getText().toString());
                                        tb_4_historys_of_car.next_Changed_Kilo = Integer.parseInt(String.valueOf(due_kilo.getText())) + due_fix_kilo;
                                        repo_4_historys_of_car.insert(tb_4_historys_of_car);
                                        dialog_2.dismiss();
                                    }
                                }
                            });

                            bCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog_2.dismiss();
                                }
                            });
                            dialog_2.show();
                        } else if (which == 1) {
                            final Dialog dialog_2 = new Dialog(CM_7_List_Parts.this);
                            dialog_2.setTitle("Config Part : " + part_name);
                            dialog_2.setContentView(R.layout.dialog_custom_set_usage);

                            final EditText due_date = (EditText) dialog_2.findViewById(R.id.due_date);
                            final EditText due_kilo = (EditText) dialog_2.findViewById(R.id.due_kilo);
                            Button bSave = (Button) dialog_2.findViewById(R.id.button_save);
                            Button bCancel = (Button) dialog_2.findViewById(R.id.button_cancel);

                            due_date.setText(String.valueOf(due_fix_date));
                            due_kilo.setText(String.valueOf(due_fix_kilo));

                            bSave.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tb_2_due_of_part_fix.fix_Due_Id = due_fix_id;
                                    tb_2_due_of_part_fix.car_Id = car_id;
                                    tb_2_due_of_part_fix.part_Id = part_id;
                                    tb_2_due_of_part_fix.fix_Due_Date = Integer.parseInt(due_date.getText().toString());
                                    tb_2_due_of_part_fix.fix_Due_Kilo = Integer.parseInt(due_kilo.getText().toString());
                                    tb_2_due_of_part_fix.fix_Due_Status = String.valueOf(due_fix_status);
                                    repo_2_due_of_part_fix.update(tb_2_due_of_part_fix);
                                    dialog_2.dismiss();
                                }
                            });

                            bCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog_2.dismiss();
                                }
                            });
                            dialog_2.show();
                        } else if (which == 2) {
                            AlertDialog.Builder builder_1 = new AlertDialog.Builder(CM_7_List_Parts.this);
                            builder_1.setMessage("Delete " + part_name + " ?");
                            builder_1.setNegativeButton("OK ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    repo_2_due_of_part_fix.delete(due_fix_id);
                                    Toast.makeText(getApplicationContext(), "Part " + part_name + " is Deleted Success", Toast.LENGTH_SHORT).show();
                                    getPartList(car_id);
                                }
                            });
                            builder_1.setPositiveButton("CANCEL ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            builder_1.show();
                        }
                    }
                });
                builder.show();
            }

        });

        add_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CM_7_List_Parts.this);
                builder.setTitle("Select New Part");
                new_part = repo_5_parts.getPart_Not(car_id);

                builder.setItems(new_part, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        partName = new_part[which];
                        repo_2_due_of_part_fix.insert_PartByCar(car_id, partName);
                        tb_2_due_of_part_fix = repo_2_due_of_part_fix.getDue_FixByMax();

                        tb_4_historys_of_car.fix_Due_Id = tb_2_due_of_part_fix.fix_Due_Id;
                        tb_4_historys_of_car.car_Id = car_id;
                        tb_4_historys_of_car.changed_Kilo = tb_6_run_data.run_Kilo_End;
                        tb_4_historys_of_car.next_Changed_Kilo = tb_6_run_data.run_Kilo_End + tb_2_due_of_part_fix.fix_Due_Kilo;

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        tb_4_historys_of_car.changed_Date = df.format(c.getTime());
                        c.add(Calendar.MONTH, tb_2_due_of_part_fix.fix_Due_Date);
                        tb_4_historys_of_car.next_Changed_Date = df.format(c.getTime());

                        repo_4_historys_of_car.insert(tb_4_historys_of_car);

                        getPartList(car_id);
                        //***************  Set Toast duration  ***************//
                        final Toast toast = Toast.makeText(getApplicationContext(), "Add Part " + partName + " Success", Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 2000);
                        //*************  End Set Toast duration  *************//
                    }
                });
                builder.setNegativeButton("Add New Part(if not found)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intentAddpart = new Intent(getApplicationContext(), CM_9_Add_Parts.class);
                        intentAddpart.putExtra("car_id", car_id);
                        startActivity(intentAddpart);
                    }
                });
                builder.create();

                builder.show();
            }
        });

        mDatePicker = DatePickerDialog.newInstance(onDateSetListener,
                mCalendar.get(Calendar.YEAR),       // ปี
                mCalendar.get(Calendar.MONTH),      // เดือน
                mCalendar.get(Calendar.DAY_OF_MONTH),// วัน (1-31)
                false);

    }

    public void getDatacar() {
        //*****************************  Convert Format Date  *****************************//
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat postFormater = new SimpleDateFormat("dd MMMM yyyy");

        Date exDate = null;
        try {
            exDate = curFormater.parse(tb_1_car.car_Tax_Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //***************************** End Convert Format Date  *****************************//
        tv_regis.setText(tb_1_car.car_Register);
        tv_ex_date.setText(postFormater.format(exDate));
    }

    public void getPartList(int carId) {
        getPartList = repo_2_due_of_part_fix.getFixListByCarId(carId);
        adapter = new SimpleAdapter(CM_7_List_Parts.this, getPartList, R.layout.view_part_list, new String[]{TB_5_PARTS.Part_Name, "countKilo", "countDate"}, new int[]{R.id.part_name, R.id.textView10, R.id.textView12});
        partList.setAdapter(adapter);
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

                    SimpleDateFormat dfm = new SimpleDateFormat("dd-MMMM-yyyy");
                    SimpleDateFormat dfm_insert = new SimpleDateFormat("yyyy-MM-dd");

                    mCalendar.set(year, month, day);
                    Date date = mCalendar.getTime();
                    mCalendar.add(Calendar.MONTH, due_fix_date);
                    Date date_chg = mCalendar.getTime();
                    String textDate = dfm.format(date);
                    tb_4_historys_of_car.changed_Date = dfm_insert.format(date);
                    tb_4_historys_of_car.next_Changed_Date = dfm_insert.format(date_chg);
                    due_date.setText(textDate);

                }
            };
}
