package com.androidy.azsecuer.config;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPres {
    private static SharedPreferences sp = null;
    private static SharedPreferences.Editor editor;
    public static void save(Context context,String name,Boolean flag){
        if(sp==null){
            sp = context.getSharedPreferences("context",context.MODE_APPEND);
        }
        editor = sp.edit();
        editor.putBoolean(name,flag);
        editor.commit();
    }
    public  static boolean get(Context context,String name){
        if(sp==null){
            sp = context.getSharedPreferences("context",context.MODE_APPEND);
        }
        editor = sp.edit();
        Boolean flag = sp.getBoolean(name,false);
        return flag;
    }
}
