package com.example.example.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

/*
 * PROJECT_NAME :Example
 * VERSION :[V 1.0.0]
 * AUTHOR :  yulongsun 
 * CREATE AT : 7/24/2015 9:28 AM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE :
 */
public class MyApp extends LitePalApplication {
    public static final String TAG = "bill.lia";
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        SQLiteDatabase db = Connector.getDatabase();
    }
}
