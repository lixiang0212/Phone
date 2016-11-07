package com.androidy.azsecuer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.util.User;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private List<User> list ;
    private Context context;
    private LayoutInflater inflater;

    public UserAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    class Temp{
        private TextView tv_id,tv_name,tv_passwd;
    }

    public int getCount() {
        return list.size();
    }

    public User getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        Temp temp = null;
        if (view==null){
            temp=new Temp();
            view = inflater.inflate(R.layout.activity_test_list,null);
            temp.tv_id =(TextView)view.findViewById(R.id.test_list_one);
            temp.tv_name =(TextView)view.findViewById(R.id.test_list_name);
            temp.tv_passwd =(TextView)view.findViewById(R.id.test_list_pd);
            view.setTag(temp);
        }
        else {
            temp =(Temp)view.getTag();
        }
        User user = list.get(position);
        temp.tv_id.setText(user.getId()+"");
        temp.tv_name.setText(user.getName());
        temp.tv_passwd.setText(user.getPasswd());
        return view;
    }
}
