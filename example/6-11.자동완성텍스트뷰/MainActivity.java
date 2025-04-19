package com.cookandroid.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView auto_complete_txt;
    MultiAutoCompleteTextView multi_complete_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] items = {"CSI-뉴욕", "CSI-라스베가스", "CSI-마이애미", "Friends", "Fringe", "Lost"};

        auto_complete_txt = findViewById(R.id.auto_complete_txt);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
        auto_complete_txt.setAdapter(adapter);

        multi_complete_txt = findViewById(R.id.multi_complete_txt);
        MultiAutoCompleteTextView.CommaTokenizer token = new MultiAutoCompleteTextView.CommaTokenizer();
        multi_complete_txt.setTokenizer(token);
        multi_complete_txt.setAdapter(adapter);
    }
}
