package com.androidy.azsecuer.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.androidy.azsecuer.activity.MainActivity;
import com.androidy.azsecuer.notification.MyNotification;

public class Autostart_Receiver extends BroadcastReceiver {

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    public Autostart_Receiver() {

    }

    public void onReceive(Context context, Intent intent) {
        Boolean flag = SharedPres.get(context, "sw_auto_start");
        if (flag) {
            if (intent.getAction().equals(ACTION)) {

                 MyNotification.openNotification(context);
//                Intent intent1 = new Intent();
//                intent1.setClass(context, MainActivity.class);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);
            } else {
                //throw new UnsupportedOperationException("Not yet implemented");
            }
        }
//        else{
//            MyNotification.closeNotification(context);
//        }
    }
}
