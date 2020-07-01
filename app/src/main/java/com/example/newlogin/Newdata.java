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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Newdata extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    static final String db_patient="patientDB"; //database name;
    static final String tb_patient="patient"; //database table name
    SQLiteDatabase db; //database object
    String createTable;
    Cursor c;
    Calendar cal;

    EditText edt_id,edt_name,edt_age;
    TextView textView7;
    RadioGroup sex;
    private Button button6;

    String date,geender;
    String eName,eId,eAge;

    int flag=0;//判別是不是已經有資料;
    int mYear,mMonth,mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdata);
        nowTime();

        cal =  Calendar.getInstance();


        textView7=findViewById(R.id.textView7);
        edt_id=findViewById(R.id.edt_id);
        edt_name=findViewById(R.id.edt_name);
        edt_age=findViewById(R.id.edt_age);
        sex=findViewById(R.id.radioGroup);
        sex.setOnCheckedChangeListener(this);


        //open or build database
        db =openOrCreateDatabase(db_patient, Context.MODE_PRIVATE,null);
        createTable="CREATE TABLE IF NOT EXISTS "+tb_patient+"(name VARCHAR(10),"+"id VARCHAR(10),"+"age VARCHAR(3),"+"gender VARCHAR(1),"+"date VARCHAR(32))";
        db.execSQL(createTable);

        //deleteData();
    }

    public void onDay(View v){

        // c = new Calendar.getInstance();
        //mYear=cal.get(Calendar.YEAR);
        mDay=cal.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(Newdata.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String format = setDateFormat(year, month, day);
                button6.setText(format);
            }
        },mYear,mMonth,mDay).show();
    }


    private String setDateFormat(int year, int month, int day) {
        return String.valueOf(year)+"/"+String.valueOf(month+1)+"/"+String.valueOf(day);
    }

    public void onClick(View v) {
        Boolean iAge,iId;
        eId=edt_id.getText().toString();
        eId=eId.toUpperCase();

        int x;
        //iId=vreifyId(eId);
        iId = Boolean.TRUE;
        iAge=isInteger(edt_age.getText().toString());
        flag=searchData(edt_id.getText().toString(),flag);

        if (!iId){
            textView7.setText("請輸入正確的身分證格式(A123456789)");
            flag=3;
        }
        else if (!iAge){
            flag=3;
            textView7.setText("年齡請輸入整數!!'");
        }
        else if (flag==2){
            textView7.setText("已有此資料");
        }
        else if (geender==null){
            flag=3;
            textView7.setText("性別還沒選");
        }
        else if (flag == 1) {
            addData(edt_name.getText().toString(),eId,edt_age.getText().toString(),geender,date);
            db.close();
            Intent i=new Intent(Newdata.this,consent.class);
            startActivity(i);
            finish();
        }
    }

    public Boolean  vreifyId(String id){
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
    }

    public static boolean isInteger(String str) //判斷是否為正整數
    {
        Pattern pattern = Pattern.compile("^[+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    private int searchData(String str1,int flag) //判別是否已經有此資料了
    {
        c=db.rawQuery("SELECT * FROM patient WHERE patient.id='"+str1+"'",null);

        if (c.getCount()==0){
            flag=1;
        }

        if (c.moveToFirst()) {

            do {
                flag = 2;
            } while (c.moveToNext());
        }
        return flag;
    }

    private void deleteData(){

        String str1="a123456789";
        String str="A123456789";
        String str2="b123456789";
        String str3="B123456789";
        String str4="S123456789";

        db.delete(tb_patient, "id=?" ,new String[]{str1});
        db.delete(tb_patient, "id=?" ,new String[]{str});
        db.delete(tb_patient, "id=?" ,new String[]{str2});
        db.delete(tb_patient, "id=?" ,new String[]{str3});
        db.delete(tb_patient, "id=?" ,new String[]{str4});
    }

    private void addData(String name,String id,String age,String gender,String date) {
        ContentValues cv=new ContentValues(5);
        cv.put("name",name);
        cv.put("id",id);
        cv.put("age",age);
        cv.put("gender",gender);
        cv.put("date",date);
        db.insert(tb_patient,null,cv);

    }

    public void nowTime()//取得當日日期並且顯示在按鈕上
    {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd");
        date=formatter.format(new java.util.Date());
        button6=findViewById(R.id.button6);
        button6.setText(date);
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (sex.getCheckedRadioButtonId()){
            case R.id.male:
                Toast.makeText(this,"男",Toast.LENGTH_LONG).show();
                geender="男";
                break;
            case R.id.female:
                Toast.makeText(this,"女",Toast.LENGTH_LONG).show();
                geender="女";
                break;
        }
    }


}


