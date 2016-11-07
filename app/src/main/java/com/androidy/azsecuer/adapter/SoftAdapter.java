package com.androidy.azsecuer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.util.SoftResultInfo;
import java.util.ArrayList;
import java.util.List;

public class SoftAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    private List<SoftResultInfo> list;

    public SoftAdapter(Context context, List<SoftResultInfo> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }
    public class Temp{
        private CheckBox checkBox;
        private ImageView imageView;
        private TextView label,packageName,version;
    }
    public List<SoftResultInfo> getAdapterList(){
        return list;
    }

    public int getCount() {
        return list.size();
    }

    public SoftResultInfo getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        Temp temp =null;
        if (view==null){
            temp = new Temp();
            view = inflater.inflate(R.layout.soft_ware_list,null);
            temp.checkBox =(CheckBox)view.findViewById(R.id.soft_list_checkBox);
            temp.imageView=(ImageView)view.findViewById(R.id.soft_list_icon);
            temp.label = (TextView)view.findViewById(R.id.soft_list_name);
            temp.packageName = (TextView)view.findViewById(R.id.soft_list_package);
            temp.version = (TextView)view.findViewById(R.id.soft_list_version);
            view.setTag(temp);
        }
        else { temp=(Temp)view.getTag();}
            temp.checkBox.setTag(position);
            temp.checkBox.setOnCheckedChangeListener(listener);
            SoftResultInfo info = getItem(position);
            temp.checkBox.setChecked(info.isSeleced);
            temp.imageView.setImageDrawable(info.drawable);
            temp.label.setText(info.label);
            temp.packageName.setText(info.packageName);
            temp.version.setText(info.version);

        return view;
    }
    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton compoundButton, boolean isSelected) {
            int position = (Integer)compoundButton.getTag();
            SoftResultInfo info = getItem(position);
            info.isSeleced=isSelected;
        }
    };




}
