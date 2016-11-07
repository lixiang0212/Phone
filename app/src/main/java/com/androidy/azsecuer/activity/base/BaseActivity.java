package com.androidy.azsecuer.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.androidy.azsecuer.R;

public abstract class BaseActivity extends Activity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        listenerView();

    }

    public abstract void initView();
    public abstract void listenerView();

    protected void startAvtivity(Class intentclass){
        Intent intent = new Intent(this,intentclass);
        startActivity(intent);
    }
    protected void startAvtivity(Class intentclass,Bundle bundle){
        Intent intent = new Intent(this,intentclass);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
