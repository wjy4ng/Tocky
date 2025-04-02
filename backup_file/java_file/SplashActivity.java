package com.cookandroid.mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {
    private TextView splash_text;
    private ImageView splash_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_logo = findViewById(R.id.splash_logo);
        splash_text = findViewById(R.id.splash_text);

        // 핸들러 정의
        Handler handler = new Handler();
        // 애니메이션 정의
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2000); // 애니메이션 지속시간 2초
        fadeIn.setFillAfter(true); // 애니메이션 종료 후 상태 유지

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 스플래시 이미지 보이게
                splash_logo.setVisibility(ImageView.VISIBLE);
                splash_text.setVisibility(TextView.VISIBLE);
                // 애니메이션 적용
                splash_logo.startAnimation(fadeIn);
                splash_text.startAnimation(fadeIn);
            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
