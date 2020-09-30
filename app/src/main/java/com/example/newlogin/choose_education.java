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
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class choose_education extends AppCompatActivity {
    SQLiteDatabase db;
    static final String Nurse="nurse"; //database table name
    static final String Patient="patient"; //database table name
    TextView kindney_reason_date,kindney_reason_grade;
    TextView kindney_function_date,kindney_function_grade;
    Cursor cu;
    String nurseID;
    String id;
    //String exam_id="kidney_reason"+id+count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_education);
        kindney_reason_date=findViewById(R.id.What_is_chronic_kidney_disease_date);
        kindney_reason_grade=findViewById(R.id.What_is_chronic_kidney_disease_grade);

        TextView nurse=findViewById(R.id.tex_nurse_name);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        Intent i=this.getIntent();
        nurseID=i.getStringExtra("nurseID");
        cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+nurseID+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String nurse_name=cu.getString(1);
            nurse.setText(nurse_name+" _登入中...");
        }
        TextView patient = findViewById(R.id.tex_patient_name);
        id=i.getStringExtra("id");
        cu = db.rawQuery("SELECT * FROM Patient WHERE patient_id='"+id+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String patient_name=cu.getString(1);
            patient.setText("姓名："+patient_name);
        }
        show_kidney_reason( id,"kidney_reason");
        show_kindney_function( id,"kindney_function");
    }

    public String[] choi_Q()//隨機產生5題題目
    {
        int [] array;
        String []Q_array=new String[5];
        int count=0,total_Q=0;
        cu = db.rawQuery("SELECT * FROM Question ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            total_Q=cu.getCount();
        }
        array=new int [5];
        while (count<5){
            int num=(int)(Math.random()*(total_Q))+1;
            boolean flag=true;
            for (int j=0;j<5;j++){
                if (num==array[j]){
                    flag=false;
                    break;
                }
            }
            if (flag){
                array[count]=num;
                Q_array[count]=num+"";
                count++;
            }
        }
        return Q_array;
    }

    public void show_kindney_function(String id,String s_p){
        kindney_function_date=findViewById(R.id.kindney_function_date);
        kindney_function_grade=findViewById(R.id.kindney_function_grade);
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            kindney_function_date.setText("  "+date);
            kindney_function_grade.setText(" "+grade);
        }
        else {
            kindney_function_date.setText(" EORROR");
            kindney_function_grade.setText(" EORROR");
        }
    }
    public void show_kidney_reason(String id,String s_p){
        //patient_name LIKE '"+s_p+"%'";
        //String exam_id="kidney_reason"+id+count;
        int count=0;
        String str=s_p+id;
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+str+"%'",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            count=cu.getCount();
        }
        count=count-1;
        String exam_id=s_p+id+count;
        cu=db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"' ",null);
        if (cu.getCount()>0){
            cu.moveToFirst();
            String date=cu.getString(1);
            String grade=cu.getString(2);
            kindney_reason_date.setText("  "+date);
            kindney_reason_grade.setText(" "+grade);
        }
        else {
            kindney_reason_date.setText(" ERROR!!");
            kindney_reason_grade.setText(" ERROR!!");
        }
    }

    private void insertExam(String exam_id ,String nurse_id,  String patient_id){
      //  exam_id TEXT, exam_date DateTime, exam_score INT,question_id_1 TEXT,question_id_2 TEXT,question_id_3 TEXT,question_id_4 TEXT,question_id_5 TEXT, patient_id TEXT, nurse_id TEXT
        // ==格式化
        SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd");
        //==GMT標準時間往後加八小時
        nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //==取得目前時間
        String exam_date = nowdate.format(new java.util.Date());
        ContentValues cv =new ContentValues(5);
        cv.put("exam_id",exam_id);
        cv.put("exam_date",exam_date);
        cv.put("exam_score",-1);
        cv.put("patient_id",patient_id);
        cv.put("nurse_id",nurse_id);
        db.insert("Exam", null, cv);
        Toast.makeText(getApplicationContext(), "成功注冊", Toast.LENGTH_SHORT).show();
        Cursor c = db.rawQuery("SELECT * FROM Exam",null);
        if(c.getCount()>0) {
            c.moveToFirst();
            String s = c.getString(0) + "\\n" + c.getString(1) + "\\n" + c.getString(2) + "\\n"+c.getString(3) + "\\n"+c.getString(4) + "\\n";
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public  void  back(View v){
        Intent i=new Intent(choose_education.this,Searchlogin.class);
        i.putExtra("nurseID",nurseID);
        startActivity(i);
        finish();
    }
    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(choose_education.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(choose_education.this,MainActivity.class);
                        startActivity(i);
                        finish();
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
        String Q_array[]=new String[5];
        int count=0;//看有幾張考卷了
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+"kidney_function"+id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            cu.moveToFirst();
            count=cu.getCount();
            String exam_id="kindney_function"+id+count;//考卷id=衛教資料名+病友id+第幾筆
            Q_array=choi_Q();
            insertExam(exam_id ,nurseID, id);
            Intent i=new Intent( this,kidney_reason.class);
            //String nurseID=i.getStringExtra("nurseID");
            //  String id=i.getStringExtra("eid");
            i.putExtra("nurseID",nurseID);
            i.putExtra("id",id);
            i.putExtra("exam_id",exam_id);
            i.putExtra("Q_array",Q_array);
            db.close();
            startActivity(i);
            finish();
        }
        else {
            //前側
            String exam_id="kindney_function"+id+count;
            Q_array=choi_Q();
            insertExam(exam_id ,nurseID, id);
            Intent i=new Intent( this,fronttest.class);
            //String nurseID=i.getStringExtra("nurseID");
            // String id=i.getStringExtra("eid");
            i.putExtra("nurseID",nurseID);
            i.putExtra("id",id);
            i.putExtra("exam_id",exam_id);
            i.putExtra("health_education","kindney_function");
            i.putExtra("Q_array",Q_array);

            db.close();
            startActivity(i);
            finish();
        }
    }

    public void reason(View v){
        String Q_array[]=new String[5];
        int count=0;//看有幾張考卷了
        cu = db.rawQuery("SELECT * FROM Exam WHERE exam_id LIKE '"+"kidney_reason"+id+"%'",null);
        if (cu.getCount()>0){
            //衛教+後側
            cu.moveToFirst();
            count=cu.getCount();
            String exam_id="kidney_reason"+id+count;//考卷id=衛教資料名+病友id+第幾筆
            Q_array=choi_Q();
            insertExam(exam_id ,nurseID, id);
            Intent i=new Intent( this,kidney_reason.class);
            //String nurseID=i.getStringExtra("nurseID");
            //  String id=i.getStringExtra("eid");
            i.putExtra("nurseID",nurseID);
            i.putExtra("id",id);
            i.putExtra("exam_id",exam_id);
            i.putExtra("Q_array",Q_array);
            db.close();
            startActivity(i);
            finish();
        }
        else {
            //前側
            String exam_id="kidney_reason"+id+count;
            Q_array=choi_Q();
            insertExam(exam_id ,nurseID, id);
            Intent i=new Intent( this,fronttest.class);
            //String nurseID=i.getStringExtra("nurseID");
            // String id=i.getStringExtra("eid");
            i.putExtra("nurseID",nurseID);
            i.putExtra("id",id);
            i.putExtra("exam_id",exam_id);
            i.putExtra("health_education","kidney_reason");
            i.putExtra("Q_array",Q_array);

            db.close();
            startActivity(i);
            finish();
        }
    }
}
