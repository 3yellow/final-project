package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

public class Search_patient extends AppCompatActivity {
    static final String Patient = "patient";
    private String s_p;//要收尋得名
    SQLiteDatabase dbs;
    TableLayout layout2;
    ArrayList id_array = new ArrayList();
    int flag1=0;//這是用來區分按鈕按下去的還是一打開頁面的 ( 控制自動產生按鈕的 )

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);
        Intent i = this.getIntent();
        s_p = i.getStringExtra("s_p");
        flag1=i.getIntExtra("flag",0);
        layout2=findViewById(R.id.tbl);
        dbs = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        if (flag1==1){
            search_read(s_p);
        }
        else {
            read();
        }
        //String sql = "SELECT  patient_gender  FROM Patient WHERE patient_id = '" + s_p + "'";
        //Cursor cu = dbs.rawQuery(sql, null);
        /*Cursor cu = dbs.rawQuery("SELECT * FROM " + Patient, null);
        if (!cu.moveToFirst()) {
            Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
        }
        else {

            if (s_p.length() == 10) {
                //用身分證找尋
            }
            else {

            }
        }*/
    }

    public void search_read(String str){
        Cursor cu=null;
        if (s_p.length()==10){
            //收尋病人使用 身分證
            String sql = "SELECT * FROM Patient WHERE patient_id = '"+str+"'";
            cu=dbs.rawQuery(sql,null);
            if (cu.getCount() > 0) {
                cu.moveToFirst();
                do {
                    String text = cu.getString(1) + "\t\t" + cu.getString((0)) + "\t\t\t" + cu.getString(3);
                    id_array.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
                    String namee = cu.getString(0);
                    String idd = cu.getString(1);
                    String agee = cu.getString(2);
                    final Button button = new Button(this);//final Button
                    final Button btn_modify = new Button(this);//final Button
                    TableRow r = new TableRow(this);//final TableRow
                    //  final ScrollView sc=new ScrollView(this);
                    // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                    r.setLayoutParams(new TableRow.LayoutParams(1520, 80));
                    button.setLayoutParams(new TableRow.LayoutParams(684, 80));

                    btn_modify.setLayoutParams(new TableRow.LayoutParams(120, 80));

                    button.setTextSize(35);
                    button.setText(text);
                    // la.addView(layout2);
                    btn_modify.setTextSize(35);
                    btn_modify.setText("修改");
                    r.addView(button);//yout
                    r.addView(btn_modify);//yout2
                    layout2.addView(r);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent i = new Intent(Search_patient.this, choose_education.class);
                            dbs.close();
                            startActivity(i);
                        }
                    });
                    btn_modify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tmp = btn_modify.getId();
                            String id_tmp = id_array.get(tmp).toString();
                            int flag = 1;
                            Intent intent = new Intent(Search_patient.this, Newdata.class);
                            intent.putExtra("id", id_tmp);
                            intent.putExtra("flag", flag);
                            dbs.close();
                            startActivity(intent);
                        }
                    });
                } while (cu.moveToNext());
            }
        }


        else {
            final Button button = new Button(this);
            button.setText("沒有找到資料");
            button.setTextSize(25);
        }
    }

    public void search(View v){
        //收尋病人時會轉換頁面
        EditText edt_search = findViewById(R.id.edt_search);
        String s_p = edt_search.getText().toString().trim();
        if (s_p.length()>0)//判斷是否有輸入東西  但還沒改好
        {
            Intent intent=new Intent(Search_patient.this,Searchlogin.class);
            intent.putExtra("s_p",s_p);
            dbs.close();
            startActivity(intent);
        }

    }
    public void read() {
        Cursor cu = dbs.rawQuery("SELECT * FROM " + Patient, null);
        if (cu.getCount() > 0) {
            cu.moveToFirst();
            do {
                String text = cu.getString(1) + "\t\t" + cu.getString((0)) + "\t\t\t" + cu.getString(3);
                id_array.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
                String namee = cu.getString(0);
                String idd = cu.getString(1);
                String agee = cu.getString(2);
                final Button button = new Button(this);//final Button
                final Button btn_modify = new Button(this);//final Button
                TableRow r = new TableRow(this);//final TableRow
                //  final ScrollView sc=new ScrollView(this);
                // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                r.setLayoutParams(new TableRow.LayoutParams(1520, 80));
                button.setLayoutParams(new TableRow.LayoutParams(684, 80));

                btn_modify.setLayoutParams(new TableRow.LayoutParams(120, 80));

                button.setTextSize(35);
                button.setText(text);
                // la.addView(layout2);
                btn_modify.setTextSize(35);
                btn_modify.setText("修改");
                r.addView(button);//yout
                r.addView(btn_modify);//yout2
                layout2.addView(r);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(Search_patient.this, choose_education.class);
                        dbs.close();
                        startActivity(i);
                    }
                });
                btn_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tmp = btn_modify.getId();
                        String id_tmp = id_array.get(tmp).toString();
                        int flag = 1;
                        Intent intent = new Intent(Search_patient.this, Newdata.class);
                        intent.putExtra("id", id_tmp);
                        intent.putExtra("flag", flag);
                        dbs.close();
                        startActivity(intent);
                    }
                });
            } while (cu.moveToNext());
        }
    }
}