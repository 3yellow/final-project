package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class Grade extends AppCompatActivity {

  //  String nurseID=null,id=null,ed_name_chinese=null,ed_name_ec=null;
    Cursor cu;
    SQLiteDatabase db;
    TextView txv_1_date,txv_1_time,txv_1_nurse,txv_2_date,txv_2_time,txv_2_nurse,txv_3_date,txv_3_time,txv_3_nurse,txv_4_date,txv_4_time,txv_4_nurse,txv_5_date,txv_5_time,txv_5_nurse;
    Button btn_1_score,btn_2_score,btn_3_score,btn_4_score,btn_5_score;
    //int count=0;//計數有幾張考卷

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"

        txv_1_date=findViewById(R.id.txv_1_date);
        txv_1_time=findViewById(R.id.txv_1_time);
        txv_1_nurse=findViewById(R.id.txv_1_nurse);
        btn_1_score=findViewById(R.id.btn_1_score);
        txv_2_date=findViewById(R.id.txv_2_date);
        txv_2_time=findViewById(R.id.txv_2_time);
        txv_2_nurse=findViewById(R.id.txv_2_nurse);
        btn_2_score=findViewById(R.id.btn_2_score);
        txv_3_date=findViewById(R.id.txv_3_date);
        txv_3_time=findViewById(R.id.txv_3_time);
        txv_3_nurse=findViewById(R.id.txv_3_nurse);
        btn_3_score=findViewById(R.id.btn_3_score);
        txv_4_date=findViewById(R.id.txv_4_date);
        txv_4_time=findViewById(R.id.txv_4_time);
        txv_4_nurse=findViewById(R.id.txv_4_nurse);
        btn_4_score=findViewById(R.id.btn_4_score);
        txv_5_date=findViewById(R.id.txv_5_date);
        txv_5_time=findViewById(R.id.txv_5_time);
        txv_5_nurse=findViewById(R.id.txv_5_nurse);
        btn_5_score=findViewById(R.id.btn_5_score);
        TextView nurse=findViewById(R.id.nurse_id);
        TextView patient=findViewById(R.id.patient_id);
        TextView title=findViewById(R.id.textView14);
        TextView patient_id=findViewById(R.id.tev_id);
        Intent intent=this.getIntent();
        String nurseID=intent.getStringExtra("nurseID");
        String id=intent.getStringExtra("id"); ;
        String ed_name_chinese=intent.getStringExtra("ed_name_chinese");
        String ed_name_ec=intent.getStringExtra("ed_name_ec");
        int count=0;//計數有幾張考卷

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
        Log.e("Grade",ed_name_ec+id);
        cu=db.rawQuery("SELECT * FROM exam_id LIKE '"+ed_name_ec+id+"%'",null);
        Log.e("Grade","76");
        if (cu.getCount()>0){
            cu.moveToFirst();
            count=cu.getCount();
        }

    }

    public void show_five_grade(String ed_name_ec,String id,int count){

    }
}
