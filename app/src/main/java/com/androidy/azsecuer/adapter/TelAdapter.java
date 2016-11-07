package com.androidy.azsecuer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.util.TelServiceInfo;

import java.util.ArrayList;
import java.util.List;

public class TelAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<TelServiceInfo> data =new ArrayList<TelServiceInfo>();
    public TelAdapter(Context context,List<TelServiceInfo> data) {
        this.context =context;
        this.data = data;
        inflater =LayoutInflater.from(context);
    }
    class Temp{
        private TextView tv;
    }

    public int getCount() {
        return data.size();
    }

    public TelServiceInfo getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        Temp temp =null;
        if (view==null){
            temp = new Temp();
            view = inflater.inflate(R.layout.activity_tel_service_list,null);
            temp.tv = (TextView)view.findViewById(R.id.tel_list_text);
            view.setTag(temp);
        }
        else{temp =(Temp)view.getTag();}
        TelServiceInfo name =getItem(position);
        temp.tv.setText(name.name);
        return view;
    }
}
