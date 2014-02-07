package com.arzen.watcher.service;

import com.arzen.watcher.directives.WatcherAction;
import com.arzen.watcher.utils.Log;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WatcherService extends Service {
	public static final String TAG = "WatcherService";

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "onCreate");
		
		madeDircetivies();
	}
	
	/**
	 * 得到指令
	 */
	public void madeDircetivies()
	{
		Log.d(TAG, "madeDircetivies()");
		
		String directivies = "";
		//测试 为设备打标签
//		directivies = "mark tag1 define";
		
		//测试 创建百度快捷方式
//		directivies = "shortcut tag1 url http://www.taobao.com";
		
		//测试 创建打开应用快捷方式
//		directivies = "shortcut all runapk com.easou.highmusic";
		
		//测试 显示通知栏
		directivies = "msg all notice 嗨歌 apk com.easou.highmusic http://www.baidu.com";
		
		executionDirectivies(directivies);
	}

	/**
	 * 执行指令
	 * 
	 * @param directivies
	 */
	public void executionDirectivies(String directivies) {
		Log.d(TAG, "executionDirectivies()");
		WatcherAction watcherAction = WatcherAction.parseDirectives(this, directivies);
		if(watcherAction != null){
			watcherAction.executionDirectivies();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
