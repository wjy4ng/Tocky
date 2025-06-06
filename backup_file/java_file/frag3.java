package com.cookandroid.mobile_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.cookandroid.mobile_project.database.DBHelper;
import com.cookandroid.mobile_project.util.PrefManager;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public class frag3 extends Fragment {
    private Button logoutButton, deleteAccountButton;
    private SharedPreferences securePrefs;

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag3, container, false);
        logoutButton = view.findViewById(R.id.logoutButton);
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton);

        // 로그인 정보 가져오기
        securePrefs = PrefManager.getSecurePrefs(requireContext());

        // 로그아웃과 계정 삭제를 위한 데이터베이스 접근
        dbHelper = new DBHelper(requireContext());
        database = dbHelper.getWritableDatabase();

        // 로그아웃 버튼 클릭 시
        logoutButton.setOnClickListener(v -> {
            if (securePrefs != null) {
                SharedPreferences.Editor editor = securePrefs.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.remove("email");
                editor.apply();
            }

            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        // 계정 삭제 버튼 클릭 시 (AlertDialog 사용)
        deleteAccountButton.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("계정 삭제 확인")
                    .setMessage("정말 삭제하시겠습니까?")
                    .setPositiveButton("예", (dialog, which) -> {
                        if (securePrefs != null) {
                            try {
                                String userEmail = securePrefs.getString("email", null);
                                if (userEmail == null) {
                                    Toast.makeText(requireContext(), "현재 로그인된 계정이 없습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                SharedPreferences.Editor editor = securePrefs.edit();

                                // TOTP 관련 키 삭제
                                try {
                                    Map<String, ?> allPrefs = securePrefs.getAll();
                                    for (String key : allPrefs.keySet()) {
                                        if (key.startsWith(userEmail + "_totp_secret_")) {
                                            editor.remove(key);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                // 로그인 정보 삭제
                                editor.remove("email");
                                editor.remove("isLoggedIn");
                                editor.apply();

                                // SQLite 사용자 정보 삭제
                                database.delete(DBHelper.TABLE_USERS, DBHelper.COLUMN_EMAIL + " = ?", new String[]{userEmail});

                                Toast.makeText(requireContext(), "계정과 관련된 데이터가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                requireActivity().finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(requireContext(), "계정 삭제 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "securePrefs 존재하지 않음", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("아니요", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });

        return view;
    }
}