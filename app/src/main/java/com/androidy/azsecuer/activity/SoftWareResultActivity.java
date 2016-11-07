package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.adapter.SoftAdapter;
import com.androidy.azsecuer.util.SoftResultInfo;
import java.util.ArrayList;
import java.util.List;

public class SoftWareResultActivity extends ActionBarActivity {

    private ListView lv;
    private ProgressBar progressBar;
    private List<SoftResultInfo> list;
    private SoftAdapter adapter;
    private Button btn_clean;
    private int id=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_ware_result);
        list = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("resultCode",0);//默认的情况选择0
        if (id ==0){
            setActionBack("所有软件");
        }
        else if(id ==1){
            setActionBack("系统软件");
        }
        else{
            setActionBack("用户软件");
        }
        initView();
        listenerView();
        //initData(id);
    }
    protected void onResume() {
        super.onResume();
        // 异步加载数据(在此位置再加载数据是为了在卸载后能重新加载数据)
        initData(id);
    }
    private void initView() {
        lv=(ListView)findViewById(R.id.rubbish_list_view);
        progressBar=(ProgressBar)findViewById(R.id.rubbish_progress_bar);
        btn_clean=(Button)findViewById(R.id.soft_result_btn);

    }
    private void listenerView() {
        btn_clean.setOnClickListener(this);
    }

    private  List<SoftResultInfo> findData(int id){//方法1

        PackageManager pm = getPackageManager();
        List<SoftResultInfo> listAll = new ArrayList<>();
        List<SoftResultInfo> listSystem = new ArrayList<>();
        List<SoftResultInfo> listUser = new ArrayList<>();
        //int flag = PackageManager.GET_ACTIVITIES|PackageManager.GET_UNINSTALLED_PACKAGES;
        List<PackageInfo> infos = pm.getInstalledPackages(0);//0=Activity
        for (PackageInfo info:infos) {
            int flag = info.applicationInfo.flags;
            String label = info.applicationInfo.loadLabel(pm).toString();
            String packageName = info.packageName;
            String version = info.versionName;
            if(version.equals("4.4.4-eng.genymotion.20160608.190536")){
                version="4.4.4";
            }
            Drawable icon = info.applicationInfo.loadIcon(pm);
            if((flag& ApplicationInfo.FLAG_SYSTEM)==0){
                listUser.add(new SoftResultInfo(icon,label,packageName,version));
            }
            else{
                listSystem.add(new SoftResultInfo(icon,label,packageName,version));
            }
            listAll.add(new SoftResultInfo(icon,label,packageName,version));
         }
        if(id == 0) {
          return listAll;
        }
        else if(id == 1){
          return  listSystem;
        }
        else {
          return listUser;
        }
    }
//    private  List<SoftResultInfo> findData1(int id){
//
//        PackageManager pm = getPackageManager();
//        List<SoftResultInfo> listAll = new ArrayList<>();
//        List<SoftResultInfo> listSystem = new ArrayList<>();
//        List<SoftResultInfo> listUser = new ArrayList<>();
//        // 能过PackageManager查询到所有MAIN+LAUNCHER的应用程序
//        Intent mainIntent = new Intent();
//        mainIntent.setAction(Intent.ACTION_MAIN);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
//        for (ResolveInfo resolveInfo : resolveInfos) {
//            int flag = resolveInfo.activityInfo.applicationInfo.flags;
//            Drawable icon = resolveInfo.loadIcon(pm);
//            String label = resolveInfo.loadLabel(pm).toString();
//            String packageName = resolveInfo.activityInfo.applicationInfo.packageName;
//            String version = "";
//            try {
//                version = pm.getPackageInfo(packageName,0).versionName;
//            } catch (PackageManager.NameNotFoundException e) {
//                version = "?.?";
//            }
//
//            if((flag& ApplicationInfo.FLAG_SYSTEM)==0){
//                listUser.add(new SoftResultInfo(icon,label,packageName,version));
//            }
//            else{
//                listSystem.add(new SoftResultInfo(icon,label,packageName,version));
//            }
//            listAll.add(new SoftResultInfo(icon,label,packageName,version));
//        }
//        if(id == 0) {
//            return listAll;
//        }
//        else if(id == 1){
//            return  listSystem;
//        }
//        else {
//            return listUser;
//        }
//    }
    private void initData(int id){
        final int number =id;
        lv.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
        public void run() {
            list=findData(number);
            runOnUiThread(new Runnable() {
        public void run() {
            adapter= new SoftAdapter(SoftWareResultActivity.this,list);
            lv.setAdapter(adapter);
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
            case R.id.soft_result_btn:
                List<SoftResultInfo> selectList = new ArrayList<>();
                selectList = adapter.getAdapterList();
                for (SoftResultInfo info:selectList) {
                    if (info.isSeleced){
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + info.packageName));
                        startActivity(intent);
                    }
                }
                break;
        }
    }
}
