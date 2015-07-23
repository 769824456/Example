package com.example.example.weather.utils;

import android.util.Log;

/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun
 * CREATE AT : 7/21/20152:09 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 日志工具类
 */
public class LogUtil {
    public static final int VERBOSE=1;
    public static final int DEBUG=2;
    public static final int INFO=3;
    public static final int WARN =4;
    public static final int ERROR=5;
    public static final int NOTHING=6;
    public static final int LEVEL=VERBOSE;

    public static void v(String tag,String msg){
        if(LEVEL<VERBOSE){
            Log.v(tag, msg);
        }
    }
    public static void d(String tag,String msg){
        if(LEVEL<VERBOSE){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag,String msg){
        if(LEVEL<VERBOSE){
            Log.i(tag, msg);
        }
    }
    public static void w(String tag,String msg){
        if(LEVEL<VERBOSE){
            Log.w(tag, msg);
        }
    }
    public static void e(String tag,String msg){
        if(LEVEL<VERBOSE){
            Log.e(tag, msg);
        }
    }

}
