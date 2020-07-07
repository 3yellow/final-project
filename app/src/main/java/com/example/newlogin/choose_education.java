package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class choose_education extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_education);
    }
    public void function(View v){
        Intent i=this.getIntent();
        int flag=i.getIntExtra("flag",0);
        if (flag==1){
             i=new Intent( this,fronttest.class);
            startActivity(i);
        }
        else {
             i=new Intent( this,kindney_function.class);
            startActivity(i);
        }
    }
    public void reason(View v){
        Intent i=this.getIntent();
        int flag=i.getIntExtra("flag",0);
        if (flag==1){
            i=new Intent( this,fronttest.class);
            startActivity(i);
        }
        else {
            i=new Intent( this,kidney_reason.class);
            startActivity(i);
        }
    }
}
