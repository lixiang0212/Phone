package com.androidy.azsecuer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.util.FileManagerInfo;
import com.androidy.azsecuer.util.PublicUtils;
import java.util.List;

public class FileAdapter extends BaseAdapter {

        private List<FileManagerInfo> list;
        private LayoutInflater inflater;

        public FileAdapter(List<FileManagerInfo> list, Context context) {
            this.list = list;
            inflater=LayoutInflater.from(context);
        }
        class Temp{
            private TextView tv,tv1;
            private ImageView iv;
            private ProgressBar progressBar;
        }
        public List<FileManagerInfo> getAdapter(){
            return list;
        }
        public int getCount() {
            return list.size();
        }

        public FileManagerInfo getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View view, ViewGroup viewGroup) {
            Temp temp =null;
            if (view==null){
                temp = new Temp();
                view = inflater.inflate(R.layout.file_manager_item,null);
                temp.tv = (TextView)view.findViewById(R.id.file_list_name);
                temp.tv1 = (TextView)view.findViewById(R.id.file_list_size);
                temp.iv = (ImageView)view.findViewById(R.id.file_list_iamge);
                temp.progressBar =(ProgressBar)view.findViewById(R.id.file_list_progressBar);
                view.setTag(temp);
            }
            else{temp =(Temp)view.getTag();}
            FileManagerInfo data = getItem(position);
            temp.tv.setText(data.fileKind);
            temp.tv1.setText(PublicUtils.formatSize(data.fileSize));
            temp.iv.setVisibility(View.VISIBLE);
            temp.progressBar.setVisibility(View.INVISIBLE);
            return view;
        }
    }

