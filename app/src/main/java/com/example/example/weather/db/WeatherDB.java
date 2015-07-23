package com.example.example.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.example.weather.bean.City;
import com.example.example.weather.bean.County;
import com.example.example.weather.bean.Province;

import java.util.ArrayList;
import java.util.List;

/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun 
 * CREATE AT : 7/21/2015 5:16 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 数据库操作类
 */
public class WeatherDB {

    public static final String DB_NAME = "weather.db";//数据库名字

    public static final int VERSION = 1;//数据库版本
    private static WeatherDB weatherDB;
    private final SQLiteDatabase db;
    private ContentValues contentValues;


    /**
     * 构造方法私有化
     *
     * @param context
     */
    private WeatherDB(Context context) {
        WeatherSQLiteOpenHelper openHelper = new WeatherSQLiteOpenHelper(context, DB_NAME, null, VERSION);
        db = openHelper.getWritableDatabase();
    }


    /**
     * 获取操作数据库实例
     *
     * @param context
     * @return
     */
    public synchronized static WeatherDB getInstance(Context context) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    //========================省===========================

    /**
     * 保存省份到本地
     *
     * @param province
     */
    public void saveProvince(Province province) {
        if (province != null) {
            contentValues = new ContentValues();
            contentValues.put("province_name", province.getProvinceName());
            contentValues.put("province_code", province.getProvinceCode());
            db.insert("Province", null, contentValues);
        }
    }

    /**
     * 从数据库读取所有的省份信息
     *
     * @return
     */
    public List<Province> getProvinces() {
        ArrayList<Province> provinces = new ArrayList<>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                provinces.add(province);
            } while (cursor.moveToNext());
        }
        return provinces;
    }
    //========================市===========================

    /**
     * 保存市
     *
     * @param city
     */
    public void saveCity(City city) {
        if (city != null) {
            contentValues = new ContentValues();
            contentValues.put("city_name", city.getCityName());
            contentValues.put("city_code", city.getCityCode());
            contentValues.put("province_id",city.getProvinceId());
            db.insert("City", null, contentValues);
        }
    }

    /**
     * 读取数据库中某省下的所有市
     *
     * @param provinceId
     * @return
     */
    public List<City> getCities(int provinceId) {
        ArrayList<City> cities = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                cities.add(city);
            } while (cursor.moveToNext());
        }
        return cities;
    }

    //========================县===========================

    /**
     * 保存县
     *
     * @param county
     */
    public void saveCounty(County county) {
        if (county != null) {
            contentValues = new ContentValues();
            Log.d("county", county.toString());
            contentValues.put("county_name", county.getCountyName());
            contentValues.put("county_code", county.getCountyCode());
            contentValues.put("city_id",county.getCityId());
            db.insert("County", null, contentValues);
        }
    }

    /**
     * 从数据库中读取所有保存的县
     *
     * @param cityId
     * @return
     */
    public List<County> getCounties(int cityId) {
        ArrayList<County> countries = new ArrayList<>();
        Cursor cursor = db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                countries.add(county);
            } while (cursor.moveToNext());
        }
        return countries;
    }


}
