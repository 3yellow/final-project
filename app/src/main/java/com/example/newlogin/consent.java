package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class consent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);
    }
    public void onclick(View v){
        Intent i=this.getIntent();
        String nurseID =i.getStringExtra("nurseID");
        String id=i.getStringExtra("id");
        i=new Intent(consent.this,signature.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        startActivity(i);
        finish();
    }
}

