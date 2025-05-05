package com.cookandroid.mobile_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.cookandroid.mobile_project.database.DBHelper;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private EditText emailEditText, passwordEditText;
    private Button loginButton, signupButton;
    private CheckBox autoLoginCheckBox;
    private SharedPreferences securePrefs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        autoLoginCheckBox = findViewById(R.id.autoLoginCheckBox);

        // DBHelper 초기화
        dbHelper = new DBHelper(this);

        // 자동 로그인 확인
        try{
            MasterKey masterKey = new MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            securePrefs = EncryptedSharedPreferences.create(
                    this,
                    "secure_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // 자동 로그인 확인
            boolean isLoggedIn = securePrefs.getBoolean("isLoggedIn", false);
            if(isLoggedIn){
                String savedEmail = securePrefs.getString("email", null);
                if (savedEmail != null){
                    // 자동 로그인 성공 -> MainActivity로 이동
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    return;
                }
            }
        } catch (GeneralSecurityException | IOException e){
            e.printStackTrace();
            Toast.makeText(this, "보안 설정 오류 발생", Toast.LENGTH_SHORT).show();
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "아이디/비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 로그인 성공: MainActivity로 이동
                    if(checkLogin(email, password)) {
                        // 자동 로그인 체크되어 있는 경우
                        if(autoLoginCheckBox.isChecked()){
                            SharedPreferences.Editor editor = securePrefs.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("email", email);
                            editor.apply();
                        }
                        Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // 로그인 실패: 오류 메시지 표시
                        Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signupButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });
    }

    private boolean checkLogin(String email, String password){
        database = dbHelper.getWritableDatabase();
        // select * from [table] where [email] = ? and [password] = ?;
        String query = "select * from " +
                DBHelper.TABLE_USERS + " where " +
                DBHelper.COLUMN_EMAIL + " = ? and " +
                DBHelper.COLUMN_PASSWORD + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{email, password});

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        database.close();
        return isValid;
    }
}



