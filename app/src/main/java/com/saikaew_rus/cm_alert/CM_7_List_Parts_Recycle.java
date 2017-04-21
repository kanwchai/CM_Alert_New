package com.saikaew_rus.cm_alert;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CM_7_List_Parts_Recycle extends AppCompatActivity {

    TextView tv_regis, tv_kilo, tv_ex_date, due_date;
    int car_id, part_id, due_fix_id, due_fix_kilo, due_fix_date, sortPartList, partId;
    String part_name, due_fix_status, maintenance;
    String[] new_part, Choice;
    EditText due_kilo, due_date_2, due_kilo_2;
    ImageButton imBtRegis, imBtKilo, imBtExpTax;
    ImageView icon;
    LinearLayout linearLayout;

    DecimalFormat decimalFormat;

    ArrayList<HashMap<String, String>> getdataPartList, partDonHave;
    Intent intent;
    Calendar mCalendar;
    DatePickerDialog mDatePicker;

    TB_1_CAR tb_1_car;
    TB_2_DUE_OF_PART_FIX tb_2_due_of_part_fix;
    TB_6_RUN_DATA tb_6_run_data;
    TB_4_HISTORYS_OF_CAR tb_4_historys_of_car;

    Repo_1_CAR repo_1_car;
    Repo_2_DUE_OF_PART_FIX repo_2_due_of_part_fix;
    Repo_5_PARTS repo_5_parts;
    Repo_4_HISTORYS_OF_CAR repo_4_historys_of_car;
    Repo_6_RUN_DATA repo_6_run_data;
    A_Repo_Check repo_check;

    DatePickerDialog mDatePicker2;
    FloatingActionButton actionButton;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_list_parts_recycle);
        this.setTitle(R.string.title_list_part);

        setLayout();
        setValue();
        setEvent();

        onResume();
        getPartList(car_id);
    }

    public void setLayout() {
        tv_regis = (TextView) findViewById(R.id.textView6);
        tv_kilo = (TextView) findViewById(R.id.textView7);
        tv_ex_date = (TextView) findViewById(R.id.textView8);
        imBtRegis = (ImageButton) findViewById(R.id.editRegis);
        imBtKilo = (ImageButton) findViewById(R.id.editKilo);
        imBtExpTax = (ImageButton) findViewById(R.id.editTax);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyPart);
        linearLayout = (LinearLayout) findViewById(R.id.linearListPart);
    }

    public void setValue() {
        mCalendar = Calendar.getInstance();
        sortPartList = 2;
        intent = getIntent();
        car_id = intent.getIntExtra(TB_1_CAR.Car_Id, 0);
        Choice = getResources().getStringArray(R.array.choice_Lis);

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        tb_1_car = new TB_1_CAR();
        tb_2_due_of_part_fix = new TB_2_DUE_OF_PART_FIX();
        tb_6_run_data = new TB_6_RUN_DATA();
        tb_4_historys_of_car = new TB_4_HISTORYS_OF_CAR();

        repo_1_car = new Repo_1_CAR(this);
        repo_2_due_of_part_fix = new Repo_2_DUE_OF_PART_FIX(this);
        repo_5_parts = new Repo_5_PARTS(this);
        repo_4_historys_of_car = new Repo_4_HISTORYS_OF_CAR(this);
        repo_6_run_data = new Repo_6_RUN_DATA(this);
        repo_check = new A_Repo_Check(this);

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

        //Create a button to attach the menu:
        icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
        actionButton = new FloatingActionButton.Builder(this)
                .setPosition(4)
                .setContentView(icon).build();
        actionButton.setBackground(getResources().getDrawable(R.drawable.btn_add_part));
    }

    public void setEvent() {
        imBtRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRegis("Setting Registration", "Registration Car : ", tb_1_car.car_Register);
            }
        });

        imBtKilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKilo("Setting Number Kilo", "Number Kilo : ", String.valueOf(tb_6_run_data.run_Kilo_End));
            }
        });

        imBtExpTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePicker2.setYearRange(2000, 2030);
                mDatePicker2.show(getSupportFragmentManager(), "datePicker");
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_NewPart();
            }
        });
    }

    public void dialog_NewPart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CM_7_List_Parts_Recycle.this);
        builder.setTitle(R.string.lp_SelectNewPart);

        partDonHave = repo_5_parts.getPart_Not(car_id);
        new_part = new String[partDonHave.size()];

        String[] partName = {TB_5_PARTS.Part_Name_en, TB_5_PARTS.Part_Name_th};

        for (int i = 0; i < partDonHave.size(); i++) {
            int numLang = Integer.parseInt(getResources().getString(R.string.num_Language));
            new_part[i] = partDonHave.get(i).get(partName[numLang]);
        }

        builder.setItems(new_part, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("partnothave", partDonHave.get(which).get(TB_5_PARTS.Part_Id) + " +++ "
                        + partDonHave.get(which).get(TB_5_PARTS.Part_Name_en) + " +++ "
                        + partDonHave.get(which).get(TB_5_PARTS.Part_Name_th));

                partId = Integer.parseInt(partDonHave.get(which).get(TB_5_PARTS.Part_Id));

                Cursor cursor = repo_2_due_of_part_fix.chkPartId_CarId(partId, car_id);
                if (cursor.getCount() >= 1) {
                    String fix_id = cursor.getString(cursor.getColumnIndex(TB_2_DUE_OF_PART_FIX.Fix_Due_Id));
                    repo_2_due_of_part_fix.update_StatusShow(fix_id, 1);
                } else {
                    repo_2_due_of_part_fix.insert_PartByCar(car_id, partId);
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
                }

                getPartList(car_id);

                showToast("Add Part " + partDonHave.get(which).get(TB_5_PARTS.Part_Name_en) + " Success");
            }
        });

        builder.setNegativeButton(R.string.lp_AddNewPart, new DialogInterface.OnClickListener() {
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
        getdataPartList = repo_2_due_of_part_fix.getFixListByCarId(carId, sortPartList);
        mAdapter = new CM_7_List_Parts_Recycle_Sub(this, getdataPartList);
        mRecyclerView.setAdapter(mAdapter);
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

        if (car_id == 0) {
            Bundle bundle = getIntent().getExtras();
            car_id = bundle.getInt(TB_1_CAR.Car_Id, 0);
        }
        tb_6_run_data = repo_6_run_data.getLastRunByCar_Id(car_id);

        decimalFormat = new DecimalFormat("#,###,###");

        tv_kilo.setText(String.valueOf(decimalFormat.format(tb_6_run_data.run_Kilo_End)));
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
        }, 1200);
    }

    public void dialogRegis(String titleDialog, String detailTitle, String detailData) {
        final Dialog dialog = new Dialog(CM_7_List_Parts_Recycle.this);
        dialog.setContentView(R.layout.dialog_custom_edit_regis);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView data = (TextView) dialog.findViewById(R.id.data);
        final EditText regisFront = (EditText) dialog.findViewById(R.id.editText3);
        final EditText regisBack = (EditText) dialog.findViewById(R.id.detail);
        TextView bSave = (TextView) dialog.findViewById(R.id.button_save);
        TextView bCancel = (TextView) dialog.findViewById(R.id.button_cancel);

        dialog.setTitle(titleDialog);
        data.setText(detailTitle);
        regisFront.setText(detailData.substring(0, detailData.indexOf("-") - 1));
        regisBack.setText(detailData.substring(detailData.indexOf("-") + 1));

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regisFront.getText().toString().matches("") && regisBack.getText().toString().matches("")) {
                    showToast("Please complete the form below.");
                } else {
                    repo_1_car.updateRegis(car_id, regisFront.getText().toString() + " - " + regisBack.getText().toString());

                    getPartList(car_id);
                    onResume();
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

    public void dialogKilo(String titleDialog, String detailTitle, String detailData) {
        final Dialog dialog = new Dialog(CM_7_List_Parts_Recycle.this);
        dialog.setContentView(R.layout.dialog_custom_edit);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView data = (TextView) dialog.findViewById(R.id.data);
        final EditText detail = (EditText) dialog.findViewById(R.id.detail);
        TextView bSave = (TextView) dialog.findViewById(R.id.button_save);
        TextView bCancel = (TextView) dialog.findViewById(R.id.button_cancel);

        dialog.setTitle(titleDialog);
        data.setText(detailTitle);
        detail.setText(detailData);

        detail.setFilters(new InputFilter[]{new A_SetMinMaxNumber("1", "500000")});

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detail.getText().toString().matches("")) {
                    showToast("Please complete the form below.");
                } else {
                    if (Double.parseDouble(detail.getText().toString()) > tb_6_run_data.run_Kilo_End ||
                            Double.parseDouble(detail.getText().toString()) < tb_6_run_data.run_Kilo_End) {
                        tb_6_run_data.car_Id = car_id;
                        tb_6_run_data.run_Kilo_Start = tb_6_run_data.run_Kilo_End;
                        tb_6_run_data.run_Kilo_End = Double.parseDouble(detail.getText().toString());
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        tb_6_run_data.run_Date_Start = df.format(c.getTime());
                        tb_6_run_data.run_Date_End = df.format(c.getTime());
                        repo_6_run_data.insert(tb_6_run_data);

                        if (repo_check.chkDueKilo(car_id) >= 1) {
                            notifiChkkilo();
                        }

                    }
                    getPartList(car_id);
                    onResume();
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

    private static final String TITLE = "Maintenance Your Car";
    private static final String MESSAGE = "You have parts breakdown!!!";

    public void notifiChkkilo() {
        Intent intent = new Intent(getApplicationContext(), CM_7_List_Parts_Recycle.class);
        intent.putExtra(TB_1_CAR.Car_Id, car_id);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(CM_7_List_Parts_Recycle.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_logo)
                .setLargeIcon(icon)
                .setContentTitle(TITLE)
                .setContentText(MESSAGE)
                .setAutoCancel(true)
                .setVibrate(new long[]{500, 1000, 500})
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void sortPart(View view) {
        boolean clickable = view.isClickable();

        switch (view.getId()) {
            case R.id.listPartName:
                if (clickable) {
                    if (sortPartList != 0) {
                        sortPartList = 0;
                    } else {
                        sortPartList = 3;
                    }
                    getPartList(car_id);
                }
                break;
            case R.id.listPartDay:
                if (clickable) {
                    if (sortPartList != 1) {
                        sortPartList = 1;
                    } else {
                        sortPartList = 4;
                    }
                    getPartList(car_id);
                }
                break;
            case R.id.listPartKilo:
                if (clickable) {
                    if (sortPartList != 2) {
                        sortPartList = 2;
                    } else {
                        sortPartList = 5;
                    }
                    getPartList(car_id);
                }
                break;
        }
    }

    public class CM_7_List_Parts_Recycle_Sub extends RecyclerView.Adapter<CM_7_List_Parts_Recycle_Sub.ViewHolder> {
        private Context mContext;
        private ArrayList<HashMap<String, String>> getPartList_Recy;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView partName, partDate, partKilo;
            public final LinearLayout linearLayout;

            public ViewHolder(View view) {
                super(view);
                partName = (TextView) view.findViewById(R.id.partName);
                partDate = (TextView) view.findViewById(R.id.partDate);
                partKilo = (TextView) view.findViewById(R.id.partKilo);
                linearLayout = (LinearLayout) view.findViewById(R.id.linPartlist);
            }
        }

        public CM_7_List_Parts_Recycle_Sub(Context mContext, ArrayList<HashMap<String, String>> getCarList) {
            this.mContext = mContext;
            this.getPartList_Recy = getCarList;
        }

        @Override
        public CM_7_List_Parts_Recycle_Sub.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_part_list, parent, false);

            CM_7_List_Parts_Recycle_Sub.ViewHolder viewHolder = new CM_7_List_Parts_Recycle_Sub.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CM_7_List_Parts_Recycle_Sub.ViewHolder holder, final int position) {
            if (position == getPartList_Recy.size() - 1) {
                actionButton.setPosition(FloatingActionButton.POSITION_RIGHT_CENTER, (FrameLayout.LayoutParams) actionButton.getLayoutParams());
            } else {
                actionButton.setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT, (FrameLayout.LayoutParams) actionButton.getLayoutParams());
            }

            DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
            Double aDouble = Double.valueOf((getPartList_Recy.get(position).get(TB_2_DUE_OF_PART_FIX.CountKilo)));

            Log.d("dataPartList", String.valueOf(aDouble) + " " + getPartList_Recy.get(position).get(TB_5_PARTS.Part_Name_en));

            holder.partName.setText(getPartList_Recy.get(position).get(TB_5_PARTS.Part_Name_en));
            holder.partKilo.setText(decimalFormat.format(aDouble));
            holder.partDate.setText(getPartList_Recy.get(position).get(TB_2_DUE_OF_PART_FIX.CountDate));

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    due_fix_id = Integer.valueOf(getPartList_Recy.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Id));
                    part_id = Integer.valueOf(getPartList_Recy.get(position).get(TB_5_PARTS.Part_Id));
                    part_name = getPartList_Recy.get(position).get(TB_5_PARTS.Part_Name_en).toString();
                    if (getPartList_Recy.get(position).get(TB_5_PARTS.Maintenance_Guide_En) == null) {
                        maintenance = "  ";
                    } else {
                        maintenance = getPartList_Recy.get(position).get(TB_5_PARTS.Maintenance_Guide_En).toString();
                    }
                    if (!getPartList_Recy.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo).equals("")) {
                        due_fix_kilo = Integer.valueOf(getPartList_Recy.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Kilo));
                    } else {
                        due_fix_kilo = 0;
                    }
                    if (!getPartList_Recy.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Date).equals("")) {
                        due_fix_date = Integer.valueOf(getPartList_Recy.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Date));
                    } else {
                        due_fix_date = 0;
                    }
                    due_fix_status = getPartList_Recy.get(position).get(TB_2_DUE_OF_PART_FIX.Fix_Due_Status);

                    showToast(mContext.getResources().getString(R.string.lp_SelectedPart) + " : " + part_name);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(CM_7_List_Parts_Recycle.this);
                    builder.setTitle(mContext.getResources().getString(R.string.lp_Part) + " : " + part_name);
                    builder.setItems(Choice, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                final Dialog dialog_2 = new Dialog(CM_7_List_Parts_Recycle.this);
                                dialog_2.setContentView(R.layout.dialog_custom_specify);

                                dialog_2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                due_date = (TextView) dialog_2.findViewById(R.id.due_date_spec);
                                due_kilo = (EditText) dialog_2.findViewById(R.id.due_kilo_spec);
                                TextView bSave = (TextView) dialog_2.findViewById(R.id.button_save);
                                TextView bCancel = (TextView) dialog_2.findViewById(R.id.button_cancel);

                                due_kilo.setText(String.valueOf(tb_6_run_data.run_Kilo_End));

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
                                            showToast(mContext.getResources().getString(R.string.lp_Please_complete));
                                        } else {
                                            tb_4_historys_of_car.fix_Due_Id = due_fix_id;
                                            tb_4_historys_of_car.car_Id = car_id;
                                            tb_4_historys_of_car.changed_Kilo = Double.parseDouble(due_kilo.getText().toString());
                                            tb_4_historys_of_car.next_Changed_Kilo = Double.parseDouble(String.valueOf(due_kilo.getText())) + due_fix_kilo;
                                            repo_4_historys_of_car.insert(tb_4_historys_of_car);

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

                            } else if (which == 1) {
                                final Dialog dialog_2 = new Dialog(CM_7_List_Parts_Recycle.this);
                                dialog_2.setTitle("Maturity : " + part_name);
                                dialog_2.setContentView(R.layout.dialog_custom_set_usage);

                                dialog_2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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
                                            tb_2_due_of_part_fix.fix_Due_Date = Integer.valueOf(due_date_2.getText().toString());
                                            tb_2_due_of_part_fix.fix_Due_Kilo = Integer.valueOf(due_kilo_2.getText().toString());
                                            tb_2_due_of_part_fix.fix_Due_Status = String.valueOf(due_fix_status);
                                            repo_2_due_of_part_fix.update(tb_2_due_of_part_fix);

                                            dialog_2.dismiss();
                                            getPartList(car_id);
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
                                AlertDialog.Builder builder_1 = new AlertDialog.Builder(CM_7_List_Parts_Recycle.this);
                                builder_1.setTitle(part_name);
                                builder_1.setMessage(maintenance);
                                Toast.makeText(getApplicationContext(), "Part " + part_name + " Maintenance Guide", Toast.LENGTH_SHORT).show();
                                builder_1.show();
                            } else if (which == 3) {
                                AlertDialog.Builder builder_1 = new AlertDialog.Builder(CM_7_List_Parts_Recycle.this);
                                builder_1.setMessage(mContext.getResources().getString(R.string.lp_delete) + part_name + " ?");
                                builder_1.setNegativeButton("OK ", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        tb_2_due_of_part_fix.fix_Due_Id = due_fix_id;
                                        repo_2_due_of_part_fix.update_StatusShow(String.valueOf(tb_2_due_of_part_fix.fix_Due_Id), 0);
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

        }

        @Override
        public int getItemCount() {
            return getPartList_Recy.size();
        }

    }

}
