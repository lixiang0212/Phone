package com.androidy.azsecuer.util;

import android.graphics.drawable.Drawable;

public class SoftResultInfo {

    public boolean  isSeleced;
    public Drawable drawable;
    public String   label;
    public String   packageName;
    public String   version;

    public SoftResultInfo(Drawable drawable, String label, String packageName, String version) {
        this.drawable = drawable;
        this.label = label;
        this.packageName = packageName;
        this.version = version;
        isSeleced=false;
    }
}
