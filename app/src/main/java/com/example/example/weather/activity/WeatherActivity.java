package com.example.example.weather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.example.R;
import com.example.example.weather.net.HttpCallbackListener;
import com.example.example.weather.net.HttpUtil;
import com.example.example.weather.service.AutoUpdateService;
import com.example.example.weather.utils.ResponseHandlerUtil;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun
 * CREATE AT : 7/21/2015 2:01 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 显示天气
 */
public class WeatherActivity extends ActionBarActivity {


    @Bind(R.id.btn_switch_city)
    Button btnSwitchCity;
    @Bind(R.id.tv_city_name)
    TextView tvCityName;
    @Bind(R.id.btn_refresh_weather)
    Button btnRefreshWeather;
    @Bind(R.id.tv_publish_time)
    TextView tvPublishTime;
    @Bind(R.id.tv_current_date)
    TextView tvCurrentDate;
    @Bind(R.id.tv_weather_desp)
    TextView tvWeatherDesp;
    @Bind(R.id.tv_temp1)
    TextView tvTemp1;
    @Bind(R.id.tv_temp2)
    TextView tvTemp2;
    @Bind(R.id.ll_weather_info_layout)
    LinearLayout llWeatherInfoLayout;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);
        initView();
    }

    void initView() {
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {
            // 有县级代号时就去查询天气
            tvPublishTime.setText("同步中...");
            llWeatherInfoLayout.setVisibility(View.INVISIBLE);
            tvCityName.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            // 没有县级代号时就直接显示本地天气
            showWeather();
        }
    }

    @OnClick(R.id.btn_switch_city)
    void switchCity() {
        Intent intent = new Intent(this, ChooseAreaActivity.class);
        intent.putExtra("from_weather_activity", true);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_refresh_weather)
    void refreshWeather() {
        tvPublishTime.setText("同步中...");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = prefs.getString("weather_code", "");
        if (!TextUtils.isEmpty(weatherCode)) {
            queryWeatherInfo(weatherCode);
        }
    }

    /**
     * 查询县级代号所对应的天气代号。
     */
    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address, "countyCode");
    }

    /**
     * 查询天气代号所对应的天气。
     */
    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address, "weatherCode");
    }

    /**
     * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
     */
    private void queryFromServer(final String address, final String type) {
        HttpUtil.sendHttpGetRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                Log.d("WeatherActivity:", response);

                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        // 从服务器返回的数据中解析出天气代号
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    // 处理服务器返回的天气信息
                    ResponseHandlerUtil.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPublishTime.setText("同步失败");
                    }
                });
            }
        });
    }

    /**
     * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上。
     */
    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tvCityName.setText(prefs.getString("city_name", ""));
        tvTemp1.setText(prefs.getString("temp1", ""));
        tvTemp2.setText(prefs.getString("temp2", ""));
        tvWeatherDesp.setText(prefs.getString("weather_desp", ""));
        tvPublishTime.setText("今天" + prefs.getString("publish_time", "") + "发布");
        tvCurrentDate.setText(prefs.getString("current_date", ""));
        llWeatherInfoLayout.setVisibility(View.VISIBLE);
        tvCityName.setVisibility(View.VISIBLE);
        //开启自动更新服务
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }


}
