package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class backtestanalysis extends AppCompatActivity {
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backtestanalysis);
        TextView Que=(TextView)findViewById(R.id.Question);
        final TextView Als=(TextView)findViewById(R.id.Analysis);
        final RadioGroup ans=(RadioGroup)findViewById(R.id.Ans);
        final ScrollView scroll=(ScrollView)findViewById(R.id.roll);
        final Button next=(Button)findViewById(R.id.button16);
        RadioButton item1=(RadioButton)findViewById(R.id.radioButton9);
        RadioButton item2=(RadioButton)findViewById(R.id.radioButton10);
        RadioButton item3=(RadioButton)findViewById(R.id.radioButton11);
        RadioButton item4=(RadioButton)findViewById(R.id.radioButton12);

        Intent intent =getIntent();
        /*取出Intent中附加的数据*/
        String question= intent.getStringExtra("qustion");
        Que.setText(question);
        String A= intent.getStringExtra("itemA");
        String B= intent.getStringExtra("itemB");
        String C= intent.getStringExtra("itemC");
        String D= intent.getStringExtra("itemD");
        item1.setText(A);
        item2.setText(B);
        item3.setText(C);
        item4.setText(D);
    }
    public void backmenu(View v) {
        intent.setClass(this, Searchlogin.class);
        startActivity(intent);
        finish();
    }
}
