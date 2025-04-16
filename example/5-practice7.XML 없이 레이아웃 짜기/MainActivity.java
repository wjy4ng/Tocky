package com.cookandroid.myapplication;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout base = new LinearLayout(this);
        base.setOrientation(LinearLayout.VERTICAL);
        base.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        LinearLayout base2 = new LinearLayout(this);
        base2.setOrientation(LinearLayout.HORIZONTAL);
        base2.setLayoutParams(params2);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        LinearLayout red = new LinearLayout(this);
        red.setLayoutParams(params3);
        red.setBackgroundColor(Color.RED);

        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        LinearLayout base3 = new LinearLayout(this);
        base3.setLayoutParams(params4);
        base3.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        LinearLayout yw = new LinearLayout(this);
        yw.setLayoutParams(params5);
        yw.setBackgroundColor(Color.YELLOW);

        LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        LinearLayout bk = new LinearLayout(this);
        bk.setLayoutParams(params6);
        bk.setBackgroundColor(Color.BLACK);

        LinearLayout.LayoutParams params7 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        LinearLayout bl = new LinearLayout(this);
        bl.setLayoutParams(params7);
        bl.setBackgroundColor(Color.BLUE);

        base3.addView(yw);
        base3.addView(bk);
        base2.addView(red);
        base2.addView(base3);
        base.addView(base2);
        base.addView(bl);
        setContentView(base);
    }
}
