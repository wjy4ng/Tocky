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

import com.cookandroid.mobile_project.util.TOTPUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.SiteViewHolder> {
    private List<String> siteList;
    private final Context context;
    private final SharedPreferences prefs;
    private final ExecutorService executor;
    private final Handler mainHandler;

    public SiteAdapter(Context context, List<String> siteList, ExecutorService executor, Handler mainHandler) {
        this.context = context;
        this.siteList = siteList;
        this.executor = executor;
        this.mainHandler = mainHandler;
        this.prefs = context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public SiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new SiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteViewHolder holder, int position) {
        String site = siteList.get(position);
        holder.numberTxt.setText(String.valueOf(position + 1));
        holder.siteNameTxt.setText(site);
        holder.totpTxt.setText("미생성");

        holder.generateBtn.setOnClickListener(v -> {
            String secret = prefs.getString("totp_secret_" + site, null);
            if (secret != null) {
                executor.execute(() -> {
                    try {
                        String code = TOTPUtil.getCurrentTOTP2(secret);
                        mainHandler.post(() -> holder.totpTxt.setText(code));
                    } catch (Exception e) {
                        mainHandler.post(() -> Toast.makeText(context, "TOTP 생성 오류", Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });

        holder.deleteBtn.setOnClickListener(v -> {
            prefs.edit().remove("totp_secret_" + site).apply();
            siteList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, siteList.size());
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

