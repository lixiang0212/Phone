package com.androidy.azsecuer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.adapter.ExpendAdapter;
import com.androidy.azsecuer.config.BatteryProgressBar;
import com.androidy.azsecuer.manager.PhoneTestManager;
import com.androidy.azsecuer.util.TestChildInfo;
import com.androidy.azsecuer.util.TestGroupInfo;

import java.util.ArrayList;

public class PhoneTestActivity extends ActionBarActivity {

    private BatteryProgressBar pb;
    private TextView tv;
    private MyReceiver myReceiver;
    private ExpandableListView lv;
    private ExpendAdapter adapter = null;
    private PhoneTestManager phoneTestManager;

    private class MyReceiver extends BroadcastReceiver{
        public void onReceive(Context context, Intent intent) {
            int max = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            int current = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            tv.setText(current + "%");
            pb.setMax(max);
            pb.setProgressReturn(current);
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_test);
        initView();
        initData();
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver,intentFilter);

    }

    private void initView() {
        String title = "手机检测";
        setActionBack(title);
        pb = (BatteryProgressBar) findViewById(R.id.phone_test_progressBar);
        tv = (TextView)findViewById(R.id.phone_test_text);
        lv =(ExpandableListView)findViewById(R.id.phone_test_list);
        adapter = new ExpendAdapter(this);
        lv.setAdapter(adapter);

    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_actionbar_launcher:
                finish();
                break;
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                phoneTestManager = new PhoneTestManager(PhoneTestActivity.this);
                //设备属性
                final ArrayList<TestChildInfo> list = phoneTestManager.getPhoneMessage();
                Drawable icon = getResources().getDrawable(R.drawable.setting_info_icon_version);
                final TestGroupInfo testGroupInfo = new TestGroupInfo(icon, "设备属性 ");
                //系统信息
                final ArrayList<TestChildInfo> list1 = phoneTestManager.getSystemMessage();
                Drawable icon1 = getResources().getDrawable(R.drawable.setting_info_icon_root);
                final TestGroupInfo testGroupInfo1 = new TestGroupInfo(icon1, "系统信息 ");
                //网络信息
                final ArrayList<TestChildInfo> list2 = phoneTestManager.getWIFIMessage(PhoneTestActivity.this);
                Drawable icon2 = getResources().getDrawable(R.drawable.setting_info_icon_cpu);
                final TestGroupInfo testGroupInfo2 = new TestGroupInfo(icon2, "网络信息 ");
                //相机信息
                final ArrayList<TestChildInfo> list3 = phoneTestManager.getCameraMessage();
                Drawable icon3 = getResources().getDrawable(R.drawable.setting_info_icon_camera);
                final TestGroupInfo testGroupInfo3 = new TestGroupInfo(icon3, "相机信息 ");
                //内存信息
                final ArrayList<TestChildInfo> list4 = phoneTestManager.getMemoryMessage();
                Drawable icon4 = getResources().getDrawable(R.drawable.setting_info_icon_space);
                final TestGroupInfo testGroupInfo4 = new TestGroupInfo(icon4, "内存信息 ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addData(testGroupInfo, list);
                        adapter.addData(testGroupInfo1, list1);
                        adapter.addData(testGroupInfo2, list2);
                        adapter.addData(testGroupInfo3, list3);
                        adapter.addData(testGroupInfo4, list4);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
