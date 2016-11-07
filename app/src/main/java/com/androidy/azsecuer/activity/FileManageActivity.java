package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.adapter.FileAdapter;
import com.androidy.azsecuer.manager.FileManager;
import com.androidy.azsecuer.manager.MemoryManager;
import com.androidy.azsecuer.util.FileManagerInfo;
import com.androidy.azsecuer.util.FileUtils;
import com.androidy.azsecuer.util.PublicUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManageActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView lv;
    private ProgressBar progressBar;
    private TextView tv_title,tv_size;
    private Button btn;
    private FileAdapter adapter;
    private List<FileManagerInfo> list;
    private List<FileManagerInfo> listAll = new ArrayList<>();
    private List<FileManagerInfo> listTxt = new ArrayList<>();
    private List<FileManagerInfo> listPng = new ArrayList<>();
    private List<FileManagerInfo> listApk = new ArrayList<>();
    private List<FileManagerInfo> listMp4 = new ArrayList<>();
    private List<FileManagerInfo> listMp3 = new ArrayList<>();
    private List<FileManagerInfo> listZip = new ArrayList<>();
    private List<FileManagerInfo> listOther = new ArrayList<>();
    private  long sizeAll=0;
    private FileManager manager;
    private Handler handler = new Handler(){
      public void handleMessage(Message msg){
            switch (msg.what){
                case 0x01:
                    tv_size.setText(PublicUtils.formatSize(sizeAll));
                    break;
            }
      }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manage);
        list = new ArrayList<>();
        initView();
        listenerView();
        initData(0);
    }

    private void initView() {
        setActionBack("文件管理");
        lv =(ListView)findViewById(R.id.rubbish_list_view);
        progressBar = (ProgressBar)findViewById(R.id.rubbish_progress_bar);
        tv_title = (TextView)findViewById(R.id.file_manager_title);
        tv_size = (TextView)findViewById(R.id.file_manager_size);
        btn = (Button)findViewById(R.id.file_manager_btn);
    }
    private void listenerView() {
        lv.setOnItemClickListener(this);
        btn.setOnClickListener(this);
    }
    private void setData(File file){
        if (file != null) {
            if (file.isFile()) {
                String name = file.getName();
                int index = name.lastIndexOf(".");
                long size = file.length();
                sizeAll = sizeAll + size;
                Message message = Message.obtain();
                message.what = 0x01;
                handler.sendMessage(message);
                listAll.add(new FileManagerInfo(name, size,file));
            /* 获取文件的后缀名 */
                String end = name.substring(index + 1, name.length()).toLowerCase();
                if (end.equals("")) {
                    listOther.add(new FileManagerInfo(name, size,file));
                } else if (end.equals("txt") || end.equals("xml") || end.equals("js") || end.equals("css")) {
                    listTxt.add(new FileManagerInfo(name, size,file));
                } else if (end.equals("png") || end.equals("jpeg") || end.equals("jpg") || end.equals("gif")) {
                    listPng.add(new FileManagerInfo(name, size,file));
                } else if (end.equals("apk")) {
                    listApk.add(new FileManagerInfo(name, size,file));
                } else if (end.equals("mp4") || end.equals("fvi") || end.equals("3gp") || end.equals("lsf")) {
                    listMp4.add(new FileManagerInfo(name, size,file));
                } else if (end.equals("mp3") || end.equals("aif") || end.equals("s3m") || end.equals("tsi")) {
                    listMp3.add(new FileManagerInfo(name, size,file));
                } else if (end.equals("zip") || end.equals("jar") || end.equals("tar") || end.equals("rar")) {
                    listZip.add(new FileManagerInfo(name, size,file));
                } else {
                    listOther.add(new FileManagerInfo(name, size,file));
                }
            }
            //是目录的情况下 递归
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                if (files != null) {
                    for (File a : files) {
                        setData(a);
                    }
                }}}
        manager=FileManager.getManager();
        manager.setListTxt(listTxt);
        manager.setListPng(listPng);
        manager.setListApk(listApk);
        manager.setListMp4(listMp4);
        manager.setListMp3(listMp3);
        manager.setListZip(listZip);
        manager.setListOther(listOther);
    }
    private List<FileManagerInfo> findData(int id){

        String RomPath = null;
        if(id ==0){
            RomPath = MemoryManager.getInRomPath();
        }
        else{
            RomPath=MemoryManager.getOutRomPath();
            Log.i("AAA",RomPath+"aaa");
            if (RomPath==null||RomPath.equals("")){
                RomPath = MemoryManager.getInRomPath();
            }
        }
            File file = new File(RomPath);
            setData(file);


        long size7 =0,size1=0,size2=0,size3=0,size4=0,size5=0,size6=0;
        List<FileManagerInfo> info = new ArrayList<>();
        for (FileManagerInfo txt:listTxt) {
            size1 = size1+txt.fileSize;}
        for (FileManagerInfo png:listPng) {
            size2 = size2+png.fileSize;}
        for (FileManagerInfo apk:listApk) {
            size3 = size3+apk.fileSize;}
        for (FileManagerInfo mp4:listMp4) {
            size4 = size4+mp4.fileSize;}
        for (FileManagerInfo mp3:listMp3) {
            size5 = size5+mp3.fileSize;}
        for (FileManagerInfo zip:listZip) {
            size6 = size6+zip.fileSize;}
        for (FileManagerInfo other:listOther) {
            size7 = size7+other.fileSize;}
        info.add(new FileManagerInfo("文本文件",size1,file));
        info.add(new FileManagerInfo("图像文件",size2,file));
        info.add(new FileManagerInfo("APK文件",size3,file));
        info.add(new FileManagerInfo("视频文件",size4,file));
        info.add(new FileManagerInfo("音频文件",size5,file));
        info.add(new FileManagerInfo("压缩文件",size6,file));
        info.add(new FileManagerInfo("其它文件",size7,file));
        return  info;
    }
    private void initData(int number){
        final int id =number;
        new Thread(new Runnable() {
            public void run() {
                list=findData(id);
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new FileAdapter(list,FileManageActivity.this);
                        lv.setAdapter(adapter);
                        tv_title.setText("搜索完毕");
                        lv.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
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
            case R.id.file_manager_btn:
                btn.setClickable(false);
                tv_title.setText("深度搜索中...");
                sizeAll = 0;
                initData(1);
                btn.setClickable(true);
                break;

        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        FileManagerInfo info = adapter.getItem(position);
        long id =adapter.getItemId(position);
        long size =info.fileSize;
        Intent intent = new Intent();
        intent.setClass(this,FileResultActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("size",size);
        startActivity(intent);
    }
}
