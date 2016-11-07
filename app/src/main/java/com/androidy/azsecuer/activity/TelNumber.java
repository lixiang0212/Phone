package com.androidy.azsecuer.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.adapter.NumberAdapter;
import com.androidy.azsecuer.config.DBFileconfig;
import com.androidy.azsecuer.sql.MyOpenHelper;
import com.androidy.azsecuer.util.TelNumberInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TelNumber extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private NumberAdapter adapter;
    private MyOpenHelper helper;
    private List<TelNumberInfo> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_number);
        fillData();
        initView();
        listenerView();
    }
    public List<TelNumberInfo> fillData() {
        Bundle bundle = getIntent().getExtras();
        int idx = bundle.getInt("data");
        helper = new MyOpenHelper(this);
        File file = new File(DBFileconfig.path, DBFileconfig.name);
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file,null);
        //Cursor cursor = db.query("classlist",null,null,null,null,null,"id ASC");//DESC逆序
        Cursor cursor = db.rawQuery("select * from table"+idx, null);
        list = new ArrayList<TelNumberInfo>();
        while (cursor.moveToNext()){
            String number = cursor.getString(cursor.getColumnIndex("number"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(new TelNumberInfo(number,name));
        }
        cursor.close();
        db.close();
        return list;
    }
    private void initView() {
        String title ="联系人";
        setActionBack(title);
        listView = (ListView) findViewById(R.id.tel_number_list);
        adapter = new NumberAdapter(list, this);
        listView.setAdapter(adapter);
    }
    private void listenerView() {
        listView.setOnItemClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_actionbar_launcher:
                finish();
                break;
        }}
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        TelNumberInfo telNumberInfo =adapter.getItem(position);
        String name = telNumberInfo.name;
        final String number =telNumberInfo.number;
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.tel_people_icon)
                .setTitle("联系人姓名:"+name)
                .setMessage("电话号码："+number)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel://" + number));
                        startActivity(intent);
                    }})
                .show();
    }
}
