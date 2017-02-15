package com.saikaew_rus.cm_alert_new;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NB_A on 27/1/2560.
 */

public class A_CM_3_Car_Base_Adapter_Recycle extends RecyclerView.Adapter<A_CM_3_Car_Base_Adapter_Recycle.ViewHolder> {
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

        public ViewHolder(View view) {
            super(view);

            car_Regis = (TextView) view.findViewById(R.id.car_Register);
            car_Prov = (TextView) view.findViewById(R.id.car_Province);
            linearLayout = (LinearLayout) view.findViewById(R.id.linRecycle);
            c2 = (A_CircularBar) view.findViewById(R.id.circularprogressbar2);

            repo_1_car = new Repo_1_CAR(mContext);
            a_toast_time = new A_Toast_Time();
        }
    }

    public A_CM_3_Car_Base_Adapter_Recycle(Context mContext, ArrayList<HashMap<String, String>> getCarList) {
        this.mContext = mContext;
        this.getCarList = getCarList;
    }

    @Override
    public A_CM_3_Car_Base_Adapter_Recycle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_car_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final A_CM_3_Car_Base_Adapter_Recycle.ViewHolder holder, final int position) {
        regCar = getCarList.get(position).get(TB_1_CAR.Car_Register);
        provCar = getCarList.get(position).get(TB_1_CAR.Province_Name);
        Log.d("dataPartCar", regCar + "  " + getCarList.get(position).get(TB_1_CAR.SetTitle));
        holder.car_Regis.setText(regCar);
        holder.car_Prov.setText(provCar);

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
                holder.c2.setTitle(getCarList.get(position).get(TB_1_CAR.SetTitle) + "%");
                holder.c2.setSubTitle("Status");
                if (getCarList.get(position).get(TB_1_CAR.SetColor) != null) {
                    holder.c2.setTitleColor(mContext.getResources().getColor(Integer.valueOf(getCarList.get(position).get(TB_1_CAR.SetColor))));
                    holder.c2.setSubTitleColor(mContext.getResources().getColor(Integer.valueOf(getCarList.get(position).get(TB_1_CAR.SetColor))));
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
