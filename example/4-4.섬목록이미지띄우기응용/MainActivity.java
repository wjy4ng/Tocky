package com.cookandroid.myapplication;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView txtview_1, txtview_2;
    Switch swt_start;
    RadioGroup rdo_group_land;
    RadioButton rdo_hanra, rdo_chooja, rdo_beom;
    ImageView imgview;
    Button btn_exit, btn_init;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtview_1 = findViewById(R.id.txtview_1);
        txtview_2 = findViewById(R.id.txtview_2);
        swt_start = findViewById(R.id.swt_start);
        rdo_group_land = findViewById(R.id.rdo_group_land);
        rdo_hanra = findViewById(R.id.rdo_hanra);
        rdo_chooja = findViewById(R.id.rdo_chooja);
        rdo_beom = findViewById(R.id.rdo_beom);
        imgview = findViewById(R.id.imgview);
        btn_exit = findViewById(R.id.btn_exit);
        btn_init = findViewById(R.id.btn_init);

        swt_start.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    txtview_2.setVisibility(VISIBLE);
                    rdo_group_land.setVisibility(VISIBLE);
                    imgview.setVisibility(VISIBLE);
                    btn_exit.setVisibility(VISIBLE);
                    btn_init.setVisibility(VISIBLE);
                } else {
                    txtview_2.setVisibility(INVISIBLE);
                    rdo_group_land.setVisibility(INVISIBLE);
                    imgview.setVisibility(INVISIBLE);
                    btn_exit.setVisibility(INVISIBLE);
                    btn_init.setVisibility(INVISIBLE);
                }
            }
        });

        rdo_hanra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgview.setImageResource(R.drawable.hanra);
            }
        });

        rdo_chooja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgview.setImageResource(R.drawable.chooja);
            }
        });

        rdo_beom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgview.setImageResource(R.drawable.beom);
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

        btn_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swt_start.setChecked(false);
            }
        });
    }
}
