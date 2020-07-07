package com.example.newlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class readpdf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readpdf);

        TextView txv=(TextView)findViewById(R.id.textView12);
        txv.setText("      花蓮慈濟靜思堂坐落于中央路的慈濟文化園區(花蓮慈濟醫院右側)，其創辦人證嚴上人期望以靜思堂來呈現慈濟人「無緣大慈」、「同體大悲」的慈悲濟世精神，為後世子弟保存慈濟的歷史足跡。 \n" +
                "\n" +
                "      慈濟靜思堂於1986年8月17日動土開工，全棟設計有十三層樓，自挑高的講經堂、慈濟道侶廣場、法華坡道、國際會議廳、感恩堂、慈濟世界、佛教文物展示室、宗教圖書館、史料館、藏經閣、瞭望台，每一層樓都有其佛教精神與慈濟世界的真善美，也彷彿像座慈濟歷史博物館，銘記了慈濟永恆的事蹟，倘若您來到花蓮，這裡非常值得您前來一遊。 \n");
    }
    public void tobacktest(View v){
        Intent i=new Intent(readpdf.this,backtest.class);
        startActivity(i);
    }
}
