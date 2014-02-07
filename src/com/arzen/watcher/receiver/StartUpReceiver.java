package com.arzen.watcher.receiver;

import com.arzen.watcher.service.WatcherService;
import com.arzen.watcher.utils.Log;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartUpReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.e("StartUpReceiver", "onReceive action is:" + intent.getAction());
		boolean isServiceRunning = false;
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if ("com.arzen.watcher.service.WatcherService".equals(service.service.getClassName()))
			// Service的类名
			{
				isServiceRunning = true;
			}
		}
		if (!isServiceRunning) {
			Log.e("StartUpReceiver", "onReceive start WatcherService");
			Intent i = new Intent(context, WatcherService.class);
			context.startService(i);
		}else{
			Log.e("StartUpReceiver", "WatcherService runing");
		}
	}

}
