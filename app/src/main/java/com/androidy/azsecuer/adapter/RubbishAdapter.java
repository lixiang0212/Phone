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
import com.androidy.azsecuer.util.RubbishInfo;

import java.util.ArrayList;
import java.util.List;

public class RubbishAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<RubbishInfo> list;

    public RubbishAdapter(Context context,List<RubbishInfo> list ) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }
    public class Temp{
        private CheckBox checkBox;
        private ImageView imageView;
        private TextView textView ,textView1;

    }
    public int getCount() {
        return list.size();
    }
    public List<RubbishInfo> getDataFromAdapter() {
        return list;
    }
    public RubbishInfo getItem(int position) {
        return list.get(position);
    }
    public long getItemId(int i) {
        return i;
    }
    public View getView(int position, View view, ViewGroup viewGroup) {
        Temp temp =null;
        if (view==null){
            temp = new Temp();
            view = inflater.inflate(R.layout.activity_rubbish_list,null);
            temp.checkBox =(CheckBox)view.findViewById(R.id.rubbish_checkBox);
            temp.imageView =(ImageView)view.findViewById(R.id.rubbish_iView);
            temp.textView =(TextView)view.findViewById(R.id.rubbish_name);
            temp.textView1 =(TextView)view.findViewById(R.id.rubbish_size);
            view.setTag(temp);
        }
        else {temp = (Temp) view.getTag();}

        temp.checkBox.setTag(position);
        temp.checkBox.setOnCheckedChangeListener(checkedChangeListener);
        RubbishInfo rubbishInfo =getItem(position);
        temp.checkBox.setChecked(rubbishInfo.ischeckd);
        temp.imageView.setImageDrawable(rubbishInfo.drawable);
        temp.textView.setText(rubbishInfo.name);
        temp.textView1.setText(PublicUtils.formatSize(rubbishInfo.size));

        return view;
    }
    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // 当前你选中的这个实体里的isSelected = isChecked;
            // @see getView内,视图的数据效果都是根据实体数据来显示的
            int position = (Integer) buttonView.getTag();
            RubbishInfo rubbishInfo = getItem(position);
            rubbishInfo.ischeckd= isChecked;
        }
    };
}
