package com.cookandroid.myapplication;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView txtview_1, txtview_2;
    CheckBox chb_start;
    RadioGroup rdo_group_land;
    RadioButton rdo_hanra, rdo_chooja, rdo_beom;
    Button btn_choice;
    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtview_1 = findViewById(R.id.txtview_1);
        txtview_2 = findViewById(R.id.txtview_2);
        chb_start = findViewById(R.id.chb_start);
        rdo_group_land = findViewById(R.id.rdo_group_land);
        rdo_hanra = findViewById(R.id.rdo_hanra);
        rdo_chooja = findViewById(R.id.rdo_chooja);
        rdo_beom = findViewById(R.id.rdo_beom);
        btn_choice = findViewById(R.id.btn_choice);
        imgview = findViewById(R.id.imgview);

        chb_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chb_start.isChecked()) {
                    txtview_2.setVisibility(VISIBLE);
                    rdo_group_land.setVisibility(VISIBLE);
                    btn_choice.setVisibility(VISIBLE);
                } else{
                    txtview_2.setVisibility(INVISIBLE);
                    rdo_group_land.setVisibility(INVISIBLE);
                    btn_choice.setVisibility(INVISIBLE);
                    imgview.setVisibility(INVISIBLE);
                }
            }
        });

        btn_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdo_hanra.isChecked()){
                    imgview.setImageResource(R.drawable.hanra);
                    imgview.setVisibility(VISIBLE);
                } else if (rdo_chooja.isChecked()){
                    imgview.setImageResource(R.drawable.chooja);
                    imgview.setVisibility(VISIBLE);
                } else if (rdo_beom.isChecked()){
                    imgview.setImageResource(R.drawable.beom);
                    imgview.setVisibility(VISIBLE);
                } else{
                    Toast.makeText(MainActivity.this, "이미지를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
