package com.weatherdemo.net;


import com.weatherdemo.domain.Index;
import com.weatherdemo.domain.Weather;
import com.weatherdemo.domain.WeatherData;
import com.weatherdemo.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：GetWeather
 * 类描述：获取天气
 * 创建人：徐志国
 * 修改人：徐志国
 * 修改时间：2014-12-9 下午4:16:50
 * 修改备注：
 *
 * @version 1.0.0
 */
public class GetWeather {

    public GetWeather(String location,
                      final SuccessCallback successCallback,
                      final FailCallback failCallback) {
        new NetConnection(
                Config.URL_API,
                HttpMethod.GET,
                new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            System.out.println(result);
                            JSONObject obj = new JSONObject(result);
                            switch (obj.getInt(Config.KEY_STATUS)) {
                                case Config.RESULT_STATUS_SUCCESS:
                                    if (successCallback != null) {
                                        Weather weather =new Weather();
                                        weather.setDate(obj.getString("date"));
                                        JSONArray arrResults =obj.getJSONArray("results");
                                        JSONObject objResults = arrResults.getJSONObject(0);
                                        weather.setPm25(objResults.getString("pm25"));
                                        weather.setCurrentCity(objResults.getString("currentCity"));
                                        JSONArray arrIndexs =objResults.getJSONArray("index");
                                        List<Index> indexes = new ArrayList<>();
                                        for (int i=0;i<arrIndexs.length();i++){
                                            JSONObject objIndex = (JSONObject) arrIndexs.get(i);
                                            Index index =new Index();
                                            index.setTitle(objIndex.getString("title"));
                                            index.setZs(objIndex.getString("zs"));
                                            index.setTipt(objIndex.getString("tipt"));
                                            index.setDes(objIndex.getString("des"));
                                            indexes.add(index);
                                        }

                                        JSONArray arrDatas =objResults.getJSONArray("weather_data");
                                        List<WeatherData> weatherDatas =new ArrayList<>();
                                        for (int i=0;i<arrDatas.length();i++){
                                            JSONObject objData = (JSONObject) arrDatas.get(i);
                                            WeatherData data =new WeatherData();
                                            data.setDate(objData.getString("date"));
                                            data.setDayPictureUrl(objData.getString("dayPictureUrl"));
                                            data.setNightPictureUrl(objData.getString("nightPictureUrl"));
                                            data.setWeather(objData.getString("weather"));
                                            data.setWind(objData.getString("wind"));
                                            data.setTemperature(objData.getString("temperature"));
                                            weatherDatas.add(data);
                                        }
                                        weather.setIndexs(indexes);
                                        weather.setDatas(weatherDatas);
                                       successCallback.onSuccess(weather);

                                    }
                                    break;
                                default:
                                    if (failCallback != null) {
                                        failCallback.onFail();
                                    }
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (failCallback != null) {
                                failCallback.onFail();
                            }
                        }
                    }
                }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCallback != null) {
                    failCallback.onFail();
                }
            }
        }, Config.KEY_LOCATION, location, Config.KEY_OUTPUT,
                Config.OUTPUT, Config.KEY_AK, Config.AK,
                Config.KEY_MCODE, Config.MCODE);
    }
    public static interface SuccessCallback {
        void onSuccess(Weather weather);
    }

    public static interface FailCallback {
        void onFail();
    }
}
