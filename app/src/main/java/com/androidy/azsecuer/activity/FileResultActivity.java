package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.adapter.FileResultAdapter;
import com.androidy.azsecuer.manager.FileManager;
import com.androidy.azsecuer.manager.MemoryManager;
import com.androidy.azsecuer.util.FileManagerInfo;
import com.androidy.azsecuer.util.FileResultInfo;
import com.androidy.azsecuer.util.FileUtils;
import com.androidy.azsecuer.util.PublicUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileResultActivity extends ActionBarActivity{

    private long id,size;
    private TextView tv_fileCount, tv_fileSize;
    private ListView lv;
    private CheckBox cBox;
    private Button btn_clean;
    private List<FileResultInfo> list;
    private FileResultAdapter adapter;
    private FileManager manager;
    private List<FileManagerInfo> listTxt = new ArrayList<>();
    private List<FileManagerInfo> listPng = new ArrayList<>();
    private List<FileManagerInfo> listApk = new ArrayList<>();
    private List<FileManagerInfo> listMp4 = new ArrayList<>();
    private List<FileManagerInfo> listMp3 = new ArrayList<>();
    private List<FileManagerInfo> listZip = new ArrayList<>();
    private List<FileManagerInfo> listOther = new ArrayList<>();
    private List<FileResultInfo> Txt= new ArrayList<>();
    private List<FileResultInfo> Png= new ArrayList<>();
    private List<FileResultInfo> Apk= new ArrayList<>();
    private List<FileResultInfo> Mp4= new ArrayList<>();
    private List<FileResultInfo> Mp3= new ArrayList<>();
    private List<FileResultInfo> Zip= new ArrayList<>();
    private List<FileResultInfo> Other= new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_result);
        list = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        id = bundle.getLong("id", 0);
        size=bundle.getLong("size",0);
        initView();
        initData(id);
    }

    private void initView() {
        setActionBack("文件管理");
        lv = (ListView) findViewById(R.id.file_result_list_view);
        tv_fileCount = (TextView) findViewById(R.id.file_result_cSize);
        tv_fileSize = (TextView) findViewById(R.id.file_result_size);
        cBox = (CheckBox) findViewById(R.id.file_result_cBox);
        btn_clean = (Button) findViewById(R.id.file_result_cBtn);
        btn_clean.setOnClickListener(this);
        cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    for (FileResultInfo info : list) {
                        info.isSelect = true;
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    for (FileResultInfo info : list) {
                        info.isSelect = false;
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private List<FileResultInfo> findData(long id) {
        manager = FileManager.getManager();
        listTxt = manager.getTxt();
        listPng = manager.getPng();
        listApk = manager.getApk();
        listMp4 = manager.getMp4();
        listMp3 = manager.getMp3();
        listZip = manager.getZip();
        listOther = manager.getOther();
        Drawable icon = getResources().getDrawable(R.drawable.tel_people_icon);
        for (FileManagerInfo info : listTxt) {
            Txt.add(new FileResultInfo(info.file,icon, info.fileKind,"text/plain", info.fileSize));
        }
        for (FileManagerInfo info : listPng) {
            Png.add(new FileResultInfo(info.file,icon, info.fileKind, "image/jpeg", info.fileSize));
        }
        for (FileManagerInfo info : listApk) {
            Apk.add(new FileResultInfo(info.file,icon, info.fileKind, "application/vnd.android.package-archive", info.fileSize));
        }
        for (FileManagerInfo info : listMp4) {
            Mp4.add(new FileResultInfo(info.file,icon, info.fileKind, "video/mp4", info.fileSize));
        }
        for (FileManagerInfo info : listMp3) {
            Mp3.add(new FileResultInfo(info.file,icon, info.fileKind, "audio/x-mpeg", info.fileSize));
        }
        for (FileManagerInfo info : listZip) {
            Zip.add(new FileResultInfo(info.file,icon, info.fileKind, "application/zip", info.fileSize));
        }
        for (FileManagerInfo info : listOther) {
            Other.add(new FileResultInfo(info.file,icon, info.fileKind, "", info.fileSize));
        }
        if (id == 0) {
            return Txt;
        } else if (id == 1) {
            return Png;
        } else if (id == 2) {
            return Apk;
        } else if (id == 3) {
            return Mp4;
        } else if (id == 4) {
            return Mp3;
        } else if (id == 5) {
            return Zip;
        } else {
            return Other;
        }
    }
    private void initData(long number){
        final long id = number;
        new Thread(new Runnable() {
            public void run() {
                list=findData(id);
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new FileResultAdapter(FileResultActivity.this,list);
                        tv_fileCount.setText(list.size()+"");
                        tv_fileSize.setText(PublicUtils.formatSize(size));
                        lv.setAdapter(adapter);
                    }
                });
            }
        }).start();

    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.home_actionbar_launcher:
                finish();
                break;
            case R.id.file_result_cBtn:
                new Thread(new Runnable() {
                    public void run() {
                        List<FileResultInfo> list = adapter.getDataFromAdapter();
                        for (FileResultInfo info:list) {
                            if(info.isSelect){
                                FileUtils.delFile(info.file);
                            }
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                adapter.notifyDataSetChanged();
                                lv.setAdapter(adapter);
                                cBox.setChecked(false);
                            }
                        });
                    }
                }).start();
                break;
        }}

//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//        FileResultInfo info = adapter.getItem(position);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri data = Uri.fromFile(info.file);
//        intent.setDataAndType(data,info.openType);
//        Toast.makeText(this,position+"aaa",Toast.LENGTH_SHORT).show();
//        //startActivity(intent);
//
//    }
}
