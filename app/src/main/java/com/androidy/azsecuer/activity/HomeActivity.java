package com.androidy.azsecuer.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.util.SpeedInfo;
import com.androidy.azsecuer.view.MyProgressBar;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends ActionBarActivity implements View.OnClickListener{

    private MyProgressBar mb;
    private Button btn_speed;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        listenerView();
        initData();
    }
    public void initView() {
        String title = "手机管家";
        setActionHome(title);
        mb=(MyProgressBar)findViewById(R.id.home_progress);
        btn_speed=(Button)findViewById(R.id.home_speed_btn);
    }

    public void listenerView() {

        findViewById(R.id.home_rocket).setOnClickListener(this);
        findViewById(R.id.home_softmgr).setOnClickListener(this);
        findViewById(R.id.home_phonemgr).setOnClickListener(this);
        findViewById(R.id.home_telmgr).setOnClickListener(this);
        findViewById(R.id.home_filemgr).setOnClickListener(this);
        findViewById(R.id.home_sdclean).setOnClickListener(this);
        btn_speed.setOnClickListener(this);
    }
    private void initData(){
        ActivityManager am =(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info =new ActivityManager.MemoryInfo();
        am.getMemoryInfo(info);
        final long avail =info.availMem;//获取可用内存
        final long total = info.totalMem;//获取所有内存
        final long used = total - avail;
        final int progress = (int) ((float) used / (float) total * 100);//百分比
        mb.setMax(100);
        mb.startSetProgress1(progress);
    }
    private List<SpeedInfo> findData(){

        List<SpeedInfo> user= new ArrayList<>();
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = getPackageManager();
        //获取所有正在运行的程序
        List<ActivityManager.RunningAppProcessInfo> Infos =am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info:Infos) {
            String name = info.processName;//进程名称
            try {
                ApplicationInfo appInfo = pm.getApplicationInfo(name, 0);
                Drawable drawable = appInfo.loadIcon(pm);//图标
                String label = appInfo.loadLabel(pm).toString();// 标签
                int pid = info.pid;
                Debug.MemoryInfo memoryInfo = am.getProcessMemoryInfo(new int[] { pid })[0];
                long size = memoryInfo.getTotalPrivateDirty() * 1024;// 占用内存
                //保存用户进程
                if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    user.add(new SpeedInfo(name,drawable,size,label));
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            }
            return user;
    }
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.home_actionbar_launcher:

                break;
            case R.id.home_actionbar_setting:
                intent.setClass(this,SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.home_rocket:
                intent.setClass(this,PhoneSpeedActivity.class);
                startActivity(intent);
                break;
            case R.id.home_softmgr:
                intent.setClass(this,SoftWareManageActivity.class);
                startActivity(intent);
                break;
            case R.id.home_phonemgr:
                intent.setClass(this,PhoneTestActivity.class);
                startActivity(intent);
                break;
            case R.id.home_telmgr:
                intent.setClass(this,TelServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.home_filemgr:
                intent.setClass(this,FileManageActivity.class);
                startActivity(intent);
                break;
            case R.id.home_sdclean:
                intent.setClass(this, RubbishActivity.class);
                startActivity(intent);
                break;
            case R.id.home_speed_btn:
                btn_speed.setText("加速中...");
                new Thread(new Runnable() {
                    public void run() {
                        List<SpeedInfo> listClean = findData();
                        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
                        for (SpeedInfo info:listClean) {
                                String name = info.name;
                                am.killBackgroundProcesses(name);
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                               initData();
                                btn_speed.setText("手机加速");
                            }
                        });
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
