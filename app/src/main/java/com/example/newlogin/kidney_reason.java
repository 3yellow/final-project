package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

public class kidney_reason extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidney_reason);
        PDFView pdf=findViewById(R.id.pdfView);
        pdf.fromAsset("貳．腎衰竭的原因.doc.pdf").load();
    }
    public void onClick(View v){
        String Q_array[]=new String[5];
        Intent intent=this.getIntent();
        String nurseID=intent.getStringExtra("nurseID");
        String id=intent.getStringExtra("id");
        String exam_id=intent.getStringExtra("exam_id");
        String health_education=intent.getStringExtra("health education");
        int score=intent.getIntExtra("score",0);
        int count=intent.getIntExtra("count",0);
        Q_array=intent.getStringArrayExtra("Q_array");
        intent=new Intent(this,backtest.class);
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
