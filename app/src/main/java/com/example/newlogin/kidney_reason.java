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
        Intent i=new Intent(kidney_reason.this,tbacktest.class);
        startActivity(i);
        finish();
    }
}
