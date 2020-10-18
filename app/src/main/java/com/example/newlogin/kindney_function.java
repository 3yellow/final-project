package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

public class kindney_function extends AppCompatActivity {

    String nurseID;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindney_function);
        PDFView pdf=findViewById(R.id.pdfView);

        pdf.fromAsset("014.顧腰子好朋友-呷哈咪.pdf").load();//壹．腎臟功能簡介.doc.pdf
    }
    public void tofronttest(View v){
        String Q_array[]=new String[5];
        Intent intent=this.getIntent();
        nurseID=intent.getStringExtra("nurseID");
        id=intent.getStringExtra("id");
        String exam_id=intent.getStringExtra("exam_id");
        String health_education=intent.getStringExtra("health education");
        int score=intent.getIntExtra("score",0);
        int count=intent.getIntExtra("count",0);
        Q_array=intent.getStringArrayExtra("Q_array");
        intent=new Intent(kindney_function.this,backtest.class);
        intent.putExtra("count",count);
        intent.putExtra("score",score);
        intent.putExtra("nurseID",nurseID);
        intent.putExtra("id",id);
        intent.putExtra("exam_id",exam_id);
        intent.putExtra("Q_array",Q_array);
        startActivity(intent);
        finish();
    }
}
