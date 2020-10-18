package com.example.newlogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class backtest extends AppCompatActivity {

    Intent intent = new Intent();
    boolean result;//判斷答案對錯
    SQLiteDatabase db; //database object
    int choiceid,pad=0;
    RadioGroup mRG;
    RadioButton item1;
    RadioButton item2;
    RadioButton item3;
    RadioButton item4;
    String your_ans=null;
   // RadioButton item1,item2,item3,item4;
    Cursor cu;
    String q_id,nurseID,id,exam_id,health_education,patient_answer;
    int score=0,count=0;
    String Q_array[]=new String[5];
    String right_choi;
    RadioButton tempButton;
    String[] Choi;
   // final String[] Choi = {"A.監測水中細菌量","B.測定管路中消毒液殘留量", "C.測定管路壓力", "D.不需要測定任何專案","B.測定管路中消毒液殘留量"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backtest);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);//創建資料庫
        TextView Que=(TextView)findViewById(R.id.Question);
        final TextView Als=(TextView)findViewById(R.id.Analysis);
        final TextView An=(TextView)findViewById(R.id.An);
        final RadioGroup ans = (RadioGroup) findViewById(R.id.Ans);
        mRG=(RadioGroup)findViewById(R.id.Mrg);
        item1=(RadioButton)findViewById(R.id.icho1);
        item2=(RadioButton)findViewById(R.id.icho2);
        item3=(RadioButton)findViewById(R.id.icho3);
        item4=(RadioButton)findViewById(R.id.icho4);
        final Button check=(Button)findViewById(R.id.button19);

        intent=this.getIntent();
        nurseID=intent.getStringExtra("nurseID");
        pad=intent.getIntExtra("pad",-1);
        id=intent.getStringExtra("id");
        exam_id=intent.getStringExtra("exam_id");
        health_education=intent.getStringExtra("health education");
        score=intent.getIntExtra("score",0);
        count=intent.getIntExtra("count",0);
        int c=Integer.valueOf(count);
        Q_array=intent.getStringArrayExtra("Q_array");
        q_id=Q_array[c];

        String sql = "SELECT * FROM Question WHERE question_id = '"+ q_id +"'"; //我在上一個傳給你的城市中有寫感生亂數，用那個亂數的改count，因為這個count 主要的功能是既屬第幾題
        cu = db.rawQuery( sql,null );
        if (!cu.moveToFirst()){
            Toast.makeText(getApplicationContext(), "查無此人", Toast.LENGTH_SHORT).show();
        }
        else {
            String content = cu.getString(1);
            Que.setText(content);
            Choi = new String[4];
            for (int i = 0; i < 4; i++) {
                Choi[i] = cu.getString(i + 3);//拿到存在資料庫中的選項
            }
            item1.setText(Choi[0]);
            item2.setText(Choi[1]);
            item3.setText(Choi[2]);
            item4.setText(Choi[3]);

            mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    tempButton = (RadioButton) findViewById(checkedId);
                    right_choi=cu.getString(2);
                    your_ans=tempButton.getText().toString();
                    if (your_ans.equals(right_choi)) {
                        choiceid = tempButton.getId();
                        result = true;
                       // MyToast("正確答案："+tempButton.getText()+"，恭喜你，回答正確");
                    }
                    else {
                        result = false;
                        //MyToast("回答錯誤！");
                    }
                }
            });

            right_choi=cu.getString(2);
            String explain=cu.getString(7);
            An.setText("正確答案：" + right_choi+ "\n");
            Als.setText(explain);
        }
    }



    private void showDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(backtest.this);
        normalDialog.setIcon(R.drawable.ic_launcher_background);
        normalDialog.setTitle("公佈答案");
        if(result==true)
            normalDialog.setMessage("恭喜你！回答正確！");
        else
            normalDialog.setMessage("選錯了！快來看看正確答案吧！");
        normalDialog.setPositiveButton("確定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("關閉",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    //不能返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }

    public void CHECK(View v) {
        if (your_ans != null) {
            TextView Als = (TextView) findViewById(R.id.Analysis);
            TextView An = (TextView) findViewById(R.id.An);
            Button next = (Button) findViewById(R.id.button17);
            item1.setClickable(false);
            item2.setClickable(false);
            item3.setClickable(false);
            item4.setClickable(false);

            //   RadioButton tempButton = (RadioButton) findViewById(checkedId);
            if (result == true) {
                tempButton.setTextColor(Color.GREEN);
                MyToast("恭喜你，回答正確！");
                An.setTextColor(Color.GREEN);
            } else {
                tempButton.setTextColor(Color.RED);
                MyToast("回答錯誤！");
                An.setTextColor(Color.RED);
            }
            Als.setVisibility(View.VISIBLE);
            An.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }
    }

    private void MyToast(String str)
    {
        Toast mtoast = Toast.makeText(this,str, Toast.LENGTH_SHORT);
        mtoast.setGravity(Gravity.TOP,0,400);
        mtoast.show();
    }
    public void Answer_db(String patient_id,String patient_answer,int result,String question_id,String exam_id,String question_s1,String question_s2,String question_s3, String question_s4){
        //result INT, patient_answer INT, question_id INT, exam_id INT,change_data INT,
        int change_data=pad+1;
        ContentValues cv =new ContentValues(1);//10
        //  cv.put("patient_id",patient_id);
        cv.put("result",result);
        cv.put("patient_answer",patient_answer);
        cv.put("question_id",question_id);
        cv.put("exam_id",exam_id);
        cv.put("question_id",question_s1);
        cv.put("change_data",change_data);
        db.insert("Answer", null, cv);
        Cursor c = db.rawQuery("SELECT * FROM Answer",null);
        if(c.getCount()>0) {
            c.moveToFirst();
            String s = c.getString(1) + "\n" + c.getString(3) + "\n" + c.getString(4) ;
        }
    }

    public void tofronttest2 (View v){
            count++;
            int true_or_false = -1;//判別題目有沒有做對 1:對 0:錯
            if (result == true) {
                true_or_false = 1;
                score += 20;
               // Toast.makeText(this, "right" + score, Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(this, "error" + score, Toast.LENGTH_LONG).show();
                true_or_false = 0;
            }
            if (count >= 5) {
                Answer_db(id, patient_answer, true_or_false, q_id, exam_id, Choi[0], Choi[1], Choi[2], Choi[3]);
                modify_Exam(score, id, exam_id);
                Intent intent = this.getIntent();
                health_education = intent.getStringExtra("health education");
                Intent i = new Intent(backtest.this, choose_education.class);
                i.putExtra("health_education", health_education);
                i.putExtra("nurseID", nurseID);
                i.putExtra("id", id);
                i.putExtra("score", score);
                i.putExtra("flag", 99);//到MaunActivity時要判別 修改考卷
                startActivity(i);
                finish();
            }
            else {
                Answer_db(id, patient_answer, true_or_false, q_id, exam_id, Choi[0], Choi[1], Choi[2], Choi[3]);
                Intent i = new Intent(this, backtest.class);
                i.putExtra("count", count);
                i.putExtra("score", score);
                i.putExtra("health_education", health_education);
                i.putExtra("nurseID", nurseID);
                i.putExtra("id", id);
                i.putExtra("exam_id", exam_id);
                i.putExtra("Q_array", Q_array);
                startActivity(i);
                finish();
            }
    }

    private void modify_Exam(int score,String patient_id,String exam_id){
        //exam_id TEXT, exam_date DateTime, exam_score INT, patient_id TEXT, nurse_id TEXT
        int change_data=pad+2;
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
            cv.put("change_data",change_data);
            //如果是修改
            String whereClause = "exam_id = ?";
            //  String whereArgs[] = {id};
            String whereArgs[ ]={String.valueOf(exam_id)};
            db.update("Exam", cv, whereClause, whereArgs);
        }
        c = db.rawQuery("SELECT * FROM Exam WHERE exam_id='"+exam_id+"'",null);
        if(c.getCount()>0) {
            c.moveToFirst();
            String s = c.getString(0) + "\n" + c.getString(1) + "\n" + c.getString(2) + "\n"+c.getString(3) + "\n"+c.getString(4) + "\n"+c.getString(5) ;
            //Toast.makeText(getApplicationContext(), "Modify Success!", Toast.LENGTH_SHORT).show();
        }

    }
}

