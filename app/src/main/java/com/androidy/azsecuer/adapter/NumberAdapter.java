package com.androidy.azsecuer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.util.TelNumberInfo;

import java.util.List;

public class NumberAdapter extends BaseAdapter {

    private List<TelNumberInfo> list ;
    private Context context;
    private LayoutInflater inflater;

    public NumberAdapter(List<TelNumberInfo> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }
    class Temp{
        private TextView tv,tv1;
    }

    public int getCount() {
        return list.size();
    }

    public TelNumberInfo getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        Temp temp =null;
        if (view==null){
            temp = new Temp();
            view = inflater.inflate(R.layout.activity_tel_number_list,null);
            temp.tv = (TextView)view.findViewById(R.id.tel_number_name);
            temp.tv1 = (TextView)view.findViewById(R.id.tel_number_number);
            view.setTag(temp);
        }
        else{temp =(Temp)view.getTag();}
        TelNumberInfo data = getItem(position);
        temp.tv.setText(data.name);
        temp.tv1.setText(data.number);
        return view;
    }
}
