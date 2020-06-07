package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signature extends AppCompatActivity {

    Button btnsave, btnclear;
    mainview mainV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainV = new mainview(this);
        setContentView(R.layout.activity_signature);
        mainV = (mainview) findViewById(R.id.mainview);
        btnsave = (Button) findViewById(R.id.btn_save);
        btnclear = (Button) findViewById(R.id.btn_clear);
    }
    public void clear(View view) {
        mainV.onclear();
    }
    public void W(View v){
        Intent i=new Intent(signature.this,Wtheme.class);
        startActivity(i);
        finish();
    }

   /* protected void save(View view) {
        mainV.save();
    }*/
}
