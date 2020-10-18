package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class Grade extends AppCompatActivity {


    String nurseID=null,id=null,ed_name_chinese=null,ed_name_ec=null;
    Cursor cu;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"

        TextView nurse=findViewById(R.id.nurse_id);
        TextView patient=findViewById(R.id.patient_id);
        TextView title=findViewById(R.id.textView14);
        TextView patient_id=findViewById(R.id.tev_id);
        Intent intent=this.getIntent();
        nurseID=intent.getStringExtra("nurseID");
        id=intent.getStringExtra("id"); ;
        ed_name_chinese=intent.getStringExtra("ed_name_chinese");
        ed_name_ec=intent.getStringExtra("ed_name_ec");

        cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+nurseID+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String nurse_name=cu.getString(1);
            nurse.setText(nurse_name+" 登入");
        }
        cu = db.rawQuery("SELECT * FROM Patient WHERE patient_id='"+id+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String patient_name=cu.getString(1);
            patient.setText("姓名："+patient_name);
        }
        patient_id.setText(id);
        title.setText(ed_name_chinese);
    }
}
