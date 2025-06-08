package com.cookandroid.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final static int LINE=1, CIRCLE=2, RECTANGLE=3;
    static int currentShape = LINE;
    static int currentColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new myGraphicView(this));

        initViews();
        setupListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"선 그리기");
        menu.add(0, 2, 0, "원 그리기");
        menu.add(0,3,0,"사각형 그리기");

        SubMenu subMenu = menu.addSubMenu("색상 변경 >>");
        subMenu.add(0,4,0,"빨강");
        subMenu.add(0,5,0,"초록");
        subMenu.add(0,6,0,"파랑");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                currentShape = LINE;
                return true;
            case 2:
                currentShape = CIRCLE;
                return true;
            case 3:
                currentShape = RECTANGLE;
                return true;
            case 4:
                currentColor = Color.RED;
                return true;
            case 5:
                currentColor = Color.GREEN;
                return true;
            case 6:
                currentColor = Color.BLUE;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {

    }

    private void setupListener(){

    }

    private static class myGraphicView extends View {
        int startX = -1, startY = -1, stopX = -1, stopY = -1;

        public myGraphicView(Context context) {
            super(context);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    this.invalidate();
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            @SuppressLint("DrawAllocation")
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(currentColor);

            switch (currentShape){
                case LINE:
                    canvas.drawLine(startX, startY, stopX, stopY, paint);
                    break;
                case CIRCLE:
                    int radius = (int) Math.sqrt(Math.pow(stopX - startX, 2) + Math.pow(stopY - startY, 2));
                    canvas.drawCircle(startX, startY, radius, paint);
                    break;
                case RECTANGLE:
                    canvas.drawRect(startX, startY, stopX, stopY, paint);
                    break;
            }
        }
    }
}