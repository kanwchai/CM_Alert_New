package com.saikaew_rus.cm_alert;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by NB_A on 6/2/2560.
 */

public class A_Toast_Time {
    public void Toast_Time(Context context, String msg, int time) {
        final Toast toast;
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
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
