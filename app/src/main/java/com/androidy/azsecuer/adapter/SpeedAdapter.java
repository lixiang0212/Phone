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
import com.androidy.azsecuer.util.PublicUtils;
import com.androidy.azsecuer.util.SpeedInfo;
import java.util.ArrayList;
import java.util.List;

public class SpeedAdapter  extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<SpeedInfo> list;

    public SpeedAdapter(Context context, List<SpeedInfo> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    public class Temp {
        private CheckBox checkBox;
        private ImageView imageView;
        private TextView name, size;
    }
    public  List<SpeedInfo> getAdapter(){
        return list;
    }
    public int getCount() {
        return list.size();
    }

    public SpeedInfo getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        Temp temp = null;
        if (view == null) {
            temp = new Temp();
            view = inflater.inflate(R.layout.activity_phone_speed_item, null);
            temp.checkBox = (CheckBox) view.findViewById(R.id.speed_item_checkBox);
            temp.imageView = (ImageView) view.findViewById(R.id.speed_item_iView);
            temp.name = (TextView) view.findViewById(R.id.speed_item_name);
            temp.size = (TextView) view.findViewById(R.id.speed_item_size);
            view.setTag(temp);
        } else {
            temp = (Temp) view.getTag();
        }
        temp.checkBox.setTag(position);
        temp.checkBox.setOnCheckedChangeListener(listener);
        SpeedInfo speedInfo = getItem(position);
        temp.checkBox.setChecked(speedInfo.isSelected);
        temp.imageView.setImageDrawable(speedInfo.drawable);
        temp.name.setText(speedInfo.lable);
        temp.size.setText(PublicUtils.formatSize(speedInfo.size));

        return view;
    }
    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton compoundButton, boolean isSelect) {
            int position = (Integer)compoundButton.getTag();
            SpeedInfo speedInfo = getItem(position);
            speedInfo.isSelected = isSelect;
        }
    };
}
