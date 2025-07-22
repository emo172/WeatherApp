package com.example.weatherapp.repository;

import androidx.annotation.NonNull;
import com.example.weatherapp.model.WeatherResponse;
import com.example.weatherapp.network.WeatherApiClient;
import com.example.weatherapp.service.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 天气数据仓库，负责与天气API通信并获取天气数据
 */
public class WeatherRepository {

    private final WeatherService weatherService;

    public WeatherRepository() {
        // 从网络模块获取 Retrofit 实例，并创建接口对象
        weatherService = WeatherApiClient.getClient().create(WeatherService.class);
    }

    /**
     * 请求指定城市的天气数据
     * @param city 城市名称
     * @param callback 数据回调接口，用于处理成功和失败的情况
     */
    public void getWeatherData(String city, final WeatherDataCallback callback) {
        Call<WeatherResponse> call = weatherService.getWeather(city, WeatherApiClient.getApiKey(), "zh-Hans", "c");
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("无法获取天气数据");
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                callback.onFailure("网络错误: " + t.getMessage());
            }
        });
    }

    /**
     * 天气数据回调接口，定义获取天气数据成功和失败时的处理方法
     */
    public interface WeatherDataCallback {
        void onSuccess(WeatherResponse weatherResponse);
        void onFailure(String errorMessage);
    }
}
