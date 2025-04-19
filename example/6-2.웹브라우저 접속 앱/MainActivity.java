package com.cookandroid.myapplication;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText edt_url;
    Button btn_move, btn_prev;
    WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_url = findViewById(R.id.edt_url);
        btn_move = findViewById(R.id.btn_move);
        btn_prev = findViewById(R.id.btn_prev);
        web_view = findViewById(R.id.web_view);

        web_view.setWebViewClient(new CookWebViewClient());

        WebSettings web_setting = web_view.getSettings();
        web_setting.setBuiltInZoomControls(true);
        web_setting.setJavaScriptEnabled(true);

        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web_view.loadUrl(edt_url.getText().toString());
            }
        });
    }

    class CookWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
