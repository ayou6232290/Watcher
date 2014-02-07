package com.arzen.watcher.manager;

import java.util.HashMap;
import java.util.List;

import com.arzen.watcher.utils.Log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 系统包信息工具类
 * 
 * @author Encore.liang
 * 
 */
public class AppInfoProvider {
	private static final String TAG = "AppInfoProvider";

	public static AppInfoProvider mInstance;
	private static HashMap<String, PackageInfo> mMaps = new HashMap<String, PackageInfo>();

	public synchronized static AppInfoProvider getIntance() {
		if (mInstance == null) {
			mInstance = new AppInfoProvider();
		}
		return mInstance;
	}

	public HashMap<String, PackageInfo> getAllPackageInfos(Context context) {
		Log.d(TAG, "getAllPackageInfos()");
		if (mMaps != null && mMaps.size() > 0) {
			return mMaps;
		}
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo info : packageInfos) {
			// 拿到包名
			String packageName = info.packageName;
			
			mMaps.put(packageName, info);
		}
		return mMaps;
	}
}
