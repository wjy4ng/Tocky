package com.cookandroid.mobile_project.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class PrefManager {
    private static MasterKey masterKey;

    // EncryptedSharedPrefereneces를 만들기 위해 필요한 MasterKey 생성 및 반환하는 함수
    public static MasterKey getMasterKey(Context context) {
        if (masterKey == null) {
            try {
                masterKey = new MasterKey.Builder(context)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("MasterKey 생성 실패", e);
            }
        }
        return masterKey;
    }

    // secure_prefs라는 이름으로 암호화된 SharedPrefereneces 객체를 생성 및 반환하는 함수
    // 로그인 정보, 이메일 등 민감한 사용자 정보를 저장하는 용도로 사용된다.
    public static SharedPreferences getSecurePrefs(Context context) {
        try {
            return EncryptedSharedPreferences.create(
                    context,
                    "secure_prefs", // 파일이름
                    getMasterKey(context), // 암호화 키
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // 키 암호화 알고리즘
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // 값 암호화 알고리즘
            );
        } catch (Exception e) {
            throw new RuntimeException("SecurePrefs 생성 실패", e);
        }
    }

    // 사용자별로 분리된 TOTP 저장소를 제공하는 함수
    public static SharedPreferences getTotpPrefs(Context context, String email) {
        try {
            return EncryptedSharedPreferences.create(
                    context,
                    "TOTP_PREFS_" + email,
                    getMasterKey(context),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getTotpPrefs 오류: " + e.getMessage(), e);
        }
    }
}
