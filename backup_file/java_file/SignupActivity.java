package com.cookandroid.mobile_project;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.mobile_project.database.DBHelper;

public class SignupActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signupCompleteButton;
    private ImageButton btn_backspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 뷰 초기화
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupCompleteButton = findViewById(R.id.signupButton);
        btn_backspace = findViewById(R.id.btn_backspace);

        // DBHelper 초기화
        dbHelper = new DBHelper(this);

        signupCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 기능 (간단한 예시)
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // 유효성 검사
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(SignupActivity.this, "모든 필드를 채워주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)){
                    Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // DB에 회원 정보 삽입
                registerUser(email, password);
            }
        });

        btn_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


    // 회원가입 정보 DB에 삽입하는 메소드
    private void registerUser(String email, String password){
        database = dbHelper.getWritableDatabase();

        // 중복 이메일 검사
        String checkEmailQuery = "select * from " + DBHelper.TABLE_USERS + " where " + DBHelper.COLUMN_EMAIL + " = ?";
        Cursor cursor = database.rawQuery(checkEmailQuery, new String[]{email});

        if (cursor.getCount() > 0){
            Toast.makeText(this, "이미 등록된 이메일입니다.", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }

        // 사용자 데이터 삽입
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_EMAIL, email);
        values.put(DBHelper.COLUMN_PASSWORD, password);

        // 회원 데이터 삽입
        long result = database.insert(DBHelper.TABLE_USERS, null, values);

        // 결과 처리
        if (result == -1){
            Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        }
    }
}
