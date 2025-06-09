package com.cookandroid.mobile_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewPager2, TabLayout 초기화
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // FragmentAdapter에 Fragment 추가
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this);
        fragmentAdapter.addFragment(new frag1());
        fragmentAdapter.addFragment(new frag2());
        fragmentAdapter.addFragment(new frag3());

        // ViewPager2에 FragmentAdapter 설정
        viewPager.setAdapter(fragmentAdapter);

        // 기본 탭을 frag2로 설정
        viewPager.setCurrentItem(1, false); // false면 애니메이션 없이 이동

        // TabLayout, ViewPager2 연결
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("검색");
                    break;
                case 1:
                    tab.setText("홈");
                    break;
                case 2:
                    tab.setText("설정");
                    break;
            }
        }).attach();
    }
}
