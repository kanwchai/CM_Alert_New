package com.saikaew_rus.cm_alert_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NB_A on 27/1/2560.
 */

public class CM_3_Car_base_adapter extends BaseAdapter {
    Context mContext;
    ArrayList<HashMap<String, String>> getCarList;

    public CM_3_Car_base_adapter(Context mContext, ArrayList<HashMap<String, String>> getCarList) {
        this.mContext = mContext;
        this.getCarList = getCarList;
    }

    public int getCount() {
        return getCarList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = mInflater.inflate(R.layout.view_car_list, parent, false);
        }

        TextView car_Regis = (TextView) view.findViewById(R.id.car_Register);
        TextView car_Prov = (TextView) view.findViewById(R.id.car_Province);

        final CircularBar c2 = (CircularBar) view.findViewById(R.id.circularprogressbar2);
        c2.animateProgressTo(0, 77, new CircularBar.ProgressAnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                c2.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                c2.setSubTitle("Status");
            }
        });

        car_Regis.setText(getCarList.get(position).get(TB_1_CAR.Car_Register));
        car_Prov.setText(getCarList.get(position).get(TB_1_CAR.Province_Name));

        return view;
    }
}
