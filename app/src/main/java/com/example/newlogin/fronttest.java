package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.Queue;

public class fronttest extends AppCompatActivity {
    static final String Question="question"; //database table name
    SQLiteDatabase db; //database object
    boolean result;//判斷答案對錯
    //static final String db_nurse="nurseDB"; //database name;
    Cursor cu;
    String q_id,nurseID,id,exam_id,health_education,patient_answer;
    String Q_array[]=new String[5];
    int score=0,count=0;
    String[] Choi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fronttest);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫
        TextView Que = (TextView) findViewById(R.id.Question);
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
            /*Q=new int [j];
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
            }*/
        }


        Intent intent=this.getIntent();
        nurseID=intent.getStringExtra("nurseID");
        id=intent.getStringExtra("id");
        exam_id=intent.getStringExtra("exam_id");
        health_education=intent.getStringExtra("health education");
        score=intent.getIntExtra("score",0);
        count=intent.getIntExtra("count",0);
        //int c=Integer.valueOf(count);;
        Q_array=intent.getStringArrayExtra("Q_array");
        //q_id=Q_array[c];


            /*@Override
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
                    result = false;*/

        String sql = "SELECT * FROM Question WHERE question_id = '"+ q_id +"'"; //我在上一個傳給你的城市中有寫感生亂數，用那個亂數的改count，因為這個count 主要的功能是既屬第幾題
        cu = db.rawQuery( sql,null );
        if (!cu.moveToFirst()){
            Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
        }
        else{
            String content=cu.getString(1);
            Que.setText(content);
           //{"A.發熱", "B.肌肉痙攣", "C.失衡綜合征", "D.透析性骨病", "D.透析性骨病"};
            Choi=new String[4];
            for (int i = 0;i < 4 ; i++){
                Choi[i]=cu.getString(i+3);//拿到存在資料庫中的選項
            }
            /*for (int i = 0; i < 4; i++) {
                RadioButton tempButton = new RadioButton(this);
                tempButton.setPadding(40, 0, 0, 0);                 // 设置文字距离按钮四周的距离
                tempButton.setText(Choi[i]);
                tempButton.setTextSize(1,30);
                ans.addView(tempButton, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }*/
            ans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    RadioButton tempButton = (RadioButton) findViewById(checkedId); // 通过RadioGroup的findViewById方法，找到ID为checkedID的RadioButton
                    // 以下就可以对这个RadioButton进行处理了
                    String a=cu.getString(2);//拿到資料庫中的答案
                    patient_answer=tempButton.getText().toString();
                    YAns.setText("您的答案：" + tempButton.getText());
                    //YAns.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    String str=tempButton.getText().toString();
                    if (str.equals(a)){
                        // Answer_db(str, a,count,int exam_id)
                        result = true;
                    }
                    else {
                        result = false;
                    }
                }

            });
        }
    }
    public void Answer_db(String patient_id,String patient_answer,int result,String question_id,String exam_id,String question_s1,String question_s2,String question_s3, String question_s4){
        //result TEXT, patient_answer TEXT, question_id TEXT, exam_id TEXT, question_s1 CHAR(12), question_s2 CHAR(12), question_s3 CHAR(12), question_s4 CHAR(12)
        //result INT, patient_answer INT, question_id INT, exam_id INT
        ContentValues cv =new ContentValues(1);//10
      //  cv.put("patient_id",patient_id);
        cv.put("result",result);
        cv.put("patient_answer",patient_answer);
        cv.put("question_id",question_id);
        cv.put("exam_id",exam_id);
        cv.put("question_s1",question_s1);
        cv.put("question_s2",question_s2);
        cv.put("question_s3",question_s3);
        cv.put("question_s4",question_s4);
        db.insert("Answer", null, cv);
        Cursor c = db.rawQuery("SELECT * FROM Answer",null);
        if(c.getCount()>0) {
            c.moveToFirst();
            String s = c.getString(1) + "\\n" + c.getString(3) + "\\n" + c.getString(4) + "\\n" + c.getString(5) + "\\n" + c.getString(6) + "\\n";
        }
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

            count++;
            int true_or_false=-1;//判別題目有沒有做對 1:對 0:錯
            if (result==true) {
                true_or_false=1;
                score += 20;
                Toast.makeText(this, "right"+score, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "error" + score, Toast.LENGTH_LONG).show();
                true_or_false=0;
            }
            if (count>=5) {
                Answer_db(id,patient_answer, true_or_false,q_id,exam_id,Choi[0],Choi[1],Choi[2],Choi[3]);
                modify_Exam(score,id,exam_id);
                Intent intent=this.getIntent();
                health_education=intent.getStringExtra("health education");
               Intent i = new Intent(fronttest.this, choose_education.class);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("flag",99);//到MaunActivity時要判別 修改考卷
                startActivity(i);
                finish();
            }
            else {
                Answer_db(id, patient_answer,true_or_false,q_id,exam_id,Choi[0],Choi[1],Choi[2],Choi[3]);
                Intent i=new Intent(this,fronttest.class);
                i.putExtra("count",count);
                i.putExtra("score",score);
                i.putExtra("nurseID",nurseID);
                i.putExtra("id",id);
                i.putExtra("exam_id",exam_id);
                i.putExtra("Q_array",Q_array);
                startActivity(i);
                finish();
            }
        }

    private void modify_Exam(int score,String patient_id,String exam_id){
        //exam_id TEXT, exam_date DateTime, exam_score INT, patient_id TEXT, nurse_id TEXT
        int exam_score;
        String exam_date,nurseID;
        Cursor c = db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"'",null);
        if(c.getCount()>0) {
            c.moveToFirst();
            exam_date=c.getString(1);
            nurseID=c.getString(4);

            ContentValues cv = new ContentValues(7);
            cv.put("exam_score",score);
            cv.put("exam_id",exam_id);
            cv.put("exam_date",exam_date);
            cv.put("patient_id",patient_id);
            cv.put("nurse_id",nurseID);
            //如果是修改
            String whereClause = "exam_id = ?";
            //  String whereArgs[] = {id};
            String whereArgs[ ]={String.valueOf(exam_id)};
            db.update("Exam", cv, whereClause, whereArgs);
        }
        c = db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"'",null);
        if(c.getCount()>0) {
            c.moveToFirst();
            String s = c.getString(0) + "\\n" + c.getString(1) + "\\n" + c.getString(2) + "\\n"+c.getString(3) + "\\n"+c.getString(4) + "\\n";
            Toast.makeText(getApplicationContext(), "Modify Success!", Toast.LENGTH_SHORT).show();
        }

    }

}

