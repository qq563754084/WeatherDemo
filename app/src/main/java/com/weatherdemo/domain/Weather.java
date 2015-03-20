package com.weatherdemo.domain;

import java.util.List;

/**
 * Created by Administrator on 2015/3/20.
 */
public class Weather {
    private String currentCity;
    private String pm25;
    private String date;
    private List<Index> indexs;
    private List<WeatherData> datas;

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Index> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<Index> indexs) {
        this.indexs = indexs;
    }

    public List<WeatherData> getDatas() {
        return datas;
    }

    public void setDatas(List<WeatherData> datas) {
        this.datas = datas;
    }
}
