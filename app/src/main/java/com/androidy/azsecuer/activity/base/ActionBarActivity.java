package com.androidy.azsecuer.activity.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidy.azsecuer.R;

public abstract class ActionBarActivity extends Activity implements View.OnClickListener{

    private ImageView iv_left,iv_right;
    private TextView tv_title;

    public void findActionBar() throws NotFoundActionBarException {
        iv_left = (ImageView)findViewById(R.id.home_actionbar_launcher);
        iv_right = (ImageView)findViewById(R.id.home_actionbar_setting);
        tv_title =(TextView)findViewById(R.id.home_actionbar_title);
    }
    public class NotFoundActionBarException extends Exception{
        public NotFoundActionBarException() {
            super("NotFoundActionBar");
        }
    }
    protected void setActionBar(int left_icon, int right_icon, String title) {
        try {
            findActionBar();
        } catch (NotFoundActionBarException e) {
            e.printStackTrace();
        }
        if(left_icon==-1){
            iv_left.setVisibility(View.INVISIBLE);
        }
        else{
            iv_left.setImageResource(left_icon);
            iv_left.setOnClickListener(this);

        }
        if(right_icon==-1){
            iv_right.setVisibility(View.INVISIBLE);
        }
        else{
            iv_right.setImageResource(right_icon);
            iv_right.setOnClickListener(this);
        }
        if(title==null){
            tv_title.setVisibility(View.INVISIBLE);
        }
        else{
            tv_title.setText(title);
        }
    }
    public void setActionBack(String title){
        setActionBar(R.drawable.actionbar_fanhui,-1,title);
    }
    public void setActionHome(String title){
        setActionBar(R.drawable.ic_launcher,R.drawable.ic_child_configs,title);
    }
}
