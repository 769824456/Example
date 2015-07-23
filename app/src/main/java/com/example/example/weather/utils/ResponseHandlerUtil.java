package com.example.example.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;


import com.example.example.weather.bean.City;
import com.example.example.weather.bean.County;
import com.example.example.weather.bean.Province;
import com.example.example.weather.db.WeatherDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR :  yulongsun 
 * CREATE AT : 7/22/2015 10:16 AM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 返回结果处理工具类
 */
public class ResponseHandlerUtil {


    /**
     * 解析和处理服务器返回的省级数据
     *
     * @param weatherDB
     * @param response
     * @return
     */
    public synchronized static boolean handleProvincesResponse(WeatherDB weatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            // 将解析出来的数据存储到Province表
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces
                        ) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1])
                    ;
                    weatherDB.saveProvince(province);
                }
            }
            return true;
        }
        return false;
    }


    /**
     * 解析和处理服务器返回的市级数据
     *
     * @param weatherDB
     * @param response
     * @param provinceId
     * @return
     */
    public static boolean handleCitiesResponse(WeatherDB weatherDB,
                                               String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            // 将解析出来的数据存储到City表
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);

                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     *
     * @param weatherDB
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountiesResponse(WeatherDB weatherDB,
                                                 String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            // 将解析出来的数据存储到County表
            if (allCounties != null && allCounties.length > 0) {
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);

                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }


    //==========================================解析Json,并序列化到本地======================================================

    /**
     * 解析服务器返回的JSON数据，并将解析出的数据存储到本地
     *
     * @param context
     * @param response
     */
    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherinfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherinfo.getString("city");
            String weatherCode = weatherinfo.getString("cityid");
            String temp1 = weatherinfo.getString("temp1");
            String temp2 = weatherinfo.getString("temp2");
            String weatherDesp = weatherinfo.getString("weather");
            String publishTime = weatherinfo.getString("ptime");

            //将服务器返回的所有天气信息存储到SharedPreferences文件中。
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
                    weatherDesp, publishTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将服务器返回的所有天气信息存储到SharedPreferences文件中。
     *
     * @param context
     * @param cityName
     * @param weatherCode
     * @param temp1
     * @param temp2
     * @param weatherDesp
     * @param publishTime 发布时间
     */
    public static void saveWeatherInfo(Context context, String cityName,
                                       String weatherCode, String temp1, String temp2, String weatherDesp,
                                       String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();
    }

}
