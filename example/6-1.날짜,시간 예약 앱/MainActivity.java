package com.cookandroid.myapplication;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    Button btn_start, btn_end;
    RadioButton rdo_calender, rdo_time;
    CalendarView cal_view;
    TimePicker time_picker;
    TextView txt_year, txt_month, txt_day, txt_hour, txt_minute;
    int select_year, select_month, select_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("시간 예약");

        // 위젯 초기화
        chronometer = findViewById(R.id.chronometer);

        btn_start = findViewById(R.id.btn_start);
        btn_end = findViewById(R.id.btn_end);

        rdo_calender = findViewById(R.id.rdo_calender);
        rdo_time = findViewById(R.id.rdo_time);

        cal_view = findViewById(R.id.cal_view);
        time_picker = findViewById(R.id.time_picker);

        txt_year = findViewById(R.id.txt_year);
        txt_month = findViewById(R.id.txt_month);
        txt_day = findViewById(R.id.txt_day);
        txt_hour = findViewById(R.id.txt_hour);
        txt_minute = findViewById(R.id.txt_minute);

        // 캘린더 안보이게
        cal_view.setVisibility(INVISIBLE);
        time_picker.setVisibility(INVISIBLE);

        // 캘린더뷰 클릭 시
        rdo_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal_view.setVisibility(VISIBLE);
                time_picker.setVisibility(INVISIBLE);
            }
        });

        // 시간 설정 클릭 시
        rdo_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal_view.setVisibility(INVISIBLE);
                time_picker.setVisibility(VISIBLE);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setTextColor(Color.RED);
            }
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setTextColor(Color.BLUE);

                txt_year.setText(Integer.toString(select_year));
                txt_month.setText(Integer.toString(select_month));
                txt_day.setText(Integer.toString(select_day));
                txt_hour.setText(Integer.toString(time_picker.getHour()));
                txt_minute.setText(Integer.toString(time_picker.getMinute()));
            }
        });

        cal_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                select_year = year;
                select_month = month;
                select_day = dayOfMonth;
            }
        });
    }
}
