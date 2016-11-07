package com.androidy.azsecuer.activity;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.adapter.RubbishAdapter;
import com.androidy.azsecuer.config.DBFileconfig;
import com.androidy.azsecuer.util.FileUtils;
import com.androidy.azsecuer.util.RubbishInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RubbishActivity extends ActionBarActivity {
    private ListView lv;
    private Button btn_clean;
    private List<RubbishInfo> list;
    private RubbishAdapter adapter;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubbish);
        initView();
        initData();
    }

    private void initView() {
        String title ="垃圾清理";
        setActionBack(title);
        lv = (ListView)findViewById(R.id.rubbish_list_view);
        progressBar =(ProgressBar)findViewById(R.id.rubbish_progress_bar);
        btn_clean =(Button)findViewById(R.id.rubbish_clean_btn);
        btn_clean.setOnClickListener(this);
        }

    public void initData(){
        new Thread(new Runnable() {
           public void run() {
           list=addData();
           runOnUiThread(new Runnable() {
              public void run() {
              adapter = new RubbishAdapter(RubbishActivity.this,list);
              lv.setAdapter(adapter);
              lv.setVisibility(View.VISIBLE);
              progressBar.setVisibility(View.INVISIBLE);
              }
           });}}).start();}

    public   List<RubbishInfo>    addData(){
         List<RubbishInfo> listData = new ArrayList<RubbishInfo>();
         File file = new File(DBFileconfig.path, DBFileconfig.name1);
         SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file,null);
         Cursor cursor = db.rawQuery("select * from softdetail", null);
         while(cursor.moveToNext()) {
              String name = cursor.getString(cursor.getColumnIndex("softChinesename"));
              String apkname = cursor.getString(cursor.getColumnIndex("apkname"));
              String filepath = cursor.getString(cursor.getColumnIndex("filepath"));
              // 得到库内垃圾文件位置
              filepath = Environment.getExternalStorageDirectory() + filepath;
              File cacheFile = new File(filepath);
              //if (cacheFile.exists()) {
              long size = FileUtils.getFileSize(cacheFile);
              Drawable drawable = null;
              try {
                   drawable = getPackageManager().getApplicationIcon(apkname);
              } catch (PackageManager.NameNotFoundException e) {
                    drawable = getResources().getDrawable(R.drawable.tel_people_icon);
              }
              RubbishInfo clearInfo = new RubbishInfo(drawable, apkname, filepath, name, size);
              listData.add(clearInfo);
              //}
         }
              cursor.close();
              db.close();
         return listData;
    }

    private void delFile(){
        lv.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
            List<RubbishInfo> list = new ArrayList<RubbishInfo>();
            List<RubbishInfo> listcopy =adapter.getDataFromAdapter();
            for (RubbishInfo rubbish:listcopy) {
                 if (rubbish.ischeckd) {
                     list.add(rubbish);
                 }
            }
            for (RubbishInfo rubbish:list) {
                 File file = new File(rubbish.filepath);
                 FileUtils.delFile(file);
                 list.remove(file);
            }
            runOnUiThread(new Runnable() {
            public void run() {
                 adapter.notifyDataSetChanged();
                 lv.setVisibility(View.VISIBLE);
                 progressBar.setVisibility(View.INVISIBLE);
                 }
            });
            }}).start();}

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_actionbar_launcher:
                finish();
                break;
            case R.id.rubbish_clean_btn:
                delFile();
                break;
        }}
}
