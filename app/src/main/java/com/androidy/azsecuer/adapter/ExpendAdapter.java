package com.androidy.azsecuer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.util.TestChildInfo;
import com.androidy.azsecuer.util.TestGroupInfo;
import java.util.ArrayList;
import java.util.HashMap;

public class ExpendAdapter extends BaseExpandableListAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TestGroupInfo> grouplist = new ArrayList<TestGroupInfo>();
    private HashMap<String, ArrayList<TestChildInfo>> childlist = new HashMap<String, ArrayList<TestChildInfo>>();

    public ExpendAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    public void addData(TestGroupInfo groupInfo,ArrayList<TestChildInfo> childInfos){
        grouplist.add(groupInfo);
        childlist.put(groupInfo.text,childInfos);
    }

    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int position) {
        return childlist.get(grouplist.get(position).text).size();
    }

    @Override
    public TestGroupInfo getGroup(int position) {
        return grouplist.get(position);
    }

    @Override
    public TestChildInfo getChild(int gPosition, int cPosition) {
        return childlist.get(grouplist.get(gPosition).text).get(cPosition);
    }

    @Override
    public long getGroupId(int gPosition) {
        return gPosition;
    }

    @Override
    public long getChildId(int gPosition, int cPosition) {
        return cPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean b, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.activity_phone_test_group,null);
        ImageView v = (ImageView) view.findViewById(R.id.phone_group_image);
        TextView tv = (TextView)view.findViewById(R.id.phone_group_text);
        TestGroupInfo testGroupInfo =getGroup(position);
        v.setImageDrawable(testGroupInfo.icon);
        tv.setText(testGroupInfo.text);
        return view;
    }

    @Override
    public View getChildView(int gPosition, int cPosition, boolean b, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_phone_test_child,null);
        TextView tv = (TextView) view.findViewById(R.id.phone_child_title);
        TextView tv1 = (TextView)view.findViewById(R.id.phone_child_text);

        TestChildInfo testChildInfo = getChild(gPosition,cPosition);
        tv.setText(testChildInfo.title);
        tv1.setText(testChildInfo.text);
        return view;
    }

    @Override
    public boolean isChildSelectable(int gPosition, int cPosition) {
        return false;
    }
}
