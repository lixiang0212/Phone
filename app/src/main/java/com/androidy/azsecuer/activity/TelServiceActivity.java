package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.adapter.TelAdapter;
import com.androidy.azsecuer.config.DBFileconfig;
import com.androidy.azsecuer.sql.MyOpenHelper;
import com.androidy.azsecuer.util.TelServiceInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TelServiceActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private TelAdapter adapter;
    private List<TelServiceInfo> list;
    private MyOpenHelper helper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_service);
        initView();
        listenerView();
    }

    private void initView() {
        String title = "电话簿";
        setActionBack(title);
        listView =(ListView)findViewById(R.id.tel_service_listview);
        adapter = new TelAdapter(this,filldata());
        listView.setAdapter(adapter);
    }
    private void listenerView() {
        listView.setOnItemClickListener(this);
    }

    public List<TelServiceInfo> filldata() {
        helper = new MyOpenHelper(this);
        File file = new File(DBFileconfig.path, DBFileconfig.name);
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file,null);
        //Cursor cursor = db.query("classlist",null,null,null,null,null,"id ASC");//DESC逆序
        Cursor cursor = db.rawQuery("select * from classlist", null);
        list = new ArrayList<TelServiceInfo>();
        while (cursor.moveToNext()){
            int idx = cursor.getInt(cursor.getColumnIndex("idx"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(new TelServiceInfo(idx,name));
        }
        cursor.close();
        db.close();
        return list;
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_actionbar_launcher:
                finish();
                break;
        }}

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TelServiceInfo telServiceInfo = adapter.getItem(position);
        Intent intent = new Intent(TelServiceActivity.this,TelNumber.class);
        Bundle bundle = new Bundle();
        bundle.putInt("data",telServiceInfo.idx);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
