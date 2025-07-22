package com.example.weatherapp.activitys;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.fragments.HistoryFragment;
import com.example.weatherapp.fragments.HomeFragment;
import com.example.weatherapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化底部导航栏
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 设置导航项选择监听器，使用lambda表达式
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        // 默认加载首页Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }

    /**
     * 处理导航项选择事件
     *
     * @param item 被选择的菜单项
     * @return true 显示被选择的fragment
     */
    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        // 根据选择的菜单项ID加载相应的Fragment
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.nav_history) {
            selectedFragment = new HistoryFragment();
        } else if (itemId == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
        return true;
    }
}
