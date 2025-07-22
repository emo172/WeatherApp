package com.example.weatherapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.activitys.DefaultCityActivity;

public class ProfileFragment extends Fragment {

    private static final String PREFS_NAME = "WeatherAppPrefs";
    private static final String KEY_DEFAULT_CITY = "default_city";

    private TextView tvDefaultCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnSetDefaultCity = view.findViewById(R.id.btn_set_default_city);
        tvDefaultCity = view.findViewById(R.id.tv_default_city);

        // 设置按钮点击事件
        btnSetDefaultCity.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DefaultCityActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次页面显示时更新默认城市显示
        updateDefaultCityDisplay();
    }

    // 更新默认城市显示
    private void updateDefaultCityDisplay() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String defaultCity = prefs.getString(KEY_DEFAULT_CITY, null);

        if (defaultCity != null && !defaultCity.isEmpty()) {
            tvDefaultCity.setText("当前默认城市：" + defaultCity);
        } else {
            tvDefaultCity.setText("当前默认城市：未设置");
        }
    }
}
