package com.example.example.weather.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.example.weather.service.AutoUpdateService;


/*
 * PROJECT_NAME :ExampleSet
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun
 * CREATE AT : 7/21/2015 2:01 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE :
 */
public class AutoUpdateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, AutoUpdateService.class);
		context.startService(i);
	}

}
