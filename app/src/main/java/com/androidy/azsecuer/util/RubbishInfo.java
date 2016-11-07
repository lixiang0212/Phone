package com.androidy.azsecuer.util;

import android.graphics.drawable.Drawable;

public class RubbishInfo {

    public Boolean ischeckd = false;
    public Drawable drawable;
    public String   apkname;
    public String   filepath;
    public String   name;
    public long    size;

    public RubbishInfo(Drawable drawable, String apkname, String filepath, String name, long size) {
        this.drawable = drawable;
        this.apkname = apkname;
        this.filepath = filepath;
        this.name = name;
        this.size = size;
    }

    public RubbishInfo(String name, long size, Drawable drawable) {
        this.name = name;
        this.size = size;
        this.drawable = drawable;
    }

    @Override
    public String toString() {
        return "RubbishInfo{"+"apkname='"+apkname+'\''+",filepath='"+filepath+'\''+",name='"+name+'\''+'}';
    }
}
