package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    Intent intent = new Intent();
    EditText editText, Account;
    ImageButton button;
    boolean canSee;
    SQLiteDatabase db;
    static final String Nurse ="nurse"; //database table name
    static final String Patient="patient"; //database table name
    static final String Question="question"; //database table name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);
        //db.setForeignKeyConstraintsEnabled(true);
        createNurseTable();
        createPatientTable();
        createTopicTable();
        createStudyTable();
        createQuestionTable();
        createExamTable();
        createAnswerTable();
        Account = (EditText) findViewById(R.id.editText);
        editText = (EditText) findViewById(R.id.editText2);
        button = (ImageButton) findViewById(R.id.change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过全局的一个变量的设置，这个就是判断控件里面的内容是不是能被看到
                if (canSee == false) {
                    //如果是不能看到密码的情况下，
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    canSee = true;
                } else {
                    //如果是能看到密码的状态下
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    canSee = false;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("確定要結束應用程式嗎?")
                  //  .setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                }
                            }).show();
        }
        return true;
    }

    public void choicepatient(View v) {
        //跳轉到病人畫面
        String str=Account.getText().toString().trim();
        String pas=editText.getText().toString().trim().toLowerCase();
        Cursor cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+str+"'",null);
        if (str.trim().length()>0){
            if ("admin".equals(pas)){
                AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("此權限沒有病友管理!!!")
                        .setNegativeButton("確定",null).create();
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
            else {
                if(cu.getCount()>0) {
                    cu.moveToFirst();
                    do {
                        String password=cu.getString(2);
                        int flag_staue=cu.getInt(3);
                        if (password.equals(pas) && flag_staue==1){
                            intent.setClass(this, Searchlogin.class);

                            intent.putExtra("nurseID",str);

                            intent.putExtra("nurse",str);

                            // intent.putExtra("name", Account.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                        else if (password.equals(pas) && flag_staue!=1){
                            AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("沒有權限!!!")
                                    .setNegativeButton("確定",null).create();
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
                        else {
                            AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("密碼輸入錯誤!!!")
                                    .setNegativeButton("確定",null).create();
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
                    }while(cu.moveToNext());
                }
                else {
                    AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                            .setTitle("帳號輸入錯誤!!!")
                            .setNegativeButton("確定",null).create();
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
        }
    }

    public void back(View v) {
        String str1=Account.getText().toString().trim().toLowerCase();
        String pas=editText.getText().toString().trim().toLowerCase();
        if (str1.trim().length()>0)//確定有輸入東西，不是誤按。
        {
            if ("admin".equals(str1))//只有管理員可以進入後台管理。
            {
                Cursor cu = db.rawQuery("SELECT * FROM Nurse WHERE nurse_id='"+"admin"+"'",null);
                if(cu.getCount()>0) {
                    cu.moveToFirst();
                    do {
                        String password=cu.getString(2);
                        if (password.equals(pas) )//輸入正確帳號密碼
                        {
                            Intent i = new Intent(MainActivity.this, Backstage_main.class);
                            startActivity(i);
                            finish();
                        }
                    else {
                        AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                                .setTitle("密碼輸入錯誤!!!")
                                .setNegativeButton("確定",null).create();
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
                }while(cu.moveToNext());
            }
        }
        else {
            AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("沒有後台管理權限!!!")
                    .setNegativeButton("確定",null).create();
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
        }}
    }

    private void createNurseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Nurse (nurse_id TEXT NOT NULL, nurse_name TEXT NOT NULL, nurse_password TEXT NOT NULL, nurse_authority INT NOT NULL, PRIMARY KEY(nurse_id))";
        db.execSQL(sql);
        ContentValues contentValues = new ContentValues(1);
        Cursor cursor = db.rawQuery("SELECT * FROM Nurse", null);
        if (!cursor.moveToFirst()) {
            contentValues.put("nurse_id", "admin");
            contentValues.put("nurse_name", "Admin");
            contentValues.put("nurse_password", "admin");
            contentValues.put("nurse_authority", 1);
            db.insert("Nurse", null, contentValues);
        }
        Cursor cu = db.rawQuery("SELECT * FROM Nurse",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
        }
    }

    private void createPatientTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Patient (patient_id TEXT NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign TEXT, patient_birth DATE , patient_incharge TEXT NOT NULL, PRIMARY KEY(patient_id), FOREIGN KEY(patient_incharge) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
        ContentValues contentValues = new ContentValues(1);
        Cursor cursor = db.rawQuery("SELECT * FROM Patient", null);
        if (!cursor.moveToFirst()) {
            contentValues.put("patient_id", "B123456789");
            contentValues.put("patient_name", "BB");
            contentValues.put("patient_gender", "1");
            contentValues.put("patient_register", 20200404);
            contentValues.put("patient_incharge", "admin");
            db.insert("Patient", null, contentValues);
        }
        Cursor cu = db.rawQuery("SELECT * FROM Patient",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
        }

    }

    private void createTopicTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Topic (topic_id INT, topic_name TEXT, PRIMARY KEY(topic_id))";
        db.execSQL(sql);
        ContentValues contentValues = new ContentValues(1);
        Cursor cursor = db.rawQuery("SELECT * FROM Topic", null);
        if (!cursor.moveToFirst()) {
            contentValues.put("topic_id", "t1");
            contentValues.put("topic_name", "t1");
            db.insert("Topic", null, contentValues);
        }
        Cursor cu = db.rawQuery("SELECT * FROM Topic",null);
        if(cu.getCount()>0) {
            cu.moveToFirst();
        }

    }

    private void createStudyTable()//閱讀紀錄
    {
        String sql = "CREATE TABLE IF NOT EXISTS Study (study_id INT, study_date DateTime, topic_id INT, patient_id TEXT, nurse_id TEXT, PRIMARY KEY(study_id), FOREIGN KEY (topic_id) REFERENCES Topic(topic_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY (patient_id) REFERENCES Patient(patient_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY (nurse_id) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createQuestionTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Question (question_id INT, question_content TEXT, question_answer INT, question_s1 CHAR(12), question_s2 CHAR(12), question_s3 CHAR(12), question_s4 CHAR(12), question_explain CHAR(40), exam_id INT, topic_id INT, PRIMARY KEY(question_id), FOREIGN KEY(exam_id) REFERENCES Exam(exam_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(topic_id) REFERENCES Topic(topic_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
        ContentValues contentValues = new ContentValues(1);
        Cursor cursor = db.rawQuery("SELECT * FROM Question", null);
        if (!cursor.moveToFirst()) {
            contentValues.put("topic_id", "t1");
            contentValues.put("question_id", "1");
            contentValues.put("question_content","血液透析急性併發征不包括：");
            contentValues.put("question_answer", 4);
            contentValues.put("question_s1", "A.發熱");
            contentValues.put("question_s2", "B.肌肉痙攣");
            contentValues.put("question_s3",  "C.失衡綜合征");
            contentValues.put("question_s4", "D.透析性骨病");
            contentValues.put("question_explain",  "急性并发症分為：透析失衡综合征：主要发生于肌酐、尿素氮等毒素偏高明显患者。主要症状有恶心、呕吐、头痛、疲乏、烦躁不安等，严重者可有抽搐、震颤。首次使用综合征：主要是应用新透析器及管道所引起的。多发生在透析开始后几分钟至1小时左右，表现为呼吸困难，全身发热感，可突然心跳骤停。血栓的形成，有可能是因为抗凝不到位、长时间卧床，置管、管路、滤器等异物刺激血小板聚集。还可以发生透析中低血压、高血压、头痛、肌肉痉挛、心律失常等。");

            db.insert("Question", null, contentValues);
            contentValues.put("topic_id", "t1");
            contentValues.put("question_id", "2");
            contentValues.put("question_content","血液透析室應當根據設備要求定期對水處理系統進行沖洗消毒，並定期進行水質檢測。每次沖洗消毒後均應_____，確保安全。");
            contentValues.put("question_answer", 2);
            contentValues.put("question_s1", "A.監測水中細菌量");
            contentValues.put("question_s2", "B.測定管路中消毒液殘留量");
            contentValues.put("question_s3",  "C.測定管路壓力");
            contentValues.put("question_s4", "D.不需要測定任何專案");
            contentValues.put("question_explain",  "急性并发症分為：透析失衡综合征：主要发生于肌酐、尿素氮等毒素偏高明显患者。主要症状有恶心、呕吐、头痛、疲乏、烦躁不安等，严重者可有抽搐、震颤。首次使用综合征：主要是应用新透析器及管道所引起的。多发生在透析开始后几分钟至1小时左右，表现为呼吸困难，全身发热感，可突然心跳骤停。血栓的形成，有可能是因为抗凝不到位、长时间卧床，置管、管路、滤器等异物刺激血小板聚集。还可以发生透析中低血压、高血压、头痛、肌肉痉挛、心律失常等。");

            db.insert("Question", null, contentValues);
            contentValues.put("topic_id", "t1");
            contentValues.put("question_id", "3");
            contentValues.put("question_content","血液透析室應當建立嚴格的接診制度，對所有初次透析的患者進行乙型肝炎、病毒、丙型肝炎病毒、梅毒、愛滋病病毒感染的相關檢查，每_____複查1次。");
            contentValues.put("question_answer", 3);
            contentValues.put("question_s1", "A.月");
            contentValues.put("question_s2", "B.季度");
            contentValues.put("question_s3", "C.半年");
            contentValues.put("question_s4", "D.年");
            contentValues.put("question_explain",  "急性并发症分為：透析失衡综合征：主要发生于肌酐、尿素氮等毒素偏高明显患者。主要症状有恶心、呕吐、头痛、疲乏、烦躁不安等，严重者可有抽搐、震颤。首次使用综合征：主要是应用新透析器及管道所引起的。多发生在透析开始后几分钟至1小时左右，表现为呼吸困难，全身发热感，可突然心跳骤停。血栓的形成，有可能是因为抗凝不到位、长时间卧床，置管、管路、滤器等异物刺激血小板聚集。还可以发生透析中低血压、高血压、头痛、肌肉痉挛、心律失常等。");

            db.insert("Question", null, contentValues);
            contentValues.put("topic_id", "t1");
            contentValues.put("question_id", "4");
            contentValues.put("question_content","血液透析室使用的消毒藥械、一次性醫療器械和器具應當符合國家有關規定。一次性使用的醫療器械、器具_____。");
            contentValues.put("question_answer", 3);
            contentValues.put("question_s1", "A.不得重複使用");
            contentValues.put("question_s2", "B.可以重複使用");
            contentValues.put("question_s3", "C.部分貴重的可以重複使用，但必須進行嚴格的消毒滅菌");
            contentValues.put("question_s4", "D.應進行可回收利用");
            contentValues.put("question_explain",  "急性并发症分為：透析失衡综合征：主要发生于肌酐、尿素氮等毒素偏高明显患者。主要症状有恶心、呕吐、头痛、疲乏、烦躁不安等，严重者可有抽搐、震颤。首次使用综合征：主要是应用新透析器及管道所引起的。多发生在透析开始后几分钟至1小时左右，表现为呼吸困难，全身发热感，可突然心跳骤停。血栓的形成，有可能是因为抗凝不到位、长时间卧床，置管、管路、滤器等异物刺激血小板聚集。还可以发生透析中低血压、高血压、头痛、肌肉痉挛、心律失常等。");

            db.insert("Question", null, contentValues);
            contentValues.put("topic_id", "t1");
            contentValues.put("question_id", "5");
            contentValues.put("question_content","血液透析複用用水的常規內毒素檢測應_____\"+\"\\n\"+\"至少一次。");
            contentValues.put("question_answer", 4);
            contentValues.put("question_s1", "A.不得重複使用");
            contentValues.put("question_s2", "B.可以重複使用");
            contentValues.put("question_s3", "C.部分貴重的可以重複使用，但必須進行嚴格的消毒滅菌");
            contentValues.put("question_s4", "D.每季");
            contentValues.put("question_explain",  "急性并发症分為：透析失衡综合征：主要发生于肌酐、尿素氮等毒素偏高明显患者。主要症状有恶心、呕吐、头痛、疲乏、烦躁不安等，严重者可有抽搐、震颤。首次使用综合征：主要是应用新透析器及管道所引起的。多发生在透析开始后几分钟至1小时左右，表现为呼吸困难，全身发热感，可突然心跳骤停。血栓的形成，有可能是因为抗凝不到位、长时间卧床，置管、管路、滤器等异物刺激血小板聚集。还可以发生透析中低血压、高血压、头痛、肌肉痉挛、心律失常等。");

            db.insert("Question", null, contentValues);
        }
        Cursor c = db.rawQuery("SELECT * FROM Question",null);
        if(c.getCount()>0) {
            c.moveToFirst();
            String s=c.getString(1)+"\\n"+c.getString(3)+"\\n"+c.getString(4)+"\\n"+c.getString(5)+"\\n"+c.getString(6)+"\\n";
        }

    }

    private void createExamTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Exam (exam_id INT, exam_date DateTime, exam_score INT,question_id INT, patient_id CHAR(10), nurse_id CHAR(10), PRIMARY KEY(exam_id), FOREIGN KEY(patient_id) REFERENCES Patient(patient_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(nurse_id) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(question_id) REFERENCES Question(question_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createAnswerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Answer (result INT, patient_answer INT, question_id INT, exam_id INT, PRIMARY KEY(exam_id, question_id), FOREIGN KEY(exam_id) REFERENCES Exam(exam_id)ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(question_id) REFERENCES Question(question_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    public void front(View v) {
        intent.setClass(this, fronttest.class);
        // intent.putExtra("name", Account.getText().toString());
        startActivity(intent);
        finish();
    }


}
