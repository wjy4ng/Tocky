package com.cookandroid.myapplication;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
    RadioButton rdo_calender, rdo_time;
    DatePicker date_picker;
    TimePicker time_picker;
    TextView txt_year, txt_month, txt_day, txt_hour, txt_minute;
    int select_year, select_month, select_day;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("시간 예약");

        // 위젯 초기화
        chronometer = findViewById(R.id.chronometer);

        rdo_calender = findViewById(R.id.rdo_calender);
        rdo_time = findViewById(R.id.rdo_time);

        date_picker = findViewById(R.id.date_picker);
        time_picker = findViewById(R.id.time_picker);

        txt_year = findViewById(R.id.txt_year);
        txt_month = findViewById(R.id.txt_month);
        txt_day = findViewById(R.id.txt_day);
        txt_hour = findViewById(R.id.txt_hour);
        txt_minute = findViewById(R.id.txt_minute);

        // 라디오버튼, 데이트피커, 타임피커 안보이게 초기화
        rdo_calender.setVisibility(INVISIBLE);
        rdo_time.setVisibility(INVISIBLE);
        date_picker.setVisibility(INVISIBLE);
        time_picker.setVisibility(INVISIBLE);

        // 크로노미터 클릭 시 시작
        chronometer.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 타이머 시작
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setTextColor(Color.RED);

                // 라디오 버튼 보이게
                rdo_calender.setVisibility(VISIBLE);
                rdo_time.setVisibility(VISIBLE);
                return false;
            }
        });

        // 캘린더뷰 클릭 시 (여기선 데이트뷰가 됨)
        rdo_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_picker.setVisibility(VISIBLE);
                time_picker.setVisibility(INVISIBLE);
            }
        });

        // 시간 설정 클릭 시
        rdo_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_picker.setVisibility(INVISIBLE);
                time_picker.setVisibility(VISIBLE);
            }
        });

        // (0000년) 길게 클릭 시
        txt_year.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 레이아웃 다 안 보이게
                rdo_calender.setVisibility(INVISIBLE);
                rdo_time.setVisibility(INVISIBLE);
                date_picker.setVisibility(INVISIBLE);
                time_picker.setVisibility(INVISIBLE);

                // 타이머 끄기
                chronometer.stop();
                chronometer.setTextColor(Color.BLUE);

                // 날짜, 시간 변경 적용
                txt_year.setText(Integer.toString(select_year));
                txt_month.setText(Integer.toString(select_month));
                txt_day.setText(Integer.toString(select_day));
                txt_hour.setText(Integer.toString(time_picker.getHour()));
                txt_minute.setText(Integer.toString(time_picker.getMinute()));
                return false;
            }
        });

        // 날짜 정의
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date_picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    select_year = year;
                    select_month = monthOfYear+1;
                    select_day = dayOfMonth;
                }
            });
        }
    }
}
