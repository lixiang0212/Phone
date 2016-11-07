package com.androidy.azsecuer.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidy.azsecuer.R;

public class AA extends AppCompatActivity {

    private Button button,button1;
    private EditText et,et1;
    private ProgressBar progressBar;
    private Handler handler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        initView();
        handler=new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 0x01:
                        progressBar.setProgress(msg.arg1);
                        break;
                    case 0x02:
                        Toast.makeText(AA.this,"OK",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    private void initView() {
//
    }
}
