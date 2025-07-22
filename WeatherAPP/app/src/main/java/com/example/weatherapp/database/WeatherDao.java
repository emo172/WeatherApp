package com.example.weatherapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.weatherapp.model.WeatherHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherDao {
    private WeatherDbHelper dbHelper;

    public WeatherDao(Context context) {
        dbHelper = new WeatherDbHelper(context);
    }

    // 保存天气记录
    public long saveWeatherHistory(WeatherHistory history) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 使用当前手机时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        ContentValues values = new ContentValues();
        values.put(WeatherDbHelper.COLUMN_CITY, history.getCityName());
        values.put(WeatherDbHelper.COLUMN_TEMPERATURE, history.getTemperature());
        values.put(WeatherDbHelper.COLUMN_CONDITION, history.getWeatherCondition());
        values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION, history.getWindDirection());
        values.put(WeatherDbHelper.COLUMN_WIND_SCALE, history.getWindScale());
        values.put(WeatherDbHelper.COLUMN_WIND_SPEED, history.getWindSpeed());
        values.put(WeatherDbHelper.COLUMN_HUMIDITY, history.getHumidity());
        values.put(WeatherDbHelper.COLUMN_VISIBILITY, history.getVisibility());
        values.put(WeatherDbHelper.COLUMN_WEATHER_CODE, history.getWeatherCode());
        // 使用当前手机时间
        values.put(WeatherDbHelper.COLUMN_TIMESTAMP, currentTime);

        long id = db.insert(WeatherDbHelper.TABLE_HISTORY, null, values);
        db.close();
        return id;
    }

    // 在getAllHistory方法中添加时间戳字段查询
    public List<WeatherHistory> getAllHistory() {
        List<WeatherHistory> historyList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + WeatherDbHelper.TABLE_HISTORY +
                " ORDER BY " + WeatherDbHelper.COLUMN_TIMESTAMP + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                WeatherHistory history = new WeatherHistory();
                history.setId(cursor.getInt(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_ID)));
                history.setCityName(cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_CITY)));
                history.setTemperature(cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_TEMPERATURE)));
                history.setWeatherCondition(cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_CONDITION)));
                history.setWindDirection(cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_WIND_DIRECTION)));
                history.setWindScale(cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_WIND_SCALE)));
                history.setWindSpeed(cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_WIND_SPEED)));
                history.setHumidity(cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_HUMIDITY)));
                history.setVisibility(cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_VISIBILITY)));
                history.setWeatherCode(cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_WEATHER_CODE)));

                // 添加时间戳查询
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(WeatherDbHelper.COLUMN_TIMESTAMP));
                // 将时间字符串转换为Date对象
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    history.setQueryTime(sdf.parse(timestamp));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                historyList.add(history);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return historyList;
    }

    // 添加根据ID删除历史记录的方法
    public void deleteHistory(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(WeatherDbHelper.TABLE_HISTORY,
                WeatherDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // 删除所有历史记录
    public void clearAllHistory() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(WeatherDbHelper.TABLE_HISTORY, null, null);
        db.close();
    }
}
