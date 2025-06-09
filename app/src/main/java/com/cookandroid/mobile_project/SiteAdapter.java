package com.cookandroid.mobile_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.mobile_project.util.PrefManager;
import com.cookandroid.mobile_project.util.TOTPUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.SiteViewHolder> {
    private List<String> siteList;
    private final Context context;
    private final SharedPreferences prefs;
    private final ExecutorService executor;
    private final Handler mainHandler;
    // TOTP 생성 버튼을 눌렀을 때 어떤 계정의 totp_secret인지 구분하기 위함
    private String userEmail = null;

    public SiteAdapter(Context context, List<String> siteList, ExecutorService executor, Handler mainHandler, String userEmail) {
        this.context = context;
        this.siteList = siteList;
        this.executor = executor;
        this.mainHandler = mainHandler;
        this.userEmail = userEmail;

        this.prefs = PrefManager.getTotpPrefs(context, userEmail);
    }

    @NonNull
    @Override
    public SiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new SiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteViewHolder holder, int position) {
        // 목록 초기 값 설정
        String site = siteList.get(position);
        holder.numberTxt.setText(String.valueOf(position + 1));
        holder.siteNameTxt.setText(site);
        holder.totpTxt.setText("미생성");

        // TOTP 생성 버튼 클릭 시
        holder.generateBtn.setOnClickListener(v -> {
            holder.generateBtn.setEnabled(false); // 버튼 연속으로 누를 경우 오류를 방지
            String key = userEmail + "_totp_secret_" + site;
            String secret = prefs.getString(key, null);

            if (secret != null) {
                executor.execute(() -> {
                    try {
                        String code = TOTPUtil.getCurrentTOTP2(secret);
                        mainHandler.post(() -> {
                            holder.totpTxt.setText(code);
                            holder.generateBtn.setEnabled(true); // 버튼 활성화
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        mainHandler.post(() -> {
                            Toast.makeText(context, "TOTP 생성 오류", Toast.LENGTH_SHORT).show();
                            holder.generateBtn.setEnabled(true);
                        });

                    }
                });
            } else{
                Toast.makeText(context, "해당 사이트의 secret이 없습니다.", Toast.LENGTH_SHORT).show();
                holder.generateBtn.setEnabled(true);
            }
        });

        // TOTP 삭제 버튼 클릭 시
        holder.deleteBtn.setOnClickListener(v -> {
            executor.execute(() -> {
                String key = userEmail + "_totp_secret_" + site;
                prefs.edit().remove(key).apply();

                mainHandler.post(() -> {
                    if (position >= 0 && position < siteList.size()){
                        siteList.remove(position); // 데이터 리스트에서 항목 제거
                        notifyItemRemoved(position); // RecyclerView에 알림
                        notifyItemRangeChanged(position, siteList.size()); // 데이터 재갱신
                        Toast.makeText(context, "삭제 완료: " + site, Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }

    @Override
    public int getItemCount() {
        return siteList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSiteList(List<String> newSiteList) {
        this.siteList = newSiteList;
        notifyDataSetChanged();
    }

    static class SiteViewHolder extends RecyclerView.ViewHolder{
        TextView numberTxt, siteNameTxt, totpTxt;
        Button generateBtn, deleteBtn;

        public SiteViewHolder(@NonNull View itemView) {
            super(itemView);
            numberTxt = itemView.findViewById(R.id.numberTxt);
            siteNameTxt = itemView.findViewById(R.id.siteNameTxt);
            totpTxt = itemView.findViewById(R.id.totpTxt);
            generateBtn = itemView.findViewById(R.id.generateBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}