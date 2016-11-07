package com.androidy.azsecuer.config;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import java.util.Timer;
import java.util.TimerTask;

public class BatteryProgressBar extends ProgressBar {

    public BatteryProgressBar(Context context) {
        super(context);
    }

    public BatteryProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public  void setProgressReturn(final int progress){
        setProgress(0);
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setProgress(getProgress() + 1);
                if (getProgress() >= progress) {
                    setProgress(progress);
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 0, 200);
    }
}

