package com.example.weatherapp.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 查找进入按钮
        ImageView enterButton = findViewById(R.id.EnterButton);

        // 设置按钮点击监听器，使用lambda表达式
        enterButton.setOnClickListener(v -> {
            // 创建一个意图，启动MainActivity
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            // 调用finish()以便在返回时关闭欢迎界面
            finish();
        });
    }
}
