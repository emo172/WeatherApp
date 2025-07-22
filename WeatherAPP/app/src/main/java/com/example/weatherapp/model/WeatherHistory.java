package com.example.weatherapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 天气历史记录实体类，用于存储天气查询历史数据
 */
public class WeatherHistory {
    private int id;
    private String cityName;      // 城市名称
    private String temperature;   // 温度
    private String weatherCondition; // 天气状况
    private String windDirection; // 风向
    private String windScale;     // 风力等级
    private String windSpeed;     // 风速
    private String humidity;      // 湿度
    private String visibility;    // 能见度
    private String weatherCode;   // 天气代码，用于匹配图标
    private Date queryTime;       // 查询时间

    // 无参构造函数，用于ORM框架反射创建对象
    public WeatherHistory() {
        // 默认构造函数
    }

    // 带参数的构造函数，用于创建天气历史记录对象
    public WeatherHistory(String cityName, String temperature, String weatherCondition,
                          String windDirection, String windScale, String windSpeed,
                          String humidity, String visibility, String weatherCode) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.windDirection = windDirection;
        this.windScale = windScale;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.visibility = visibility;
        this.weatherCode = weatherCode;
    }

    // 以下为各属性的Getter和Setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindScale() {
        return windScale;
    }

    public void setWindScale(String windScale) {
        this.windScale = windScale;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }

    /**
     * 获取格式化后的查询时间字符串
     * @return 格式化后的时间字符串，格式为"yyyy-MM-dd HH:mm"
     */
    public String getFormattedTime() {
        if (queryTime == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(queryTime);
    }
}
