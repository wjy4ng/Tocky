package com.cookandroid.mobile_project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class frag1 extends Fragment {
    private EditText urlEditText;
    private Button loadButton;
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1, container, false);
        urlEditText = view.findViewById(R.id.urlEditText);
        loadButton = view.findViewById(R.id.loadButton);
        webView = view.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        loadButton.setOnClickListener(v -> {
            String input = urlEditText.getText().toString().trim();

            if (TextUtils.isEmpty(input)){
                Toast.makeText(getContext(), "주소를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            // http:// or https:// 없으면 자동 추가
            if (!input.startsWith("http://") && !input.startsWith("https://")){
                input = "https://" + input;
            }
            webView.loadUrl(input);
        });
        return view;
    }

    // 뒤로가기 버튼이 웹뷰에서 동작하게 설정 (안하면 앱이 뒤로가져서 앱이 나가짐)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (webView.canGoBack()) {
                            webView.goBack(); // 웹페이지 뒤로가기
                        } else {
                            setEnabled(false); // 콜백 비활성화 후 기본 동작 수행
                            requireActivity().onBackPressed();
                        }
                    }
                });
    }
}

