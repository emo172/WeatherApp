package com.example.weatherapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.database.WeatherDao;
import com.example.weatherapp.model.WeatherHistory;
import com.example.weatherapp.model.WeatherResponse;
import com.example.weatherapp.repository.WeatherRepository;
import com.example.weatherapp.utils.WeatherIconMapper;

import java.util.Locale;

/**
 * 主页面Fragment，负责显示天气信息和处理用户交互
 */
public class HomeFragment extends Fragment {

    // UI控件
    private EditText cityInput;
    private TextView cityName;
    private TextView temperature;
    private TextView weatherCondition;
    private TextView windInfo;
    private TextView humidityInfo;
    private TextView visibilityInfo;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;
    private ImageView weatherIcon;
    private View weatherCard;
    private Button searchButton;

    // 数据仓库
    private WeatherRepository weatherRepository;
    private WeatherDao weatherDao;

    // SharedPreferences相关常量
    private static final String PREFS_NAME = "WeatherAppPrefs";
    private static final String KEY_DEFAULT_CITY = "default_city";

    // 标记是否为默认城市查询
    private boolean isDefaultCityQuery = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 加载布局文件
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 初始化视图和监听器
        initViews(view);
        setupListeners();

        // 初始化数据仓库
        weatherRepository = new WeatherRepository();

        // 初始化数据库访问对象
        weatherDao = new WeatherDao(requireContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次显示时检查默认城市
        checkAndLoadDefaultCity();
    }

    /**
     * 检查并加载默认城市
     */
    private void checkAndLoadDefaultCity() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String defaultCity = prefs.getString(KEY_DEFAULT_CITY, null);

        if (defaultCity != null && !defaultCity.isEmpty()) {
            // 标记为默认城市查询
            isDefaultCityQuery = true;
            // 直接查询默认城市天气（不通过输入框）
            fetchWeatherData(defaultCity);
        }
    }

    /**
     * 初始化所有UI控件
     * @param rootView 根视图
     */
    private void initViews(View rootView) {
        if (rootView == null) return;

        cityInput = rootView.findViewById(R.id.cityInput);
        cityName = rootView.findViewById(R.id.cityName);
        temperature = rootView.findViewById(R.id.temperature);
        weatherCondition = rootView.findViewById(R.id.weatherCondition);
        windInfo = rootView.findViewById(R.id.windInfo);
        humidityInfo = rootView.findViewById(R.id.humidityInfo);
        visibilityInfo = rootView.findViewById(R.id.visibilityInfo);
        errorMessage = rootView.findViewById(R.id.errorMessage);
        loadingIndicator = rootView.findViewById(R.id.loadingIndicator);
        weatherIcon = rootView.findViewById(R.id.weatherIcon);
        weatherCard = rootView.findViewById(R.id.weatherCard);
        searchButton = rootView.findViewById(R.id.searchButton);
    }

    /**
     * 设置UI控件的监听器
     */
    private void setupListeners() {
        if (searchButton == null) return;

        searchButton.setOnClickListener(v -> {
            if (cityInput == null) return;

            String city = cityInput.getText().toString().trim();
            if (!city.isEmpty()) {
                // 标记为用户主动搜索
                isDefaultCityQuery = false;
                fetchWeatherData(city);
            } else {
                showToast(getString(R.string.hint_enter_city));
            }
        });
    }

    /**
     * 获取天气数据
     * @param city 城市名称
     */
    private void fetchWeatherData(String city) {
        showLoading(true);

        weatherRepository.getWeatherData(city, new WeatherRepository.WeatherDataCallback() {
            @Override
            public void onSuccess(WeatherResponse weatherResponse) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    showLoading(false);
                    displayWeatherData(weatherResponse);
                });
            }

            @Override
            public void onFailure(String error) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    showLoading(false);
                    showError(error);
                    showToast(getString(R.string.error_api_failed) + error);
                });
            }
        });
    }

    /**
     * 显示天气数据
     * @param weatherResponse 天气API响应数据
     */
    private void displayWeatherData(WeatherResponse weatherResponse) {
        if (errorMessage == null || weatherCard == null || weatherResponse == null) return;

        errorMessage.setVisibility(View.GONE);
        weatherCard.setVisibility(View.VISIBLE);

        WeatherResponse.Result result = weatherResponse.getResults()[0];
        WeatherResponse.Now now = result.getNow();

        // 更新UI数据
        if (cityName != null) {
            cityName.setText(result.getLocation().getName());
        }

        if (temperature != null) {
            temperature.setText(String.format(Locale.getDefault(), "%s°C", now.getTemperature()));
        }

        if (weatherCondition != null) {
            weatherCondition.setText(now.getText());
        }

        if (weatherIcon != null) {
            weatherIcon.setImageResource(WeatherIconMapper.getWeatherIcon(now.getWeatherCode()));
        }

        if (windInfo != null) {
            windInfo.setText(String.format(Locale.getDefault(),
                    "%s风 %s级 | 风速: %skm/h",
                    now.getWindDirection(), now.getWindScale(), now.getWindSpeed()));
        }

        if (humidityInfo != null) {
            humidityInfo.setText(String.format(Locale.getDefault(), "湿度: %s%%", now.getHumidity()));
        }

        if (visibilityInfo != null) {
            visibilityInfo.setText(String.format(Locale.getDefault(), "能见度: %s公里", now.getVisibility()));
        }

        // 如果不是默认城市查询，则保存到数据库
        if (!isDefaultCityQuery) {
            WeatherHistory history = new WeatherHistory(
                    result.getLocation().getName(),
                    now.getTemperature(),
                    now.getText(),
                    now.getWindDirection(),
                    now.getWindScale(),
                    now.getWindSpeed(),
                    now.getHumidity(),
                    now.getVisibility(),
                    now.getWeatherCode()
            );

            weatherDao.saveWeatherHistory(history);
        }

        // 如果是默认城市查询，清除输入框内容
        if (isDefaultCityQuery && cityInput != null) {
            cityInput.setText("");
        }
    }

    /**
     * 显示错误信息
     * @param message 错误消息
     */
    private void showError(String message) {
        if (weatherCard == null || errorMessage == null) return;

        weatherCard.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(message);
    }

    /**
     * 控制加载状态显示
     * @param isLoading 是否正在加载
     */
    private void showLoading(boolean isLoading) {
        if (loadingIndicator == null || searchButton == null) return;

        loadingIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        searchButton.setEnabled(!isLoading);
    }

    /**
     * 显示Toast提示
     * @param message 提示消息
     */
    private void showToast(String message) {
        if (getContext() == null) return;
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
