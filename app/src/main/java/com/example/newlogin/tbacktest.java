package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class tbacktest extends AppCompatActivity {

    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbacktest);

        TextView Que=(TextView)findViewById(R.id.Question);
        final TextView Als=(TextView)findViewById(R.id.Analysis);
        RadioButton item1=(RadioButton)findViewById(R.id.icho1);
        RadioButton item2=(RadioButton)findViewById(R.id.icho2);
        RadioButton item3=(RadioButton)findViewById(R.id.icho3);
        RadioButton item4=(RadioButton)findViewById(R.id.icho4);

        Que.setText("1.血液透析室應當根據設備要求定期對水處理系統進行沖洗消毒，並定期進行水質檢測。每次沖洗消毒後均應_____，確保安全。");
        final String[] Choi = {"A.監測水中細菌量","B.測定管路中消毒液殘留量", "C.測定管路壓力", "D.不需要測定任何專案","B.測定管路中消毒液殘留量"};
        item1.setText("\t"+Choi[0]);
        item2.setText("\t"+Choi[1]);
        item3.setText("\t"+Choi[2]);
        item4.setText("\t"+Choi[3]);


        Als.setText("正確答案："+Choi[4]+"\n"+
                "急性并发症分為：\n"+
                "    透析失衡综合征：主要发生于肌酐、尿素氮等毒素偏高明显患者。主要症状有恶心、呕吐、头痛、疲乏、烦躁不安等，严重者可有抽搐、震颤。\n" +
                "\n"+
                "    首次使用综合征：主要是应用新透析器及管道所引起的。多发生在透析开始后几分钟至1小时左右，表现为呼吸困难，全身发热感，可突然心跳骤停。\n" +
                "\n"+
                "    血栓的形成，有可能是因为抗凝不到位、长时间卧床，置管、管路、滤器等异物刺激血小板聚集。\n" +
                "    还可以发生透析中低血压、高血压、头痛、肌肉痉挛、心律失常等。");
    }

    public void CHECK(View v) {
        TextView Als=(TextView)findViewById(R.id.Analysis);
        Button next=(Button)findViewById(R.id.button17);
        Als.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);

    }

    public void NEXT(View v) {
        intent.setClass(this, tbacktest2.class);
        // intent.putExtra("name", Account.getText().toString());
        startActivity(intent);
        finish();
    }
}
