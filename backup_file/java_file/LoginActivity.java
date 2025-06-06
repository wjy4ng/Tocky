package com.cookandroid.mobile_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mobile_project.database.DBHelper;
import com.cookandroid.mobile_project.util.PrefManager;


public class LoginActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private EditText emailEditText, passwordEditText;
    private CheckBox autoLoginCheckBox;
    private SharedPreferences securePrefs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 뷰 초기화
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButton);
        autoLoginCheckBox = findViewById(R.id.autoLoginCheckBox);

        // DBHelper 초기화
        dbHelper = new DBHelper(this);

        // 암호화 SharedPreferences 초기화
        securePrefs = PrefManager.getSecurePrefs(this);

        // 자동 로그인 체크
        if(securePrefs.getBoolean("isLoggedIn", false)){
            String savedEmail = securePrefs.getString("email", null);
            if (savedEmail != null){
                // 자동 로그인 성공 -> MainActivity로 이동
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                return;
            }
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // 로그인 칸이 비어있는지 확인
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "아이디/비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 로그인 성공: MainActivity로 이동
                if(checkLogin(email, password)) {
                    SharedPreferences.Editor editor = securePrefs.edit();
                    editor.putString("email", email);
                    editor.putBoolean("isLoggedIn", autoLoginCheckBox.isChecked());
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    // 로그인 실패: 오류 메시지 표시
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 회원가입 버튼
        signupButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });
    }

    // 로그인 확인 기능
    private boolean checkLogin(String email, String password){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String query = "select * from " + DBHelper.TABLE_USERS +
                " where " + DBHelper.COLUMN_EMAIL + " = ?" +
                " and " + DBHelper.COLUMN_PASSWORD + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{email, password});
        boolean isValid = cursor.getCount() > 0;

        cursor.close();
        database.close();
        return isValid;
    }
}