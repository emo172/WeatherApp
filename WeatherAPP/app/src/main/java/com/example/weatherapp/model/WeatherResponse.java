package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * 天气API响应数据模型，用于解析和风天气API返回的数据
 */
public class WeatherResponse {
    @SerializedName("results")
    private Result[] results;

    public Result[] getResults() {
        return results;
    }

    /**
     * 结果数据内部类，包含位置和当前天气信息
     */
    public static class Result {
        @SerializedName("location")
        private Location location;

        @SerializedName("now")
        private Now now;

        public Location getLocation() {
            return location;
        }

        public Now getNow() {
            return now;
        }
    }

    /**
     * 位置信息内部类
     */
    public static class Location {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }

    /**
     * 当前天气信息内部类，包含温度、天气状况、风力等信息
     */
    public static class Now {
        @SerializedName("temperature")
        private String temperature;

        @SerializedName("text")
        private String text;

        @SerializedName("wind_direction")
        private String windDirection;

        @SerializedName("wind_scale")
        private String windScale;

        @SerializedName("wind_speed")
        private String windSpeed;

        @SerializedName("humidity")
        private String humidity;

        @SerializedName("visibility")
        private String visibility;

        @SerializedName("code")
        private String weatherCode;

        public String getTemperature() {
            return temperature;
        }

        public String getText() {
            return text;
        }

        public String getWindDirection() {
            return windDirection;
        }

        public String getWindScale() {
            return windScale;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public String getHumidity() {
            return humidity;
        }

        public String getVisibility() {
            return visibility;
        }

        public String getWeatherCode() {
            return weatherCode;
        }
    }
}
