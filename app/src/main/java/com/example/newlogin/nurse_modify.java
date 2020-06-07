package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class nurse_modify extends AppCompatActivity {
    RadioGroup work;
    String w_stause="";
    static final String db_nurse="nurseDB"; //database name;
    static final String tb_nurse="nurse"; //database table name
    SQLiteDatabase db;
    String createTable;

    EditText edt_id,edt_name,edt_pas1,edt_pas2;
    TextView textView7;

    int flag=0;//判別是不是已經有資料;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_modify);
        db =openOrCreateDatabase(db_nurse, Context.MODE_PRIVATE,null);
        createTable="CREATE TABLE IF NOT EXISTS "+db_nurse+"(name VARCHAR(10),"+"id VARCHAR(10),"+"pas VARCHAR(20),"+"staue VARCHAR(3))";
        db.execSQL(createTable);
        ContentValues cv = new ContentValues(4);
        edt_name=findViewById(R.id.edt_name);
        edt_id=findViewById(R.id.edt_id);
        edt_pas1=findViewById(R.id.edt_pas1);
        edt_pas2=findViewById(R.id.edt_pas2);
        textView7=findViewById(R.id.textView7);
        work=findViewById(R.id.radioGroup);
        //work.setOnCheckedChangeListener();
    }
    public void onclick(View v){
        Boolean iId;
        String pas1,eId;
        pas1=edt_pas1.getText().toString();
        flag=pas1.compareTo(edt_pas2.getText().toString());
        eId=edt_id.getText().toString();
        eId=eId.toUpperCase();
        iId=vreifyId(eId);
        if (flag!=0) {
            textView7.setText("兩個密碼輸入同");
        }
        else if (!iId) {
            textView7.setText("請輸入正確的身分證格式(A123456789)");
        }
        else if(w_stause==null){
            textView7.setText("工作狀態還沒選");
        }
        else if(flag==0&iId){
            addData(edt_name.getText().toString(),eId,pas1,w_stause);
            db.close();
            Intent i=new Intent(this,Menu.class);
            startActivity(i);
            finish();
        }
    }

    private void addData(String name,String id,String pas,String stause) {
        ContentValues cv=new ContentValues(5);
        cv.put("name",name);
        cv.put("id",id);
        cv.put("pas",pas);
        cv.put("staue",stause);//1:表示有正常 0:保釋停權
        db.insert(tb_nurse,null,cv);
    }

    public Boolean vreifyId(String id){
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

    public void setOnCheckedChangeListener(RadioGroup group, int checkedId) {
        switch (work.getCheckedRadioButtonId()){
            case R.id.male:
                Toast.makeText(this,"在職",Toast.LENGTH_LONG).show();
                w_stause="在職";
                break;
            case R.id.female:
                Toast.makeText(this,"離職",Toast.LENGTH_LONG).show();
                w_stause="離職";
                break;
        }
    }
}
