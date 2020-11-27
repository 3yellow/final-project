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
            insertQuestion( "39", "家族中若有人患有腎臟病，則家人得到腎臟病的機會較高。", "1", "0", "", "t4", "1");
            insertQuestion( "40", "經常服用消炎類止痛藥不會造成腎臟傷害。", "1", "0", "這類藥物影響腎臟血液灌流，破壞腎臟自我調節的機制而造成急性腎衰竭，此外，也會對腎小管造成破壞而產生間質性腎炎。", "t4", "0");
            insertQuestion( "41", "尿酸會沉積在腎組織，影響腎功能。", "1", "0", "", "t4", "1");
            insertQuestion( "42", "引起痛風主要的原因是尿酸過低。", "1", "0", "尿酸過高。", "t4", "0");
            insertQuestion( "43", "前列腺良性肥大導致尿路阻塞會影響腎臟功能。", "1", "0", "", "t4", "1");
            insertQuestion( "44", "血壓高，不會影響腎臟的問題。", "1", "0", "會加重血管硬化，影響腎臟功能。", "t4", "0");
            insertQuestion( "45", "中藥藥性溫和，多吃並不會造成腎臟傷害。", "1", "0", "過多無法排除的藥物，均可能造成腎臟傷害。", "t4", "0");
            insertQuestion( "46", "中藥裏的補藥，既然是補藥，當然多吃無害。", "1", "0", "補藥過量也會造成腎臟無法負荷，產生腎臟傷害。", "t4", "0");
            insertQuestion( "47", "腎臟反覆發炎時，只要治好，腎臟功能就都不會有影響。", "1", "0", "死亡的腎臟細胞是無法再生的，腎臟反覆發炎，表示反覆受傷，會造成腎功能下降。", "t4", "0");

            //t5
            insertQuestion( "48", "血液透析及腹膜透析都是腎衰竭的替代療法。", "1", "0", "", "t5", "1");
            insertQuestion( "49", "腎移植不是腎衰竭的替代療法。", "1", "0", "", "t5", "1");
            insertQuestion( "50", "血液透析俗稱「洗腎」，意思就是把腎臟拿出來洗。", "1", "0", "不是將腎臟拿出來洗，而是透過機器、人工腎臟透析器、透析液，經過毒素的交換及移除，排除體內廢物，達成洗腎的目的。", "t5", "0");
            insertQuestion( "51", "腹膜透析可以在家中自行操作，達到洗腎的目的。", "1", "0", "", "t5", "1");
            insertQuestion( "52", "腎移植時，是把原來的腎臟拿掉，換一個新的腎臟。", "1", "0", "是在骨盆腔的腸骨窩處植入另一個健康的腎臟，觸摸腹股溝上方即可摸到新的腎臟。", "t5", "0");
            insertQuestion( "53", "換腎時新的移植腎臟位置是放在腸骨窩處。", "1", "0", "", "t5", "1");
            insertQuestion( "54", "腎臟移植分有親屬捐腎或屍腎移植。", "1", "0", "", "t5", "1");
            insertQuestion( "55", "血液透析洗得比腹膜透析好。", "1", "0", "兩者的治療都可以達到一定毒素的清除，主要仍需依病患的需求及方便性，選擇合適的方法。", "t5", "0");
            insertQuestion( "56", "保守療法主要在控制含氮廢物的產生。", "1", "0", "", "t5", "1");
            insertQuestion( "57", "保守療法必需要控制原發疾病的問題及低蛋白飲食。", "1", "0", "", "t5", "1");

            //t6



            //t7
            insertQuestion( "", "血液透析就是一般人俗稱的洗腎。", "1", "0", "", "t7", "1");
            insertQuestion( "", "血液透析一週需要治療三次，每次4小時。", "1", "0", "", "t7", "1");
            insertQuestion( "", "一旦需要長期透析治療，就要執行動靜脈廔管手術。", "1", "0", "", "t7", "1");
            insertQuestion( "", "血液透析是利用濃度及壓力差，進行物質交換，來矯正因水份、電解質、酸鹼及有毒物質所引起的內在環境改變。", "1", "0", "", "t7", "1");
            insertQuestion( "", "洗腎就是把腎臟拿出來洗。", "1", "0", "不是將腎臟拿出來洗，而是透過機器、人工腎臟透析器、透析液，經過毒素的交換及移除，排除體內廢物，達成洗腎的目的。", "t7", "0");
            insertQuestion( "", "洗腎可以不舒服再到醫院洗，不用規則洗。。", "1", "0", "沒有規則透析，一容易發生來不及透析的危險，也容易導致其他合併症的發生，造成更多的不舒服。", "t7", "0");
            insertQuestion( "", "小便量還很多，所以可以不需要常規洗腎。", "1", "0", "洗腎不是只有排除水份的功能，還包括體內廢物毒素的排除。", "t7", "0");
            insertQuestion( "", "人工腎臟透析器可以完全取代腎臟的功能。", "1", "0", "人工腎臟只能過濾掉大部份體內產生的廢物，仍有許多物質不容易被排除，因此不能完全取代腎臟功能。", "t7", "0");
            insertQuestion( "", "緊急血液透析時的血管通路可先插暫時性的雙腔導管。", "1", "0", "", "t7", "1");
            insertQuestion( "", "人工腎臟透析器的作用就是在移除體內多餘的毒素及水份。", "1", "0", "", "t7", "1");

            //t8
            insertQuestion( "", "熱敷可以保養血管，所以愈熱愈好、愈久愈好。", "1", "0", "熱敷太久及太熱，都可能導致皮膚燙傷。", "t8", "0");
            insertQuestion( "", "透析前應於血管廔管處，以肥皂清潔乾淨，降低穿刺感染機會。", "1", "0", "", "t8", "1");
            insertQuestion( "", "為避免打針會痛，所以注射的位置不要輪流更換。", "1", "0", "要輪流更換，以免傷口不易癒合及容易發生血管狹窄及假性動脈瘤的機會。", "t8", "0");
            insertQuestion( "", "透析結束止血後，為避免血管阻塞，應立即開始握球運動。", "1", "0", "要確認完全止血後才適合再開始握球運動，如不能確認則隔日再行握球運動。", "t8", "0");
            insertQuestion( "", "只要洗腎前檢查廔管是否有震顫感或聽看看是否有嘈音即可。", "1", "0", "應該每天檢查，以防血管突然間喪失功能，來不及處理血管問題。", "t8", "0");
            insertQuestion( "", "洗腎前應確認廔管表面的皮膚是否完整無任何傷口。", "1", "0", "", "t8", "1");
            insertQuestion( "", "每天有洗澡，就代表手部及廔管處是乾淨的不需再特別清潔。", "1", "0", "仍要需要清潔，因為仍會流汗或者工作造成手部髒污。", "t8", "0");
            insertQuestion( "", "打針時，如果手部清潔沒有做好，容易造成血管廔管感染，嚴重甚至引發心內膜炎。", "1", "0", "", "t8", "1");
            insertQuestion( "", "打針前，手部的髒污，靠消毒就可以了，不須特別洗手及清潔廔管。", "1", "0", "手部如果太髒，光靠消毒，打針時仍可能將細菌帶入，先用肥皂清洗乾淨後再消毒，可以加強消毒的成效。", "t8", "0");
            insertQuestion( "", "養成洗腎前洗清洗血管廔管，可以將感染的風險降低。", "1", "0", "", "t8", "1");

            //t9
            insertQuestion( "", "每天要規律運動，可促進腸蠕動。", "1", "0", "", "t9", "1");
            insertQuestion( "", "養成定時排便的習慣，較不容易發生便秘的情形", "1", "0", "", "t9", "1");
            insertQuestion( "", "排便順暢，較不容易影響體內水分精確的計算，引起透析中的超濾不當。", "1", "0", "", "t9", "1");
            insertQuestion( "", "要養成規律服藥，所以已經腹瀉了，仍要吃緩瀉或軟便藥。", "1", "0", "已經腹瀉就不要再吃，以免加重腹瀉症狀。", "t9", "0");
            insertQuestion( "", "緩瀉或軟便藥，有助於排泄，所以需要在洗腎前服用。", "1", "0", "有可能會造成透析中急著想解便不適，甚至來不及收機去解便，而解在褲子裏的窘境。", "t9", "0");
            insertQuestion( "", "服用緩瀉或軟便藥，發生排便次數過多是正常情形，因此繼續服用就對了。", "1", "0", "應告知醫師調整藥物量。", "t9", "0");
            insertQuestion( "", "多食用富含水溶性纖維食物，有助於排便順暢。", "1", "0", "", "t9", "1");
            insertQuestion( "", "過度用力排便可能導致隱藏性心腦血管事件發生危及生命。", "1", "0", "", "t9", "1");
            insertQuestion( "", "糞便停留時間太長，鉀離子排除的途徑相對下降。", "1", "0", "", "t9", "1");
            insertQuestion( "", "腎友常需要服用的鈣片、胃乳片、鐵劑，容易造成便秘，所以乾脆不要吃就好了。", "1", "0", "鈣片、胃乳片、鐵劑，仍屬於腎友治療的一部份，不吃反而可能造成其他合併症，因此仍需要按時服用，而所導致的便秘問題，則只能靠服用緩瀉或軟便藥治療。", "t9", "0");

            //t10
            insertQuestion( "", "末期腎衰竭病人，有時皮膚上可見粉末狀的沈積物，稱為尿毒霜。", "1", "0", "", "t10", "1");
            insertQuestion( "", "尿毒霜的沈積物容易導致汗腺的萎縮與破壞，導致皮膚更癢。", "1", "0", "", "t10", "1");
            insertQuestion( "", "末期腎衰竭時，皮膚上粉末狀的沈積物，是因為洗澡洗不乾淨造成的。", "1", "0", "皮膚上可見粉末狀的沈積物，通常是因為身體內磷質太高，導致沈積在皮膚上，稱為尿毒霜。", "t10", "0");
            insertQuestion( "", "不要用太熱的水及避免用肥皂，可減少皮膚上的油脂洗得太乾淨，增加搔癢的症狀。", "1", "0", "", "t10", "1");
            insertQuestion( "", "棉質較不刺激皮膚的衣物，可減輕皮膚搔癢的刺激。。", "1", "0", "", "t10", "1");
            insertQuestion( "", "皮脂腺或汗腺萎縮會讓皮膚更乾躁。", "1", "0", "", "t10", "1");
            insertQuestion( "", "乳液選擇愈油的擦，皮膚較不會癢。", "1", "0", "保濕性佳親水性的乳液效果較好。", "t10", "0");
            insertQuestion( "", "透析病人因血小板功能較差，且透析時常需使用抗凝劑，容易抓破流血難止，因此要儘量避免用力抓癢。", "1", "0", "", "t10", "1");
            insertQuestion( "", "高磷會加重皮膚搔癢的情形。", "1", "0", "", "t10", "1");
            insertQuestion( "", "用熱水洗澡，可以減輕搔癢情形。", "1", "0", "不可過熱，太熱會讓皮膚表皮的油脂洗掉更多，失去滋潤效果。", "t10", "0");

            //t11
            insertQuestion( "", "乾體重意思就是：洗完腎、脫完水、患者沒有不舒服，而且透析前血壓正常，透析後血壓不會過低，最低的適合體重。", "1", "0", "", "t11", "1");
            insertQuestion( "", "兩次透析間體重的增加以不超過乾體重的5%為主。", "1", "0", "", "t11", "1");
            insertQuestion( "", "當食慾變好，體重容易增加，所以要調低乾體重。", "1", "0", "要調高才對。", "t11", "0");
            insertQuestion( "", "洗腎前、洗腎後量體重，身上的裝備要相同，以免造成誤差。", "1", "0", "", "t11", "1");
            insertQuestion( "", "透析中吃東西不需要加入脫水量。", "1", "0", "透析進食，食物和飲品也要秤重，並加入脫水量中，才不會設量的脫水有誤差。", "t11", "0");
            insertQuestion( "", "量體重時，體重計可以任意擺放。", "1", "0", "量體重的磅秤最好放在固定的位置，避免移來移去影響準確度。", "t11", "0");
            insertQuestion( "", "量體重時，要扶著牆壁，以預防跌倒。", "1", "0", "量體重時，身體不可靠牆壁，但可以扶體重機的扶手。", "t11", "0");
            insertQuestion( "", "發生耳鳴、頭暈、口乾舌燥、胸悶、血壓下降、抽筋等情形，可能是長胖了。", "1", "0", "", "t11", "1");
            insertQuestion( "", "乾體重若控制得好，可以避免心臟衰竭及減少合併症的發生。", "1", "0", "", "t11", "1");
            insertQuestion( "", "量體重前，一定要先確定體重計上的指標是否歸零。", "1", "0", "", "t11", "1");

            //t12
            insertQuestion( "", "廔管開刀的手可以搬重物及運動，再大的力量也不會影響。", "1", "0", "廔管的手若搬過重的重物，可能會導致血流的阻斷，使血管沒有功能。", "t12", "0");
            insertQuestion( "", "瘻管的手可以做治療，例如：打針、抽血、量血壓等。", "1", "0", "要避免，以免受傷，影響功能。", "t12", "0");
            insertQuestion( "", "瘻管開刀的手仍可以當枕頭墊，並不會傷害血管。", "1", "0", "會造成廔管血管壓迫，阻斷血流使血管沒有功能。", "t12", "0");
            insertQuestion( "", "透析前清洗廔管，可以降低感染的機會。", "1", "0", "", "t12", "1");
            insertQuestion( "", "透析中發現打針的部位疼痛並漏血，應馬上跟醫護人員反應。", "1", "0", "", "t12", "1");
            insertQuestion( "", "視血管的健康程度，可每3或6 個月定期安排看診心臟血管內或外科，可提前覺查是否有異常。", "1", "0", "", "t12", "1");
            insertQuestion( "", "廔管的手如果發現紅、腫、熱、痛的現象要立即告知醫護人員。", "1", "0", "", "t12", "1");
            insertQuestion( "", "洗腎結束拔掉穿刺針，穿刺處用手加壓越大力越好，才不會流血。", "1", "0", "過度加壓，會使廔管的血管被壓扁，影響功能，嚴重可能使血管損壞。", "t12", "0");
            insertQuestion( "", "透析血管通路是透析病人的第二生命，損壞就無法透析。", "1", "0", "", "t12", "1");
            insertQuestion( "", "每隔兩天會洗腎，所以瘻管是否通暢無阻塞，由護理師檢查就好了，不用擔心。", "1", "0", "應該每天都要自我檢查，等透析當日才由護理師檢查，如果已經沒有功能，當日就無法進行透析。", "t12", "0");
            insertQuestion( "", "需要長期透析的病人，需要建立永久性的血管通路較佳。", "1", "0", "", "t12", "1");
            insertQuestion( "", "吸煙不會影響血管廔管的健康。", "1", "0", "吸菸會造成血管收縮及血流速度減少，影響廔管的健康。", "t12", "0");
            insertQuestion( "", "熱敷時，愈熱才會愈有效果。", "1", "0", "容易燙傷以外，更可能損壞廔管功能。", "t12", "0");
            insertQuestion( "", "廔管部位要熱敷時，溫度要請家人看是否會太熱，以免燙傷。", "1", "0", "", "t12", "1");
            insertQuestion( "", "握球運動可以增強血流及增強肌肉組織。", "1", "0", "", "t12", "1");

            //t13
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
