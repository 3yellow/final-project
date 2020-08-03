package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

public class choose_education extends AppCompatActivity {
    SQLiteDatabase db;
    static final String Nurse="nurse"; //database table name
    static final String Patient="patient"; //database table name
    Cursor cu;

    String patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_education);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        Intent i=this.getIntent();
        String nurse_id=i.getStringExtra("nurse_name");
        TextView nurse=findViewById(R.id.tex_nurse_name);
        cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+nurse_id+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String nurse_name=cu.getString(1);
            nurse.setText(nurse_name+" _登入中...");
        }
        TextView patient = findViewById(R.id.tex_patient_name);
        patient_id=i.getStringExtra("id");
        cu = db.rawQuery("SELECT * FROM Patient WHERE patient_id='"+patient_id+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String patient_name=cu.getString(1);
            patient.setText("姓名："+patient_name);
        }
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(choose_education.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(choose_education.this,MainActivity.class);
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

    public void function(View v){
        Intent i=this.getIntent();
        int flag=i.getIntExtra("flag",0);
        if (flag==1){
             i=new Intent( this,fronttest.class);
             db.close();
            startActivity(i);
        }
        else {
             i=new Intent( this,kindney_function.class);
            db.close();
            startActivity(i);
        }
    }

    public void reason(View v){
        Intent i=this.getIntent();
        int flag=i.getIntExtra("flag",0);
        if (flag==1){
            i=new Intent( this,fronttest.class);
            db.close();
            startActivity(i);
        }
        else {
            i=new Intent( this,kidney_reason.class);
            db.close();
            startActivity(i);
        }
    }
}
