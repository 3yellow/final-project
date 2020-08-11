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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    static final String Nurse="nurse"; //database table name
    Intent intent = new Intent();
    EditText editText, Account;
    ImageButton button;
    boolean canSee;
    SQLiteDatabase db;

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
<<<<<<< HEAD
                            intent.putExtra("nurseID",str);
=======
                            intent.putExtra("nurse",str);
>>>>>>> 5614fa123f1a6fbda43856c96be541372165e8ed
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
    }

    private void createPatientTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Patient (patient_id TEXT NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign TEXT, patient_birth DATE , patient_incharge TEXT NOT NULL, PRIMARY KEY(patient_id), FOREIGN KEY(patient_incharge) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createTopicTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Topic (topic_id INT, topic_name TEXT, PRIMARY KEY(topic_id))";
        db.execSQL(sql);
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
}
