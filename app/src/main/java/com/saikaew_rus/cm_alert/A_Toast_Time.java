package com.saikaew_rus.cm_alert;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by NB_A on 6/2/2560.
 */

public class A_Toast_Time {
    Toast toast;
    public void Toast_Time(Context context, String message, int time) {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, time);
    }
}
