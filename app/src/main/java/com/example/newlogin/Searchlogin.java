package com.example.newlogin;


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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Searchlogin extends AppCompatActivity {
    static final String Nurse="nurse"; //database table name
    static final String Patient="patient"; //database table name

    ArrayList id_array= new ArrayList();
    ArrayList id_array_search= new ArrayList();
    EditText edt_search;
    Button btn_search;
    String namee=null;
    String idd=null;
    String agee=null;
    int pad=0;
    TableLayout layout2;
    TableRow row;
    SQLiteDatabase db;
    int flag=0,i = 0;
   // private Button button,btn_modify;
    private TableRow r;
    String nurseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlogin);
       // btn_search = findViewById(R.id.btn_birth);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        TextView user=(TextView)findViewById(R.id.textView);
        Intent i=this.getIntent();
        nurseID=i.getStringExtra("nurseID");
        pad=i.getIntExtra("pad",-1);
        Cursor cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+nurseID+"' ",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            String nurse_name=cu.getString(1);
            user.setText(nurse_name+"登入");
            user.setTextSize(28);
        }


        // db = openOrCreateDatabase("dbs", Context.MODE_PRIVATE, null);
        layout2=findViewById(R.id.tbl);
       // row=findViewById(R.id.tbr);
        read();



        // 定义一个List集合
        final List<String> components = new ArrayList<>();
        components.add("TextView");
        components.add("EditText");
        components.add("Button");
        components.add("CheckBox");
        components.add("RadioButton");
        components.add("ToggleButton");
        components.add("ImageView");

        // 将List包装成ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.custom_item, R.id.content_tv, components);

        // 为ListView设置Adapter
        //listView.setAdapter(adapter);

        // 为ListView列表项绑定点击事件监听器
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(Searchlogin.this, components.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public void search(View v){
        Cursor cu=null;
        edt_search = findViewById(R.id.edt_search);
        String s_p = edt_search.getText().toString().trim();
        //if (s_p.length()>0)//判斷是否有輸入東西  但還沒改好
        //{
        for(int x = 0;x<=i;x++)
        {
            ViewGroup layout = (ViewGroup) findViewById(R.id.tbl);
            View command = layout.findViewById(x);
            layout.removeView(command);
        }
            if (s_p.length()==10)//收尋病人使用 身分證
            {
                i=0;
                String sql = "SELECT * FROM Patient WHERE patient_id = '"+s_p+"'";
                cu=db.rawQuery(sql,null);
                if (cu.getCount() > 0) {
                    cu.moveToFirst();
                    do {
                        i++;
                        String text = cu.getString(1) + "\t\t" + cu.getString((0)) + "\t\t\t" + cu.getString(3);
                        id_array_search.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
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
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                int tmp = button.getId();
                                String id_tmp = id_array_search.get(tmp).toString();
                                Intent i = new Intent(Searchlogin.this, choose_education.class);
                                i.putExtra("flag",0);//要前側
                                i.putExtra("nurseID",nurseID);
                                i.putExtra("pad",pad);
                                i.putExtra("id",id_tmp);
                                db.close();
                                startActivity(i);
                                finish();
                            }
                        });
                        btn_modify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int tmp = btn_modify.getId();
                                String id_tmp = id_array_search.get(tmp).toString();
                                int flag = 1;
                                Intent intent = new Intent(Searchlogin.this, Newdata.class);
                                intent.putExtra("id",id_tmp);
                                intent.putExtra("nurseID",nurseID);
                                intent.putExtra("flag",flag);
                                intent.putExtra("pad",pad);
                                // intent.putExtra("id", id_tmp);
                                // intent.putExtra("flag", flag);
                                db.close();
                                startActivity(intent);
                                finish();
                            }
                        });
                    } while (cu.moveToNext());
                }
            }
            else if(s_p.length() > 0) //收尋病人名
            {

                i=0;
                //String sql = "SELECT * FROM Patient  WHERE patient_name = '"+s_p+"'";  收尋名字時 只能找到輸入全名
                String sql = "SELECT * FROM Patient  WHERE patient_name LIKE '"+s_p+"%'";
                cu=db.rawQuery(sql,null);
                if (cu.getCount() > 0) {
                    cu.moveToFirst();
                    do {
                        String text = cu.getString(1) + "\t\t" + cu.getString((0)) + "\t\t\t" + cu.getString(3);
                        id_array_search.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
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
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                int tmp = button.getId();
                                String id_tmp = id_array_search.get(tmp).toString();
                                Intent i = new Intent(Searchlogin.this, choose_education.class);
                                i.putExtra("flag",0);//要前側
                                i.putExtra("nurseID",nurseID);
                                i.putExtra("id",id_tmp);
                                db.close();
                                startActivity(i);
                            }
                        });
                        btn_modify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int tmp = btn_modify.getId();
                                String id_tmp = id_array_search.get(tmp).toString();
                                int flag = 1;
                                Intent intent = new Intent(Searchlogin.this, Newdata.class);
                                intent.putExtra("id",id_tmp);
                                intent.putExtra("nurseID",nurseID);
                                intent.putExtra("flag",flag);
                               // intent.putExtra("id", id_tmp);
                                //intent.putExtra("flag", flag);
                                db.close();
                                startActivity(intent);
                            }
                        });
                    } while (cu.moveToNext());
                }
            }
            else
                read();
        //}

    }

    public void read()
    {
        i=0;//計數有幾筆資料
        Cursor cu = db.rawQuery("SELECT * FROM "+Patient,null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
            do {
                String text=cu.getString(1)+"\t\t"+cu.getString((0))+"\t\t\t"+cu.getString(3);
                id_array.add(cu.getString(0));//這是要判斷用來存陣列的，要讓修改去抓的，存id;
                namee=cu.getString(0);
                idd=cu.getString(1);
                agee=cu.getString(2);
                final Button button = new Button(this);//final Button
                final Button btn_modify=new Button(this);//final Button
                r=new TableRow(this);//final TableRow
                //  final ScrollView sc=new ScrollView(this);
                // sc.setLayoutParams(new LinearLayout.LayoutParams(560,540));
                r.setLayoutParams(new TableRow.LayoutParams(1520,80));
                button.setLayoutParams(new TableRow.LayoutParams(824,80));

                btn_modify.setLayoutParams(new TableRow.LayoutParams(60,80));
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
                button.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        int tmp=button.getId();
                        String id_tmp=id_array.get(tmp).toString();
                        Intent i=new Intent(Searchlogin.this,choose_education.class);
                        i.putExtra("flag",0);//要前側
                        i.putExtra("nurseID",nurseID);
                        i.putExtra("pad",pad);
                        i.putExtra("id",id_tmp);
                        db.close();
                        startActivity(i);
                        finish();
                    }
                });
                btn_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tmp=btn_modify.getId();
                        String id_tmp=id_array.get(tmp).toString();
                        flag=1;
                        Intent intent=new Intent(Searchlogin.this,Newdata.class);
                        intent.putExtra("id",id_tmp);
                        intent.putExtra("nurseID",nurseID);
                        intent.putExtra("pad",pad);
                        intent.putExtra("flag",flag);
                        db.close();
                        startActivity(intent);
                        finish();
                    }
                });
            }while(cu.moveToNext());
        }
    }

    public void insertpaient(View v){
        Intent intent = new Intent();
        intent.setClass(this,Newdata.class);
        intent.putExtra("nurseID",nurseID);
        intent.putExtra("pad",pad);
        startActivity(intent);
        finish();
    }

    public void onclick(View v){
        AlertDialog dialog=new AlertDialog.Builder(Searchlogin.this)
                .setTitle("確定要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Searchlogin.this,MainActivity.class);
                        finish();
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
