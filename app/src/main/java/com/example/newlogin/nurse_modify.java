package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class nurse_modify extends AppCompatActivity {

    RadioButton malee,femalee;
    static final String db_nurse="nurseDB"; //database name;
    static final String Nurse="nurse"; //database table name
    SQLiteDatabase db;
    String idd;
    EditText edt_id,edt_name,edt_pas1,edt_pas2;
    TextView textView7;
    RadioGroup work;
    int w_stause=0;
    int flag=0;//判別是不是已經有資料;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_modify);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        ContentValues cv = new ContentValues(4);
        edt_name=findViewById(R.id.edt_name);
        edt_id=findViewById(R.id.edt_id);
        edt_pas1=findViewById(R.id.edt_pas1);
        edt_pas2=findViewById(R.id.edt_pas2);
        textView7=findViewById(R.id.textView7);
        work=findViewById(R.id.radioGroup);
      //  work.setOnCheckedChangeListener(work,w_stause);
        Intent i=this.getIntent();
        idd=i.getStringExtra("id").toString();
        work=findViewById(R.id.radioGroup);
        //sex=findViewById(R.id.radioGroup);
       // work.setOnCheckedChangeListener(this);
        malee = findViewById(R.id.male);
        femalee = findViewById(R.id.female);

        String sql = "SELECT * FROM Nurse WHERE nurse_id = '"+ idd +"'";
        Cursor cu = db.rawQuery( sql,null );

        if (!cu.moveToFirst()){
            Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
        }
        else if(w_stause==0){
            int w = cu.getInt(3);//性別的預設值
            if (w==1){
                malee.setChecked(true);
                w_stause = 1;
            }
            else {
                femalee.setChecked(true);
                w_stause = 2;
            }
        }
        read(idd);
        //work.setOnCheckedChangeListener(radGrpRegionOnCheckedChange);

    }


    public void on(View v)//RadioGroup group, int checkedId
    {
        switch (work.getCheckedRadioButtonId()) {
            case R.id.male:
                //  Toast.makeText(this, "在職", Toast.LENGTH_LONG).show();
                w_stause = 1;
                break;
            case R.id.female:
                //Toast.makeText(this, "離職", Toast.LENGTH_LONG).show();
                w_stause = 2;
                break;
        }
    }

    public void onclick(View v){
        Boolean iId;
        String pas1,eId;
        pas1=edt_pas1.getText().toString();
        flag=pas1.compareTo(edt_pas2.getText().toString());
        eId=edt_id.getText().toString();
        eId=eId.toUpperCase();
        iId=Boolean.TRUE;
//        iId=vreifyId(eId);
        if (flag!=0) {
            textView7.setText("兩個密碼輸入同");
        }
        else if (!iId) {
            textView7.setText("請輸入正確的身分證格式(A123456789)");
        }
        else if(w_stause==0){
            textView7.setText("工作狀態還沒選");
        }
        else if(flag==0&iId){
            pas1=pas1.toLowerCase();//讓密碼統一都是小寫
            modify_nurse(edt_name.getText().toString(),eId,pas1,w_stause);
            String sql = "SELECT * FROM Nurse WHERE nurse_id = '"+ eId +"'";
            Cursor cu = db.rawQuery( sql,null );

            if(cu.getCount()>0) {
                cu.moveToFirst();

                String staue1 = null;
                int a = cu.getInt(3);
                if (a == 1) {
                    staue1 = "在職中";
                } else {
                    staue1 = "離職";

                }
                String text = cu.getString(1) + "\t\t" + cu.getString((0)) + "\t\t\t" + staue1;

            }
            db.close();
            Intent i=new Intent(this,Menu.class);
            startActivity(i);
            finish();
        }
    }
    public void read(String id_tmp){
        String sql = "SELECT * FROM Nurse WHERE nurse_id = '"+ id_tmp +"'";
        Cursor cu = db.rawQuery( sql,null );

        if (!cu.moveToFirst()){
            Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
        }
        else{
            edt_id.setFocusable(false);
            edt_id.setFocusableInTouchMode(false);
            String anamee = cu.getString(1);
            String pa=cu.getString(2);
            edt_name.setText(anamee);
            edt_id.setText(idd);
            edt_pas1.setText(pa);

            //以下兩行是不要讓密碼顯示出來
          //  edt_pas1.setTransformationMethod(PasswordTransformationMethod.getInstance());
           // edt_pas2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edt_pas2.setText(pa);
            int w = cu.getInt(3);//性別的預設值
            if (w==1){
                //w_stause=1;
                malee.setChecked(true);
            }
            else {
                //w_stause=2;
                femalee.setChecked(true);
            }
        }
    }

    /*private void addData(String name,String id,String pas,int staue) {
        ContentValues cv=new ContentValues(5);
        cv.put("nurse_name",name);
        cv.put("nurse_id",id);
        cv.put("nurse_password",pas);
        cv.put("nurse_authority",staue);//1:表示有正常 0:保釋停權
        db.insert(Nurse,null,cv);
    }*/
    private void modify_nurse(String name,String id,String pas,int staue){
        ContentValues cv = new ContentValues(7);
        cv.put("nurse_id", id);
        cv.put("nurse_name", name);
        cv.put("nurse_password", pas);
        cv.put("nurse_authority", staue);
        //如果是修改
        String whereClause = "nurse_id = ?";
        String whereArgs[] = {id};
        db.update("Nurse", cv, whereClause, whereArgs);
        Toast.makeText(getApplicationContext(), "Modify Success!", Toast.LENGTH_SHORT).show();


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


}
