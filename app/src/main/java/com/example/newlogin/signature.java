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
        Intent i=this.getIntent();
        String nurseID =i.getStringExtra("nurseID");
        String id=i.getStringExtra("id");
        i=new Intent(signature.this,choose_education.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("flag",1);//要前側
        startActivity(i);
        finish();
    }

   /* protected void save(View view) {
        mainV.save();
    }*/
}
