package com.androidy.azsecuer.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.adapter.SpeedAdapter;
import com.androidy.azsecuer.config.BatteryProgressBar;
import com.androidy.azsecuer.util.PublicUtils;
import com.androidy.azsecuer.util.SpeedInfo;
import java.util.ArrayList;
import java.util.List;

public class PhoneSpeedActivity extends ActionBarActivity {

    private TextView tv_name,tv_size;
    private ListView lv;
    private Button btn_all,btn_clean;
    private BatteryProgressBar progressBar;//显示进度条
    private ProgressBar pb;//刷新进度条
    private List<SpeedInfo> list;
    private SpeedAdapter adapter;
    private CheckBox checkedAll;
    private boolean  flag=true;//是否显示隐藏进程
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_speed);
        initView();
        initSize();
        initData(1);//初始化用户进程
    }

    private void initView() {

        setActionBack("手机加速");
        tv_name = (TextView) findViewById(R.id.phone_speed_name);
        tv_name.setText(Build.MODEL.toUpperCase());
        lv = (ListView) findViewById(R.id.rubbish_list_view);
        tv_size = (TextView) findViewById(R.id.phone_speed_size);
        btn_all = (Button) findViewById(R.id.phone_speed_btn);
        btn_all.setOnClickListener(this);
        btn_clean = (Button) findViewById(R.id.phone_speed_clean);
        btn_clean.setOnClickListener(this);
        progressBar = (BatteryProgressBar) findViewById(R.id.phone_speed_progressBar);
        pb = (ProgressBar) findViewById(R.id.rubbish_progress_bar);
        checkedAll = (CheckBox) findViewById(R.id.phone_speed_cb);
        checkedAll.setOnCheckedChangeListener(changeListener);
    }

    private void initSize(){
        // 获取运行内存数据信息(全部，空闲可用，已使用)
        ActivityManager am =(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        final long avail = memoryInfo.availMem;//获取可用内存
        final long total = memoryInfo.totalMem;//获取所有内存
        final long used = total - avail;
        final int ratio = (int) ((float) used / (float) total * 100);//百分比
        progressBar.setMax(100);
        progressBar.setProgressReturn(ratio);
        StringBuilder str = new StringBuilder();
        str.append("可用内存:");
        str.append(PublicUtils.formatSize(avail));
        str.append("/");
        str.append(PublicUtils.formatSize(total));
        tv_size.setText(str.toString());
    }

    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton compoundButton, boolean selected) {
        if(compoundButton.isChecked()){
           lv.setVisibility(View.INVISIBLE);
           pb.setVisibility(View.VISIBLE);
           new Thread(new Runnable() {
               public void run() {
               for (SpeedInfo info:list) {
                    info.isSelected=true;
                    }
               runOnUiThread(new Runnable() {
               public void run() {
                    adapter.notifyDataSetChanged();
                    lv.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                    }
               });}}).start();}
        else{
            lv.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                public void run() {
                for (SpeedInfo info:list) {
                     info.isSelected=false;
                     }
                runOnUiThread(new Runnable() {
                public void run() {
                     adapter.notifyDataSetChanged();
                     lv.setVisibility(View.VISIBLE);
                     pb.setVisibility(View.INVISIBLE);
                     }
                });
            }}).start();}}};

    private List<SpeedInfo> findData(int number){

        List<SpeedInfo> user= new ArrayList<>();
        List<SpeedInfo> all= new ArrayList<>();
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager  pm = getPackageManager();
        //获取所有正在运行的程序
        List<ActivityManager.RunningAppProcessInfo> processInfos =am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info:processInfos) {
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
                //保存所有进程
                all.add(new SpeedInfo(name,drawable,size,label));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (number==0){
            return all;
        }
        else{
            return user;
        }
    }
    private void initData(int number){
        final int id = number;
        new Thread(new Runnable() {
        //加载用户进程
        public void run() {
            if (id==0){
                list = findData(0);
            }
            else{
                list=findData(1);
            }
            runOnUiThread(new Runnable() {
            public void run() {
                adapter = new SpeedAdapter(PhoneSpeedActivity.this,list);
                lv.setAdapter(adapter);
                lv.setVisibility(View.VISIBLE);
                pb.setVisibility(View.INVISIBLE);
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
            case R.id.phone_speed_btn:
                if(flag) {
                    lv.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.VISIBLE);
                    btn_all.setText("显示用户进程");
                    new Thread(new Runnable() {
                        public void run() {
                            initData(0);
                            flag=false;
                        }
                    }).start();
                }
                else {
                    lv.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.VISIBLE);
                    btn_all.setText("睡你MB,起来浪！");
                    new Thread(new Runnable() {
                        public void run() {
                            initData(1);
                            flag=true;
                        }
                    }).start();
                }
                break;
            case R.id.phone_speed_clean:
                lv.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.VISIBLE);
                 new Thread(new Runnable() {
                 public void run() {
                     List<SpeedInfo> listClean = new ArrayList<SpeedInfo>();
                     ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
                     listClean=adapter.getAdapter();
                     for (SpeedInfo info:listClean) {
                         if(info.isSelected){
                             String name = info.name;
                             am.killBackgroundProcesses(name);
                         }
                     }
                     runOnUiThread(new Runnable() {
                         public void run() {
                             adapter.notifyDataSetChanged();
                             initData(1);
                             initSize();
                             checkedAll.setChecked(false);
                         }
                     });
                     }
                 }).start();
                break;
        }
    }
}

