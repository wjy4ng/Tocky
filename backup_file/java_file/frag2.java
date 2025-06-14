package com.cookandroid.mobile_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.mobile_project.util.PrefManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class frag2 extends Fragment {
    private final ExecutorService executor = Executors.newSingleThreadExecutor(); // 백그라운드 전용
    private final Handler mainHandler = new Handler(Looper.getMainLooper()); // 메인 스레드 핸들러
    private ActivityResultLauncher<Intent> qrScanLauncher;
    private final List<String> siteList = new ArrayList<>(); // 삭제 버튼 클릭시 index 초기화하기 위함
    private SharedPreferences userSecurePrefs;
    private SharedPreferences userTotpPrefs;


    // QR 스캔 레이아웃
    private EditText siteName;
    private View scanButton;

    // Recycler 레이아웃
    private RecyclerView siteRecyclerView;
    private SiteAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 뷰 초기화
        View view = inflater.inflate(R.layout.frag2, container, false);
        siteName = view.findViewById(R.id.siteName);
        scanButton = view.findViewById(R.id.scanBtn);
        siteRecyclerView = view.findViewById(R.id.siteRecyclerView);

        // MasterKey 및 SharedPreferences 초기화
        try {
            userSecurePrefs = PrefManager.getSecurePrefs(requireContext());
            String email = userSecurePrefs.getString("email", null);
            if (email != null) {
                userTotpPrefs = PrefManager.getTotpPrefs(requireContext(), email);
                setupRecyclerView();
            } else {
                userTotpPrefs = null;
                Toast.makeText(requireContext(), "이메일 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(), "보안 환경 초기화 실패", Toast.LENGTH_SHORT).show();
        }

        setupQrScanner();
        loadSavedSites(); // 저장된 사이트 정보 복원

        scanButton.setOnClickListener(v -> launcherQrScanner());
        return view;
    }

    private void setupRecyclerView() { // RecyclerView 생성
        siteRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        siteRecyclerView.setHasFixedSize(true);

        String userEmail = getCurrentUserEmail();
        adapter = new SiteAdapter(requireContext(), siteList, executor, mainHandler, userEmail);
        siteRecyclerView.setAdapter(adapter);
    }

    private void setupQrScanner() { // QR 스캐너 생성
        // QR 코드 스캔 결과를 처리
        qrScanLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    IntentResult intentResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if(intentResult.getContents() != null){
                        parseAndSaveTotpKey(intentResult.getContents());
                    } else{
                        Toast.makeText(requireContext(), "QR 스캔 취소됨", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void launcherQrScanner() { // QR 스캔 버튼 클릭 시
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(frag2.this); // frag2 설정
        integrator.setPrompt("QR 코드를 스캔하세요.");
        integrator.setBeepEnabled(false); // 비프음 제거
        integrator.setOrientationLocked(true); // 화면 고정
        integrator.setBarcodeImageEnabled(false); // 이미지 저장 안함
        qrScanLauncher.launch(integrator.createScanIntent());
    }

    private void loadSavedSites(){ // 저장된 사이트 정보 복원하는 함수
        SharedPreferences sharedPreferences = getUserTotpPreferences();
        siteList.clear();

        for (String key : sharedPreferences.getAll().keySet()){
            if (key.contains("_totp_secret_")){
                String site = key.substring(key.lastIndexOf("_totp_secret_") + "_totp_secret_".length());
                siteList.add(site); // 저장된 site 이름으로 UI 복원
            }
        }
        renderSiteList(); // 매번 addView 하지 않고, 리스트만 갱신하고 UI는 새로 렌더링
    }


    // QR 코드에서 비밀 키를 추출하여 사이트 이름과 함께 저장 및 테이블에 추가하는 함수
    @SuppressLint("SetTextI18n")
    private void parseAndSaveTotpKey(String url){
        String secret = extractSecretFromURL(url);
        if (secret != null){
            String site = siteName.getText().toString().trim();
            if(site.isEmpty()) site="이름없음"; // 사이트 이름 지정 안하면 이름없음으로 처리

            siteName.clearFocus(); // 포커스 해제로 렉 완화

            String finalSite = site;
            executor.execute(()->{
                String email = getCurrentUserEmail();
                if (email != null){
                    String key = email + "_totp_secret_" + finalSite;
                    saveSecretKey(key, secret);

                    mainHandler.post(()->{
                        siteName.setText(""); // 입력 필드 초기화
                        if (!siteList.contains(finalSite)){ // 중복 사이트 방지
                            siteList.add(finalSite); // 행 추가
                            renderSiteList();
                        }
                        Toast.makeText(requireContext(), "비밀 키 저장됨 : "+secret, Toast.LENGTH_SHORT).show();
                    });
                } else{
                    mainHandler.post(()->
                            Toast.makeText(requireContext(), "이메일 없음, 저장 실패", Toast.LENGTH_SHORT).show());
                }
            });
        } else{
            Toast.makeText(requireContext(), "QR 코드에서 비밀 키를 추출 할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // otpauth:// Url에서 secret 파라미터를 추출하는 함수
    private String extractSecretFromURL(String url) {
        try{
            URI uri = new URI(url);
            String query = uri.getQuery();
            if(query != null){ // ex. secret=JBSWY3DPEHPK3PXP 에서 J~ 를 추출
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

    // TOTP 키를 EncryptedSharedPreferences에 저장하는 함수
    private void saveSecretKey(String key, String secret) {
        if (userTotpPrefs == null) {
            mainHandler.post(() -> Toast.makeText(requireContext(), "로그인 정보 없음, 저장 실패", Toast.LENGTH_SHORT).show());
            return;
        }
        executor.execute(() -> {
            try {
                userTotpPrefs.edit().putString(key, secret).apply();
                mainHandler.post(() -> Toast.makeText(requireContext(), "비밀 키 저장됨", Toast.LENGTH_SHORT).show());
            } catch (Exception e){
                mainHandler.post(() -> Toast.makeText(requireContext(), "비밀키 저장 실패", Toast.LENGTH_SHORT).show());
            }
        });
    }

    // 로그인된 사용자 이메일을 가져오는 함수
    private String getCurrentUserEmail(){
        if (userSecurePrefs == null) return null;
        return userSecurePrefs.getString("email", null);
    }

    // 현재 로그인한 사용자의 전용 TOTP 저장소를 반환
    private SharedPreferences getUserTotpPreferences(){
        if (userTotpPrefs == null) {
            Toast.makeText(requireContext(), "로그인 정보 없음", Toast.LENGTH_SHORT).show();
            throw new IllegalStateException("로그인 정보 없음");
        }
        return userTotpPrefs;
    }

    // XML 없이 동적으로 행을 추가하는 함수
    // 기존 TableRow → LinearLayout 변경된 구조 기준
    private void renderSiteList() {
        adapter.setSiteList(new ArrayList<>(siteList));
    }
}
