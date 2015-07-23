package com.example.example.weather.bean;

/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun
 * CREATE AT : 7/21/2015 4:58 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 市
 */
public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private int provinceId;//所属省

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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
