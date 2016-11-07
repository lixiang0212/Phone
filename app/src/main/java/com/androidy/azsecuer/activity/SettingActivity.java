package com.androidy.azsecuer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.base.ActionBarActivity;
import com.androidy.azsecuer.config.SharedPres;
import com.androidy.azsecuer.notification.MyNotification;

public class SettingActivity extends ActionBarActivity{

    private Switch sw_auto_start,sw_message_icon;
    private static Boolean flag,flag1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        listenerView();
    }

    private void initView() {
        String title = "系统设置";
        setActionBack(title);
        flag =SharedPres.get(this,"sw_auto_start");
        flag1 =SharedPres.get(this,"sw_message_icon");
        sw_auto_start= (Switch) findViewById(R.id.setting_sw_list1);
        sw_message_icon= (Switch) findViewById(R.id.setting_sw_list2);
        sw_auto_start.setChecked(flag);
        sw_message_icon.setChecked(flag1);
    }

    private void  listenerView(){
        sw_auto_start.setOnClickListener(this);
        sw_message_icon.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_actionbar_launcher:
                  finish();
                  break;
            case R.id.setting_sw_list1:
                  flag =!flag;
                  sw_auto_start.setChecked(flag);
                  SharedPres.save(this,"sw_auto_start",flag);
                  break;
            case R.id.setting_sw_list2:
                  flag1=!flag1;
                  sw_message_icon.setChecked(flag1);
                  SharedPres.save(this,"sw_message_icon",flag1);
                    if (flag1){
                        MyNotification.openNotification(this);
                    }
                    else{
                        MyNotification.closeNotification(this);
                    }
                  break;
        }
    }

}
