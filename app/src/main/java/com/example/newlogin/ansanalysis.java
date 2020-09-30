package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class ansanalysis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ansanalysis);
    }
    //不能返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
    }
    public void reaf(View v){
        Intent i=this.getIntent();
        String nurseID=i.getStringExtra("nurseID");
        String id=i.getStringExtra("id");
        i=new Intent(ansanalysis.this,Searchlogin.class);
        i.putExtra("nurseID",nurseID);
        //i.putExtra("id",id);
        startActivity(i);
        finish();
    }
}
