package com.cookandroid.mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private TextView splash_text;
    private ImageView splash_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 뷰 초기화
        splash_logo = findViewById(R.id.splash_logo);
        splash_text = findViewById(R.id.splash_text);

        // 애니메이션 설정
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2000); // 2초
        fadeIn.setFillAfter(true);

        // 애니메이션 끝나면 다음 액티비티로 이동
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                splash_logo.setVisibility(ImageView.VISIBLE);
                splash_text.setVisibility(TextView.VISIBLE);
            }

            // 애니메이션이 끝난 후 LoginAcitivity로 자동 전환
            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }, 500); // 약간의 딜레이 후 전환
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        splash_logo.startAnimation(fadeIn);
        splash_text.startAnimation(fadeIn);
    }
}