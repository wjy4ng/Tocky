package com.cookandroid.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText txt_1, txt_2;
    Button btn_plus, btn_minus, btn_multi, btn_div, btn_remainder;
    TextView result;
    String a, b;
    int hap;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_1 = findViewById(R.id.txt_1);
        txt_2 = findViewById(R.id.txt_2);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_multi = findViewById(R.id.btn_multi);
        btn_div = findViewById(R.id.btn_div);
        btn_remainder = findViewById(R.id.btn_remainder);
        result = findViewById(R.id.result);

        btn_plus.setTextSize(20);
        btn_minus.setTextSize(20);
        btn_multi.setTextSize(20);
        btn_div.setTextSize(20);
        btn_remainder.setTextSize(20);
        result.setTextSize(20);
        result.setTextColor(Color.RED);

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = txt_1.getText().toString().trim();
                b = txt_2.getText().toString().trim();


                if (a.isEmpty() || b.isEmpty()){
                    Toast.makeText(MainActivity.this, "텍스트를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    hap = Integer.parseInt(a) + Integer.parseInt(b);
                    result.setText("계산 결과 : " + hap);
                }
            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = txt_1.getText().toString().trim();
                b = txt_2.getText().toString().trim();

                if (a.isEmpty() || b.isEmpty()){
                    Toast.makeText(MainActivity.this, "텍스트를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    hap = Integer.parseInt(a) - Integer.parseInt(b);
                    result.setText("계산 결과 : " + hap);
                }
            }
        });
        btn_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = txt_1.getText().toString().trim();
                b = txt_2.getText().toString().trim();

                if (a.isEmpty() || b.isEmpty()){
                    Toast.makeText(MainActivity.this, "텍스트를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    hap = Integer.parseInt(a) * Integer.parseInt(b);
                    result.setText("계산 결과 : " + hap);
                }
            }
        });
        btn_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = txt_1.getText().toString().trim();
                b = txt_2.getText().toString().trim();

                if (a.isEmpty() || b.isEmpty()){
                    Toast.makeText(MainActivity.this, "텍스트를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int num1 = Integer.parseInt(a);
                int num2 = Integer.parseInt(b);

                if (num1 == 0 || num2 == 0) {
                    Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    hap = num1 / num2;
                    result.setText("계산 결과 : " + hap);
                }
            }
        });
        btn_remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = txt_1.getText().toString().trim();
                b = txt_2.getText().toString().trim();

                if (a.isEmpty() || b.isEmpty()){
                    Toast.makeText(MainActivity.this, "텍스트를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int num1 = Integer.parseInt(a);
                int num2 = Integer.parseInt(b);

                if (num1 == 0 || num2 == 0) {
                    Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    hap = num1 % num2;
                    result.setText("계산 결과 : " + hap);
                }
            }
        });
    }
}
