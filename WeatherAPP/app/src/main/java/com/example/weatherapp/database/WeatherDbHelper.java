package com.example.weatherapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "weather_history.db";
    private static final int DATABASE_VERSION = 1;

    // 表名和列名
    public static final String TABLE_HISTORY = "weather_history";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_CONDITION = "condition";
    public static final String COLUMN_WIND_DIRECTION = "wind_direction";
    public static final String COLUMN_WIND_SCALE = "wind_scale";
    public static final String COLUMN_WIND_SPEED = "wind_speed";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_VISIBILITY = "visibility";
    public static final String COLUMN_WEATHER_CODE = "weather_code";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // 创建表SQL语句
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_HISTORY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CITY + " TEXT NOT NULL, " +
                    COLUMN_TEMPERATURE + " TEXT, " +
                    COLUMN_CONDITION + " TEXT, " +
                    COLUMN_WIND_DIRECTION + " TEXT, " +
                    COLUMN_WIND_SCALE + " TEXT, " +
                    COLUMN_WIND_SPEED + " TEXT, " +
                    COLUMN_HUMIDITY + " TEXT, " +
                    COLUMN_VISIBILITY + " TEXT, " +
                    COLUMN_WEATHER_CODE + " TEXT, " +
                    COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }
}
