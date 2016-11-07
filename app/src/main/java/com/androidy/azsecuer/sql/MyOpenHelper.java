package com.androidy.azsecuer.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context,"tel_number", null, 5);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(id integer primary key autoincrement,name varchar(20),number varchar(20))");
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("alter table user add account varchar(20)");
    }
}
