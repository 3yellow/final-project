package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class consent extends AppCompatActivity {
    String nurseID,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);
        Intent i=this.getIntent();
        nurseID =i.getStringExtra("nurseID");
        id=i.getStringExtra("id");
    }
    public void onclick(View v){

        Intent i=new Intent(consent.this,signature.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        startActivity(i);
        finish();
    }
}

