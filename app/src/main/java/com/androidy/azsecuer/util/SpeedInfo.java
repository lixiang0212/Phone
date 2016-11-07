package com.androidy.azsecuer.util;

import android.graphics.drawable.Drawable;

public class SpeedInfo {

    public Drawable drawable;
    public String   name;
    public String   lable;
    public long     size;
    public boolean isSelected;
    public boolean isSystemApp;

    public SpeedInfo(Drawable drawable, String name, String lable, long size, boolean isSystemApp) {
        this.drawable = drawable;
        this.name = name;
        this.lable = lable;
        this.size = size;
        this.isSystemApp = isSystemApp;
        isSelected = false;
    }

    public SpeedInfo(String name, Drawable drawable, long size,String lable) {
        this.name = name;
        this.drawable = drawable;
        this.lable = lable;
        this.size = size;
        isSelected = false;
    }

}
