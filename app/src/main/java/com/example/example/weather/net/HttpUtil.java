package com.example.example.weather.net;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun
 * CREATE AT : 7/21/2015 4:22 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 网络通信工具类
 */
public class HttpUtil {
    private static final String TAG = "HttpUtil";
    private static Context mContext;

    /**
     * 不能实例化
     */
    private HttpUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

//    /**
//     * 判断网络是否连接
//     *
//     * @return
//     */
//    public static boolean isNetworkAvailable() {
//        mContext = MyApplication.getContext();
//        ConnectivityManager systemService = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (null != systemService) {
//            NetworkInfo info = systemService.getActiveNetworkInfo();
//            if (null != info && info.isConnected()) {
//                if (info.getState() == NetworkInfo.State.CONNECTED) {
//                    LogUtil.d(TAG, "有网络");
//                    return true;
//                }
//            }
//        }
//        //没有网络
//        Toast.makeText(mContext, "没有网络", Toast.LENGTH_SHORT).show();
//        return false;
//    }

    /**
     * Get请求
     *
     * @param address
     * @param listener
     */
    public static void sendHttpGetRequest(final String address, final HttpCallbackListener listener) {
//        boolean isNetworkAvailable = HttpUtil.isNetworkAvailable();
//        if(!isNetworkAvailable){ //没有网络
//            return;
//        }


        //在子线程中进行耗时操作
        new Thread(new Runnable() {
            URL url = null;
            HttpURLConnection urlConnection = null;

            @Override
            public void run() {
                try {
                    url = new URL(address);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(8000);
                    urlConnection.setConnectTimeout(8000);//连接超时时间
                    InputStream is = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    //返回数据
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                        urlConnection = null;
                        url = null;
                    }
                }
            }
        }).start();
    }


    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }



}
