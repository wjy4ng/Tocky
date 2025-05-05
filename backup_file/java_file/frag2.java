package com.cookandroid.mobile_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.mobile_project.util.TOTPUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.URI;
import java.net.URISyntaxException;

public class frag2 extends Fragment {
    private View scanButton;
    private ActivityResultLauncher<Intent> qrScanLauncher;
    private static final String PREFERENCE_NAME = "secure_prefs";
    private static final String TOTP_SECRET_KEY = "totp_secret";
    private View showTotpBtn;
    private TextView totpTxt;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 뷰 초기화
        View view = inflater.inflate(R.layout.frag2, container, false);
        scanButton = view.findViewById(R.id.scanBtn);
        showTotpBtn = view.findViewById(R.id.showTotpBtn);
        totpTxt = view.findViewById(R.id.totpTxt);


        showTotpBtn.setOnClickListener(v -> {
            showCurrentTotp();
        });

        // QR 코드 스캔 결과를 처리하는 ActivityResultLauncher
        qrScanLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    IntentResult intentResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if(intentResult != null && intentResult.getContents() != null){
                        parseAndSaveTotpKey(intentResult.getContents());
                    } else{
                        Toast.makeText(requireContext(), "QR 스캔 취소됨", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // QR 스캔 버튼 클릭 시 카메라 열기
        scanButton.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(requireActivity());
            integrator.setPrompt("QR 코드를 스캔하세요.");
            integrator.setOrientationLocked(false);
            qrScanLauncher.launch(integrator.createScanIntent());
        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void parseAndSaveTotpKey(String url){
        // QR 코드에서 비밀 키를 추출하여 SharedPreferences에 저장하는 함수
        String secret = extractSecretFromURL(url);
        if (secret != null){
            // 비밀 키를 SharedPreferences에 안전하게 저장
            saveSecretKey(secret);
            Toast.makeText(requireContext(), "비밀 키 저장됨 : "+secret, Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(requireContext(), "QR 코드에서 비밀 키를 추출 할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private String extractSecretFromURL(String url) {
        // otpauth:// Url에서 secret 파라미터를 추출하는 함수
        // ex. secret=JBSWY3DPEHPK3PXP
        try{
            URI uri = new URI(url);
            String query = uri.getQuery();
            if(query != null){
                String[] params = query.split("&");
                for (String param : params){
                    if(param.startsWith("secret=")){
                        return param.substring(7);
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveSecretKey(String secret){
        // 비밀 키를 SharedPreferences에 안전하게 저장하는 함수
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOTP_SECRET_KEY, secret);
        editor.apply();
    }

    private String getSavedSecretKey(){
        // 저장된 비밀 키를 SharedPreferences에서 읽어오는 함수
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOTP_SECRET_KEY, null);
    }

    @SuppressLint("SetTextI18n")
    private void showCurrentTotp(){
        // base32 인코딩된 비밀키
        String secret = getSavedSecretKey();
        if (secret != null){
            try{
                // TOTP 생성기: 30초 주기, 6자리 코드
                String code = TOTPUtil.getCurrentTOTP(secret);
                totpTxt.setText("현재 TOTP : "+code);
            } catch (Exception e){
                e.printStackTrace();
                Log.e("TOTP_ERROR", "TOTP 생성 중 오류", e);
                Toast.makeText(getContext(), "TOTP 생성 중 오류 발생", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(getContext(), "저장된 비밀 키가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
