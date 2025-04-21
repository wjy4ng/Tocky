package com.cookandroid.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button main_btn;
    EditText main_edt_name, main_edt_email;
    EditText dialog_edt_name, dialog_edt_email;
    TextView toast_txt_view;
    View dialogview, toastview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_btn = findViewById(R.id.main_btn);
        main_edt_name = findViewById(R.id.main_edt_name);
        main_edt_email = findViewById(R.id.main_edt_email);

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogview = (View) View.inflate(MainActivity.this, R.layout.dialog1, null);
                dialog_edt_name = dialogview.findViewById(R.id.dialog_edt_name);
                dialog_edt_email = dialogview.findViewById(R.id.dialog_edt_email);

                dialog_edt_name.setText(main_edt_name.getText().toString());
                dialog_edt_email.setText(main_edt_email.getText().toString());

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("사용자 정보 입력")
                        .setIcon(R.mipmap.ic_launcher)
                        .setView(dialogview)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main_edt_name.setText(dialog_edt_name.getText().toString());
                                main_edt_email.setText(dialog_edt_email.getText().toString());
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast toast = new Toast(MainActivity.this);
                                Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
                                int x_offset = (int)(Math.random() * display.getWidth());
                                int y_offset = (int)(Math.random() * display.getHeight());

                                toastview = View.inflate(MainActivity.this, R.layout.toast1, null);
                                toast_txt_view = toastview.findViewById(R.id.toast_txt_view);
                                toast_txt_view.setText("취소했습니다.");
                                toast.setView(toastview);
                                toast.show();
                            }
                        });
                dlg.show();
            }
        });
    }
}
