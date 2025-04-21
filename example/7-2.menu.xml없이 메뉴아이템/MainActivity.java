package com.cookandroid.myapplication;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    LinearLayout base_layout;
    Button btn_1, btn_2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("배경색 바꾸기");

        base_layout = findViewById(R.id.base_layout);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);

        btn_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);

                popupMenu.getMenu().add(0,1,0,"red");
                popupMenu.getMenu().add(0,2,0,"green");
                popupMenu.getMenu().add(0,3,0,"blue");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        onOptionsItemSelected(item);
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });

        btn_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);

                popupMenu.getMenu().add(0,4,0,"rotate");
                popupMenu.getMenu().add(0,5,0,"size");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        onOptionsItemSelected(item);
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==1){
            base_layout.setBackgroundColor(Color.RED);
            return true;
        } else if(item.getItemId()==2){
            base_layout.setBackgroundColor(Color.GREEN);
            return true;
        } else if(item.getItemId()==3){
            base_layout.setBackgroundColor(Color.BLUE);
            return true;
        } else if(item.getItemId()==4){
            btn_2.setRotation(45);
            return true;
        } else if(item.getItemId()==5){
            btn_2.setScaleX(2);
            return true;
        }
        return false;
    }
}
