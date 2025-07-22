package com.example.weatherapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 天气API客户端，负责创建Retrofit实例和提供API密钥
 */
public class WeatherApiClient {

    // API的基础URL，使用和风天气API
    private static final String BASE_URL = "https://api.seniverse.com/v3/";
    // API密钥，实际开发中应存储在更安全的地方
    private static final String API_KEY = "SL6n6M-2JnloWz2qM";

    private static Retrofit retrofitInstance;

    /**
     * 获取Retrofit实例，使用单例模式确保只创建一个实例
     * @return Retrofit实例
     */
    public static Retrofit getClient() {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    /**
     * 获取API密钥
     * @return API密钥字符串
     */
    public static String getApiKey() {
        return API_KEY;
    }
}
