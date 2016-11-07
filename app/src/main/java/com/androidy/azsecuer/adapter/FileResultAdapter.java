package com.androidy.azsecuer.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.util.FileResultInfo;
import com.androidy.azsecuer.util.PublicUtils;
import com.androidy.azsecuer.util.RubbishInfo;
import java.util.ArrayList;
import java.util.List;

public class FileResultAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<FileResultInfo> list;

    public FileResultAdapter(Context context,List<FileResultInfo> list ) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }
    public class Temp{
        private CheckBox checkBox;
        private ImageView imageView;
        private TextView tv_name ,tv_time,tv_size;

    }
    public int getCount() {
        return list.size();
    }
    public List<FileResultInfo> getDataFromAdapter() {
        return list;
    }
    public FileResultInfo getItem(int position) {
        return list.get(position);
    }
    public long getItemId(int i) {
        return i;
    }
    public View getView(int position, View view, ViewGroup viewGroup) {
        Temp temp =null;
        if (view==null){
            temp = new Temp();
            view = inflater.inflate(R.layout.file_result_item,null);
            temp.checkBox =(CheckBox)view.findViewById(R.id.file_result_item_checkBox);
            temp.imageView =(ImageView)view.findViewById(R.id.file_result_item_icon);
            temp.tv_name =(TextView)view.findViewById(R.id.file_result_item_name);
            temp.tv_time =(TextView)view.findViewById(R.id.file_result_item_time);
            temp.tv_size =(TextView)view.findViewById(R.id.file_result_item_size);
            view.setTag(temp);
        }
        else {temp = (Temp) view.getTag();}

        temp.checkBox.setTag(position);
        temp.checkBox.setOnCheckedChangeListener(checkedChangeListener);
        final FileResultInfo Info =getItem(position);
        temp.checkBox.setChecked(Info.isSelect);
        temp.imageView.setImageDrawable(Info.drawable);
        temp.tv_name.setText(Info.fileName);
        temp.tv_time.setText(PublicUtils.formatDate(Info.file.lastModified()));
        temp.tv_size.setText(PublicUtils.formatSize(Info.size));
        temp.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri data = Uri.fromFile(Info.file);
                intent.setDataAndType(data,Info.openType);
                context.startActivity(intent);
            }
        });

        return view;
    }
    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // 当前你选中的这个实体里的isSelected = isChecked;
            // @see getView内,视图的数据效果都是根据实体数据来显示的
            int position = (Integer) buttonView.getTag();
            FileResultInfo Info = getItem(position);
            Info.isSelect= isChecked;
        }
    };
}

