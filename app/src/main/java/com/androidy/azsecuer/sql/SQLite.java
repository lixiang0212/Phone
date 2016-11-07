package com.androidy.azsecuer.sql;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androidy.azsecuer.util.User;

import java.util.ArrayList;
import java.util.List;

public class SQLite {

    private MyOpenHelper helper ;
    public SQLite(Context context) {
        helper = new MyOpenHelper(context);
    }

    public void insert(User user){

        SQLiteDatabase db =helper.getWritableDatabase();
        //database.execSQL("insert into user(name,number) values('张三',123456)");
        //database.execSQL("insert into user(name,number) values(?,?)",new Object[]{"李四","111111"});
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("number",user.getPasswd());
        //long id =db.insert("user",null,values);//表名
        db.insert("user",null,values);//表名
        //user.setId(id);
        db.close();
    }
    public void delete(long id){

        SQLiteDatabase db =helper.getWritableDatabase();
        //database.execSQL("delete * from user where id = "+id);
        db.delete("user","id=?",new String[]{id+""});
        db.close();

    }
    public void update(User user,long id){

        SQLiteDatabase db =helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("number",user.getPasswd());
        db.update("user",values,"id=?",new String[]{id+""});
        db.close();

    }
    public User select(long id){

        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where id=?",new String[]{id+""});
        User user=null;
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String pd = cursor.getString(cursor.getColumnIndex("number"));
            long ids = cursor.getLong(cursor.getColumnIndex("id"));
            user = new User(name, pd, ids);
        }
        return user;

    }
    public List<User> queryAll(){

        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = db.query("user",null,null,null,null,null,"id ASC");//DESC逆序
        List<User> list = new ArrayList<User>();
        while (cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String pd = cursor.getString(cursor.getColumnIndex("number"));
            list.add(new User(name,pd,id));
        }
        cursor.close();
        db.close();
        return list;
    }
}

