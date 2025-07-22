package com.example.weatherapp.utils;

import com.example.weatherapp.R;

/**
 * 天气图标映射工具类，用于将天气代码映射为对应的图标资源
 */
public class WeatherIconMapper {

    /**
     * 根据天气代码获取对应的图标资源ID
     * @param weatherCode 天气代码
     * @return 图标资源ID
     */
    public static int getWeatherIcon(String weatherCode) {
        switch (weatherCode) {
            case "0": // 晴（国内城市白天晴）
                return R.drawable.ic_day_sunny;
            case "1": // 晴（国内城市夜晚晴）
                return R.drawable.ic_night_sunny;
            case "2": // 晴（国外城市白天晴）
                return R.drawable.ic_day_sunny;
            case "3": // 晴（国外城市夜晚晴）
                return R.drawable.ic_night_sunny;
            case "4": // 多云
                return R.drawable.ic_cloudy;
            case "5": // 晴间多云
                return R.drawable.ic_day_cloudy;
            case "6": // 晴间多云
                return R.drawable.ic_night_cloudy;
            case "7": // 大部多云
                return R.drawable.ic_day_cloudy;
            case "8": // 大部多云
                return R.drawable.ic_night_cloudy;
            case "9": // 阴
                return R.drawable.ic_overcast;
            case "10": // 阵雨
                return R.drawable.ic_shower;
            case "11": // 雷阵雨
                return R.drawable.ic_thundershower;
            case "12": // 雷阵雨伴有冰雹
                return R.drawable.ic_thundershower_hail;
            case "13": // 小雨
                return R.drawable.ic_light_rain;
            case "14": // 中雨
                return R.drawable.ic_moderate_rain;
            case "15": // 大雨
                return R.drawable.ic_heavy_rain;
            case "16": // 暴雨
                return R.drawable.ic_storm;
            case "17": // 大暴雨
                return R.drawable.ic_heavy_storm;
            case "18": // 特大暴雨
                return R.drawable.ic_severe_storm;
            case "19": // 冻雨
                return R.drawable.ic_ice_rain;
            case "20": // 雨夹雪
                return R.drawable.ic_sleet;
            case "21": // 阵雪
                return R.drawable.ic_snow_flurry;
            case "22": // 小雪
                return R.drawable.ic_light_snow;
            case "23": // 中雪
                return R.drawable.ic_moderate_snow;
            case "24": // 大雪
                return R.drawable.ic_heavy_snow;
            case "25": // 暴雪
                return R.drawable.ic_snowstorm;
            case "26": // 浮尘
                return R.drawable.ic_dust;
            case "27": // 扬沙
                return R.drawable.ic_sand;
            case "28": // 沙尘暴
                return R.drawable.ic_duststorm;
            case "29": // 强沙尘暴
                return R.drawable.ic_sandstorm;
            case "30": // 雾
                return R.drawable.ic_foggy;
            case "31": // 霾
                return R.drawable.ic_haze;
            case "32": // 风
                return R.drawable.ic_wind;
            case "33": // 大风
                return R.drawable.ic_strong_wind;
            case "34": // 飓风
                return R.drawable.ic_hurricane;
            case "35": // 热带风暴
                return R.drawable.ic_tropical_storm;
            case "36": // 龙卷风
                return R.drawable.ic_tornado;
            case "37": // 冷
                return R.drawable.ic_cold;
            case "38": // 热
                return R.drawable.ic_hot;
            case "99": // 未知
            default:
                return R.drawable.ic_unknown;
        }
    }
}
