package com.example.example.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun 
 * CREATE AT : 7/21/2015 5:04 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 数据库帮助类
 */
public class WeatherSQLiteOpenHelper extends SQLiteOpenHelper {

    /**
     * 省
     */
    public static final String CREATE_PROVINCE = "create table Province(" +
            "id integer primary key autoincrement," +
            "province_name varchar," +
            "province_code varchar)";
    /**
     * 市
     */
    public static final String CREATE_CITY = "create table City(" +
            "id integer primary key autoincrement," +
            "city_name varchar," +
            "city_code varchar," +
            "province_id integer)";

    /**
     * 区
     */
    public static final String CREATE_COUNTRY = "create table County(" +
            "id integer primary key autoincrement," +
            "county_name varchar," +
            "county_code varchar," +
            "city_id integer)";


    public WeatherSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**创建数据库调用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTRY);

    }

    /**更新数据库调用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
