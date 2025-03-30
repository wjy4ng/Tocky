package com.cookandroid.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView txt_title, txt_question;
    CheckBox chb_start;
    RadioGroup rdo_answer;
    RadioButton rdo_dog, rdo_cat, rdo_rabbit;
    Button btn_choice;
    ImageView img_pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_title = findViewById(R.id.txt_title);
        txt_question = findViewById(R.id.txt_question);
        chb_start = findViewById(R.id.chb_start);
        rdo_answer = findViewById(R.id.rdo_answer);
        rdo_dog = findViewById(R.id.rdo_dog);
        rdo_cat = findViewById(R.id.rdo_cat);
        rdo_rabbit = findViewById(R.id.rdo_rabbit);
        btn_choice = findViewById(R.id.btn_choice);
        img_pet = findViewById(R.id.img_pet);

        chb_start.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                txt_question.setVisibility(View.VISIBLE);
                rdo_answer.setVisibility(View.VISIBLE);
                btn_choice.setVisibility(View.VISIBLE);
                img_pet.setVisibility(View.VISIBLE);
            } else{
                txt_question.setVisibility(View.INVISIBLE);
                rdo_answer.setVisibility(View.INVISIBLE);
                btn_choice.setVisibility(View.INVISIBLE);
                img_pet.setVisibility(View.INVISIBLE);
            }
        }));

        btn_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked_radio = rdo_answer.getCheckedRadioButtonId();
                if (checked_radio == R.id.rdo_dog) img_pet.setImageResource(R.drawable.ic_launcher_background);
                else if (checked_radio == R.id.rdo_cat) img_pet.setImageResource(R.drawable.ic_launcher_foreground);
                else Toast.makeText(getApplicationContext(), "동물먼저 선택하세요", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
