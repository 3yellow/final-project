package com.example.newlogin;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Searchlogin extends AppCompatActivity {

    Intent intent = new Intent();
    TableLayout layout,layout2;
    TableRow row;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlogin);

        TextView user=(TextView)findViewById(R.id.textView);
     //   String name=getIntent().getStringExtra("name").toString();
        user.setText("登入中...");


        db = openOrCreateDatabase("dbs", Context.MODE_PRIVATE, null);
        layout2=findViewById(R.id.tbl);
        row=findViewById(R.id.tbr);
        creat();
        read();
       // ListView listView = (ListView) findViewById(R.id.ListView01);

        // 定义一个List集合
        final List<String> components = new ArrayList<>();
        components.add("TextView");
        components.add("EditText");
        components.add("Button");
        components.add("CheckBox");
        components.add("RadioButton");
        components.add("ToggleButton");
        components.add("ImageView");

        // 将List包装成ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.custom_item, R.id.content_tv, components);

        // 为ListView设置Adapter
        //listView.setAdapter(adapter);

        // 为ListView列表项绑定点击事件监听器
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(Searchlogin.this, components.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public void insertpaient(View v){
        intent.setClass(this,Newdata.class);
        startActivity(intent);
    }

    public void creat(){
        String[] nurseid = {"admin","001","222","333"};
        String[] name = {"Admin","Yumi","222","333"};
        String[] pass = {"admin","001","222","333"};
        String sql = "CREATE TABLE IF NOT EXISTS NurseDB ( NurseId TEXT PRIMARY KEY, Name TEXT , Password TEXT , Status INT )";
        db.execSQL(sql);
        ContentValues cv = new ContentValues(4);

        Cursor cu = db.rawQuery("SELECT * FROM NurseDB",null);
        if(!cu.moveToFirst())
        {
            for(int i=0 ; i<4 ; i++)
            {
                cv.put("NurseId",nurseid[i]);
                cv.put("Name",name[i]);
                cv.put("Password",pass[i]);
                cv.put("Status",1);
                db.insert("db_nurse",null,cv);
            }
            Toast.makeText(this,"done",Toast.LENGTH_LONG).show();
        }
    }
    public void read( ) {
        String sql = "SELECT * FROM NurseDB";
        Cursor cu = db.rawQuery("SELECT * FROM NurseDB",null);
        int i=0;
        if(cu.moveToFirst()) {
            do {
                final Button button = new Button(this);
                final Button btn_modify=new Button(this);
                final TableRow r=new TableRow(this);
                //str= cu.getString(0)+"\t"+cu.getString(1)+"\t"+cu.getString(3);
                r.setLayoutParams(new TableRow.LayoutParams(1520,80));
                button.setLayoutParams(new TableRow.LayoutParams(684,80));
                btn_modify.setLayoutParams(new TableRow.LayoutParams(120,80));
                button.setId(i);
                //button.setText(str);
                btn_modify.setId(i+i);
                button.setTextSize(35);
                button.setText("B"+i);
                // la.addView(layout2);
                btn_modify.setTextSize(35);
                btn_modify.setText("修改");

                r.addView(button);//yout
                r.addView(btn_modify);//yout2
                layout2.addView(r);
                button.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent i=new Intent(Searchlogin.this,choose_education.class);
                        startActivity(i);
                    }
                });
                btn_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(Searchlogin.this,Newdata.class);
                        startActivity(i);
                    }
                });
                i++;
            }while(cu.moveToNext());
        }
        else
            Toast.makeText(this,"error",Toast.LENGTH_LONG).show();
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(Searchlogin.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Searchlogin.this,MainActivity.class);
                        startActivity(i);
                    }
                }).setNegativeButton("取消",null).create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(26);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(26);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            //通过反射修改title字体大小和颜色
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(32);
            mTitleView.setTextColor(Color.RED);
            //通过反射修改message字体大小和颜色
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }
}
