package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Nurse_Newdata extends AppCompatActivity {
    static final String db_nurse="nurseDB"; //database name;
    static final String Nurse="nurse"; //database table name
    SQLiteDatabase db;
    String createTable;

    EditText edt_id,edt_name,edt_pas1,edt_pas2;
    TextView textView7;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse__newdata);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        edt_name=findViewById(R.id.edt_name);
        edt_id=findViewById(R.id.edt_id);
        edt_pas1=findViewById(R.id.edt_pas1);
        edt_pas2=findViewById(R.id.edt_pas2);
        textView7=findViewById(R.id.textView7);
    }
    public void back(View v){
        Intent i=new Intent(this,Menu.class);
        startActivity(i);
        finish();
    }
    public void onclick(View v){
        Boolean iId;
        String pas1,eId;
        pas1=edt_pas1.getText().toString();
        flag=pas1.compareTo(edt_pas2.getText().toString());

        eId=edt_id.getText().toString();
        eId=eId.toUpperCase();
        int flag_2=0;
        flag_2=searchData(eId, flag_2);
        if (flag!=0) {
            textView7.setText("兩個密碼輸入同");
        }
        if (flag_2==2 ){
            textView7.setText("已有此資料");
        }
        else if (pas1==null){
            //判別是不是空
            textView7.setText("密碼必須數入");
        }

        else if(flag==0&&flag_2!=2 ){
            pas1=pas1.toLowerCase();//讓密碼統一都是小寫
            addData(edt_name.getText().toString(),eId,pas1,1);
            db.close();
            Intent i=new Intent(this,Menu.class);
            startActivity(i);
            finish();
        }

    }

    //不能返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }


    private int searchData(String str1,int flag) //判別是否已經有此資料了
    {
        Cursor c;
        c=db.rawQuery("SELECT nurse_id FROM Nurse  WHERE nurse_id='"+str1+"'",null);
        if (c.moveToFirst()) {
            flag = 2;
        }
        else {
            flag =1;
        }
        return flag;
    }
    private void addData(String name,String id,String pas,int staue) {
        ContentValues cv=new ContentValues(5);
        cv.put("nurse_name",name);
        cv.put("nurse_id",id);
        cv.put("nurse_password",pas);
        cv.put("nurse_authority",staue);//1:表示有正常 0:保釋停權
        db.insert(Nurse,null,cv);
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
}
