package com.androidy.azsecuer.util;

import android.graphics.drawable.Drawable;
import android.widget.CheckBox;

import java.io.File;

public class FileResultInfo {

    public File file;
    public  boolean isSelect;
    public Drawable drawable;
    public String fileName;
    public String openType;
    public long size;

    public FileResultInfo(File file,Drawable drawable, String fileName, String openType, long size) {
        this.file = file;
        this.drawable = drawable;
        this.fileName = fileName;
        this.openType = openType;
        this.size = size;
        isSelect =false;
    }

    public String toString() {
        return "FileResultInfo{" + "fileName='" + fileName + '\'' + ", time='" + openType + '\'' + '}';
    }
}
