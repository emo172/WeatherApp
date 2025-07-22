package com.example.weatherapp.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.R;

public class DefaultCityActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "WeatherAppPrefs";
    private static final String KEY_DEFAULT_CITY = "default_city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_city);

        EditText etDefaultCity = findViewById(R.id.et_default_city);
        Button btnSave = findViewById(R.id.btn_save_default_city);
        Button btnClear = findViewById(R.id.btn_clear_default_city);

        // 显示当前默认城市
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String currentCity = prefs.getString(KEY_DEFAULT_CITY, "");
        etDefaultCity.setText(currentCity);

        // 保存按钮点击事件
        btnSave.setOnClickListener(v -> {
            String city = etDefaultCity.getText().toString().trim();

            if (!city.isEmpty()) {
                // 保存到SharedPreferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(KEY_DEFAULT_CITY, city);
                editor.apply();

                Toast.makeText(this, "默认城市已设置为：" + city, Toast.LENGTH_SHORT).show();
                finish(); // 关闭当前页面
            } else {
                Toast.makeText(this, "请输入城市名称", Toast.LENGTH_SHORT).show();
            }
        });

        // 清除按钮点击事件
        btnClear.setOnClickListener(v -> {
            // 清除默认城市设置
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(KEY_DEFAULT_CITY);
            editor.apply();

            // 清空输入框
            etDefaultCity.setText("");

            Toast.makeText(this, "默认城市已清除", Toast.LENGTH_SHORT).show();
        });
    }
}
