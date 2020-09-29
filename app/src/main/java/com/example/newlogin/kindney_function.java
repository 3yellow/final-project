package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

public class kindney_function extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindney_function);
        PDFView pdf=findViewById(R.id.pdfView);
        pdf.fromAsset("壹．腎臟功能簡介.doc.pdf").load();
    }
    public void tofronttest(View v){
        Intent i=new Intent(kindney_function.this,tbacktest.class);
        startActivity(i);
        finish();
    }
}
