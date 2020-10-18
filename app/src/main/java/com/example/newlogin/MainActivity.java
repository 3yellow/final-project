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
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    static final String Nurse="nurse"; //database table name
    EditText editText, Account;
    Intent intent=new Intent();
    ImageButton button;
    boolean canSee;
    SQLiteDatabase db;
    int flag=0;
    Cursor cu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        db = openOrCreateDatabase("DBS", Context.MODE_PRIVATE, null);
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

    private void insertQuestion(String question_id,String content,String s1,String s2,String explain, String topic_id,String An)
    {
        ContentValues cv =new ContentValues(1);//10
        cv.put("question_id",question_id);
        cv.put("question_content",content);
        cv.put("question_answer",An);
        cv.put("question_s1",s1);
        cv.put("question_s2",s2);
        cv.put("question_explain",explain);
        cv.put("topic_id",topic_id);
        db.insert("Question", null, cv);
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
        String pas=editText.getText().toString().trim();
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
                            intent.putExtra("pad",1); //修改狀態時 要分辨是哪一台電腦用的。
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

    private void insertTopic(String topic_id,String topic_name )
    {
        ContentValues cv =new ContentValues(1);//10
        cv.put("topic_id",topic_id);
        cv.put("topic_name",topic_name);
        db.insert("Topic", null, cv);
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
                            Intent i = new Intent(MainActivity.this, Menu.class);
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
        String sql = "CREATE TABLE IF NOT EXISTS Nurse (nurse_id TEXT NOT NULL, nurse_name TEXT NOT NULL, nurse_password TEXT NOT NULL, nurse_authority INT NOT NULL,change_data INT,  PRIMARY KEY(nurse_id))";
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
        String sql = "CREATE TABLE IF NOT EXISTS Patient (patient_id TEXT NOT NULL, patient_name TEXT NOT NULL, patient_gender INT, patient_register DATE, patient_sign TEXT, patient_birth DATE , patient_incharge TEXT NOT NULL,change_data INT,  PRIMARY KEY(patient_id), FOREIGN KEY(patient_incharge) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createTopicTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Topic (topic_id TEXT, topic_name TEXT,change_data INT,  PRIMARY KEY(topic_id))";
        db.execSQL(sql);
        ContentValues contentValues = new ContentValues(1);
        Cursor cursor = db.rawQuery("SELECT * FROM Topic", null);
        if (!cursor.moveToFirst()) {
            insertTopic("t1","壹.腎臟功能簡介.pdf");//這個之後要改成t1
            insertTopic("t2","貳.甚麼是慢性腎臟病.pdf");
            insertTopic("t3","參.健康人如何保護自己健康.pdf");
            insertTopic("t4","肆.腎衰竭的原因.pdf");
            insertTopic("t5","伍.腎衰竭原因治療.pdf");
            insertTopic("t6","陸.血液透析治療與照護.pdf");
            insertTopic("t7","柒.何為血液透析.pdf");
            insertTopic("t8","捌.動靜脈廔管的照顧.pdf");
            insertTopic("t9","玖.腎友如何預防便祕.pdf");
            insertTopic("t10","拾.甚有皮膚搔癢該如何處理.pdf");
            insertTopic("t11","拾壹.乾體重.pdf");
            insertTopic("t12","拾貳.血管通路的介紹.pdf");
            insertTopic("t13","拾參.飲食控制要點.pdf");
        }
    }

    private void createStudyTable()//閱讀紀錄
    {
        String sql = "CREATE TABLE IF NOT EXISTS Study (study_id TEXT, study_date DateTime, topic_id TEXT, patient_id TEXT, nurse_id TEXT,change_data INT,  PRIMARY KEY(study_id), FOREIGN KEY (topic_id) REFERENCES Topic(topic_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY (patient_id) REFERENCES Patient(patient_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY (nurse_id) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createQuestionTable()
    {
        //String question_id,String content,String s1,String s2,String s3,String s4,String explain, String topic_id,String An
        String sql = "CREATE TABLE IF NOT EXISTS Question (question_id TEXT, question_content TEXT, question_answer TEXT, question_s1 TEXT, question_s2 TEXT, question_explain TEXT, exam_id TEXT, topic_id TEXT,change_data INT,  PRIMARY KEY(question_id), FOREIGN KEY(exam_id) REFERENCES Exam(exam_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(topic_id) REFERENCES Topic(topic_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
        Cursor cursor = db.rawQuery("SELECT * FROM Question", null);
        if (!cursor.moveToFirst()) {
            //t1的部分
            insertQuestion( "1", "腎臟是一對位於後腹腔的器官,右腎較左腎略低。", "1", "0",  "", "t1", "1");
            insertQuestion( "2", "腎臟是人體主要的排毒器官，負責清除血液中的代謝廢物。", "1", "0",   "", "t1", "1");
            insertQuestion( "3", "腎臟所分泌的腎素（Renin），為調節血壓維持恆定重要的荷爾蒙。", "1", "0",   "", "t1", "1");
            insertQuestion( "4", "每個人每天約有3,000毫升（ML）的尿液。", "1", "0","每天約有1,500～2,000毫升（ML）的尿液。", "t1", "0");
            insertQuestion( "5", "體內水份過多排不出時，血壓也可能會升高。", "1", "0","", "t1", "1");
            insertQuestion( "6", "當腎臟功能喪失，會導致體內毒素無法排除，造成尿毒症。", "1","", "", "t1", "1");
            insertQuestion( "7", "腎絲球廓清率下降至每分鐘30～35毫升時，稱做末期腎衰竭(尿毒)。", "1", "0", "是屬於中度慢性腎臟病，如控制不好，離末期腎衰竭也不遠了。", "t1", "0");
            insertQuestion( "8", "腎絲球廓清率下降至每分鐘5～15毫升時，稱做末期腎衰竭(尿毒)。", "1", "0",  "", "t1", "1");
            insertQuestion( "9", "腎臟功能喪失，鉀離子排泄下降，嚴重時會導致死亡。", "1", "0", "", "t1", "1");
            insertQuestion( "10", "腎臟功能喪失，不會造成貧血。", "1", "0", "腎臟功能下降時，會影響血球的功能，且會影響紅血球生素的製造，故會產生貧血。", "t1", "0");
            insertQuestion( "11", "腎臟功能不良時，初期容易感到疲倦、倦怠、食慾不振、失眠等症狀。", "1", "0", "", "t1", "1");

            //t2的部分
            insertQuestion( "12", "腎臟受損超過三個月，結構或功能無法恢復正常時，稱為「急性腎損傷」。", "1", "0", "為慢性腎臟病才對。", "t2", "0");
            insertQuestion( "13", "腎臟受損超過三個月，結構或功能無法恢復正常時，稱為「慢性」腎臟病。", "1", "0", "", "t2", "1");
            insertQuestion( "14", "一旦發生慢性腎臟病，現行醫學並無法恢復腎功能，只能減緩腎臟功能衰退的速度。", "1", "0", "", "t2", "1");
            insertQuestion( "15", "急性腎損傷經過適當的治療，大部分可使腎功能恢復正常。", "1", "0", "", "t2", "1");
            insertQuestion( "16", "腎臟病徵兆的口訣：泡、水、高、貧、倦。", "1", "0", "", "t2", "1");
            insertQuestion( "17", "當腎絲球過濾率衰退至每分鐘30-59毫升，就需要開始透析治療。", "1", "0", "腎絲球過濾率小於每分鐘15毫升，才需依臨床症狀決定是否立即透析治療。", "t2", "0");
            insertQuestion( "18", "慢性腎衰竭，小便常可以看到泡泡尿。", "1", "0", "", "t2", "1");
            insertQuestion( "19", "當看到小便出現許多泡泡，可能是因為解尿衝擊造成的的水波，沒有關係。", "1", "0", "", "t2", "0");
            insertQuestion( "20", "慢性腎衰竭常發生高血鉀，但不會致死。", "1", "0", "高血鉀會產生心律不整，嚴重會致死。", "t2", "0");
            insertQuestion( "21", "腎衰竭病人口腔有金屬味或尿味，主要是牙周病。", "1", "0", "是體內含氮廢物未能排除所導致的味道。", "t2", "0");
            insertQuestion( "22", "腎衰竭病人水腫常見於下肢與腳踝水腫以及晨間眼部浮腫。", "1", "0", "", "t2", "1");

            //t3的部分
            insertQuestion( "23", "有病治病，無病強身，所以補品和健康食品都可以多吃無害。", "1", "0", "補品及健康食品吃過多時，仍可能造成腎臟負擔，且許多的添加也可能會導致腎臟傷害。", "t3", "0");
            insertQuestion( "24", "補藥、地攤藥、「健康食品」、減肥藥、止痛劑、類固醇、抗生素及不明來歷的藥品等，這些都可能傷害我們的腎臟。", "1", "0", "", "t3", "1");
            insertQuestion( "25", "美國糖尿病學會建議三餐前全血血糖控制在80-120毫克/毫升。", "1", "0", "", "t3", "1");
            insertQuestion( "26", "即使大吃大喝，只要每天都有在運動，並不會影響身體健康。", "1", "0", "過度大吃大喝本來就對身體造成負荷影響健康。", "t3", "0");
            insertQuestion( "27", "當收縮壓常態性超過140毫米汞柱時，代表已經有輕度的高血壓跡象。", "1", "0", "", "t3", "1");
            insertQuestion( "28", "高血壓前期，還不至於高血壓，所以可以不管他。", "1", "0", "如未積極介入預防，會加快高血壓的進程。", "t3", "0");
            insertQuestion( "29", "腎臟保養之道應該要生活起居正常、避免腎毒性物質（如止痛劑、顯影劑、消炎藥）、攝取充足的水份、多運動等。", "1", "0", "", "t3", "1");
            insertQuestion( "30", "因為時常會頭痛，所以固定吃止痛藥沒關係。", "1", "0", "長期服用止痛藥，就可能會造成腎臟病變。", "t3", "0");
            insertQuestion( "31", "常態性收縮壓在120-139，舒張壓80-89毫米汞柱，代表有高血壓前期的癥症。", "1", "0", "", "t3", "1");
            insertQuestion( "32", "適當的運動有助於血壓及血糖的控制。", "1", "0", "", "t3", "1");

            //t4的部分
            insertQuestion( "33", "糖尿病腎病變已變為末期腎衰竭之首要原因。", "1", "0", "", "t4", "1");
            insertQuestion( "34", "血壓控制不良，也會造成腎臟功能惡化。", "1", "0", "", "t4", "1");
            insertQuestion( "35", "抽菸會增加致癌的風險，也會對腎臟的血管造成傷害。", "1", "0", "", "t4", "1");
            insertQuestion( "36", "抽菸主要傷害肺部，不會對腎臟血管有傷害。", "1", "0", "吸菸會影響腎臟動脈的變化，使腎臟血管收縮及血流速度減少，刺激血管收縮的功能，使血壓上昇，而且也會增加蛋白尿的情形加重，造成腎功能惡。", "t4", "0");
            insertQuestion( "37", "腎臟功能隨著老化的過程逐年衰退，所以要小心愛護。", "1", "0", "", "t4", "1");
            insertQuestion( "38", "老化也會讓腎臟功能下降。", "1", "0", "", "t4", "1");
            insertQuestion( "38", "老化也會讓腎臟功能下降。", "1", "0", "", "t4", "1");
        }
    }

    private void createExamTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Exam (exam_id TEXT, exam_date DateTime, exam_score INT, patient_id TEXT, nurse_id TEXT,change_data INT,  PRIMARY KEY(exam_id), FOREIGN KEY(patient_id) REFERENCES Patient(patient_id) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(nurse_id) REFERENCES Nurse(nurse_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    private void createAnswerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Answer (result INT, patient_answer INT, question_id INT, exam_id INT,change_data INT,  PRIMARY KEY(exam_id, question_id), FOREIGN KEY(exam_id) REFERENCES Exam(exam_id)ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(question_id) REFERENCES Question(question_id) ON DELETE SET NULL ON UPDATE CASCADE)";
        db.execSQL(sql);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
