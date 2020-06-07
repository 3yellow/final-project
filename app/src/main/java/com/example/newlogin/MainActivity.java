package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    Intent intent = new Intent();
    EditText editText,Account;
    ImageButton button;
    boolean canSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Account= (EditText) findViewById(R.id.editText);
        editText= (EditText) findViewById(R.id.editText2);
        button= (ImageButton) findViewById(R.id.change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过全局的一个变量的设置，这个就是判断控件里面的内容是不是能被看到
                if (canSee==false){
                    //如果是不能看到密码的情况下，
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    canSee=true;
                }else {
                    //如果是能看到密码的状态下
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    canSee=false;
                }
            }
        });
    }
    public void choicepatient(View v){
        intent.setClass(this, Searchlogin.class);
        intent.putExtra("name", Account.getText().toString());
        startActivity(intent);
        finish();
    }
    public  void  back(View v){
        Intent i=new Intent(MainActivity.this,Backstage_main.class);
        startActivity(i);
        finish();
    }
}
