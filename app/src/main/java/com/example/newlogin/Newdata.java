package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Newdata extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    //static final String db_patient="patientDB"; //database name;
    //static final String Patient="patient"; //database table name
    SQLiteDatabase DBS; //database object
    String createTable;
    Cursor c;
    Calendar cal;
    Calendar ca2;

    EditText edt_id,edt_name=null;
    TextView textView7;
    RadioGroup sex;
    private Button button6;
    private Button btn_birth;

    String date,birth;
    int geender=0;//1：男 2：女
    String eName,eId=null;

    //以下是要在修改時使用的
    String idd=null;
    int flag1=0;//判斷是要修改的還是新增。 1為修改

    int flag=0;//判別是不是已經有資料;
    int mYear_b,mMonth_b,mDay_b;
    RadioButton malee,femalee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdata);
        DBS = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"

        cal =  Calendar.getInstance();
        ca2 =  Calendar.getInstance();
        textView7=findViewById(R.id.textView7);
        edt_id=findViewById(R.id.edt_id);
        edt_name=findViewById(R.id.edt_name);
        //  edt_age=findViewById(R.id.btn_birth);
        sex=findViewById(R.id.radioGroup);
        sex.setOnCheckedChangeListener(this);
        malee = findViewById(R.id.male);
        femalee = findViewById(R.id.female);

        //修改資料
        Intent i=this.getIntent();
        flag1=i.getIntExtra("flag",0);

        if (flag1 == 1) {
            idd=i.getStringExtra("id").toString();
            String sql = "SELECT  patient_gender  FROM Patient WHERE patient_id = '"+ idd +"'";
            Cursor cu = DBS.rawQuery( sql,null );
            if (!cu.moveToFirst()){
                Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
            }
            else {
                edt_id.setFocusable(false);
                edt_id.setFocusableInTouchMode(false);
                geender = cu.getInt(0);//性別的預設值
                if (geender==1){
                    malee.setChecked(true);
                }
                else {
                    femalee.setChecked(true);
                }
            }

            read(idd);
        }
        else {
            String sql = "CREATE TABLE IF NOT EXISTS Patient (patient_id TEXT NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign TEXT, patient_birth DATE , patient_incharge TEXT NOT NULL, PRIMARY KEY(patient_id), FOREIGN KEY(patient_incharge) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
            DBS.execSQL(sql);
            DBS.execSQL("PRAGMA foreign_keys=ON;");
        }
        nowTime(1,idd,flag1);//收案日期的
        nowTime(0,idd,flag1);//生日
    }

    public void read(String id_tmp){
        String sql = "SELECT patient_name,patient_gender,patient_register,patient_birth FROM Patient WHERE patient_id = '"+ id_tmp +"'";
        Cursor cu = DBS.rawQuery( sql,null );

        if (!cu.moveToFirst()){
            Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
        }
        else{
            String anamee = cu.getString(0);
            String date_register=cu.getString(2);
            String bi=cu.getString(3);
            edt_name.setText(anamee);
            edt_id.setText(idd);
        }
    }
    public void onDay2(View v)//設定時間的元件 View v int flag,String date
    {
        mYear_b = 0;mMonth_b = 0; mDay_b = 0;
        if (flag1 == 1){
            String[] token=birth.split("/");
            mYear_b=Integer.valueOf(token[0]);
            mMonth_b=Integer.valueOf(token[1])-1;
            mDay_b=Integer.valueOf(token[2]);
        }
        else {
            mYear_b=ca2.get(Calendar.YEAR);
            mMonth_b=ca2.get(Calendar.MONTH);
            mDay_b=ca2.get(Calendar.DAY_OF_MONTH);
        }
        new DatePickerDialog(Newdata.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String format = setDateFormat(year, month, day);
                btn_birth.setText(format);
            }
        },mYear_b,mMonth_b,mDay_b).show();
    }
    public void onDay(View v){
        mYear_b = 0;mMonth_b = 0; mDay_b = 0;
        if (flag1 == 1){
            String[] token=date.split("/");
            mYear_b=Integer.valueOf(token[0]);
            mMonth_b=Integer.valueOf(token[1])-1;
            mDay_b=Integer.valueOf(token[2]);
        }
        else {
            mYear_b=ca2.get(Calendar.YEAR);
            mMonth_b=ca2.get(Calendar.MONTH);
            mDay_b=ca2.get(Calendar.DAY_OF_MONTH);
        }
        new DatePickerDialog(Newdata.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String format = setDateFormat(year, month, day);
                button6.setText(format);
            }
        },mYear_b,mMonth_b,mDay_b).show();
    }
    private String setDateFormat(int year, int month, int day) {
        return String.valueOf(year)+"/"+String.valueOf(month+1)+"/"+String.valueOf(day);
    }

    public void onClick(View v) {
        Boolean iAge,iId;
        eId=edt_id.getText().toString().trim();//trim去除多餘空白
        eId=eId.toUpperCase();
        String ename=edt_name.getText().toString().trim();

        flag=searchData(eId,flag);
        if (flag==2&&flag1!=1){
            textView7.setText("已有此資料");
        }
        else if (ename.isEmpty()){
            textView7.setText("身分證還沒填");
        }
        else if (eId.isEmpty()){
            textView7.setText("身分證還沒填");
        }
        else if (geender==0){
            flag=3;
            textView7.setText("性別還沒選");
        }
        else if(eId==null){
            flag=3;
            textView7.setText("身分證還沒選");
        }
        else if (flag1==1){
            modify_patient(ename,eId,geender,button6.getText().toString(),btn_birth.getText().toString());
            DBS.close();
            Intent i=new Intent(Newdata.this,Searchlogin.class);
            startActivity(i);
            finish();
        }
        else if (flag == 1) {
            addData(ename,eId,geender,button6.getText().toString(),btn_birth.getText().toString());
            //(String name,String id,String age,int gender,String date,String birth_date)
            DBS.close();
            Intent i=new Intent(Newdata.this,consent.class);
            startActivity(i);
            finish();
        }
    }

    /*public Boolean  vreifyId(String id){
        int c=0,n=0; //c判斷第一個字是否為英文字 n判別第二個字是否為1或2
        if (id.length()!=10){
            return false;
        }
        for (int i=65;i<=90;i++)
        {
            char ch=(char)i;
            if (id.charAt(0)==i){
                c=1;//第一個字為英文字
            }
        }
        if (c==0){
            return false;//第一個要為英文字母
        }
        char cha1=49,cha2=50;//在ascill碼49為1 50為2
        if (id.charAt(1)==49||id.charAt(2)==50){
            n=1;//第一個字為1或2
        }
        if (n==0){
            return false;
        }

        //判斷格式是否符合身分證
        String str="ABCDEFGHJKLMNPQRSTUVXYWZIO";
        int e=str.indexOf(id.charAt(0))+10;
        int f=e/10,g=e%10,total=0;
        g*=9;
        int aa=0;
        for (int j=1;j<=8;j++)
            total+=(id.charAt(j)-48)*(9-j);           //-48原因在於id.charAt(抓的是數字的char)
        total+=+f+g;
        total%=10;
        int bb=((id.charAt(9)-48)+total)%10;
        if (bb==0)
        {
            System.out.println("這是正確的身分證號碼!!");
            aa=1;
            return true;
        }
        if (aa==0) {              //aa不等於0則輸入身分證字號不符合
            System.out.println("這不是正確的身分證字號!!");
            return false;
        }
        return false;
    }*/
    private int searchData(String str1,int flag) //判別是否已經有此資料了
    {
        c=DBS.rawQuery("SELECT patient_id FROM Patient  WHERE patient_id='"+str1+"'",null);
        if (c.moveToFirst()) {
            flag = 2;
        }
        else {
            flag =1;
        }
        return flag;
    }

    private void addData(String name,String id ,int gender,String date,String birth_date) {
        ContentValues cv=new ContentValues(1);
        cv.put("patient_id",id);
        cv.put("patient_name",name);
        cv.put("patient_gender",gender);
        cv.put("patient_register",date);
        cv.put("patient_birth",birth_date);
        cv.put("patient_incharge","admin");//目前沒有護理師的資料，護理師的資料是從登入那抓取id，一直傳
        DBS.insert("Patient", null, cv);

        Cursor cu = DBS.rawQuery("SELECT * FROM Patient",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
        }
    }

    private void modify_patient(String name,String id ,int gender,String date,String birth_date){
        ContentValues cv = new ContentValues(7);
        cv.put("patient_id", id);
        cv.put("patient_name", name);
        cv.put("patient_gender", gender);
        cv.put("patient_register", date);
        cv.put("patient_birth", birth_date);
        //如果是修改
        String whereClause = "patient_id = ?";
        String whereArgs[] = {id};
        DBS.update("Patient", cv, whereClause, whereArgs);
        Toast.makeText(getApplicationContext(), "Modify Success!", Toast.LENGTH_SHORT).show();
    }

    public void nowTime(int flag_data,String id_tmp,int flag)//取得當日日期並且顯示在按鈕上
    {
        if (flag_data==1){
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd");
            if (flag==1){
                String sql = "SELECT patient_register FROM Patient WHERE patient_id = '"+ id_tmp +"'";
                Cursor cu = DBS.rawQuery( sql,null );
                if (!cu.moveToFirst()){
                    Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
                }
                else {
                    date = cu.getString(0);//formatter.format(new java.util.Date());
                }
            }
            else {
                date=formatter.format(new java.util.Date());
            }
            button6=findViewById(R.id.button6);
            button6.setText(date);

        }
        else {
            SimpleDateFormat formatter_b=new SimpleDateFormat("yyyy/MM/dd");
            if (flag==1){
                String sql = "SELECT patient_birth FROM Patient WHERE patient_id = '"+ id_tmp +"'";
                Cursor cu = DBS.rawQuery( sql,null );
                if (!cu.moveToFirst()){
                    Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
                }
                else {
                    birth = cu.getString(0);
                }
            }
            else {
                birth=formatter_b.format(new java.util.Date());
            }
            btn_birth=findViewById(R.id.btn_birth);
            btn_birth.setText(birth);
        }
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (sex.getCheckedRadioButtonId()){
            case R.id.male://
                Toast.makeText(this,"男",Toast.LENGTH_LONG).show();
                geender=1;//"男"
                break;
            case R.id.female:
                Toast.makeText(this,"女",Toast.LENGTH_LONG).show();
                geender=2;//"女"
                break;
        }

    }


}


