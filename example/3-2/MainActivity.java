package com.cookandroid.baseapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_nate, btn_911, btn_gallery, btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // xml 파일의 버튼 객체 연결
        btn_nate = findViewById(R.id.btn_nate);
        btn_911 = findViewById(R.id.btn_911);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_exit = findViewById(R.id.btn_exit);

        // 버튼 색상 지정
        btn_nate.setBackgroundColor(Color.GRAY);
        btn_911.setBackgroundColor(Color.GREEN);
        btn_gallery.setBackgroundColor(Color.RED);
        btn_exit.setBackgroundColor(Color.YELLOW);

        btn_nate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.nate.com"));
                startActivity(mIntent);
            }
        });

        btn_911.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/911"));
                startActivity(mIntent);
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(mIntent);
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
}