package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    static final String Nurse="nurse"; //database table name

    ArrayList id_array= new ArrayList();
    EditText edt_search;
    Button btn_search;
    String namee=null;
    String idd=null;
    String agee=null;
    Intent intent = new Intent();
    TableLayout layout2;
    TableRow row;
    SQLiteDatabase db;
    int flag=0,i = 0;
    private TableRow r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btn_search = findViewById(R.id.btn_birth);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        TextView user=(TextView)findViewById(R.id.textView);
        //   String name=getIntent().getStringExtra("name").toString();
        user.setText("登出");


        // db = openOrCreateDatabase("dbs", Context.MODE_PRIVATE, null);
        layout2=findViewById(R.id.tbl);
        // row=findViewById(R.id.tbr);
        read();
    }

    public void on_dialog(String str){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Menu.this);
        View v1 = getLayoutInflater().inflate(R.layout.dialog_signin,null);
        alertDialog.setView(v1);
        Button btn=v1.findViewById(R.id.btn_right);
        Button btn_cancle=v1.findViewById(R.id.btn_left);
        final TextView username=v1.findViewById(R.id.username);
        final EditText password=v1.findViewById(R.id.password);
        username.setText(str);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=username.getText().toString().trim();
                String pas=password.getText().toString().trim();
                Cursor cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id = '"+ s +"'",null);
                if (!cu.moveToFirst()){
                    Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
                }
                else{
                    String password1=cu.getString(2);
                    if (password1.equals(pas) )//輸入正確帳號密碼
                    {
                        flag=1;
                        Intent intent=new Intent(Menu.this,nurse_modify.class);
                        intent.putExtra("id",s);
                        intent.putExtra("flag",flag);
                        dialog.cancel();
                        db.close();
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "輸入錯誤!!", Toast.LENGTH_SHORT).show();
                    }
                }
                /*
                Intent i=new Intent(Menu.this,Nurse_Newdata.class);
                i.putExtra("aa",s);
                TextView v11=findViewById(R.id.textView);
                v11.setText(s);
                startActivity(i);
                */
            }
        });
    }
    public void read()
    {
        i=0;//計數有幾筆資料
        Cursor cu = db.rawQuery("SELECT * FROM "+Nurse,null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            do {
                String staue=null;
                int a=cu.getInt(3);
                if (a==1){
                    staue="在職中";
                }
                else {
                    staue="離職";

                }
                String text=cu.getString(1)+"\t\t"+cu.getString((0))+"\t\t\t"+staue;
                id_array.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
                namee=cu.getString(0);
                idd=cu.getString(1);
                agee=cu.getString(2);
                final Button button = new Button(this);//final Button
                final Button btn_modify = new Button(this);//final Button
                TableRow r = new TableRow(this);//final TableRow
                //  final ScrollView sc=new ScrollView(this);
                // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                r.setLayoutParams(new TableRow.LayoutParams(1520,80));
                button.setLayoutParams(new TableRow.LayoutParams(684,80));

                btn_modify.setLayoutParams(new TableRow.LayoutParams(120,80));
                btn_modify.setId(i);
                button.setId(i);
                r.setId(i);
                i++;
                button.setTextSize(35);
                button.setText(text);
                // la.addView(layout2);
                btn_modify.setTextSize(35);
                btn_modify.setText("修改");
                r.addView(button);//yout
                r.addView(btn_modify);//yout2
                layout2.addView(r);
                btn_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tmp=btn_modify.getId();
                        String id_tmp=id_array.get(tmp).toString();
                        on_dialog(id_tmp);
                    }
                });
            }while(cu.moveToNext());
    }
    }

    public void nwedata(View v){
        Intent i=new Intent(Menu.this,Nurse_Newdata.class);
        startActivity(i);
    }
    public void search(View v){
        Cursor cu=null;
        edt_search = findViewById(R.id.edt_search);
        String s_p = edt_search.getText().toString().trim();
        if (s_p.length()>0)//判斷是否有輸入東西  但還沒改好
        {
            for(int x = 0;x<=i;x++)
            {
                ViewGroup layout = (ViewGroup) findViewById(R.id.tbl);
                View command = layout.findViewById(x);
                layout.removeView(command);
            }
            if (s_p.length()==10)//收尋病人使用 身分證
            {
                i=0;
                String sql = "SELECT * FROM Nurse WHERE nurse_id = '"+s_p+"'";
                cu=db.rawQuery(sql,null);
                if (cu.getCount() > 0) {
                    cu.moveToFirst();
                    do {
                        i++;
                        String staue=null;
                        if (cu.getInt(3)==1){
                            staue="在職中";
                        }
                        else {
                            staue="離職";

                        }
                        String text=cu.getString(1)+"\t\t"+cu.getString((0))+"\t\t\t"+staue;
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
                        btn_modify.setId(i);
                        button.setId(i);
                        r.setId(i);
                        button.setTextSize(35);
                        button.setText(text);
                        // la.addView(layout2);
                        btn_modify.setTextSize(35);
                        btn_modify.setText("修改");
                        r.addView(button);//yout
                        r.addView(btn_modify);//yout2
                        layout2.addView(r);

                        btn_modify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int tmp=btn_modify.getId();
                                String id_tmp=id_array.get(tmp).toString();
                                on_dialog(id_tmp);
                            }
                        });
                    } while (cu.moveToNext());
                }
            }
            else //收尋病人名
            {
                i=0;
                //String sql = "SELECT * FROM Patient  WHERE patient_name = '"+s_p+"'";  收尋名字時 只能找到輸入全名
                String sql = "SELECT * FROM Nurse  WHERE nurse_name LIKE '"+s_p+"%'";
                cu=db.rawQuery(sql,null);
                if (cu.getCount() > 0) {
                    cu.moveToFirst();
                    do {
                        String staue=null;
                        if (cu.getInt(3)==1){
                            staue="在職中";
                        }
                        else {
                            staue="離職";

                        }
                        String text=cu.getString(1)+"\t\t"+cu.getString((0))+"\t\t\t"+staue;
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
                        btn_modify.setId(i);
                        button.setId(i);
                        r.setId(i);
                        i++;
                        btn_modify.setTextSize(35);
                        btn_modify.setText("修改");
                        r.addView(button);//yout
                        r.addView(btn_modify);//yout2
                        layout2.addView(r);
                        btn_modify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int tmp=btn_modify.getId();
                                String id_tmp=id_array.get(tmp).toString();
                                on_dialog(id_tmp);
                            }
                        });
                    } while (cu.moveToNext());
                }
            }
        }
        else {
            for(int x = 0;x<=i;x++)
            {
                ViewGroup layout = (ViewGroup) findViewById(R.id.tbl);
                View command = layout.findViewById(x);
                layout.removeView(command);
            }
            read();
        }

    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(Menu.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Menu.this,MainActivity.class);
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
}
