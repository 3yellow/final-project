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
        Intent i=this.getIntent();
        String nurseID=i.getStringExtra("nurseID");
        String id=i.getStringExtra("id");
        Q_array=i.getStringArrayExtra("Q_array");
        i=new Intent(kidney_reason.this,backtest.class);
        i.putExtra("nurseID",nurseID);
        i.putExtra("id",id);
        i.putExtra("Q_array",Q_array);
        startActivity(i);
        finish();
    }
}
