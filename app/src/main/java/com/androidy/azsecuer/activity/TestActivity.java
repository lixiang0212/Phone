package com.androidy.azsecuer.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.androidy.azsecuer.R;
import com.androidy.azsecuer.sql.SQLite;
import com.androidy.azsecuer.adapter.UserAdapter;
import com.androidy.azsecuer.util.User;

import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ListView listView;
    private EditText et_name,et_pd,et_id;
    private Button bt_add,bt_delete,bt_update,bt_select,bt_query;
    private List<User> list;
    private UserAdapter adapter;
    private SQLite sql;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        sql=new SQLite(this);

        initView();
        listenerView();
    }

    private void initView() {
        listView =(ListView)findViewById(R.id.test_list_view);
        et_name = (EditText)findViewById(R.id.test_et_name);
        et_pd= (EditText)findViewById(R.id.test_et_pd);
        et_id= (EditText)findViewById(R.id.test_et_id);
        bt_add = (Button)findViewById(R.id.test_bt_add);
        bt_delete = (Button)findViewById(R.id.test_bt_delete);
        bt_update = (Button)findViewById(R.id.test_bt_update);
        bt_select= (Button)findViewById(R.id.test_bt_select);
        bt_query = (Button)findViewById(R.id.test_bt_now);
        list=sql.queryAll();
        adapter = new UserAdapter(list,this);
        listView.setAdapter(adapter);
    }
    private void listenerView() {
        bt_add.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_select.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        final int i = position;
        User user = adapter.getItem(position);
        String name = user.name;
        String passwd = user.passwd;
        final long id = user.id;
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.tel_people_icon)
                .setTitle(name)
                .setMessage(passwd)
                .setNegativeButton("取消",null)
                .setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int position) {
                        list.remove(i);
                        sql.delete(id);
                        adapter.notifyDataSetChanged();

                        Toast.makeText(TestActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("确定",null)
                .show();

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.test_bt_add:
                if (et_name.getText().toString().equals("")|et_pd.getText().toString().equals("")){
                    Toast.makeText(this,"请输入有效的姓名和密码！",Toast.LENGTH_SHORT).show();
                    et_name.setText("");
                    et_pd.setText("");
                    return;
                } else {
                    sql.insert(new User(et_name.getText().toString(), et_pd.getText().toString()));
                    Toast.makeText(this,"添加成功！",Toast.LENGTH_SHORT).show();
                    et_name.setText("");
                    et_pd.setText("");
                }
                break;
            case R.id.test_bt_delete:
                if(et_id.getText().toString().equals("")){
                    Toast.makeText(this,"请输入有效id！",Toast.LENGTH_SHORT).show();
                    et_id.setText("");
                    return;
                }else {
                    long id = Long.parseLong(et_id.getText().toString());
                    sql.delete(id);
                    Toast.makeText(this,"删除成功！",Toast.LENGTH_SHORT).show();
                    et_id.setText("");
                }
                break;
            case R.id.test_bt_update:
                if(et_name.getText().toString().equals("")|
                   et_pd.getText().toString().equals("")|et_id.getText().toString().equals("")){
                    Toast.makeText(this,"请输入有效id和姓名，密码！",Toast.LENGTH_SHORT).show();
                    et_name.setText("");
                    et_pd.setText("");
                    et_id.setText("");
                    return;
                }else {
                    long id = Long.parseLong(et_id.getText().toString());
                    sql.update(new User(et_name.getText().toString(), et_pd.getText().toString()),id);
                    Toast.makeText(this,"更新成功！",Toast.LENGTH_SHORT).show();
                    et_name.setText("");
                    et_pd.setText("");
                    et_id.setText("");
                }
                break;
            case R.id.test_bt_select:
                if(et_id.getText().toString().equals("")){
                    Toast.makeText(this,"请输入有效的id！",Toast.LENGTH_SHORT).show();
                    et_name.setText("");
                    et_pd.setText("");
                    et_id.setText("");
                    return;
                }else {
                    long id = Long.parseLong(et_id.getText().toString());
                    User user=sql.select(id);
                    Toast.makeText(this,"查找成功！",Toast.LENGTH_SHORT).show();
                    et_name.setText(user.getName());
                    et_pd.setText(user.getPasswd());
                    et_id.setText(user.getId()+"");
                }
                break;
            case R.id.test_bt_now:
                    list=sql.queryAll();
                    adapter = new UserAdapter(list,this);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    et_name.setText("");
                    et_pd.setText("");
                    et_id.setText("");
                break;
        }
    }
}
