package com.example.newlogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class fronttest3 extends AppCompatActivity {

    boolean result;//判斷答案對錯

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fronttest3);
        TextView Que = (TextView) findViewById(R.id.Question);
        final TextView YAns = (TextView) findViewById(R.id.YourAns);
        final TextView Als = (TextView) findViewById(R.id.Analysis);
        final RadioGroup ans = (RadioGroup) findViewById(R.id.Ans);
        final ScrollView scroll = (ScrollView) findViewById(R.id.roll);
        final Button next = (Button) findViewById(R.id.button12);
        Button dialog = (Button) findViewById(R.id.button);


        Que.setText("3.血液透析室應當建立嚴格的接診制度，對所有初次透析的患者進行乙型肝炎、病毒、丙型肝炎病毒、梅毒、愛滋病病毒感染的相關檢查，每_____複查1次。\n");
        final String[] Choi = {"A.月", "B.季度", "C.半年", "D.年", "C.半年"};

        for (int i = 0; i < 4; i++) {
            RadioButton tempButton = new RadioButton(this);
            tempButton.setPadding(40, 0, 0, 0);                 // 设置文字距离按钮四周的距离
            tempButton.setText(Choi[i]);
            tempButton.setTextSize(1,30);
            ans.addView(tempButton, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        ans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                RadioButton tempButton = (RadioButton) findViewById(checkedId); // 通过RadioGroup的findViewById方法，找到ID为checkedID的RadioButton
                // 以下就可以对这个RadioButton进行处理了
                YAns.setText("您的答案：" + tempButton.getText());
              //  YAns.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                if (tempButton.getText() == Choi[4])
                    result = true;
                else
                    result = false;
            }
        });
    }
      /*  Als.setText("正確答案："+Choi[4]+"\n"+
                "急性并发症分為：\n"+
                "    透析失衡综合征：主要发生于肌酐、尿素氮等毒素偏高明显患者。主要症状有恶心、呕吐、头痛、疲乏、烦躁不安等，严重者可有抽搐、震颤。\n" +
                "\n"+
                "    首次使用综合征：主要是应用新透析器及管道所引起的。多发生在透析开始后几分钟至1小时左右，表现为呼吸困难，全身发热感，可突然心跳骤停。\n" +
                "\n"+
                "    血栓的形成，有可能是因为抗凝不到位、长时间卧床，置管、管路、滤器等异物刺激血小板聚集。\n" +
                "    还可以发生透析中低血压、高血压、头痛、肌肉痉挛、心律失常等。");

        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                YAns.setVisibility(View.VISIBLE);
                scroll.setVisibility(View.VISIBLE);
                Als.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }
        });

    }



    private void showDialog(){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(fronttest3.this);
        normalDialog.setIcon(R.drawable.ic_launcher_background);
        normalDialog.setTitle("公佈答案");
        if(result==true)
            normalDialog.setMessage("恭喜你！回答正確！");
        else
            normalDialog.setMessage("選錯了！快來看看正確答案吧！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }*/
    public void tofronttest4(View v){
        Intent i=new Intent(fronttest3.this,fronttest4.class);
        startActivity(i);
        finish();
    }
}

