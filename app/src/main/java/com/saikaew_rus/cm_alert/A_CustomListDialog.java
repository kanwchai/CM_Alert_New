package com.saikaew_rus.cm_alert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NB_A on 27/3/2560.
 */

public class A_CustomListDialog extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> data;

    public A_CustomListDialog(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = layoutInflater.inflate(R.layout.list_dialog_maintenence, viewGroup, false);

        TextView textView = (TextView) view.findViewById(R.id.titlename);
        TextView textView1 = (TextView) view.findViewById(R.id.totalname);

        textView.setText(data.get(i).get(CM_3_Car_Recycle.alert_name));
        textView1.setText(data.get(i).get(CM_3_Car_Recycle.alert_total));

        return view;
    }
}
