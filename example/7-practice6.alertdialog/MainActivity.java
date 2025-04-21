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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    RadioButton rdo_dog, rdo_cat, rdo_rabbit, rdo_horse;
    Button btn_1;
    ImageView picture_img_view;
    View dialog_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rdo_dog = findViewById(R.id.rdo_dog);
        rdo_cat = findViewById(R.id.rdo_cat);
        rdo_rabbit = findViewById(R.id.rdo_rabbit);
        rdo_horse = findViewById(R.id.rdo_horse);
        btn_1 = findViewById(R.id.btn_1);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dialog_view = View.inflate(MainActivity.this, R.layout.picture, null);
                ImageView dialog_img_view = dialog_view.findViewById(R.id.picture_img_view);

                if(rdo_dog.isChecked()){
                    dialog_img_view.setImageResource(R.drawable.hanra);
                    dlg.setTitle(rdo_dog.getText().toString());
                } else if(rdo_cat.isChecked()){
                    dialog_img_view.setImageResource(R.drawable.chooja);
                    dlg.setTitle(rdo_cat.getText().toString());
                } else if(rdo_rabbit.isChecked()){
                    dialog_img_view.setImageResource(R.drawable.beom);
                    dlg.setTitle(rdo_rabbit.getText().toString());
                } else if(rdo_horse.isChecked()){
                    dialog_img_view.setImageResource(R.drawable.btn_star_big_on);
                    dlg.setTitle(rdo_horse.getText().toString());
                }

                dlg.setView(dialog_view);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }
        });
    }

    public void func(RadioButton rdo){

    }
}
