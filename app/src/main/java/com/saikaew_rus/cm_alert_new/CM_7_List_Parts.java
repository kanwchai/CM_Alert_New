package com.saikaew_rus.cm_alert_new;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private static final String TITLE = "Maintenance Your Car";
    private static final String MESSAGE = "You have parts breakdown!!!";

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
    Button add_part;
    String[] new_part;
    String[] Choice;
    Calendar mCalendar;
    DatePickerDialog mDatePicker;
    TextView due_date;
    EditText due_kilo;
    EditText due_date_2;
    EditText due_kilo_2;
    int due_fix_kilo;
    int due_fix_date;
    String due_fix_status;
    String partName;
    ImageButton imBtRegis;
    ImageButton imBtKilo;
    ImageButton imBtExpTax;

    TB_1_CAR tb_1_car;
    TB_2_DUE_OF_PART_FIX tb_2_due_of_part_fix;
    TB_6_RUN_DATA tb_6_run_data;
    TB_4_HISTORYS_OF_CAR tb_4_historys_of_car;

    Repo_1_CAR repo_1_car;
    Repo_2_DUE_OF_PART_FIX repo_2_due_of_part_fix;
    Repo_5_PARTS repo_5_parts;
    Repo_4_HISTORYS_OF_CAR repo_4_historys_of_car;
    Repo_6_RUN_DATA repo_6_run_data;
    Repo_Check repo_check;

    DatePickerDialog mDatePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_list_parts);

        tv_regis = (TextView) findViewById(R.id.textView6);
        tv_kilo = (TextView) findViewById(R.id.textView7);
        tv_ex_date = (TextView) findViewById(R.id.textView8);
        partList = (ListView) findViewById(R.id.listView2);
        add_part = (Button) findViewById(R.id.button10);
        imBtRegis = (ImageButton) findViewById(R.id.editRegis);
        imBtKilo = (ImageButton) findViewById(R.id.editKilo);
        imBtExpTax = (ImageButton) findViewById(R.id.editTax);

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
        repo_check = new Repo_Check(this);

        onResume();

        partList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> AdapterView, final View view, final int position, long id) {

                due_fix_id = Integer.parseInt(getPartList.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Id));
                part_id = Integer.parseInt(getPartList.get(position).get(TB_5_PARTS.Part_Id));
                part_name = getPartList.get(position).get(TB_5_PARTS.Part_Name).toString();
                due_fix_kilo = Integer.parseInt(getPartList.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo));
                due_fix_date = Integer.parseInt(getPartList.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Date));
                due_fix_status = getPartList.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Status);

                showToast("Selected Part : " + part_name);

                AlertDialog.Builder builder = new AlertDialog.Builder(CM_7_List_Parts.this);
                builder.setTitle("Part : " + part_name);
                Choice = new String[]{"Specify Last Maintenance", "Set Maturity", "Delete"};
                builder.setItems(Choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            final Dialog dialog_2 = new Dialog(CM_7_List_Parts.this);
                            dialog_2.setContentView(R.layout.dialog_custom_specify);

                            due_date = (TextView) dialog_2.findViewById(R.id.due_date_spec);
                            due_kilo = (EditText) dialog_2.findViewById(R.id.due_kilo_spec);
                            TextView bSave = (TextView) dialog_2.findViewById(R.id.button_save);
                            TextView bCancel = (TextView) dialog_2.findViewById(R.id.button_cancel);

                            dialog_2.setTitle("Last Maintenance : " + part_name);

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
                                    if (due_date.getText().toString().matches("") || due_kilo.getText().toString().matches("")) {
                                        showToast("Please complete the form below.");
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
                            dialog_2.setTitle("Maturity : " + part_name);
                            dialog_2.setContentView(R.layout.dialog_custom_set_usage);

                            due_date_2 = (EditText) dialog_2.findViewById(R.id.due_date);
                            due_kilo_2 = (EditText) dialog_2.findViewById(R.id.due_kilo);
                            TextView bSave = (TextView) dialog_2.findViewById(R.id.button_save);
                            TextView bCancel = (TextView) dialog_2.findViewById(R.id.button_cancel);

                            due_date_2.setText(String.valueOf(due_fix_date));
                            due_kilo_2.setText(String.valueOf(due_fix_kilo));

                            bSave.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (due_date_2.getText().toString().matches("") ||
                                            due_kilo_2.getText().toString().matches("")) {
                                        showToast("Please complete the form below.");
                                    } else {
                                        tb_2_due_of_part_fix.fix_Due_Id = due_fix_id;
                                        tb_2_due_of_part_fix.car_Id = car_id;
                                        tb_2_due_of_part_fix.part_Id = part_id;
                                        tb_2_due_of_part_fix.fix_Due_Date = Integer.parseInt(due_date.getText().toString());
                                        tb_2_due_of_part_fix.fix_Due_Kilo = Integer.parseInt(due_kilo.getText().toString());
                                        tb_2_due_of_part_fix.fix_Due_Status = String.valueOf(due_fix_status);
                                        repo_2_due_of_part_fix.update(tb_2_due_of_part_fix);

                                        getPartList(car_id);
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

                        showToast("Add Part " + partName + " Success");
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

        mDatePicker2 = DatePickerDialog.newInstance(onDateSetListener2,
                mCalendar.get(Calendar.YEAR),       // ปี
                mCalendar.get(Calendar.MONTH),      // เดือน
                mCalendar.get(Calendar.DAY_OF_MONTH),// วัน (1-31)
                false);

        imBtRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog("Setting Registration", "Registration Car : ", tb_1_car.car_Register, 1);
            }
        });

        imBtKilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog("Setting Number Kilo", "Number Kilo : ", String.valueOf(tb_6_run_data.run_Kilo_End), 2);
            }
        });

        imBtExpTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePicker2.setYearRange(2000, 2030);
                mDatePicker2.show(getSupportFragmentManager(), "datePicker");

            }
        });
    }

    public void getDatacar() {
        tb_1_car = repo_1_car.getCarById(car_id);
        //*****************************  Convert Format Date  *****************************//
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat postFormater = new SimpleDateFormat("dd / MM / yyyy");

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

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

            SimpleDateFormat dfm = new SimpleDateFormat("dd-MM-yyyy");
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

    private DatePickerDialog.OnDateSetListener onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

            SimpleDateFormat dfm_insert = new SimpleDateFormat("yyyy-MM-dd");

            mCalendar.set(year, month, day);
            Date date_chg = mCalendar.getTime();
            tb_1_car.car_Tax_Date = dfm_insert.format(date_chg);
            repo_1_car.update(tb_1_car);
            getDatacar();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        intent = getIntent();
        car_id = intent.getIntExtra("car_Id", 0);
        if (car_id == 0) {
            Bundle bundle = getIntent().getExtras();
            car_id = bundle.getInt("car_Id", 0);
        }
        tb_6_run_data = repo_6_run_data.getLastRunByCar_Id(car_id);
        tv_kilo.setText(String.valueOf(tb_6_run_data.run_Kilo_End));
        getDatacar();
        getPartList(car_id);

    }

    public void showToast(String message) {
        final Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1500);
    }

    public void editDialog(String titleDia, String detailTitle, final String detailData, final int typeInput) {
        final Dialog dialog = new Dialog(CM_7_List_Parts.this);
        dialog.setContentView(R.layout.dialog_custom_edit);

        final TextView data = (TextView) dialog.findViewById(R.id.data);
        final EditText detail = (EditText) dialog.findViewById(R.id.detail);
        TextView bSave = (TextView) dialog.findViewById(R.id.button_save);
        TextView bCancel = (TextView) dialog.findViewById(R.id.button_cancel);

        dialog.setTitle(titleDia);
        data.setText(detailTitle);
        detail.setText(detailData);
        if (typeInput == 2) {
            detail.setInputType(InputType.TYPE_CLASS_NUMBER);
            detail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        } else {
            detail.setInputType(InputType.TYPE_CLASS_TEXT);
            detail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        }
        detail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detail.getText().toString().matches("")) {
                    showToast("Please complete the form below.");
                } else {
                    if (typeInput == 2) {
                        if (Integer.parseInt(detail.getText().toString()) > tb_6_run_data.run_Kilo_End || Integer.parseInt(detail.getText().toString()) < tb_6_run_data.run_Kilo_End) {
                            tb_6_run_data.car_Id = car_id;
                            tb_6_run_data.run_Kilo_Start = tb_6_run_data.run_Kilo_End;
                            tb_6_run_data.run_Kilo_End = Integer.parseInt(detail.getText().toString());
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c = Calendar.getInstance();
                            tb_6_run_data.run_Date_Start = df.format(c.getTime());
                            tb_6_run_data.run_Date_End = df.format(c.getTime());
                            repo_6_run_data.insert(tb_6_run_data);
                            onResume();

                            if (repo_check.chkDueKilo(car_id) >= 1) {
                                notifiChkkilo();
                            }

                        } else {

                        }
                    } else {
                        repo_1_car.updateRegis(car_id, detail.getText().toString());
                        getDatacar();
                    }
                    dialog.dismiss();
                }
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void notifiChkkilo() {
        Intent intent = new Intent(getApplicationContext(), CM_7_List_Parts.class);
        intent.putExtra("car_Id", car_id);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(CM_7_List_Parts.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(TITLE)
                .setContentText(MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), CM_3_Car.class));
        finish();
    }
}
