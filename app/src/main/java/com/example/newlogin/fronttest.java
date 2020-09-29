package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Queue;

public class fronttest extends AppCompatActivity {

    boolean result;//判斷答案對錯
    SQLiteDatabase db;
    int i=0;
    int Q[];//控制哪幾題被選到
    TextView Que = (TextView) findViewById(R.id.Question);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fronttest);

       // TextView Que = (TextView) findViewById(R.id.Question);
        final TextView YAns = (TextView) findViewById(R.id.YourAns);
        final TextView Als = (TextView) findViewById(R.id.Analysis);
        final RadioGroup ans = (RadioGroup) findViewById(R.id.Ans);
        final ScrollView scroll = (ScrollView) findViewById(R.id.roll);
        final Button next = (Button) findViewById(R.id.button12);
        Button dialog = (Button) findViewById(R.id.button);
        RadioButton item1=(RadioButton)findViewById(R.id.radioButton1);
        RadioButton item2=(RadioButton)findViewById(R.id.radioButton2);
        RadioButton item3=(RadioButton)findViewById(R.id.radioButton3);
        RadioButton item4=(RadioButton)findViewById(R.id.radioButton4);

        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫  "dbs"
        Cursor c = db.rawQuery("SELECT * FROM Question ", null);
        if(c.getCount()>0) {
            c.moveToFirst();
            int j=c.getCount();
            Q=new int [j];
            for (int k=0;k<5;k++){
                //隨機產生五個數字，用來抓題目
                //題目id是1 2 3 4...
                int x=0;
                while (x==0){
                    x=(int)(Math.random()*j+1);
                    for (int b=0;b<k;b++){
                        if (Q[b]==x){
                            x=0;
                        }
                    }
                    Q[k]=x;
                }
            }
            c.close();
            String s1=String.valueOf(Q[0]);
            c = db.rawQuery("SELECT * FROM Question WHERE question_id='"+s1+"'", null);
            if(c.getCount()>0) {
                c.moveToFirst();
                String s = "1"+c.getString(1) + "\n" + c.getString(3) + "\n" + c.getString(4) + "\n" + c.getString(5) + "\n" + c.getString(6) + "\n";
                Que.setTextSize(30);
                Que.setText(s);//題目
            }
        }



        //Que.setText("1.血液透析急性併發征不包括：");
        /*final String[] Choi = {"A.發熱", "B.肌肉痙攣", "C.失衡綜合征", "D.透析性骨病", "D.透析性骨病"};

        item1.setText("\t"+Choi[0]);
        item2.setText("\t"+Choi[1]);
        item3.setText("\t"+Choi[2]);
        item4.setText("\t"+Choi[3]);
        for (int i = 0; i < 4; i++) {
            RadioButton tempButton = new RadioButton(this);
            tempButton.setPadding(40, 0, 0, 0);                 // 设置文字距离按钮四周的距离
            tempButton.setText(Choi[i]);
            tempButton.setTextSize(1,30);
            ans.addView(tempButton, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        ans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                RadioButton tempButton = (RadioButton) findViewById(checkedId); // 通过RadioGroup的findViewById方法，找到ID为checkedID的RadioButton
                // 以下就可以对这个RadioButton进行处理了
                YAns.setText("您的答案：" + tempButton.getText());
                //YAns.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                if (tempButton.getText() == Choi[4])
                    result = true;
                else
                    result = false;
            }
        });*/
    }

        /*Als.setText("正確答案："+Choi[4]+"\n"+
                "急性并发症分為：\n"+
                "    透析失衡综合征：主要发生于肌酐、尿素氮等毒素偏高明显患者。主要症状有恶心、呕吐、头痛、疲乏、烦躁不安等，严重者可有抽搐、震颤。\n" +
                "\n"+
                "    首次使用综合征：主要是应用新透析器及管道所引起的。多发生在透析开始后几分钟至1小时左右，表现为呼吸困难，全身发热感，可突然心跳骤停。\n" +
                "\n"+
                "    血栓的形成，有可能是因为抗凝不到位、长时间卧床，置管、管路、滤器等异物刺激血小板聚集。\n" +
                "    还可以发生透析中低血压、高血压、头痛、肌肉痉挛、心律失常等。");

        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                YAns.setVisibility(View.VISIBLE);
                scroll.setVisibility(View.VISIBLE);
                Als.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }
        });

    }



    private void showDialog() {
         @setIcon 设置对话框图标
         @setTitle 设置对话框标题
         @setMessage 设置对话框消息提示
         setXXX方法返回Dialog对象，因此可以链式设置属性

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(fronttest.this);
        normalDialog.setIcon(R.drawable.ic_launcher_background);
        normalDialog.setTitle("公佈答案");
        if (result == true)
            normalDialog.setMessage("恭喜你！回答正確！");
        else
            normalDialog.setMessage("選錯了！快來看看正確答案吧！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }*/
        public void tofronttest2 (View v){
            Cursor c;
            if (i<5){
                String s1=String.valueOf(Q[i]);
                c= db.rawQuery("SELECT * FROM Question  WHERE question_id='"+s1+"'", null);
                if(c.getCount()>0) {
                    c.moveToFirst();
                    String s=i+1+c.getString(1)+"\n"+c.getString(3)+"\n"+c.getString(4)+"\n"+c.getString(5)+"\n"+c.getString(6)+"\n";
                    Que.setTextSize(30);
                    Que.setText(s);//題目
                    i++;
                }
            }
            else {
                db.close();
                Intent i = new Intent(fronttest.this, readpdf.class);
                int flag=0;
                flag=i.getIntExtra("flag",0);
                i.putExtra("flag",flag);
                startActivity(i);
                finish();
            }
        }
    }

