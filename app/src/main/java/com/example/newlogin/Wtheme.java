package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Wtheme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wtheme);
    }
    public void V(View v){
        Intent i=new Intent(Wtheme.this,Vtheme.class);
        startActivity(i);
    }
    public void function(View v){
        Intent i=new Intent(Wtheme.this,kindney_function.class);
        startActivity(i);
    }
    public void rea(View v){
        Intent i=new Intent(Wtheme.this,kidney_reason.class);
        startActivity(i);
    }
}
