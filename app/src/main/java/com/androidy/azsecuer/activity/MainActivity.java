package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.manager.DBFileManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView iv1,iv2,iv3,iv4,iv5,iv6;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = (ImageView) findViewById(R.id.welcome_icon1);
//        iv2 = (ImageView) findViewById(R.id.welcome_icon2);
//        iv3 = (ImageView) findViewById(R.id.welcome_icon3);
//        iv4 = (ImageView) findViewById(R.id.welcome_icon4);
//        iv5 = (ImageView) findViewById(R.id.welcome_icon5);
//        iv6 = (ImageView) findViewById(R.id.welcome_icon6);

        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.icon1);
//        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.icon2);
//        Animation animation3 = AnimationUtils.loadAnimation(this,R.anim.icon3);
//        Animation animation4 = AnimationUtils.loadAnimation(this,R.anim.icon4);
//        Animation animation5 = AnimationUtils.loadAnimation(this,R.anim.icon5);
//        Animation animation6 = AnimationUtils.loadAnimation(this,R.anim.icon6);
        animation1.setFillAfter(true);
//        animation2.setFillAfter(true);
//        animation3.setFillAfter(true);
//        animation4.setFillAfter(true);
//        animation5.setFillAfter(true);
//        animation6.setFillAfter(true);
        iv1.startAnimation(animation1);
//        iv2.startAnimation(animation2);
//        iv3.startAnimation(animation3);
//        iv4.startAnimation(animation4);
//        iv5.startAnimation(animation5);
//        iv6.startAnimation(animation6);
        animation1.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {}

            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }

            public void onAnimationRepeat(Animation animation) {}
        });
        try {
            DBFileManager.copyFile(this,"clearpath.db","clearpath.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            DBFileManager.copyFile(this,"commonnum.db","commonnum.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
