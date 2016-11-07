package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.config.BatteryProgressBar;
import com.androidy.azsecuer.manager.MemoryManager;
import com.androidy.azsecuer.util.PublicUtils;
import com.androidy.azsecuer.view.MyProgressBar;

import java.io.File;

public class SoftWareManageActivity extends ActionBarActivity {

    private BatteryProgressBar progressBar_nei, progressBar_wai;
    private TextView tv_nei_size,tv_wai_size,tv_nei,tv_wai;
    private MyProgressBar mb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_ware_manage);
        initView();
        listenerView();
    }
    protected void onResume() {
        super.onResume();
        // 异步加载数据(在此位置再加载数据是为了在卸载后能重新加载数据)
        initProgressBar();
    }
    private void initView() {
        setActionBack("软件管理");
        progressBar_nei =(BatteryProgressBar)findViewById(R.id.soft_progress_nei);
        progressBar_wai =(BatteryProgressBar)findViewById(R.id.soft_progress_wai);
        tv_nei_size =(TextView)findViewById(R.id.soft_progress_size);
        tv_wai_size=(TextView)findViewById(R.id.soft_progress_size2);
        tv_nei=(TextView)findViewById(R.id.soft_circle_sizeIn);
        tv_wai=(TextView)findViewById(R.id.soft_circle_sizeOut);
        mb=(MyProgressBar)findViewById(R.id.soft_manager_progress);
        progressBar_nei.setMax(100);
        progressBar_wai.setMax(100);

    }
    private void listenerView() {
        findViewById(R.id.soft_manager_all).setOnClickListener(this);
        findViewById(R.id.soft_manager_system).setOnClickListener(this);
        findViewById(R.id.soft_manager_user).setOnClickListener(this);
    }

    private void initProgressBar(){

        File file_in = Environment.getDataDirectory();
        StatFs sf_in = new StatFs(file_in.getPath());
        //内置空间
        long total_in =sf_in.getTotalBytes();
        long avai_in =sf_in.getAvailableBytes();
        //外置空间
        File file_out =Environment.getExternalStorageDirectory();
        StatFs sf_out = new StatFs(file_out.getPath());
        long total_out = sf_out.getTotalBytes();
        long avai_out = sf_out.getAvailableBytes();
        // 分别计算出内置和外置已使用所占比例(进度条)
        int inRom = (int) ((float)(total_in-avai_in)/ total_in* 100);
        int outRom = (int) ((float)(total_out-avai_out)/ total_out * 100);
        //加载数据
        progressBar_nei.setProgressReturn(inRom);
        progressBar_wai.setProgressReturn(outRom);
        tv_nei_size.setText(PublicUtils.formatSize(total_in-avai_in)+"/"+PublicUtils.formatSize(total_in));
        tv_wai_size.setText(PublicUtils.formatSize(total_out-avai_out)+"/"+PublicUtils.formatSize(total_out));
        tv_nei.setText(PublicUtils.formatSize(total_in));
        tv_wai.setText(PublicUtils.formatSize(total_out));
        mb.setMax(100);
        int a =(int)(((float)total_out/(float)(total_in+total_out))*100);
        mb.startSetProgress1(a);
    }
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.home_actionbar_launcher:
                finish();
                break;
            case R.id.soft_manager_all:
                intent.setClass(this,SoftWareResultActivity.class);
                intent.putExtra("resultCode",0);
                startActivity(intent);
                break;
            case R.id.soft_manager_system:
                intent.setClass(this,SoftWareResultActivity.class);
                intent.putExtra("resultCode",1);
                startActivity(intent);
                break;
            case R.id.soft_manager_user:
                intent.setClass(this,SoftWareResultActivity.class);
                intent.putExtra("resultCode",2);
                startActivity(intent);
                break;
        }
    }
}
