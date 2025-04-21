package com.cookandroid.myapplication;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    TextView txt_view;
    EditText edt_1;
    ImageView img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("배경색 바꾸기");

        txt_view = findViewById(R.id.txt_view);
        edt_1 = findViewById(R.id.edt_1);
        img_view = findViewById(R.id.img_view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.item_rotate) {
            String a = edt_1.getText().toString().trim();
            int b = Integer.parseInt(a);
            img_view.setRotation(b);
            return true;
        } else if(item.getItemId()==R.id.item_hanra){
            img_view.setImageResource(R.drawable.hanra);
            return true;
        } else if(item.getItemId()==R.id.item_chooja){
            img_view.setImageResource(R.drawable.chooja);
            return true;
        } else if(item.getItemId()==R.id.item_beom){
            img_view.setImageResource(R.drawable.beom);
            return true;
        }
        return false;
    }
}
