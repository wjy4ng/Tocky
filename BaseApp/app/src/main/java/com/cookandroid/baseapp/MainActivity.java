    package com.cookandroid.baseapp;

    import android.content.Intent;
    import android.graphics.Color;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.MotionEvent;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    public class MainActivity extends AppCompatActivity {
        EditText t1, t2;
        Button plus, minus, multiple, divide;
        TextView Txt_result;
        String num1, num2;
        Integer result;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            // 제목 설정
            setTitle("텍스트 계산기");

            // 위젯 연결
            t1.findViewById(R.id.t1);
            t2.findViewById(R.id.t2);
            plus.findViewById(R.id.plus);
            minus.findViewById(R.id.minus);
            multiple.findViewById(R.id.multiple);
            divide.findViewById(R.id.divide);
            Txt_result.findViewById(R.id.Txt_result);

            // 버튼 색상 변경
            plus.setBackgroundColor(Color.RED);
            minus.setBackgroundColor(Color.GRAY);
            multiple.setBackgroundColor(Color.YELLOW);
            divide.setBackgroundColor(Color.GREEN);

            // plus 클릭시
            plus.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    num1 = t1.getText().toString();
                    num2 = t2.getText().toString();
                    result = Integer.parseInt(num1) + Integer.parseInt(num2);
                    Txt_result.setText("계산 결과 : "+ result.toString());
                    return false;
                }
            });
        }
    }