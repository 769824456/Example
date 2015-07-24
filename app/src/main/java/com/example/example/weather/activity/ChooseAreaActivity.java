package com.example.example.weather.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.example.R;
import com.example.example.weather.bean.City;
import com.example.example.weather.bean.County;
import com.example.example.weather.bean.Province;
import com.example.example.weather.db.WeatherDB;
import com.example.example.weather.net.HttpCallbackListener;
import com.example.example.weather.net.HttpUtil;
import com.example.example.weather.utils.ResponseHandlerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun
 * CREATE AT : 7/21/2015 2:01 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 选择区域
 */
public class ChooseAreaActivity extends ActionBarActivity {
    public static final String TAG = "天气预报";

    //==============================================
    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;
    @Bind(R.id.tv_choose_area_title)
    TextView tvChooseAreaTitle;
    @Bind(R.id.lv_choose_area)
    ListView lvChooseArea;

    private int currentLevel = 0; // 当前选中的级别

    private Province selectedProvince; //选中的省
    private City selectedCity;   //选中的市
    private County selectCountry;  //选中的县

    private List<Province> provinceList; //省列表
    private List<City> cityList;   //市列表
    private List<County> countyList;  //县列表

    private List<String> mDataList = new ArrayList<String>(); //存放数据

    private ArrayAdapter<String> mAdapter;
    private WeatherDB weatherDB;
    private boolean isFromWeatherActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        ButterKnife.bind(this);
        initViews();
    }
    //===============================================

    /**
     * 业务逻辑：
     * 1.先加载省数据
     * 2.根据选中的 省 的code去服务器获取 市 的数据
     * 3。根据选中的 市 的code去服务器获取 县 的数据
     */

    /**
     * 初始化布局
     */
    void initViews() {
        isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity", false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("city_selected", false) && !isFromWeatherActivity) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        weatherDB = WeatherDB.getInstance(this);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDataList);
        lvChooseArea.setAdapter(mAdapter);
        //加载省数据
        queryProvinces();

        lvChooseArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    //加载市数据
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    //加载县数据
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String countyCode = countyList.get(position).getCountyCode();
                    Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
                    intent.putExtra("county_code", countyCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    /**
     * 加载所有省的数据
     * 先去数据库找，数据库没有再去服务器上获取
     */
    private void queryProvinces() {
        provinceList = weatherDB.getProvinces();
        if (provinceList.size() > 0) { //数据库里面有数据
            mDataList.clear();
            //添加数据到mDataList
            for (Province province : provinceList) {
                mDataList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            lvChooseArea.setSelection(0);//默认选中第一个
            tvChooseAreaTitle.setText("中国");

            currentLevel = LEVEL_PROVINCE;

        } else {//数据库里面没有数据
            //从服务器获取数据
            queryFromServer(null, "province");
        }
    }

    /**
     * 加载市数据
     */
    private void queryCities() {
        cityList = weatherDB.getCities(selectedProvince.getId());
        //从数据库加载数据
        if (cityList.size() > 0) {
            mDataList.clear();
            for (City city : cityList) {
                mDataList.add(city.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            lvChooseArea.setSelection(0);
            tvChooseAreaTitle.setText(selectedProvince.getProvinceName());

            currentLevel = LEVEL_CITY;
        } else { //从服务器加载数据
            queryFromServer(selectedProvince.getProvinceCode(), "city");
        }
    }


    /**
     * 加载县数据
     */
    private void queryCounties() {
        countyList = weatherDB.getCounties(selectedCity.getId());
        //从数据库加载数据
        if (countyList.size() > 0) {
            mDataList.clear();
            for (County county : countyList
                    ) {
                mDataList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            lvChooseArea.setSelection(0);
            tvChooseAreaTitle.setText(selectedCity.getCityName());

            currentLevel = LEVEL_COUNTY;
        }
        //从服务区加载数据
        else {
            queryFromServer(selectedCity.getCityCode(), "county");
        }
    }


    /**
     * 从服务器获取数据
     *
     * @param code
     * @param levelType
     */
    private void queryFromServer(final String code, final String levelType) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProvinceDialog();
        HttpUtil.sendHttpGetRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i("queryFromServer", response);

                boolean result = false;
                //处理返回的数据结果
                if ("province".equals(levelType)) {
                    result = ResponseHandlerUtil.handleProvincesResponse(weatherDB, response);
                } else if ("city".equals(levelType)) {
                    result = ResponseHandlerUtil.handleCitiesResponse(weatherDB, response, selectedProvince.getId());
                } else if ("county".equals(levelType)) {
                    result = ResponseHandlerUtil.handleCountiesResponse(weatherDB, response, selectedCity.getId());
                }
                //处理结果成功之后
                if (result) {
                    // 通过runOnUiThread()方法回到主线程处理逻辑
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(levelType)) {
                                queryProvinces();
                            } else if ("city".equals(levelType)) {
                                queryCities();
                            } else if ("county".equals(levelType)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                // 通过runOnUiThread()方法回到主线程处理逻辑
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private ProgressDialog progressDialog = null;

    /**
     * 显示进度对话框
     */
    public void showProvinceDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭对话框
     */
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    ;

    /**
     * 捕获Back按键，根据当前的级别来判断，此时应该返回市列表、省列表、还是直接退出。
     */
    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        } else {
            if (isFromWeatherActivity) {
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }

}
