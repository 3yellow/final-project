package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ansanalysis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ansanalysis);
    }
    public void reaf(View v){
        Intent i=new Intent(ansanalysis.this,Searchlogin.class);
        startActivity(i);
        finish();
    }
}
