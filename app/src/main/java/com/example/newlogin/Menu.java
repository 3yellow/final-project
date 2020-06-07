package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Field;

public class Menu extends AppCompatActivity {
    TableLayout layout,layout2;
    TableRow row;
    TextView txv;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = openOrCreateDatabase("dbs", Context.MODE_PRIVATE, null);//創建資料庫
        layout2=findViewById(R.id.tbl);
        row=findViewById(R.id.tbr);
        createNurse();
        read();
    }

    private void createNurse()
    {
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
                db.insert("NurseDB",null,cv);
            }
        }
    }

    public void read()
    {
        String sql = "SELECT * FROM NurseDB";
        Cursor cu = db.rawQuery("SELECT * FROM NurseDB",null);
        //Cursor cu = db.rawQuery(sql,null);
        int i=0;
        if(cu.moveToFirst()) {
            do {
                //txv.append(cu.getString(1));
                final Button button = new Button(this);
                final Button btn_modify=new Button(this);
                final TableRow r=new TableRow(this);
                //  final ScrollView sc=new ScrollView(this);
                // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                r.setLayoutParams(new TableRow.LayoutParams(1520,80));
                button.setLayoutParams(new TableRow.LayoutParams(684,80));
                btn_modify.setLayoutParams(new TableRow.LayoutParams(120,80));
                button.setId(i);
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
                       // txv.setText(" "+button.getId());
                    }
                });
                btn_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(Menu.this,nurse_modify.class);
                        startActivity(i);
                    }
                });
                i++;
            }while(cu.moveToNext());
        }
    }

    public void nwedata(View v){
        Intent i=new Intent(Menu.this,Nurse_Newdata.class);
        startActivity(i);
    }
    public void test(View v){
        Intent i=new Intent(Menu.this,Test.class);
        startActivity(i);
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(Menu.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Menu.this,MainActivity.class);
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
