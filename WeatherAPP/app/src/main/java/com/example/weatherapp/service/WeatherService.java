package com.example.weatherapp.service;

import com.example.weatherapp.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 天气API服务接口，使用Retrofit定义天气数据请求方法
 */
public interface WeatherService {
    /**
     * 获取当前天气信息的API请求方法
     * @param cityName 城市名称
     * @param apiKey API密钥
     * @param language 返回数据的语言
     * @param unit 温度单位
     * @return 返回包含天气信息的Call对象
     */
    @GET("weather/now.json")
    Call<WeatherResponse> getWeather(
            @Query("location") String cityName,
            @Query("key") String apiKey,
            @Query("language") String language,
            @Query("unit") String unit
    );
}
