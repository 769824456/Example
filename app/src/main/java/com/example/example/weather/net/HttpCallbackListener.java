package com.example.example.weather.net;

/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun
 * CREATE AT : 7/21/20154:18 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE :  回调接口
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
